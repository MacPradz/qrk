package logic.queries;

import static org.junit.Assert.assertEquals;

import logic.Input;
import logic.QueryGeneratorI;
import logic.exceptions.InvalidInputException;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class UpdateInfoInApexTest {
    private QueryGeneratorI queryGenerator;
    private static final String expected = "exec dw.pkg_curve_maintenance.update_curve_info(123);\n" +
            "exec dw.pkg_curve_maintenance.update_curve_info(456);\n" +
            "exec dw.pkg_curve_maintenance.update_curve_info(567);\n" +
            "exec dw.pkg_curve_maintenance.update_curve_info(678);";

    @Before
    public void setInstance() {
        queryGenerator = new UpdateInfoInApex();
    }

    @Test
    public void testGetQueryOneLine() throws Exception {
        String testInput = "123,456,567,678,";
        Input input = new Input(testInput);
        String query = queryGenerator.getQuery(input);
        assertEquals(expected, query);
    }

    @Test
    public void testGetQueryMultipleLines() throws Exception {
        String testInput = "123\n456,567\n678,";
        Input input = new Input(testInput);
        String query = queryGenerator.getQuery(input);
        assertEquals(expected, query);
    }

    @Test
    public void testGetQueryList() throws Exception {
        List<String> testInput = new LinkedList<>();
        testInput.add(" 123");
        testInput.add("456 ");
        testInput.add(" 567 ");
        testInput.add("678");
        String query = UpdateInfoInApex.getQuery(testInput);
        assertEquals(expected, query);
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateInputDataCsvEmpty() throws Exception {
        Input input = new Input("");
        queryGenerator.validateInputData(input);
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateInputDataCsvWithLetters() throws Exception {
        Input input = new Input("123,234a");
        queryGenerator.validateInputData(input);
    }
}