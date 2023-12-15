package com.example.mrdkapropepeho;
import javafx.scene.control.Spinner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.scene.control.SpinnerValueFactory;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class ArrayToXML {
    //Ze vstupu Spinner[][] spinnerArray udela a vrati int[][] valueMatrix s hodnotami spinneru v spinnerArray
    public int[][] convertSpinnerArrayToValueArray(Spinner<Integer>[][] spinnerArray){
        int [][] valueMatrix = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                valueMatrix[col][row] = spinnerArray[col][row].getValue();
            }
        }
        return valueMatrix;
    }

    //vstup content je int[][] array s obsahem, ktery se nahraje do valueFactory ve Spinner[][] arrayi
    public void setSpinners(int[][] content,Spinner<Integer>[][] spinnerArray){
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SpinnerValueFactory<Integer> valueFactory = spinnerArray[col][row].getValueFactory();
                valueFactory.setValue(content[col][row]);
            }
        }
    }

    //Jako vstup ma genericky 2D array, ktery zapise do xml file
    //Je to sice genericka funkce, ale fr ong bych tam daval jen Spinner<Integer>[][]
    public StringBuilder writeArrayToXml(Spinner[][] array){
        int[][] content = convertSpinnerArrayToValueArray(array);
        StringBuilder xmlContent = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlContent.append("<Array>\n");
        for(int row = 0; row < 9; row++) {
            xmlContent.append("  <Row>\n");
            for (int col = 0; col < 9; col++) {
                xmlContent.append("    <Value>").append(content[row][col]).append("</Value>\n");
            }
            xmlContent.append("  </Row>\n");
        }
        xmlContent.append("</Array>");
        return xmlContent;
    }

    //ze zadane matice hodnot vrati string v korektnim xml formatu
    public StringBuilder writeArrayToXml(int[][] content){
        StringBuilder xmlContent = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlContent.append("<Array>\n");
        for(int row = 0; row < 9; row++) {
            xmlContent.append("  <Row>\n");
            for (int col = 0; col < 9; col++) {
                xmlContent.append("    <Value>").append(content[row][col]).append("</Value>\n");
            }
            xmlContent.append("  </Row>\n");
        }
        xmlContent.append("</Array>");
        return xmlContent;
    }
    //jako vstup ma cestu ke xml souboru a vrati jeho precteny obsah ve forme int[][]
    public int[][] readFxmlToMatrix(String filePath) throws IOException, JDOMException {
        int[][] readData = new int[9][9];

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new File(filePath));
        Element rootElement = document.getRootElement();

        int RowCount = 0;
        int ColCount = 0;
        for (Element row : rootElement.getChildren("Row")) {
            List<Element> valueElement = row.getChildren("Value");
            for(Element value : valueElement){
                readData[ColCount][RowCount] = Integer.parseInt(value.getValue());
                RowCount ++;
            }
            ColCount ++;
            RowCount = 0;
        }
        return readData;


    }

}
