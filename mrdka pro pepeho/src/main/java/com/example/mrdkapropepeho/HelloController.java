package com.example.mrdkapropepeho;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Stage stage;
    @FXML
    private ImageView imageView;

    //za pouziti filechooseru nacte vybrany obrazek do imageview a vyipse text do outputTextField
    @FXML
    private void handleOpenButton() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
        assert selectedFile != null;
        outputText += "\nLoaded image " + selectedFile.getName();
        outputTextField.setText(outputText);
    }

    @FXML
    private void handleQuit() {
        HelloApplication.close();
    }

    @FXML
    private void handleAbout() {
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
    private void handleEditMatrix() throws IOException {
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
    private Button restoreButton;
    public ToggleGroup tgImageState;
    public RadioButton modifiedRadio;
    public RadioButton originalRadio;

    @FXML
    private void handleGenerateButton() {
        restoreButton.setDisable(false);
        modifiedRadio.setDisable(false);
        originalRadio.setDisable(false);
        outputText += "\nNew image generated";
        outputTextField.setText(outputText);
    }

    @FXML
    private void handleRestoreButton() {
        outputText += "\nOriginal image restored";
        outputTextField.setText(outputText);
    }

    @FXML
    private void handleApplyMatrixButton(){
        outputText += "\nX filter applied";
        outputTextField.setText(outputText);
    }

    @FXML
    private void handleOriginalImageButton() {
    }

    @FXML
    private void handleModifiedImageButton(){
        System.out.println("Modified");
    }


    @FXML
    public GridPane matrixVisualGrid;
    public static TextField[][] textFieldVisualisation = new TextField[9][9];
    //Vytvori tu prvni, prazdnou vizualizaci matice, spousti se v initialize
    public void createMatrixToGridPane() throws IOException, JDOMException {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField textField = new TextField();
                textFieldVisualisation[row][col] = textField;
                textField.setStyle("-fx-background-color: rgb( 126, 0, 0);");
                matrixVisualGrid.add(textField, col, row);
            }
        }
    }

    //meni barvy ve vizualizaci podle zadane matice hodnot, static aby se dala spustit v editmatrix controller, spousti
    //se v editMatrixController pri stisku ok
    public static void changeTextFieldColor(int[][] valueMatrix) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFieldVisualisation[row][col].setStyle("-fx-background-color: rgb(" + (14*valueMatrix[col][row] +126) + ", 0, 0);");
            }
        }
    }

    @FXML
    private TextArea outputTextField;
    String outputText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            modifiedRadio.setToggleGroup(tgImageState);
            originalRadio.setToggleGroup(tgImageState);
            tgImageState.selectToggle(originalRadio);
        try {
            this.createMatrixToGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }

        outputText = "";
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
