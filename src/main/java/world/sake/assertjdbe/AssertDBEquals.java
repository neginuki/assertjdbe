package world.sake.assertjdbe;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

/**
* @author neginuki
*/
public class AssertDBEquals {

    private final Class<?> testClass;

    private final String testName;

    private final Path expectedDirectory;

    private final List<DataSourceEntry> dataSources;

    private List<ExpectedWorkbook> workbooks;

    private Optional<String> checkpointName = Optional.empty();

    public AssertDBEquals(Class<?> testClass, String testName, Path expectedDirectory, DataSource... dataSources) {
        this.testClass = testClass;
        this.testName = testName;
        this.expectedDirectory = expectedDirectory;
        this.dataSources = Arrays.asList(dataSources).stream().map(dataSource -> {
            return new DataSourceEntry(getDataSourceName(dataSource), dataSource);
        }).collect(Collectors.toList());
    }

    protected String getDataSourceName(DataSource dataSource) {
        try {
            return dataSource.getConnection().getCatalog();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not resolve the connect identifier specified. DataSource: " + dataSource, e);
        }
    }

    protected ExpectedWorkbook loadExpectedWorkbook(Path xlsx) {
        return new ExpectedWorkbook(xlsx);
    }

    public void assertEquals(Runnable runnable) {
        assertEquals(null, runnable);
    }

    public void assertEquals(String checkpointName, Runnable runnable) {
        this.checkpointName = Optional.ofNullable(checkpointName);
        workbooks = prepareWorkbooks();
    }

    protected List<ExpectedWorkbook> prepareWorkbooks() {
        return Arrays.asList(expectedDirectory.toFile().listFiles((dir, name) -> {
            return name.matches(getLoadFilePattern());
        })).stream().map(file -> {
            return loadExpectedWorkbook(file.toPath());
        }).collect(Collectors.toList());
    }

    protected String getLoadFilePattern() {
        return String.format("%s%s_%s.*\\.xlsx", //
                testClass.getSimpleName(), //
                checkpointName.map(name -> "_" + name).orElse(""), //
                testName//
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AssertDBEquals Settings");
        sb.append("\nTestClass: " + testClass.getName());
        sb.append("\nTestName: " + testName);
        sb.append("\nExpectedDirectory: " + expectedDirectory);

        sb.append("\nDataSources:");
        dataSources.forEach(ds -> {
            sb.append("\n  " + ds);
        });

        sb.append("\nLoad File Pattern: " + getLoadFilePattern());

        sb.append("\nExpected Workbooks:");
        Optional.of(workbooks).ifPresent(books -> {
            books.forEach(book -> {
                sb.append("\n  name: " + book.getDataSourceName() + ", path: " + book.getExpectedXlsx());
                sb.append(book);
            });
        });

        return sb.toString();
    }

    private class DataSourceEntry {
        final String name;
        final DataSource dataSource;

        public DataSourceEntry(String name, DataSource dataSource) {
            this.name = name;
            this.dataSource = dataSource;
        }

        @Override
        public String toString() {
            return "name: " + name + ", dataSource: " + dataSource;
        }
    }
}