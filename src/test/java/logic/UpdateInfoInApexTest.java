package logic;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class UpdateInfoInApexTest {

    @Test
    public void testGetQueryOneLine() throws Exception {
        String testInput = "123,234,345";
        String query = UpdateInfoInApex.getQuery(testInput);
        System.out.println(query);
    }

    @Test
    public void testGetQueryMultipleLines() throws Exception {
        String testInput = "123,234,345\n456,567\n678";
        String query = UpdateInfoInApex.getQuery(testInput);
        System.out.println(query);
    }

    @Test
    public void testGetQueryList() throws Exception {
        List<String> testInput = new LinkedList<>();
        testInput.add("123");
        testInput.add("234");
        testInput.add("345");
        String query = UpdateInfoInApex.getQuery(testInput);
        System.out.println(query);
    }

    @Test
    public void testValidateInputData() throws Exception {
        LinkedList<String> list = new LinkedList<String>() {
            {
                add(" 1 , 2 ,3,4, ");
                add(" 1 , 2 ,3,4, \n2, 3,5,6 ");
                add("1,2,wer,werwr,3");
                add("1,2,wer,werwr,");
                add(" 1,2,wer,werwr;");
                add("1 , 2 , wer,werwr;");
            }
        };

        for ( String s : list ) {
            System.out.println();
            if ( UpdateInfoInApex.validateInputData(s) ){

            }else {
            System.out.println("no success "+s);}
        }
    }
}