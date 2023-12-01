package com.example.mrdkapropepeho;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.awt.color.*;
import java.lang.Object;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.color;


public class HelloController implements Initializable {
    public Stage stage;
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


    @FXML
    private void handleEditMatrix(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("matrixEdit.fxml"));
        Scene sceneMatrix = new Scene(fxmlLoader.load());
        Stage editMatrix = new Stage();

        editMatrix.setMinWidth(300);
        editMatrix.setMaxWidth(300);
        editMatrix.setMinHeight(200);
        editMatrix.setMaxHeight(200);
        editMatrix.setScene(sceneMatrix);
        editMatrix.show();
    }
    @FXML
    private TextField cell00;
    private void setCellColor(TextField textField, int value) {
        Color color = Color.rgb(value, value, value); // Assuming grayscale, modify as needed
        String style = String.format("-fx-background-color: rgba(%d, %d, %d, 1);", (int)(color.getRed() * 255), (int)(color.getGreen() * 255), (int)(color.getBlue() * 255));
        textField.setStyle(style);
        textField.setEditable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            setCellColor(cell00, 255);

    }



    public void generateImage() {
        Image generatedImg = makeColoredImage();
        //Image OGimage = imageView.getImage();
        //Image img = convertToJavaFXImage(generatedImg);
        System.out.println("Image generated");
        imageView.setImage(generatedImg);
    }

    public Image makeColoredImage(){
        //BufferedImage bImage = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);

        WritableImage writableImage = new WritableImage(600, 600);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int x = 0; x < writableImage.getWidth(); x++){
            for (int y = 0; y < writableImage.getHeight(); y++){
                //bImage.setRGB(x, y, (new java.awt.Color(x%255, y%255, (x+y)%255)).getRGB());

                pixelWriter.setArgb(x, y, (new java.awt.Color(x%255, y%255, (x+y)%255)).getRGB());
            }
        }
        return writableImage;
    }

}
