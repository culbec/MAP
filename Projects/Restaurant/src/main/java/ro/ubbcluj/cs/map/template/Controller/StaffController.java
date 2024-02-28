package ro.ubbcluj.cs.map.template.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.ubbcluj.cs.map.template.Domain.MenuItem;
import ro.ubbcluj.cs.map.template.Domain.Order;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Service.ServiceMenuItem;
import ro.ubbcluj.cs.map.template.Service.ServiceOrder;
import ro.ubbcluj.cs.map.template.Utilities.Event.OrderEvent;
import ro.ubbcluj.cs.map.template.Utilities.MessageAlert;
import ro.ubbcluj.cs.map.template.Utilities.Observer.ObservableImplemented;
import ro.ubbcluj.cs.map.template.Utilities.Observer.Observer;
import ro.ubbcluj.cs.map.template.Utilities.OrderStatus;

import java.time.LocalDateTime;
import java.util.Comparator;

public class StaffController extends ObservableImplemented<OrderEvent> implements Observer<OrderEvent> {
    private ServiceOrder serviceOrder;
    private ServiceMenuItem serviceMenuItem;
    @FXML
    public TableView<Order> placedOrdersTableView;
    public TableColumn<Order, Integer> tablePlacedColumn;
    public TableColumn<Order, LocalDateTime> datePlacedColumn;
    public TableColumn<Order, String> itemsPlacedColumn;
    public TableView<Order> preparingOrdersTableView;
    public TableColumn<Order, Integer> tablePreparingColumn;
    public TableColumn<Order, LocalDateTime> datePreparingColumn;
    public TableColumn<Order, String> itemsPreparingColumn;

    private String getOrderString(Order order) {
        StringBuilder stringBuilder = new StringBuilder();
        order.getMenuItems().forEach(orderItem -> {
            MenuItem menuItem = this.serviceMenuItem.getMenuItem(orderItem.getSecond());
            stringBuilder.append((menuItem.getItem() + " ").repeat(Math.max(0, orderItem.getMenuItemQuantity()))).append(" ");
        });
        return stringBuilder.toString();
    }

    public void initController(ServiceOrder serviceOrder, ServiceMenuItem serviceMenuItem) {
        this.serviceOrder = serviceOrder;
        this.serviceMenuItem = serviceMenuItem;
        this.serviceOrder.addObserver(this);

        this.tablePlacedColumn.setCellValueFactory(new PropertyValueFactory<>("table"));
        this.datePlacedColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.itemsPlacedColumn.setCellValueFactory(order -> new SimpleStringProperty(this.getOrderString(order.getValue())));

        this.tablePreparingColumn.setCellValueFactory(new PropertyValueFactory<>("table"));
        this.datePreparingColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.itemsPreparingColumn.setCellValueFactory(order -> new SimpleStringProperty(this.getOrderString(order.getValue())));

        this.serviceOrder.getOrders().forEach(order -> {
            if (order.getOrderStatus().equals(OrderStatus.PLACED)) {
                this.placedOrdersTableView.getItems().add(order);
            } else if (order.getOrderStatus().equals(OrderStatus.PREPARING)) {
                this.preparingOrdersTableView.getItems().add(order);
            }
        });

        this.placedOrdersTableView.getItems().sort(Comparator.comparing(Order::getDate));
    }

    public void handlePrepareOrder() {
        if (this.placedOrdersTableView.getSelectionModel().getSelectedItem() == null) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Cannot prepare the order!", "No order was selected.");
            return;
        }

        Order order = this.placedOrdersTableView.getSelectionModel().getSelectedItem();
        this.placedOrdersTableView.getSelectionModel().clearSelection();

        try {
            this.serviceOrder.prepareOrder(order);
        } catch (ServiceException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong", e.getMessage());
        }
    }

    public void handleServeOrder() {
        if (this.preparingOrdersTableView.getSelectionModel().getSelectedItem() == null) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Cannot serve the order!", "No order was selected.");
            return;
        }

        Order order = this.preparingOrdersTableView.getSelectionModel().getSelectedItem();
        this.preparingOrdersTableView.getSelectionModel().clearSelection();

        try {
            this.serviceOrder.serveOrder(order);
        } catch (ServiceException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong", e.getMessage());
        }
    }

    @Override
    public void update(OrderEvent orderEvent) {
        switch (orderEvent.getEventType()) {
            case PLACED_ORDER -> this.placedOrdersTableView.getItems().add(orderEvent.getNewEntity());
            case PREPARING_ORDER -> {
                this.placedOrdersTableView.getItems().remove(orderEvent.getOldEntity());
                this.preparingOrdersTableView.getItems().add(orderEvent.getNewEntity());
            }
            case SERVED_ORDER -> this.preparingOrdersTableView.getItems().remove(orderEvent.getOldEntity());
        }
    }
}
