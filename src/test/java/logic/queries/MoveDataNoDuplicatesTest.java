package logic.queries;

import logic.Input;
import logic.QueryGeneratorI;
import logic.exceptions.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MoveDataNoDuplicatesTest {
    private QueryGeneratorI queryGenerator;
    private static final String expected = "insert into curve_data (curve_id, forecast_date, value_date, local_forecast_date, local_value_date, value, changed_date)\n" +
            "select  103946249, forecast_date, value_date, local_forecast_date, local_value_date, value, sysdate --where to\n" +
            "from curve_data where curve_id = 106506606  --where from\n" +
            "and (forecast_date, value_date) not in (select  forecast_date, value_date from curve_data where curve_id = 103946249);\n\n" +
            "insert into curve_data (curve_id, forecast_date, value_date, local_forecast_date, local_value_date, value, changed_date)\n" +
            "select  2, forecast_date, value_date, local_forecast_date, local_value_date, value, sysdate --where to\n" +
            "from curve_data where curve_id = 1  --where from\n" +
            "and (forecast_date, value_date) not in (select  forecast_date, value_date from curve_data where curve_id = 2);\n\n" +
            "--updating info in apex\n" +
            "exec dw.pkg_curve_maintenance.update_curve_info(103946249);\n" +
            "exec dw.pkg_curve_maintenance.update_curve_info(2);";

    @Before
    public void setInstance() {
        queryGenerator = new MoveDataNoDuplicates();
    }

    @Test
    public void testGetQuery() throws Exception {
        String testInput = "106506606 , 103946249 \n1,2";
        Input input = new Input(testInput);
        String query = queryGenerator.getQuery(input);
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

    @Test(expected = InvalidInputException.class)
    public void testValidateInputDataCsvTooLongRow() throws Exception {
        Input input = new Input("123,234,\n 234,234,5445345");
        queryGenerator.validateInputData(input);
    }

    @Test(expected = InvalidInputException.class)
    public void testValidateInputDataCsvTooShortRow() throws Exception {
        Input input = new Input("123,234,\n 234,\n234,5445345");
        queryGenerator.validateInputData(input);
    }


}