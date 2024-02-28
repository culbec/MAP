package ro.ubbcluj.cs.map.template.Domain;

import ro.ubbcluj.cs.map.template.Utilities.Hobby;

import java.util.Objects;

public class Client {
    private final Long clientId;
    private final String name;
    private final int fidelityGrade;
    private final int age;
    private final Hobby hobby;

    public Client(Long clientId, String name, int fidelityGrade, int age, Hobby hobby) {
        this.clientId = clientId;
        this.name = name;
        this.fidelityGrade = fidelityGrade;
        this.age = age;
        this.hobby = hobby;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public int getFidelityGrade() {
        return fidelityGrade;
    }

    public int getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return fidelityGrade == client.fidelityGrade && age == client.age && Objects.equals(clientId, client.clientId) && Objects.equals(name, client.name) && hobby == client.hobby;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, name, fidelityGrade, age, hobby);
    }
}
