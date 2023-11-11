package com.example.mrdkapropepeho;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class editMatrixController implements Initializable {
    @FXML
    private GridPane spinnerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Spinner<Integer> spinner = new Spinner<>();
                spinnerPane.add(spinner, col, row);
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-100, 100);
                valueFactory.setValue(0);
                spinner.setValueFactory(valueFactory);
            }
    }



}}
