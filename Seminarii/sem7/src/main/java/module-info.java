module sem7.sem7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens sem7.sem7 to javafx.fxml;
    exports sem7.sem7;
}