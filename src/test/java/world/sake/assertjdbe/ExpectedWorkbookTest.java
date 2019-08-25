package world.sake.assertjdbe;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class ExpectedWorkbookTest {

    @Rule
    public TestName testName = new TestName();

    @Test
    public void test() throws Exception {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(getClass().getName().replace('.', '/') + ".class");
        Path xlsx = Paths.get(resource.toURI()).getParent().resolve(getClass().getSimpleName() + "_" + testName.getMethodName() + ".xlsx");

        ExpectedWorkbook actual = new ExpectedWorkbook(xlsx);

        System.out.println(actual);
    }

}
