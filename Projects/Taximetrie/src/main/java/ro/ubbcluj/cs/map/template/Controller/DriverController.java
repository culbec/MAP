package ro.ubbcluj.cs.map.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.ubbcluj.cs.map.template.Domain.Driver;
import ro.ubbcluj.cs.map.template.Domain.Person;
import ro.ubbcluj.cs.map.template.Domain.PersonDTO;
import ro.ubbcluj.cs.map.template.Domain.Tuple;
import ro.ubbcluj.cs.map.template.Service.ServiceDriver;
import ro.ubbcluj.cs.map.template.Service.ServicePerson;
import ro.ubbcluj.cs.map.template.Service.ServiceRequest;
import ro.ubbcluj.cs.map.template.Utilities.Event.*;
import ro.ubbcluj.cs.map.template.Utilities.MessageAlert;
import ro.ubbcluj.cs.map.template.Utilities.Observer.ObservableImplemented;
import ro.ubbcluj.cs.map.template.Utilities.Observer.Observer;
import ro.ubbcluj.cs.map.template.Utilities.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class DriverController extends ObservableImplemented<AbstractEvent> implements Observer<AbstractEvent> {
    @FXML
    public TableView<PersonDTO> requestTableView;
    public TableColumn<PersonDTO, String> personTableColumn;
    public TableColumn<PersonDTO, String> locationTableColumn;
    public TextField maximumTimeTextField;
    public DatePicker requestDatePicker;
    public TableView<Person> requestFromDateTableView;
    public TableColumn<Person, String> personFromDateTableColumn;
    public TableView<Person> clientsTableView;
    public TableColumn<Person, String> clientTableColumn;
    public TextField mediaComenzilorTextField;
    public TextField celMaiActivClientTextField;
    private ServicePerson servicePerson;
    private ServiceDriver serviceDriver;
    private ServiceRequest serviceRequest;
    private Driver driver;

    int pageNumber = 0;
    int pageSize = 1;

    public void initController(ServicePerson servicePerson, ServiceDriver serviceDriver, ServiceRequest serviceRequest, Driver driver) {
        this.servicePerson = servicePerson;
        this.serviceDriver = serviceDriver;
        this.serviceRequest = serviceRequest;
        this.driver = driver;

        this.requestTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.personTableColumn.setCellValueFactory(new PropertyValueFactory<>("person"));
        this.locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        this.personFromDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.clientTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.getInitialClientsOnPage();
        this.updateMean();
        this.updateMostActiveClient();
    }

    public void handleAcceptRequest() {
        if (this.requestTableView.getSelectionModel().getSelectedItem() == null) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "You need to select a person!");
            return;
        }
        if (this.maximumTimeTextField.getText().isEmpty()) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "You to insert a maximum time!");
            return;
        }
        try {
            Integer.parseInt(this.maximumTimeTextField.getText());
        } catch (NumberFormatException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", e.getMessage());
            return;
        }

        Integer maximumWaitTime = Integer.parseInt(this.maximumTimeTextField.getText());
        if (maximumWaitTime < 0) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "The maximum wait time cannot be negative!");
            return;
        }

        PersonDTO personDTO = this.requestTableView.getSelectionModel().getSelectedItem();
        Tuple<Driver, Person, Integer> tuple = new Tuple<>(driver, personDTO.getPerson(), maximumWaitTime);

        this.notify(new TimeEvent(EventType.REQUEST_ACCEPTED_TAXI, tuple, null));
    }

    public void handleDate() {
        LocalDateTime date = this.requestDatePicker.getValue().atStartOfDay();

        List<Person> personList = this.serviceRequest.getClientsOfDate(driver.getId(), date);

        this.requestFromDateTableView.getItems().clear();
        this.requestFromDateTableView.getItems().addAll(personList);
    }

    private void getInitialClientsOnPage() {
        this.pageNumber = 0;
        List<Person> personList = this.serviceRequest.getClientsOfPage(driver.getId(), new Pageable(pageNumber, pageSize));
        this.clientsTableView.getItems().clear();
        this.clientsTableView.getItems().addAll(personList);
    }

    public void nextPageAction() {
        List<Person> personList = this.serviceRequest.getClientsOfPage(driver.getId(), new Pageable(pageNumber + 1, pageSize));

        if (!personList.isEmpty()) {
            this.pageNumber += 1;
            this.clientsTableView.getItems().clear();
            this.clientsTableView.getItems().addAll(personList);
        }
    }

    public void previousPageAction() {
        if (this.pageNumber - 1 >= 0) {
            this.pageNumber -= 1;
            List<Person> personList = this.serviceRequest.getClientsOfPage(driver.getId(), new Pageable(pageNumber, pageSize));
            this.clientsTableView.getItems().clear();
            this.clientsTableView.getItems().addAll(personList);
        }
    }

    private void updateMean() {
        Double mean = this.serviceRequest.getMeanOfTheLast3Months(driver.getId());
        String formattedValue = String.format("%.3f", mean);
        this.mediaComenzilorTextField.setText(formattedValue);
    }

    private void updateMostActiveClient() {
        Person person = this.serviceRequest.getMostActiveClient(driver.getId());
        if (person != null) {
            this.celMaiActivClientTextField.setText(person.getName());
        }
    }

    @Override
    public void update(AbstractEvent abstractEvent) {
        if (abstractEvent.getClass().equals(RequestEvent.class)) {
            RequestEvent requestEvent = (RequestEvent) abstractEvent;
            switch (requestEvent.getEventType()) {
                case REQUEST_ACCEPTED_CLIENT -> {
                    if (requestEvent.getNewEntity().getDriver().equals(driver)) {
                        this.serviceRequest.saveRequest(requestEvent.getNewEntity());
                        this.updateMean();
                        this.updateMostActiveClient();
                        this.getInitialClientsOnPage();
                        this.requestTableView.getItems().removeIf(personDTO -> personDTO.getPerson().equals(requestEvent.getNewEntity().getPerson()));
                    }
                }
                case REQUEST_REJECTED_CLIENT -> {
                    if (requestEvent.getNewEntity().getDriver().equals(driver)) {
                        this.requestTableView.getItems().removeIf(personDTO -> personDTO.getPerson().equals(requestEvent.getNewEntity().getPerson()));
                    }
                }
            }
        } else if (abstractEvent.getClass().equals(SentRequestEvent.class)) {
            SentRequestEvent sentRequestEvent = (SentRequestEvent) abstractEvent;
            if (Objects.requireNonNull(sentRequestEvent.getEventType()) == EventType.REQUEST_SENT) {
                this.requestTableView.getItems().add(sentRequestEvent.getNewEntity());
            }
        } else if (abstractEvent.getClass().equals(TimeEvent.class)) {
            TimeEvent timeEvent = (TimeEvent) abstractEvent;
            if (Objects.requireNonNull(timeEvent.getEventType()) == EventType.REQUEST_ACCEPTED_TAXI) {
                this.requestTableView.getItems().removeIf(personDTO -> personDTO.getPerson().equals(timeEvent.getNewEntity().getSecond()));
            }
        }
    }
}
