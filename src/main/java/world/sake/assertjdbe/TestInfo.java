package world.sake.assertjdbe;

import java.nio.file.Path;

/**
 * @author neginuki
 */
public class TestInfo {

    private final Class<?> testClass;

    private final String testName;

    private final Path expectedDirectory;

    public TestInfo(Class<?> testClass, String testName, Path expectedDirectory) {
        this.testClass = testClass;
        this.testName = testName;
        this.expectedDirectory = expectedDirectory;
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
