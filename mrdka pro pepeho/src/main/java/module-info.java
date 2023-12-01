module com.example.mrdkapropepeho {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mrdkapropepeho to javafx.fxml;
    exports com.example.mrdkapropepeho;
}