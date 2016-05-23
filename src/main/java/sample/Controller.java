package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.ChangeCurveNames;
import logic.QueryGenerator;

import java.io.IOException;

public class Controller {
    public TextField loadsetIdTextField;
    public TextArea outputTextArea;
    public TextArea inputTextArea;
    public AnchorPane anchorPaneChangeCurveNames;
    public Button goToChangeCurveNamesBtn;
    public Button goToMenuBtn;

    public void generateQuery(ActionEvent actionEvent) throws IOException {
        Button source = (Button) actionEvent.getSource();
        Scene scene = source.getScene();

        Parent root = scene.getRoot();
        ObservableList<Node> childrenUnmodifiable = root.getChildrenUnmodifiable();
        for ( Node node : childrenUnmodifiable ) {
            System.out.println("Id: " + node.getId());
            if ( node.getId().equals("title") ){

            }
        }


        /*ObservableList<Node> children = anchorPaneChangeCurveNames.getChildren();
        for ( Node node : children ) {
            System.out.println("Id: " + node.getId());
            if (node instanceof TextField || node instanceof TextArea) {
                // clear
                ((TextField)node).setText("");
            }
        }
        //Class<QueryGenerator> qg = ChangeCurveNames.class.cast(QueryGenerator);


*/
        if (1>0) return;
        String loadsetIdString = loadsetIdTextField.getText().trim();
        if ( !validateLoadsetId(loadsetIdString) ) {
            outputTextArea.setText("loadset id mustn't contain anything except digits");
            return;
        }
        String input = inputTextArea.getText();
        System.out.println(input);
        System.out.println();
        boolean b = input.matches(".*?,.*?,.*");
        System.out.println(b+"sd");
        String output = ChangeCurveNames.getQuery(loadsetIdString, input);

        outputTextArea.setText(output);
    }

    public void call(QueryGenerator c){

    }

    private boolean validateLoadsetId(String loadsetId) {
        return loadsetId.matches("\\d+");
    }

    public void copyToClipboard(ActionEvent actionEvent) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(outputTextArea.getText());
        clipboard.setContent(content);
    }

    public void goToChangeCurveNames(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/changeCurveNames.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) goToChangeCurveNamesBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goToMoveDataBtn(ActionEvent actionEvent) {

    }

    public void goToMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) goToMenuBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
