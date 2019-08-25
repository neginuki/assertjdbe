package world.sake.assertjdbe;

import java.nio.file.Path;
import java.util.Optional;

/**
 * @author neginuki
 */
public class TestInfo {

    private final Class<?> testClass;

    private final String testName;

    private final Path expectedDirectory;

    private Optional<String> checkpointName = Optional.empty();

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

    public void setCheckpointName(String name) {
        checkpointName = Optional.ofNullable(name);
    }

    public Optional<String> getCheckpointName() {
        return checkpointName;
    }

    protected String getLoadFilePattern() {
        return String.format("%s%s_%s.*\\.xlsx", //
                getTestClass().getSimpleName(), //
                getCheckpointName().map(name -> "_" + name).orElse(""), //
                getTestName()//
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TestInfo:") //
                .append("\nTestClass: " + getTestClass().getName())
                .append("\nTestName: " + getTestName())
                .append("\nExpectedDirectory: " + getExpectedDirectory())
                .append("\nCheckpointName: " + getCheckpointName().orElse(""))
                .append("\nLoad File Pattern: " + getLoadFilePattern());

        return sb.toString();
    }
}
