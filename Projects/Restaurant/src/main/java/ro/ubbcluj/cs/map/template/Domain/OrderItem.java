package ro.ubbcluj.cs.map.template.Domain;

public class OrderItem extends Tuple<Integer, Integer>{
    private Integer menuItemQuantity;

    public OrderItem(Integer orderId, Integer menuItemId, Integer menuItemQuantity) {
        super(orderId, menuItemId);
        this.menuItemQuantity = menuItemQuantity;
    }

    public Integer getMenuItemQuantity() {
        return menuItemQuantity;
    }

    public void setMenuItemQuantity(Integer menuItemQuantity) {
        this.menuItemQuantity = menuItemQuantity;
    }
}
