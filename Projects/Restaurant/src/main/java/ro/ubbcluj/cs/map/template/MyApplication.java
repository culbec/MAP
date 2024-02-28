package ro.ubbcluj.cs.map.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.template.Controller.StaffController;
import ro.ubbcluj.cs.map.template.Controller.TableController;
import ro.ubbcluj.cs.map.template.Repository.MenuItemDBRepository;
import ro.ubbcluj.cs.map.template.Repository.OrderDBRepository;
import ro.ubbcluj.cs.map.template.Repository.TableDBRepository;
import ro.ubbcluj.cs.map.template.Service.ServiceMenuItem;
import ro.ubbcluj.cs.map.template.Service.ServiceOrder;
import ro.ubbcluj.cs.map.template.Service.ServiceTable;

import java.io.IOException;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader staffLoader = new FXMLLoader(MyApplication.class.getResource("views/staff.fxml"));

        OrderDBRepository orderDBRepository = new OrderDBRepository();
        MenuItemDBRepository menuItemDBRepository = new MenuItemDBRepository();
        TableDBRepository tableDBRepository = new TableDBRepository();

        ServiceOrder serviceOrder = new ServiceOrder(orderDBRepository);
        ServiceMenuItem serviceMenuItem = new ServiceMenuItem(menuItemDBRepository);
        ServiceTable serviceTable = new ServiceTable(tableDBRepository);

        Scene staffScene = new Scene(staffLoader.load());
        StaffController staffController = staffLoader.getController();
        staffController.initController(serviceOrder, serviceMenuItem);

        stage.setTitle("Staff");
        stage.setScene(staffScene);
        stage.show();

        serviceTable.getTables().forEach(table -> {
            FXMLLoader tableLoader = new FXMLLoader(MyApplication.class.getResource("views/table.fxml"));

            try {
                Scene tableScene = new Scene(tableLoader.load());
                TableController tableController = tableLoader.getController();
                tableController.initController(serviceOrder, serviceMenuItem, table);

                Stage stageTable = new Stage();
                stageTable.setScene(tableScene);
                stageTable.setTitle("Table " + table.getId());
                stageTable.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}