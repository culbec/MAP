package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Client;
import ro.ubbcluj.cs.map.template.Domain.SpecialOffer;
import ro.ubbcluj.cs.map.template.Repository.SpecialOfferDBRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceSpecialOffer {
    private final SpecialOfferDBRepository specialOfferDBRepository;

    public ServiceSpecialOffer(SpecialOfferDBRepository specialOfferDBRepository) {
        this.specialOfferDBRepository = specialOfferDBRepository;
    }

    public List<SpecialOffer> getSpecialOffersOfHotelOnDate(double hotelId, LocalDate date) {
        return StreamSupport.stream(this.specialOfferDBRepository.getAll().spliterator(), false)
                .filter(specialOffer -> specialOffer.getHotelId() == hotelId
                        && specialOffer.getStartDate().isBefore(date)
                        && specialOffer.getEndDate().isAfter(date)).
                collect(Collectors.toList());
    }

    public List<SpecialOffer> getSpecialOffersForClient(Client client) {
        return StreamSupport.stream(this.specialOfferDBRepository.getAll().spliterator(), false)
                .filter(specialOffer -> client.getFidelityGrade() > specialOffer.getPercents() &&
                        specialOffer.getStartDate().isBefore(LocalDate.now()) &&
                        specialOffer.getEndDate().isAfter(LocalDate.now()))
                .toList();
    }
}
