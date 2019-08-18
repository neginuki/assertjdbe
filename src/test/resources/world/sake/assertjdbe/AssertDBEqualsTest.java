package world.sake.assertjdbe;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import world.sake.assertjdbe.mock.MockConnection;
import world.sake.assertjdbe.mock.MockDataSource;

public class AssertDBEqualsTest {

    @Rule
    public TestName testName = new TestName();

    private AssertDBEquals assertjdbe;

    @Before
    public void setUp() throws Exception {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(getClass().getName().replace('.', '/') + ".class");
        Path expectedDirectory = Paths.get(resource.toURI()).getParent();

        assertjdbe = new AssertDBEquals(getClass(), testName.getMethodName(), expectedDirectory, mockDataSource("test")) {
            @Override
            public void assertEquals(String checkpointName, Runnable runnable) {
                super.assertEquals(checkpointName, runnable);
                System.out.println(this);
            }

            @Override
            protected ExpectedWorkbook loadExpectedWorkbook(Path xlsx) {
                return new ExpectedWorkbook(xlsx) {
                    @Override
                    protected String getSettingsSheetName() {
                        return "#SETTINGS";
                    }
                };
            }
        };
    }

    @Test
    public void case1() {
        assertjdbe.assertEquals(() -> {});
    }

    @Test
    public void case2() {
        assertjdbe.assertEquals(() -> {});
    }

    private DataSource mockDataSource(String catalogName) {
        return new MockDataSource() {
            @Override
            public Connection getConnection() throws SQLException {
                return new MockConnection() {
                    @Override
                    public String getCatalog() throws SQLException {
                        return catalogName;
                    }
                };
            }
        };
    }
}
