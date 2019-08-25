package world.sake.assertjdbe;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.assertj.db.type.Changes;

/**
* @author neginuki
*/
public class AssertJDBE {

    private final TestInfo testInfo;

    private List<ExpectedWorkbook> workbooks;

    private final List<DataSourceEntry> dataSources;

    public AssertJDBE(TestInfo testInfo, DataSource... dataSources) {
        this.testInfo = testInfo;

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

    public void assertDB(Runnable runnable) {
        assertDB(null, runnable);
    }

    public void assertDB(String checkpointName, Runnable runnable) {
        testInfo.setCheckpointName(checkpointName);
        workbooks = prepareWorkbooks();
    }

    protected void assertEquals(Changes changes, Path expectedXlsx) {
        // todo
    }

    protected List<ExpectedWorkbook> prepareWorkbooks() {
        return Arrays.asList(testInfo.getExpectedDirectory().toFile().listFiles((dir, name) -> {
            return name.matches(testInfo.getLoadFilePattern());
        })).stream().map(file -> {
            return loadExpectedWorkbook(file.toPath());
        }).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AssertDBEquals Settings");
        sb.append(testInfo);

        sb.append("\nDataSources:");
        dataSources.forEach(ds -> {
            sb.append("\n  " + ds);
        });

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