package ro.ubbcluj.cs.map.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ro.ubbcluj.cs.map.template.Domain.OrderItem;
import ro.ubbcluj.cs.map.template.Domain.Table;
import ro.ubbcluj.cs.map.template.Service.ServiceMenuItem;
import ro.ubbcluj.cs.map.template.Service.ServiceOrder;
import ro.ubbcluj.cs.map.template.Utilities.Event.OrderEvent;
import ro.ubbcluj.cs.map.template.Utilities.MessageAlert;
import ro.ubbcluj.cs.map.template.Utilities.Observer.ObservableImplemented;
import ro.ubbcluj.cs.map.template.Utilities.Observer.Observer;
import ro.ubbcluj.cs.map.template.Utilities.OrderItemTableView;
import ro.ubbcluj.cs.map.template.Utilities.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TableController extends ObservableImplemented<OrderEvent> implements Observer<OrderEvent> {
    private ServiceOrder serviceOrder;
    private Table table;

    @FXML
    public Label orderStatusLabel;
    public Label tableLabel;
    public VBox antreuVBox;
    public VBox felPrincipalVBox;
    public VBox desertVBox;
    public VBox bauturaVBox;

    OrderItemTableView antreuTableView = new OrderItemTableView();
    OrderItemTableView felPrincipalTableView = new OrderItemTableView();
    OrderItemTableView desertTableView = new OrderItemTableView();
    OrderItemTableView bauturaTableView = new OrderItemTableView();

    public void initController(ServiceOrder serviceOrder, ServiceMenuItem serviceMenuItem, Table table) {
        this.serviceOrder = serviceOrder;
        this.table = table;

        this.serviceOrder.addObserver(this);

        // Setting the item category for each table view.
        this.antreuTableView.setItemCategory("Antreu");
        this.felPrincipalTableView.setItemCategory("Fel principal");
        this.desertTableView.setItemCategory("Desert");
        this.bauturaTableView.setItemCategory("Bautura");

        // Initializing the tables.
        this.antreuTableView.initTableView(serviceMenuItem);
        this.felPrincipalTableView.initTableView(serviceMenuItem);
        this.desertTableView.initTableView(serviceMenuItem);
        this.bauturaTableView.initTableView(serviceMenuItem);

        // Adding the tables to the VBoxes.
        this.antreuVBox.getChildren().add(this.antreuTableView);
        this.felPrincipalVBox.getChildren().add(this.felPrincipalTableView);
        this.desertVBox.getChildren().add(this.desertTableView);
        this.bauturaVBox.getChildren().add(this.bauturaTableView);

        // Setting the table label;
        this.tableLabel.setText("Table " + table.getId());
    }

    public void handlePlaceOrder() {
        List<OrderItem> orderItems = new ArrayList<>();

        this.antreuTableView.getItems().forEach(orderItem -> {
            if (orderItem.getMenuItemQuantity() != null) {
                orderItems.add(orderItem);
            }
        });

        this.felPrincipalTableView.getItems().forEach(orderItem -> {
            if (orderItem.getMenuItemQuantity() != null) {
                orderItems.add(orderItem);
            }
        });

        this.desertTableView.getItems().forEach(orderItem -> {
            if (orderItem.getMenuItemQuantity() != null) {
                orderItems.add(orderItem);
            }
        });

        this.bauturaTableView.getItems().forEach(orderItem -> {
            if (orderItem.getMenuItemQuantity() != null) {
                orderItems.add(orderItem);
            }
        });

        if (orderItems.isEmpty()) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "You need to select at least one item!");
            return;
        }

        this.serviceOrder.addOrder(this.table.getId(), orderItems, LocalDateTime.now(), OrderStatus.PLACED);
    }

    @Override
    public void update(OrderEvent orderEvent) {
        switch (orderEvent.getEventType()) {
            case PREPARING_ORDER -> {
                if (orderEvent.getNewEntity().getTable().equals(this.table.getId())) {
                    this.orderStatusLabel.setText("Your order is being prepared!");
                }
            }
            case SERVED_ORDER -> {
                if (orderEvent.getNewEntity().getTable().equals(this.table.getId())) {
                    this.orderStatusLabel.setText("Your order is served!");
                }
            }
        }
    }
}
