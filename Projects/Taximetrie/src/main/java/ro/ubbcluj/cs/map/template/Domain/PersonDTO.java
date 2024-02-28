package ro.ubbcluj.cs.map.template.Domain;

public class PersonDTO {
    private final Person person;
    private final String location;

    public PersonDTO(Person person, String location) {
        this.person = person;
        this.location = location;
    }

    public Person getPerson() {
        return person;
    }

    public String getLocation() {
        return location;
    }
}
