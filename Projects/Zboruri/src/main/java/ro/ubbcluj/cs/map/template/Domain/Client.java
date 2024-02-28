package ro.ubbcluj.cs.map.template.Domain;

import java.util.Objects;

public class Client {
    public final String username;
    public final String name;

    public Client(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(username, client.username) && Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name);
    }
}
