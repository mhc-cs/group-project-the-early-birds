module guesswho {
    exports guesswho;
    exports Messages;
    exports application;

    requires gson;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    
    opens application;
    opens guesswho;
    opens application.defaultImages;
    opens Messages to gson;
}
