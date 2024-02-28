package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.MenuItem;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.MenuItemDBRepository;

import java.util.List;
import java.util.Optional;

public class ServiceMenuItem {
    private final MenuItemDBRepository menuItemDBRepository;

    public ServiceMenuItem(MenuItemDBRepository menuItemDBRepository) {
        this.menuItemDBRepository = menuItemDBRepository;
    }

    public MenuItem getMenuItem(Integer id) {
        try {
            Optional<MenuItem> menuItemOptional = menuItemDBRepository.getOne(id);
            if (menuItemOptional.isEmpty()) {
                throw new ServiceException("The menu item does not exist!");
            }
            return menuItemOptional.get();
        } catch (RepositoryException e) {
            throw new ServiceException("Couldn't get the menu item! Cause: " + e.getMessage());
        }
    }

    public List<MenuItem> getMenuItems() {
        return (List<MenuItem>) this.menuItemDBRepository.getAll();
    }
}
