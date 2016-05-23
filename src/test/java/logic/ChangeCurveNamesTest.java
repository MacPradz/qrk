package logic;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class ChangeCurveNamesTest {

    @Test
    public void testGetQuery() throws Exception {
        String testInput = "ctr.long , LgT.LNG , %ctr.long%\nctr.short , ShT.LNG , %ctr.short%";
        String query = ChangeCurveNames .getQuery("12384", testInput);

        ClassLoader classLoader = getClass().getClassLoader();
        File expectedOutputFile = new File(classLoader.getResource("changeCurveNameExpectedTestOutput.txt").getFile());
        String expectedQuery = new String(Files.readAllBytes(expectedOutputFile.toPath()));
        expectedQuery=expectedQuery.trim();
        assertEquals("test query should match expected query", expectedQuery, query);
    }
}