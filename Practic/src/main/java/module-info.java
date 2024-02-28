module ro.ubbcluj.cs.map.template {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports ro.ubbcluj.cs.map.template;
    opens ro.ubbcluj.cs.map.template to javafx.fxml;

    exports ro.ubbcluj.cs.map.template.Controller;
    opens ro.ubbcluj.cs.map.template.Controller to javafx.fxml;

    exports ro.ubbcluj.cs.map.template.Utilities;
    opens ro.ubbcluj.cs.map.template.Utilities to javafx.fxml;

    exports ro.ubbcluj.cs.map.template.Domain;
    opens ro.ubbcluj.cs.map.template.Domain to javafx.fxml;

    exports ro.ubbcluj.cs.map.template.Repository;
    opens ro.ubbcluj.cs.map.template.Repository to javafx.fxml;

    exports ro.ubbcluj.cs.map.template.Service;
    opens ro.ubbcluj.cs.map.template.Service to javafx.fxml;

    exports ro.ubbcluj.cs.map.template.Exception;
    opens ro.ubbcluj.cs.map.template.Exception to javafx.fxml;
}