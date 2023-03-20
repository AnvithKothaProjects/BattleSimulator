module com.example.starterfile {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens project.battlesimulator to javafx.fxml;
    exports project.battlesimulator;
}