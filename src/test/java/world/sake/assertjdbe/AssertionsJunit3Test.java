package world.sake.assertjdbe;

import org.junit.Test;

public class AssertionsJunit3Test extends AbstractAssertionsJunit3Test {

    @Test
    public void test_case1() {
        Assertions.assertThat(() -> {
            // database update
        }).isEquals();
    }
}
