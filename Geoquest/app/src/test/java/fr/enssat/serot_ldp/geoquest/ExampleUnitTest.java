package fr.enssat.serot_ldp.geoquest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test() throws Exception {
        JsonParser TestParser = new JsonParser();
        TestParser.JsonParser("/app/src/main/res/Test.json");
    }
}