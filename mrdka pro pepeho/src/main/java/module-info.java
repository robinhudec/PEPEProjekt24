module com.example.mrdkapropepeho {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jdom2;


    opens com.example.mrdkapropepeho to javafx.fxml;
    exports com.example.mrdkapropepeho;
}