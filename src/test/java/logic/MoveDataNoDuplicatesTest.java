package logic;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class MoveDataNoDuplicatesTest {

    @Test
    public void testGetQuery() throws Exception {
        String testInput = "106506606,103946249\n12,15";

        String query = MoveDataNoDuplicates.getQuery(testInput);
        System.out.println(query);

//        ClassLoader classLoader = getClass().getClassLoader();
//        File expectedOutputFile = new File(classLoader.getResource("changeCurveNameExpectedTestOutput.txt").getFile());
//        String expectedQuery = new String(Files.readAllBytes(expectedOutputFile.toPath()));
//        expectedQuery=expectedQuery.trim();
//        assertEquals("test query should match expected query", expectedQuery, query);
    }
}