public class Order {
    private String item;
    private int time;
    
    public Order(String item, int time) {
        this.item = item;
        this.time = time;
    }

    public String getItem() {
        return item;
    }
    public int getTime() {
        return time;
    }
}
