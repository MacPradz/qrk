package logic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputTest {

    private static final String EXPECTED_LOADSET_ID = "123";

    @Test
    public void testSetLoadsetId() throws Exception {
        Input input = new Input("test,csv");
        input.setLoadsetId(EXPECTED_LOADSET_ID);
        String actual = input.getLoadsetId();
        assertEquals(EXPECTED_LOADSET_ID,actual);
    }
}