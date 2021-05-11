module GuessWho {
    exports guesswho;
    exports Messages;
    exports application;

    //warning due to gson not including a module-info file
    requires gson;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    
    opens application;
    opens guesswho;
    //warning due to there being no compiled classes in folder
    opens application.defaultImages;
    opens Messages to gson;
}
