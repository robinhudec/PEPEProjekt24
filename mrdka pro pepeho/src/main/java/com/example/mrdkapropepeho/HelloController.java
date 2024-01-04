package com.example.mrdkapropepeho;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jdom2.JDOMException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
        Platform.exit(); // Toto okamžitě ukončí aplikaci
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
        handleGenerateButton();
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

        for (int x = 0; x < writableImage.getWidth(); x++) {
            for (int y = 0; y < writableImage.getHeight(); y++) {
                //bImage.setRGB(x, y, (new java.awt.Color(x%255, y%255, (x+y)%255)).getRGB());

                pixelWriter.setArgb(x, y, (new java.awt.Color(x % 255, y % 255, (x + y) % 255)).getRGB());
            }
        }
        return writableImage;
    }

    @FXML
    private void handleSaveButton() {
        Image imageToSave = imageView.getImage();
        if (imageToSave != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    BufferedImage bImage = new BufferedImage(
                            (int) imageToSave.getWidth(),
                            (int) imageToSave.getHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                    for (int x = 0; x < bImage.getWidth(); x++) {
                        for (int y = 0; y < bImage.getHeight(); y++) {
                            bImage.setRGB(x, y, imageToSave.getPixelReader().getArgb(x, y));
                        }
                    }
                    ImageIO.write(bImage, "png", file);
                    outputText += "\nImage saved as: " + file.getName();
                    outputTextField.setText(outputText);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            outputText += "\nNo image to save.";
            outputTextField.setText(outputText);
        }
    }

    @FXML
    private void handleNegative() {
        Image image = imageView.getImage();
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage negativeImage = new WritableImage(width, height);
        PixelWriter pixelWriter = negativeImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                javafx.scene.paint.Color color = pixelReader.getColor(x, y);
                javafx.scene.paint.Color negativeColor = color.invert();
                pixelWriter.setColor(x, y, negativeColor);
            }
        }
        imageView.setImage(negativeImage);
        outputText += "\nImage negated";
        outputTextField.setText(outputText);
    }

    @FXML
    public void handleCloseButton() {
        imageView.setImage(null);
    }

    @FXML
    private void handlePixelation() {
        int pixelSize = 10;
        Image image = imageView.getImage();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        PixelReader pixelReader = image.getPixelReader();
        WritableImage pixelatedImage = new WritableImage(width, height);
        PixelWriter pixelWriter = pixelatedImage.getPixelWriter();
        for (int y = 0; y < height; y += pixelSize) {
            for (int x = 0; x < width; x += pixelSize) {
                int argb = getAverageColor(pixelReader, x, y, pixelSize, width, height);
                for (int i = 0; i < pixelSize; i++) {
                    for (int j = 0; j < pixelSize; j++) {
                        int newX = Math.min(x + i, width - 1);
                        int newY = Math.min(y + j, height - 1);
                        pixelWriter.setArgb(newX, newY, argb);
                    }
                }
            }
        }
        imageView.setImage(pixelatedImage);
        outputText += "\nImage pixelated";
        outputTextField.setText(outputText);
    }

    private int getAverageColor(PixelReader pixelReader, int x, int y, int pixelSize, int width, int height) {
        int red = 0, green = 0, blue = 0, alpha = 0;
        int count = 0;
        for (int i = 0; i < pixelSize && x + i < width; i++) {
            for (int j = 0; j < pixelSize && y + j < height; j++) {
                Color color = pixelReader.getColor(x + i, y + j);
                red += (int) (color.getRed() * 255);
                green += (int) (color.getGreen() * 255);
                blue += (int) (color.getBlue() * 255);
                alpha += (int) (color.getOpacity() * 255);
                count++;
            }
        }
        red /= count;
        green /= count;
        blue /= count;
        alpha /= count;
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    @FXML
    public void handleBlackAndWhite() {
        Image image = imageView.getImage();
        PixelReader pixelReader = image.getPixelReader();
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage blackAndWhiteImage = new WritableImage(width, height);
        PixelWriter pixelWriter = blackAndWhiteImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                javafx.scene.paint.Color color = pixelReader.getColor(x, y);
                double luminosity = 0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue();
                javafx.scene.paint.Color grayColor = javafx.scene.paint.Color.rgb((int) (luminosity * 255), (int) (luminosity * 255), (int) (luminosity * 255));
                pixelWriter.setColor(x, y, grayColor);
            }
        }
        imageView.setImage(blackAndWhiteImage);
        outputText += "\nImage converted to black and white";
        outputTextField.setText(outputText);
    }
}

