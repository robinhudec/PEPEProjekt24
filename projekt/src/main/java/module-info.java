module com.example.mrdkapropepeho {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jdom2;
    requires java.desktop;


    opens com.example.projekt to javafx.fxml;
    exports com.example.projekt;
}