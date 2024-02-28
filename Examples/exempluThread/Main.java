public class Main {
    public static void main(String[] args) {
        Order order = new Order("bread", 2);
        OrderComputer orderComputer = new OrderComputer(order);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(!orderComputer.prepareOrder()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("Order is not ready!");
                }
                System.out.println("Order ready!");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
