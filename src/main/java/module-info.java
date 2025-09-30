module code.calculatorfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens code.calculatorfx to javafx.fxml;
    exports code.calculatorfx;
}