module com.example.tutosma {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tutosma to javafx.fxml;
    exports com.example.tutosma;
}