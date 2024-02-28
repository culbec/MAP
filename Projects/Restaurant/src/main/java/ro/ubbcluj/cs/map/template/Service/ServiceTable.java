package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Table;
import ro.ubbcluj.cs.map.template.Repository.TableDBRepository;

import java.util.List;

public class ServiceTable {
    private final TableDBRepository tableDBRepository;

    public ServiceTable(TableDBRepository tableDBRepository) {
        this.tableDBRepository = tableDBRepository;
    }

    public List<Table> getTables() {
        return (List<Table>) tableDBRepository.getAll();
    }
}
