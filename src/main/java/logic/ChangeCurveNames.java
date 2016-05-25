package logic;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import utils.AdjustedCSVParser;

import java.io.IOException;
import java.util.List;

/**
 * Created by uc198829 on 10/5/2016.
 */
public class ChangeCurveNames implements QueryGenerator{
    private static final String DW_CURVE_UPDATE = "UPDATE dw.curve SET name = REGEXP_REPLACE(name, '%s', '%s') WHERE name LIKE '%s' AND id = oldRow.curve_id;";
    private static final String LOAD_SET2_CURVE_UPDATE = "UPDATE load_set2_curve SET external_id = REGEXP_REPLACE(external_id, '%s', '%s') WHERE external_id LIKE '%s' AND curve_id = oldRow.curve_id;";
    private static final String DW_CURVE_REMOVE = "DELETE dw.curve WHERE name LIKE '%s';";
    private static final String LOAD_SET2_CURVE_REMOVE = "DELETE load_set2_curve WHERE external_id LIKE '%s';";

    private static final String QUERY_START = "DECLARE\n" +
            "    v_code  NUMBER;\n" +
            "    v_errm  VARCHAR2(255);\n" +
            "BEGIN\n" +
            "FOR oldRow IN ( SELECT curve_id FROM load_set2_curve WHERE load_set_id = %LOADSET_ID%) LOOP\n" +
            "BEGIN\n" +
            "    DBMS_OUTPUT.PUT_LINE ( '---' ||oldRow.curve_id);\n" +
            " -- DBMS_OUTPUT.PUT_LINE('Renaming curve for id=' ||oldRow||);\n" +
            " \n";

    private static final String QUERY_END = "\nEXCEPTION  WHEN OTHERS  THEN\n" +
            "    v_code := SQLCODE;\n" +
            "    v_errm := SUBSTR(SQLERRM, 1, 250);\n" +
            "    DBMS_OUTPUT.PUT_LINE (v_code || ' ' || v_errm);\n" +
            "END;\n" +
            "END LOOP;\n" +
            "end;";
    private static final int CHANGE_FROM = 0;
    private static final int CHANGE_TO = 1;
    private static final int WHERE_CURVE_LIKE = 2;

    public static String getQuery(String loadsetId, String input) throws IOException {
        StringBuilder queryBuilder = new StringBuilder(QUERY_START.replace("%LOADSET_ID%", loadsetId));
        CSVParser csv = AdjustedCSVParser.getCsv(input);
        List<CSVRecord> records = csv.getRecords();
        for ( CSVRecord record : records ) {
            appendUpdateStatement(queryBuilder, record.get(CHANGE_FROM), record.get(CHANGE_TO), record.get(WHERE_CURVE_LIKE));
            appendRemoveStatement(queryBuilder, record.get(WHERE_CURVE_LIKE));
        }
        return queryBuilder.append(QUERY_END).toString();
    }

    private static void appendRemoveStatement(StringBuilder queryBuilder, String whereLike) {
        queryBuilder.append("--REMOVE " + " where curve like: " + whereLike + "'\n");
        queryBuilder.append(String.format(DW_CURVE_REMOVE, whereLike) + "\n");
        queryBuilder.append(String.format(LOAD_SET2_CURVE_REMOVE, whereLike) + "\n\n");
    }

    private static void appendUpdateStatement(StringBuilder queryBuilder, String fromString, String toString, String whereLike) {
        queryBuilder.append("--UPDATE from: " + fromString + " to: " + toString + " where curve like: " + whereLike + "'\n");
        queryBuilder.append(String.format(DW_CURVE_UPDATE, fromString, toString, whereLike) + "\n");
        queryBuilder.append(String.format(LOAD_SET2_CURVE_UPDATE, fromString, toString, whereLike) + "\n");
    }

    @Override
    public String getQuery() {
        return null;
    }

    @Override
    public boolean validateInputData() {
        return false;
    }
}
