package logic;

/**
 * Created by uc198829 on 25/5/2016.
 */
public class Input {
    private String loadsetId = null;
    private String csv = null;

    public Input(String csv) {
        this.csv = csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }

    public void setLoadsetId(String loadsetId) {
        this.loadsetId = loadsetId;
    }

    public String getLoadsetId() {
        return loadsetId.trim();
    }

    public String getCsv() {
        return csv.trim();
    }
}
