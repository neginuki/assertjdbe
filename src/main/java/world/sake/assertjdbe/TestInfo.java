package world.sake.assertjdbe;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author neginuki
 */
public class TestInfo {

    private final Class<?> testClass;

    private final String testName;

    private final Path expectedDirectory;

    public TestInfo(Class<?> testClass, String testName) {
        this.testClass = testClass;
        this.testName = testName;
        this.expectedDirectory = resolveExpectedDirectoryPath(testClass);
    }

    protected static Path resolveExpectedDirectoryPath(Class<?> testClass) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(testClass.getName().replace('.', '/') + ".class");
        try {
            return Paths.get(resource.toURI()).getParent();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getTestClass() {
        return testClass;
    }

    public String getTestName() {
        return testName;
    }

    public Path getExpectedDirectory() {
        return expectedDirectory;
    }

    protected String getLoadFilePattern() {
        return String.format("%s_%s.*\\.xlsx", //
                getTestClass().getSimpleName(), //               
                getTestName()//
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TestInfo:") //
                .append("\n  TestClass: " + getTestClass().getName())
                .append("\n  TestName: " + getTestName())
                .append("\n  ExpectedDirectory: " + getExpectedDirectory())
                .append("\n  Load File Pattern: " + getLoadFilePattern());

        return sb.toString();
    }
}
