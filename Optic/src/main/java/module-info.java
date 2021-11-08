module com.example.optic {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.optic to javafx.fxml;
    exports com.example.optic;
}