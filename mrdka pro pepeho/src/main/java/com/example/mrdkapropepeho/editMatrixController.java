package com.example.mrdkapropepeho;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;

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

                ///prida listener ktery odpovida na zmeny hodnoty a zavola handleValuechanged
                spinner.valueProperty().addListener(observable -> {
                    handleValueChanged();
                });

                ///ulozi spinner do spinnerArray
                spinnerArray[col][row] = spinner;
            }
        }
        System.out.println(spinnerArray[8][8].getValue());
    }

    ///zavola se vzdy kdyz se zmeni hodnota v kteremkoliv spinneru
    private void handleValueChanged(){
        System.out.println("value changed");
    }

    @FXML
    private CheckBox checkBox;

    ///zavola se kdyz se zmeni checkbox normalisation/coeffitient
    @FXML
    private void handleNormalisation(){
        if(checkBox.isSelected()){
            System.out.println("Normalisation");
        }
        else {
            System.out.println("Coefittient");
        }
    }

}
