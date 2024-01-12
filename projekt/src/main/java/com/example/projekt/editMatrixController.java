package com.example.projekt;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.jdom2.*;

public class editMatrixController implements Initializable {
    @FXML
    private GridPane spinnerPane;
    Spinner<Integer>[][] spinnerArray = new Spinner[9][9];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ///provede akce pro kazde policko v 9*9 gridu
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                ///Vytvori novy spinner
                Spinner<Integer> spinner = new Spinner<>();

                ///Nastavi value factory v range -100 do 100
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-100, 100);
                valueFactory.setValue(0);
                spinner.setValueFactory(valueFactory);

                ///prida spinner do grid pane
                spinnerPane.add(spinner, col, row);

                ///na spinner prida listener ktery odpovida na zmeny hodnoty a zavola handleValuechanged
                spinner.valueProperty().addListener(observable -> handleValueChanged());

                ///ulozi spinner do spinnerArray
                spinnerArray[col][row] = spinner;
            }
        }
        File path = new File(".\\projekt\\src\\main\\resources\\com\\example\\projekt\\temp.xml");
        try {
            handleLoadMatrix(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    ///zavola se vzdy kdyz se zmeni hodnota v kteremkoliv spinneru
    //zatim zadna funkcnost
    private void handleValueChanged() {

    }

    @FXML
    private CheckBox checkBox;

    ///zavola se kdyz se zmeni checkbox normalisation/coeffitient
    @FXML
    private void handleNormalisation() {
        if (checkBox.isSelected()) {
            System.out.println("Normalisation");
        } else {
            System.out.println("Coefittient");
        }
    }

    // ulozi hodnoty spinneru do souboru za pomoci filechooseru
    @FXML
    private void handleSaveMatrix() {  //Spusti se pri stisknuti save tlacitka v editMatrix okne, vysledkem je ulozeny soubor
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        ArrayToXML converter = new ArrayToXML();
        //Do promenne content da matrix v xml formatu prevedeny v ArrayToXML
        String content = converter.writeArrayToXml(spinnerArray).toString();  //do promenne content ulozi vraceny string ve formatu xml ze tridy ArrayToXml
        //Ukaze filechooser, do selected file ulozi cestu k vybranemu souboru i s jeho nazvem
        Window stage = spinnerPane.getScene().getWindow();
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            //moc nechapu na co exception, ale intellij bez nej rve, zapise do vybraneho souboru content a zavre ho
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                writer.write(content);
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Z ulozeneho xml souboru nacte hodnoty do spinneru filechooserem
    @FXML
    private void handleLoadMatrix() throws IOException, JDOMException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Window stage = spinnerPane.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            ArrayToXML converter = new ArrayToXML();
            int[][] content = converter.readFxmlToMatrix(selectedFile.getPath());
            converter.setSpinners(content, spinnerArray);
        }
    }

    //Z ulozeneho xml souboru nacte hodnoty do spinneru
    private void handleLoadMatrix(File path) throws IOException, JDOMException {
        ArrayToXML converter = new ArrayToXML();
        int[][] content = converter.readFxmlToMatrix(path.getPath());
        converter.setSpinners(content, spinnerArray);
        }

    //vsechny hodnoty spinneru zmeni na nulu volanim ArrayToXML.setspinners()
    @FXML
    private void handleClearMatrix() {
        int[][] content = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                content[col][row] = 0;
            }
        }
        ArrayToXML converter = new ArrayToXML();

        converter.setSpinners(content, spinnerArray);
    }

    @FXML
    private Button okButton;

    //Ulozi nastavenou matici do souboru temp.xml, aby se z něj daly dělat výpočty a taky aby se pri znovuotevrení okna
    //obnovil obsah matice
    @FXML
    private void handleOkButton() {
        File file = new File(".\\projekt\\src\\main\\resources\\com\\example\\projekt\\temp.xml");
        ArrayToXML converter = new ArrayToXML();
        String content = converter.writeArrayToXml(spinnerArray).toString();  //do promenne content ulozi vraceny string ve formatu xml ze tridy ArrayToXml
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int[][] values = converter.convertSpinnerArrayToValueArray(spinnerArray);
        HelloController.changeTextFieldColor(values);
        closeScene();

    }

    //uzavre okno editMatrix
    @FXML
    private void closeScene() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
//                textField.setStyle("-fx-background-color: rgb(" + (14*valueMatrix[row][col] +126) + ", 0, 0);");