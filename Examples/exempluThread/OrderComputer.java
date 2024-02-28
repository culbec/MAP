public class OrderComputer {
    private Order order;
    private int currentTime = 0;

    public boolean prepareOrder() {
        if(order.getTime() != this.currentTime) {
            this.currentTime++;
            return false;
        }
        return true;
    }

    public OrderComputer(Order order) {
        this.order = order;
    }

}