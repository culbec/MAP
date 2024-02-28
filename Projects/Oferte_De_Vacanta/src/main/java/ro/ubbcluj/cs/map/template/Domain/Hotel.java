package ro.ubbcluj.cs.map.template.Domain;

import ro.ubbcluj.cs.map.template.Utilities.HotelType;

import java.util.Objects;

public class Hotel {
    private final double hotelId;
    private final double locationId;
    private final String hotelName;
    private final int noRooms;
    private final double pricePerNight;
    private final HotelType type;

    public Hotel(double hotelId, double locationId, String hotelName, int noRooms, double pricePerNight, HotelType type) {
        this.hotelId = hotelId;
        this.locationId = locationId;
        this.hotelName = hotelName;
        this.noRooms = noRooms;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }

    public double getHotelId() {
        return hotelId;
    }

    public double getLocationId() {
        return locationId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getNoRooms() {
        return noRooms;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public HotelType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Double.compare(hotelId, hotel.hotelId) == 0 && Double.compare(locationId, hotel.locationId) == 0 && noRooms == hotel.noRooms && Double.compare(pricePerNight, hotel.pricePerNight) == 0 && Objects.equals(hotelName, hotel.hotelName) && type == hotel.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, locationId, hotelName, noRooms, pricePerNight, type);
    }
}
