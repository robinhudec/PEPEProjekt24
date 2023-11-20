package com.example.mrdkapropepeho;
import javafx.scene.control.Spinner;

//Intellij mi tu rve nejaky errory, ale funkcnost je 100%, takze je vklidu ignorujte
public class ArrayToXML <T>{
    //Ze vstupu Spinner[][] spinnerArray udela a vrati int[][] valueMatrix s hodnotami spinneru v spinnerArray
    private int[][] convertSpinnerArrayToValueArray(Spinner<Integer>[][] spinnerArray){
        int [][] valueMatrix = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                valueMatrix[col][row] = spinnerArray[col][row].getValue();
            }
        }
        return valueMatrix;
    }

    //Jako vstup ma genericky 2D array, ktery zapise do xml file, zatim nefunkcni filechooser
    //Je to sice genericka funkce, ale fr bych tam daval jen Spinner<Integer>[][]
    public StringBuilder writeArrayToXml(T[][] array){
        int[][] content = convertSpinnerArrayToValueArray((Spinner<Integer>[][]) array);
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
}
