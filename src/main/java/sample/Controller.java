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
import logic.MoveDataNoDuplicates;
import logic.UpdateInfoInApex;

import java.io.IOException;
import java.util.Iterator;

public class Controller {
    public TextField loadsetIdTextField;
    public TextArea outputTextArea;
    public TextArea inputTextArea;

    public void generateQuery(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        Parent parent = button.getParent();
        String anchorId = parent.getId();


        if ( anchorId.equals("anchorPaneChangeCurveNames") ){
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
        }else if (  anchorId.equals("anchorPaneMoveDataNoDuplicates") ){
            TextArea inputArea = getElementById(actionEvent, "inputTextArea");
            String input = inputArea.getText();
            String output = MoveDataNoDuplicates.getQuery(input);

            TextArea outputArea = getElementById(actionEvent, "outputTextArea");
            outputArea.setText(output);


        }else if (  anchorId.equals("anchorPaneUpdateInfoInApex") ){
            TextArea inputArea = getElementById(actionEvent, "inputTextArea");
            String input = inputArea.getText();
            String output = UpdateInfoInApex.getQuery(input);

            TextArea outputArea = getElementById(actionEvent, "outputTextArea");
            outputArea.setText(output);
        }
    }

    private boolean validateLoadsetId(String loadsetId) {
        return loadsetId.matches("\\d+");
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

    public TextArea getElementById(ActionEvent actionEvent, String searchedId) {
        Button button = (Button) actionEvent.getSource();
        Parent parent = button.getParent();
        ObservableList<Node> childrenUnmodifiable = parent.getChildrenUnmodifiable();
        Iterator<Node> iterator = childrenUnmodifiable.iterator();
        while ( iterator.hasNext() ){
            Node node = iterator.next();
            String id = node.getId();
            if ( searchedId.equals(id) ){
                return  (TextArea) node;
            }
        }
        return null;//not found
    }
}
