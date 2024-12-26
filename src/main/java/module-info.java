module com.task_7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.task_7 to javafx.fxml;
    exports com.task_7;
}