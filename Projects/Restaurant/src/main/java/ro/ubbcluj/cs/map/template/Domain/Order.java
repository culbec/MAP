package ro.ubbcluj.cs.map.template.Domain;

import ro.ubbcluj.cs.map.template.Utilities.OrderStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class Order extends Entity<Integer> {
    private final Integer table;
    private final List<OrderItem> menuItems;
    private final LocalDateTime date;
    private final OrderStatus orderStatus;

    public Order(Integer integer, Integer table, List<OrderItem> menuItems, LocalDateTime date, OrderStatus orderStatus) {
        super(integer);
        this.table = table;
        this.menuItems = menuItems;
        this.date = date.truncatedTo(ChronoUnit.MINUTES);
        this.orderStatus = orderStatus;
    }

    public Integer getTable() {
        return table;
    }

    public List<OrderItem> getMenuItems() {
        return menuItems;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(table, order.table) && Objects.equals(menuItems, order.menuItems) && Objects.equals(date, order.date) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), table, menuItems, date, orderStatus);
    }

    @Override
    public String toString() {
        return this.table + " " + this.date + " " + this.menuItems;
    }
}
