package world.sake.assertjdbe;

import java.util.function.Consumer;

public class Assertions {
    
    private TestInfo testInfo;
    

    public static void assertThat(Runnable databaseChanges) {
        databaseChanges.run();        
    }
    
    public void settings(Consumer<TestInfo> testInfo) {

    }
}
