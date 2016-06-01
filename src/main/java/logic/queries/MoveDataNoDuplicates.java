package logic.queries;

import logic.Input;
import logic.exceptions.InvalidInputException;
import logic.QueryGenerator;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import utils.AdjustedCSVParser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by uc198829 on 23/5/2016.
 */
public class MoveDataNoDuplicates implements QueryGenerator{
    private static final String QUERY =
            "insert into curve_data (curve_id, forecast_date, value_date, local_forecast_date, local_value_date, value, changed_date)\n" +
                    "select  %CURVE_TO%, forecast_date, value_date, local_forecast_date, local_value_date, value, sysdate --where to\n" +
                    "from curve_data where curve_id = %CURVE_FROM%  --where from\n" +
                    "and (forecast_date, value_date) not in (select  forecast_date, value_date from curve_data where curve_id = %CURVE_TO%);\n\n";

    public String getQuery(Input input) throws IOException {
        String csvString = input.getCsv();
        StringBuilder queryBuilder = new StringBuilder("");
        CSVParser csv = AdjustedCSVParser.getCsv(csvString);
        List<CSVRecord> records = csv.getRecords();
        List<String> curvesToBeUpdated = new LinkedList<>();
        for ( CSVRecord record : records ) {
            String curveFrom = record.get(0);
            String curveTo = record.get(1);
            curvesToBeUpdated.add(curveTo);
            queryBuilder.append(QUERY.replaceAll("%CURVE_TO%", curveTo).replaceAll("%CURVE_FROM%", curveFrom));
        }
        queryBuilder.append("--updating info in apex\n");
        queryBuilder.append(UpdateInfoInApex.getQuery(curvesToBeUpdated));
        return queryBuilder.toString().trim();
    }

    @Override
    public void validateInputData(Input input) {
        if ( !validateCsv(input.getCsv()) ) {
            throw new InvalidInputException("each row of inserted input should contain exactly two elements separated by comma");
        }
    }

    private static boolean validateCsv(String input) {
        if ( input==null ) return false;
        String regex = "\\A( *\\d+ *, *\\d+,? *,? *($|\\n))+\\z";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
