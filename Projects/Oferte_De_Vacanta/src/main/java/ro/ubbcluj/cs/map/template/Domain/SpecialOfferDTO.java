package ro.ubbcluj.cs.map.template.Domain;

public class SpecialOfferDTO {
    private final SpecialOffer specialOffer;
    private final Hotel hotel;
    private final Location location;

    public SpecialOfferDTO(SpecialOffer specialOffer, Hotel hotel, Location location) {
        this.specialOffer = specialOffer;
        this.hotel = hotel;
        this.location = location;
    }

    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Location getLocation() {
        return location;
    }
}
