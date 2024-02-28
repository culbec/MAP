package ro.ubbcluj.cs.map.template.Utilities;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import ro.ubbcluj.cs.map.template.Domain.MenuItem;
import ro.ubbcluj.cs.map.template.Domain.OrderItem;
import ro.ubbcluj.cs.map.template.Service.ServiceMenuItem;

public class OrderItemTableView extends TableView<OrderItem> {
    private final TableColumn<OrderItem, String> orderItemNameColumn = new TableColumn<>();
    private final TableColumn<OrderItem, String> orderItemPriceColumn = new TableColumn<>();
    private TableColumn<OrderItem, Integer> orderItemQuantityColumn = new TableColumn<>();

    private String itemCategory;

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void initTableView(ServiceMenuItem serviceMenuItem) {
        // Setting the table to be editable.
        this.setEditable(true);

        this.orderItemNameColumn.setText("Item");
        this.orderItemPriceColumn.setText("Price");
        this.orderItemQuantityColumn.setText("Quantity");

        this.getColumns().add(this.orderItemNameColumn);
        this.getColumns().add(this.orderItemPriceColumn);
        this.getColumns().add(this.orderItemQuantityColumn);

        // Setting the resize policy of the columns.
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Allowing cell selection and multiple selection.
        this.getSelectionModel().setCellSelectionEnabled(true);
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Specifying the editable columns;
        this.orderItemNameColumn.setEditable(false);
        this.orderItemPriceColumn.setEditable(false);
        this.orderItemQuantityColumn.setEditable(true);

        // Setting the value factory for each cell.
        this.orderItemNameColumn.setCellValueFactory(orderItem -> {
            MenuItem menuItem = serviceMenuItem.getMenuItem(orderItem.getValue().getSecond());
            return new SimpleStringProperty(menuItem.getItem());
        });
        this.orderItemPriceColumn.setCellValueFactory(orderItem -> {
            MenuItem menuItem = serviceMenuItem.getMenuItem(orderItem.getValue().getSecond());
            return new SimpleStringProperty(menuItem.getPrice() + " " + menuItem.getCurrency());
        });

        // Make the quantity column editable and set up the cell value factory with a StringConverter.
        this.orderItemQuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // Set the cell value factory for the quantity column.
        this.orderItemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemQuantity"));


        // Add an event handler for selecting a row.
        this.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Enable or disable "Quantity" column when a row is selected.
            this.orderItemQuantityColumn.setEditable(newSelection != null);

            // If the row is selected but the cell is not actively being edited, do nothing.
            if (newSelection != null && !this.orderItemQuantityColumn.isEditable()) {
                return;
            }

            this.edit(this.getSelectionModel().getSelectedIndex(), this.orderItemQuantityColumn);
        });

        // Add an event handler for editing the quantity.
        this.orderItemQuantityColumn.setOnEditCommit(event -> {
            // Get the edited OrderItems instance.
            OrderItem editedOrderItem = event.getRowValue();

            // Parse the entered quantity as an integer.
            try {
                int newQuantity = Integer.parseInt(String.valueOf(event.getNewValue()));

                // Check if the quantity is greater than or equal to 1.
                if (newQuantity >= 1) {
                    // Update the OrderItems quantity.
                    editedOrderItem.setMenuItemQuantity(newQuantity);
                } else {
                    // Set the cell value to null if the quantity is less than 1.
                    editedOrderItem.setMenuItemQuantity(null);
                }
            } catch (NumberFormatException e) {
                // Handle invalid input (non-integer).
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "The quantity needs to be a number!");
            }

            // Update the edited cell.
            this.orderItemQuantityColumn.getTableView().getColumns().get(0).setVisible(false);
            this.orderItemQuantityColumn.getTableView().getColumns().get(0).setVisible(true);
        });

        // Adding the entries to the columns if the category is the same.
        serviceMenuItem.getMenuItems().forEach(menuItem -> {
            if (menuItem.getCategory().equals(this.itemCategory)) {
                this.getItems().add(new OrderItem(null, menuItem.getId(), null));
            }
        });
    }
}
