package com.example.mrdkapropepeho;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloController {
    public Stage stage;
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView imageView;

    @FXML
    private void handleOpenButton(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        System.out.println(selectedFile);
        Image image = new Image(selectedFile.toURI().toString());
        imageView.setImage(image);
    }
    @FXML
    private void handleQuit(ActionEvent event){
        Platform.exit();
    }

    @FXML
    private void handleAbout(ActionEvent event){
        Stage aboutStage = new Stage();
        Label textAboutUs = new Label("nase info nevim presne");
        StackPane Layout = new StackPane(textAboutUs);
        Scene newScene = new Scene(Layout);
        aboutStage.setMinWidth(300);
        aboutStage.setMaxWidth(300);
        aboutStage.setMinHeight(200);
        aboutStage.setMaxHeight(200);

        aboutStage.setScene(newScene);
        aboutStage.show();
    }

}