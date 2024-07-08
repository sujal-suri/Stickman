module com.example.final_game {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.final_game to javafx.fxml;
    exports com.example.final_game;
}