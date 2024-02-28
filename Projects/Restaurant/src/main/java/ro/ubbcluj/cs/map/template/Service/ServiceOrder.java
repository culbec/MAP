package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Order;
import ro.ubbcluj.cs.map.template.Domain.OrderItem;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.OrderDBRepository;
import ro.ubbcluj.cs.map.template.Utilities.Event.EventType;
import ro.ubbcluj.cs.map.template.Utilities.Event.OrderEvent;
import ro.ubbcluj.cs.map.template.Utilities.Observer.ObservableImplemented;
import ro.ubbcluj.cs.map.template.Utilities.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ServiceOrder extends ObservableImplemented<OrderEvent> {
    private final OrderDBRepository orderDBRepository;

    public ServiceOrder(OrderDBRepository orderDBRepository) {
        this.orderDBRepository = orderDBRepository;
    }

    public List<Order> getOrders() {
        return (List<Order>) this.orderDBRepository.getAll();
    }

    public void addOrder(Integer tableId, List<OrderItem> orderItems, LocalDateTime dateTime, OrderStatus orderStatus) {
        Integer id = this.orderDBRepository.getValidId();

        orderItems.forEach(orderItem -> orderItem.setFirst(id));

        Order order = new Order(id, tableId, orderItems, dateTime, orderStatus);
        this.orderDBRepository.save(order);
        this.orderDBRepository.addOrderItems(orderItems);

        this.notify(new OrderEvent(EventType.PLACED_ORDER, order, null));
    }

    public void prepareOrder(Order order) {
        try {
            Order newOrder = new Order(order.getId(), order.getTable(), order.getMenuItems(), order.getDate(), OrderStatus.PREPARING);
            Optional<Order> optionalOrder = orderDBRepository.update(newOrder);
            if (optionalOrder.isEmpty()) {
                throw new ServiceException("The specified order does not exist!");
            }

            this.notify(new OrderEvent(EventType.PREPARING_ORDER, newOrder, order));
        } catch (RepositoryException e) {
            throw new ServiceException("Couldn't update the order! Cause: " + e.getMessage());
        }
    }

    public void serveOrder(Order order) {
        try {
            Order newOrder = new Order(order.getId(), order.getTable(), order.getMenuItems(), order.getDate(), OrderStatus.SERVED);
            Optional<Order> optionalOrder = orderDBRepository.update(newOrder);
            if (optionalOrder.isEmpty()) {
                throw new ServiceException("The specified order does not exist!");
            }

            this.notify(new OrderEvent(EventType.SERVED_ORDER, newOrder, order));
        } catch (RepositoryException e) {
            throw new ServiceException("Couldn't update the order! Cause: " + e.getMessage());
        }
    }
}
