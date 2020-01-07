package world.sake.assertjdbe;

import java.util.function.Consumer;

public class Assertions {

    private TestInfo testInfo;

    public static DatabaseChangesAssert assertThat(Runnable databaseChanges) {
        databaseChanges.run();

        return new DatabaseChangesAssert();
    }

    public void settings(Consumer<TestInfo> testInfo) {

    }
}
