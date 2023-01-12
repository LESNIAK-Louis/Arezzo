module arezzo {
    requires javafx.fxml;
    requires javafx.controls;
    requires partition;
    requires java.desktop;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens arezzo to javafx.fxml;
    opens arezzo.whatever to javafx.fxml;
    exports arezzo.model to com.fasterxml.jackson.databind;
    exports arezzo.enumerations to com.fasterxml.jackson.databind;
    exports arezzo to javafx.graphics;
    exports arezzo.whatever;
    //exports arezzo.vues;
    //exports arezzo.modeles;
}