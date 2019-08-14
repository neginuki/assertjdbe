package world.sake.assertjdbe;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
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

    private final ExpectedWorkbook workbook;

    public AssertDBEquals(Class<?> testClass, String testName, Path expectedDirectory, DataSource... dataSources) {
        this.testClass = testClass;
        this.testName = testName;
        this.expectedDirectory = expectedDirectory;
        this.dataSources = Arrays.asList(dataSources).stream().map(dataSource -> {
            return new DataSourceEntry(getDataSourceName(dataSource), dataSource);
        }).collect(Collectors.toList());

        workbook = createExpectedWorkbook();
    }

    protected String getDataSourceName(DataSource dataSource) {
        try {
            return dataSource.getConnection().getCatalog();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not resolve the connect identifier specified. DataSource: " + dataSource, e);
        }
    }

    public void assertEquals(Runnable execute) {
        execute.run();
    }

    public void assertEquals(String checkpointName, Runnable execute) {

    }

    protected ExpectedWorkbook createExpectedWorkbook() {
        return new ExpectedWorkbook();
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

        sb.append("\n" + workbook);

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
