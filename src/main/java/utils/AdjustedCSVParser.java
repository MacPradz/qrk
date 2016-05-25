package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;

/**
 * Created by uc198829 on 24/5/2016.
 */
public class AdjustedCSVParser {
    public static CSVParser getCsv(String input) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT
                .withIgnoreSurroundingSpaces();
        return CSVParser.parse(input, format);
    }
}
