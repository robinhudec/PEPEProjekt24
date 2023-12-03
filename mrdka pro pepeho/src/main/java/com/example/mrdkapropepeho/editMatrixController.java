package com.example.mrdkapropepeho;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

import java.nio.file.Files;
import java.nio.file.Path;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

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
                spinner.valueProperty().addListener(observable -> {
                    handleValueChanged();
                });

                ///ulozi spinner do spinnerArray
                spinnerArray[col][row] = spinner;
            }
        }
    }

    @FXML
    ///zavola se vzdy kdyz se zmeni hodnota v kteremkoliv spinneru, vola static funkci ktera vypise obsah matice
    //zatim zadna funkcnost
    private void handleValueChanged(){

    }

    @FXML
    private CheckBox checkBox;

    ///zavola se kdyz se zmeni checkbox normalisation/coeffitient zatim zadna funkcnost
    @FXML
    private void handleNormalisation(){
        if(checkBox.isSelected()){
            System.out.println("Normalisation");
        }
        else {
            System.out.println("Coefittient");
        }
    }

    @FXML
    private void handleSaveMatrix(){  //Spusti se pri stisknuti save tlacitka v editMatrix okne, vysledkem je ulozeny soubor
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

    //vsechny hodnoty spinneru zmeni na nulu volanim ArrayToXML.setspinners()
    @FXML
    private void handleClearMatrix(){
        int[][] content = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                content[col][row] = 0;
            }
        }
        ArrayToXML converter = new ArrayToXML();

        converter.setSpinners(content, spinnerArray);
    }
}

