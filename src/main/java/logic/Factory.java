package logic;

import logic.queries.ChangeCurveNames;
import logic.queries.MoveDataNoDuplicates;
import logic.queries.UpdateInfoInApex;

/**
 * Created by uc198829 on 31/5/2016.
 */
public class Factory {

    public static QueryGenerator createQueryGenerator(String anchorPaneName){
        if ( anchorPaneName.equals("anchorPaneChangeCurveNames") ){
            return createChangeCurveNameQuery();
        }else if (  anchorPaneName.equals("anchorPaneMoveDataNoDuplicates") ){
            return createMoveDataNoDuplicates();
        }else if (  anchorPaneName.equals("anchorPaneUpdateInfoInApex") ){
            return createUpdateInfoInApex();
        }
        return null;
    }

    private static QueryGenerator createChangeCurveNameQuery(){
        return setupQuery(ChangeCurveNames.class);
    }

    private static QueryGenerator createMoveDataNoDuplicates(){
        return setupQuery(MoveDataNoDuplicates.class);
    }

    private static QueryGenerator createUpdateInfoInApex(){
        return setupQuery(UpdateInfoInApex.class);
    }

    private static <T extends QueryGenerator> T setupQuery(Class<T> queryClass) {
        try {
            T query = queryClass.newInstance();
            return query;
        } catch ( InstantiationException e ) {
            e.printStackTrace();
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
