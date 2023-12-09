package com.example.mrdkapropepeho;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
        clearTempXML();

    }
    public static void close(){
        Platform.exit();
    }


    //Vynuluje soubor temp.xml, spouští se při otevření nového programu
    public static void clearTempXML(){
        int[][] content = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                content[col][row] = 0;
            }
        }
        ArrayToXML converter = new ArrayToXML();
        converter.writeArrayToXml(content);
        File file = new File(".\\mrdka pro pepeho\\src\\main\\resources\\com\\example\\mrdkapropepeho\\temp.xml");
        String stringContent = converter.writeArrayToXml(content).toString();  //do promenne content ulozi vraceny string ve formatu xml ze tridy ArrayToXml
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(stringContent);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}