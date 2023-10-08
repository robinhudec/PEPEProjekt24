package com.example.mrdkapropepeho;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

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

    @FXML
    private void handleAbout(ActionEvent event){
        Stage newStage = new Stage();
        Label aboutUs = new Label("nase info nevim presne");
        StackPane newLayout = new StackPane(aboutUs);
        Scene newScene = new Scene(newLayout, 300, 200);
        newStage.setScene(newScene);
        newStage.show();
    }
}