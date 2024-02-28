package ro.ubbcluj.cs.map.template.Repository;

import ro.ubbcluj.cs.map.template.Domain.Entity;
import ro.ubbcluj.cs.map.template.Utilities.Page;
import ro.ubbcluj.cs.map.template.Utilities.Pageable;

public interface PagingRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAll(Pageable pageable);
}
