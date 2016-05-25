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
import javafx.stage.Stage;
import logic.ChangeCurveNames;
import logic.Input;
import logic.MoveDataNoDuplicates;
import logic.UpdateInfoInApex;

import java.io.IOException;
import java.util.Iterator;

public class Controller {

    public void generateQuery(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        Parent parent = button.getParent();
        String anchorId = parent.getId();

        if ( anchorId.equals("anchorPaneChangeCurveNames") ){
            TextField loadsetIdText = getTextField(actionEvent, "loadsetIdTextField");

            String loadsetIdString = loadsetIdText.getText().trim();
            TextArea outputArea = getTextArea(actionEvent, "outputTextArea");

            TextArea inputArea = getTextArea(actionEvent, "inputTextArea");
            String inputCsv = inputArea.getText();
            Input input = new Input(inputCsv);
            input.setLoadsetId(loadsetIdString);
            String output = ChangeCurveNames.getQuery(input);

            outputArea.setText(output);


        }else if (  anchorId.equals("anchorPaneMoveDataNoDuplicates") ){
            TextArea inputArea = getTextArea(actionEvent, "inputTextArea");
            String inputCsv = inputArea.getText();
            Input input = new Input(inputCsv);
            String output = MoveDataNoDuplicates.getQuery(input);

            TextArea outputArea = getTextArea(actionEvent, "outputTextArea");
            outputArea.setText(output);


        }else if (  anchorId.equals("anchorPaneUpdateInfoInApex") ){
            TextArea inputArea = getTextArea(actionEvent, "inputTextArea");
            String inputCsv = inputArea.getText();
            Input input = new Input(inputCsv);
            String output = UpdateInfoInApex.getQuery(input);

            TextArea outputArea = getTextArea(actionEvent, "outputTextArea");
            outputArea.setText(output);
        }
    }

    public void copyToClipboard(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        Parent parent = button.getParent();
        ObservableList<Node> childrenUnmodifiable = parent.getChildrenUnmodifiable();
        Iterator<Node> iterator = childrenUnmodifiable.iterator();
        while ( iterator.hasNext() ){
            Node node = iterator.next();
            String id = node.getId();
            if ( "outputTextArea".equals(id) ){
                TextArea area = (TextArea) node;
                String output = area.getText();
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(output);
                clipboard.setContent(content);
                return;
            }
        }
    }

    public void goToChangeCurveNames(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/changeCurveNames.fxml"));
        switchSceneToRoot(actionEvent, root);
    }

    public void goToMoveDataBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/moveDataNoDuplicates.fxml"));
        switchSceneToRoot(actionEvent, root);
    }

    public void goToUpdateInfoInApex(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/updateInfoInApex.fxml"));
        switchSceneToRoot(actionEvent, root);
    }

    public void goToMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        switchSceneToRoot(actionEvent, root);
    }

    private void switchSceneToRoot(ActionEvent actionEvent, Parent root) {
        Scene scene = new Scene(root);
        Button button = (Button) actionEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private Node getNodeById(ActionEvent actionEvent, String searchedId) {
        Button button = (Button) actionEvent.getSource();
        Parent parent = button.getParent();
        ObservableList<Node> childrenUnmodifiable = parent.getChildrenUnmodifiable();
        Iterator<Node> iterator = childrenUnmodifiable.iterator();
        while ( iterator.hasNext() ){
            Node node = iterator.next();
            String id = node.getId();
            if ( searchedId.equals(id) ){
                return node;
            }
        }
        return null;//not found
    }

    private TextArea getTextArea(ActionEvent actionEvent, String searchedId){
        Node node = getNodeById(actionEvent, searchedId);
        if ( node instanceof TextArea ) return  (TextArea) node;
        throw new RuntimeException("not instance of textArea");
    }

    private TextField getTextField(ActionEvent actionEvent, String searchedId){
        Node node = getNodeById(actionEvent, searchedId);
        if ( node instanceof TextField ) return  (TextField) node;
        throw new RuntimeException("not instance of textField");
    }

}
