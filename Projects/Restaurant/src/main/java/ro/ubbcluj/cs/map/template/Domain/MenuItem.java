package ro.ubbcluj.cs.map.template.Domain;

import java.util.Objects;

public class MenuItem extends Entity<Integer>{
    private final String category;
    private final String item;
    private final Double price;
    private final String currency;

    public MenuItem(Integer integer, String category, String item, Double price, String currency) {
        super(integer);
        this.category = category;
        this.item = item;
        this.price = price;
        this.currency = currency;
    }

    public String getCategory() {
        return category;
    }

    public String getItem() {
        return item;
    }

    public Double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(category, menuItem.category) && Objects.equals(item, menuItem.item) && Objects.equals(price, menuItem.price) && Objects.equals(currency, menuItem.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category, item, price, currency);
    }
}
