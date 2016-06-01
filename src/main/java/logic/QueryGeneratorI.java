package logic;

import java.io.IOException;

/**
 * Created by uc198829 on 13/5/2016.
 */
public interface QueryGeneratorI {
    public String getQuery(Input input) throws IOException;
    public void validateInputData(Input input);
}
