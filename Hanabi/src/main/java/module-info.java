module hanabi {
    requires javafx.controls;
    requires javafx.fxml;
    exports hanabi.Model;
    exports hanabi.Controller;
    opens hanabi.Controller;
}