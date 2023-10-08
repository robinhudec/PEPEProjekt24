package com.example.mrdkapropepeho;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class HelloController {
    public Stage stage;
    @FXML
    private Label welcomeText;

    @FXML
    private void handleOpenButton(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        System.out.println(selectedFile);
    }
    @FXML
    private void handleQuit(ActionEvent event){
        Platform.exit();
    }
}