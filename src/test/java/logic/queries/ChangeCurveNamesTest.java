package logic.queries;

import logic.Input;
import logic.exceptions.InvalidInputException;
import logic.exceptions.InvalidLoadsetIdException;
import logic.QueryGeneratorI;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.*;

public class ChangeCurveNamesTest {
    private QueryGeneratorI queryGenerator;
    private Input getQueryInput;
    private Input loadsetIdInput;
    private Input csvInput;

    @Before
    public void setInstance() {
        queryGenerator = new ChangeCurveNames();
    }

    @Before
    public void setInput() {
        String testInput = "ctr.long , LgT.LNG A , %ctr.long%\nctr.short , ShT.LNG , %ctr.short%";
        getQueryInput = new Input(testInput);
        getQueryInput.setLoadsetId("  36153  ");

        loadsetIdInput = new Input(testInput);
        csvInput = new Input(testInput);
        csvInput.setLoadsetId("36153");

    }

    @Test
    public void testGetQuery() throws Exception {
        String query = queryGenerator.getQuery(getQueryInput);
        ClassLoader classLoader = getClass().getClassLoader();
        File expectedOutputFile = new File(classLoader.getResource("changeCurveNameExpectedTestOutput.txt").getFile());
        String expectedQuery = new String(Files.readAllBytes(expectedOutputFile.toPath()));
        expectedQuery = expectedQuery.trim();
        assertEquals("test query should match expected query", expectedQuery, query);
    }

    @Test
    public void testValidateInputData() {
        queryGenerator.validateInputData(getQueryInput);
    }

    @Test(expected = InvalidLoadsetIdException.class)
    public void testValidateInputDataEmptyLoadsetId() throws Exception {
        loadsetIdInput.setLoadsetId("");
        queryGenerator.validateInputData(loadsetIdInput);
    }

    @Test(expected = InvalidLoadsetIdException.class)
    public void testValidateInputDataLoadsetIdWithSpace() throws Exception {
        loadsetIdInput.setLoadsetId("12345 24");
        queryGenerator.validateInputData(loadsetIdInput);
    }

    @Test(expected = InvalidLoadsetIdException.class)
    public void testValidateInputDataLoadsetIdWithLetters() throws Exception {
        loadsetIdInput.setLoadsetId("   123as45 ");
        queryGenerator.validateInputData(loadsetIdInput);
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateInputDataCsvTooShortRow() throws Exception {
        String testInput = "ctr.long , LgT.LNG , \nctr.short , ShT.LNG , %ctr.short%";
        csvInput.setCsv(testInput);
        queryGenerator.validateInputData(csvInput);
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateInputDataEmptyCsv() throws Exception {
        csvInput.setCsv("");
        queryGenerator.validateInputData(csvInput);
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateInputDataCsvTooLongRow() throws Exception {
        csvInput.setCsv("ctr.long , LgT.LNG , %ctr.long% , a\nctr.short , ShT.LNG , %ctr.short%");
        queryGenerator.validateInputData(csvInput);
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateInputDataCsvTooLongRow2() throws Exception {
        csvInput.setCsv("asd,asdasd,sadas,asd");
        queryGenerator.validateInputData(csvInput);
    }

}