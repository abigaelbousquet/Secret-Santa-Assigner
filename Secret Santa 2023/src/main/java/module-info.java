module com.example.secret_santa_2023 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.secret_santa_2023 to javafx.fxml;
    exports com.example.secret_santa_2023;
}