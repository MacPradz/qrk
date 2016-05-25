package utils;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdjustedCSVParserTest {

    @Test
    public void testGetCsv() throws Exception {
        CSVParser csv = AdjustedCSVParser.getCsv("1 , 2, 3 \n10,20  ,30");
        for ( CSVRecord strings : csv ) {
            System.out.println(strings);
        }
    }
}