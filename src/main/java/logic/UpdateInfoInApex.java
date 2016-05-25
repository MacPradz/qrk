package logic;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import utils.AdjustedCSVParser;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by uc198829 on 24/5/2016.
 */
public class UpdateInfoInApex{
    private static final String QUERY = "exec dw.pkg_curve_maintenance.update_curve_info(%CURVE_TO_UPDATE%);\n";

    public static String getQuery(Input input) throws IOException {
        String csvString = input.getCsv();
        StringBuilder queryBuilder = new StringBuilder("");
        CSVParser csv = AdjustedCSVParser.getCsv(csvString);
        List<CSVRecord> records = csv.getRecords();
        for ( CSVRecord record : records ) {
            for ( String curveToBeUpdated : record ) {
                queryBuilder.append(QUERY.replaceAll("%CURVE_TO_UPDATE%", curveToBeUpdated));
            }
        }
        return queryBuilder.toString();
    }

    public static String getQuery(List<String> list) throws IOException {
        StringBuilder queryBuilder = new StringBuilder("");
        for ( String curveToBeUpdated : list ) {
            queryBuilder.append(QUERY.replaceAll("%CURVE_TO_UPDATE%", curveToBeUpdated));
        }
        return queryBuilder.toString();
    }

    public static boolean validateInputData(String input) {
        String regex = "^(( *\\d+( *, *\\d+ *)*,? *($|\\n))+)";
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
