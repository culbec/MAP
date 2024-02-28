package ro.ubbcluj.cs.map.template.Domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class SpecialOffer {
    private final double specialOfferId;
    private final double hotelId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int percents;

    public SpecialOffer(double specialOfferId, double hotelId, LocalDate startDate, LocalDate endDate, int percents) {
        this.specialOfferId = specialOfferId;
        this.hotelId = hotelId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percents = percents;
    }

    public double getSpecialOfferId() {
        return specialOfferId;
    }

    public double getHotelId() {
        return hotelId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getPercents() {
        return percents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialOffer that = (SpecialOffer) o;
        return Double.compare(specialOfferId, that.specialOfferId) == 0 && Double.compare(hotelId, that.hotelId) == 0 && percents == that.percents && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialOfferId, hotelId, startDate, endDate, percents);
    }
}
