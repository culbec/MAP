package ro.ubbcluj.cs.map.template.Domain;

public class HotelDTO {
    private final Hotel hotel;
    private final Location location;

    public HotelDTO(Hotel hotel, Location location) {
        this.hotel = hotel;
        this.location = location;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Location getLocation() {
        return location;
    }
}
