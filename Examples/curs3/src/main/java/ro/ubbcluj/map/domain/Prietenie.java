package ro.ubbcluj.map.domain;

import java.time.LocalDateTime;


public class Prietenie extends Entity<socialnetwork.domain.Tuple<Long,Long>> {

    LocalDateTime date;

    public Prietenie() {
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }
}
