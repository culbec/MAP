package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Hotel;
import ro.ubbcluj.cs.map.template.Repository.HotelDBRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceHotel {
    private final HotelDBRepository hotelDBRepository;

    public ServiceHotel(HotelDBRepository hotelDBRepository) {
        this.hotelDBRepository = hotelDBRepository;
    }

    public Hotel getHotel(Double hotelId) {
        return this.hotelDBRepository.getOne(hotelId).orElse(null);
    }

    public List<Hotel> getHotelsOfLocation(double locationId) {
        return StreamSupport.stream(this.hotelDBRepository.getAll().spliterator(), false)
                .filter(hotel -> hotel.getLocationId() == locationId).
                collect(Collectors.toList());
    }
}
