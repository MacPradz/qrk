package logic.queries;

import static org.junit.Assert.assertEquals;

import logic.Input;
import logic.QueryGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class UpdateInfoInApexTest {
    private QueryGenerator queryGenerator;
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
        testInput.add("123");
        testInput.add("456");
        testInput.add("567");
        testInput.add("678");
        String query = UpdateInfoInApex.getQuery(testInput);
        assertEquals(expected, query);
    }

    @Test
    public void testValidateInputData() throws Exception {
        LinkedList<String> list = new LinkedList<String>() {
            {
                add(" 1 , 2 ,3,4, ");
                add(" 1 , 2 ,3,4, \n2, 3,5,6 ");
                add("1,2,wer,werwr,3");
                add("1,2,wer,werwr,");
                add(" 1,\n1,2,wer,werwr;");
                add("1 , 2 , wer,werwr;");
            }
        };

        for ( String s : list ) {
            System.out.println();
            if ( UpdateInfoInApex.validateInputData(s) ) {

            } else {
                System.out.println("no success " + s);
            }
        }
    }
}