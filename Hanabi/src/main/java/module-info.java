module hanabi {
    requires javafx.controls;
    requires javafx.fxml;
    exports hanabi.Model;
    exports hanabi.Controller;
    exports hanabi.Server;
    opens hanabi.Controller;
}

