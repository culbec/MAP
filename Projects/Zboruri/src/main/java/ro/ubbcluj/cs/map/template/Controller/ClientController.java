package ro.ubbcluj.cs.map.template.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ro.ubbcluj.cs.map.template.Domain.Client;
import ro.ubbcluj.cs.map.template.Domain.Flight;
import ro.ubbcluj.cs.map.template.Domain.FlightDTO;
import ro.ubbcluj.cs.map.template.Domain.Ticket;
import ro.ubbcluj.cs.map.template.Service.ServiceFlight;
import ro.ubbcluj.cs.map.template.Service.ServiceTicket;
import ro.ubbcluj.cs.map.template.Utilities.Event.EventType;
import ro.ubbcluj.cs.map.template.Utilities.Event.TicketEvent;
import ro.ubbcluj.cs.map.template.Utilities.MessageAlert;
import ro.ubbcluj.cs.map.template.Utilities.Observer.ObservableImplemented;
import ro.ubbcluj.cs.map.template.Utilities.Observer.Observer;
import ro.ubbcluj.cs.map.template.Utilities.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ClientController extends ObservableImplemented<TicketEvent> implements Observer<TicketEvent> {
    private ServiceFlight serviceFlight;
    private ServiceTicket serviceTicket;
    private Client client;
    int pageNumber = 0;
    int pageSize = 5;
    @FXML
    private ComboBox<String> departureComboBox;
    @FXML
    private ComboBox<String> landingComboBox;
    @FXML
    private DatePicker departureDateDatePicker;
    @FXML
    private TableView<FlightDTO> flightTableView;
    @FXML
    private TableColumn<FlightDTO, String> departureTableColumn;
    @FXML
    private TableColumn<FlightDTO, String> landingTableColumn;
    @FXML
    private TableColumn<FlightDTO, LocalDateTime> departureTimeTableColumn;
    @FXML
    private TableColumn<FlightDTO, Integer> seatsTableColumn;
    @FXML
    private TableColumn<FlightDTO, Integer> ticketsTableColumn;

    public void initController(ServiceFlight serviceFlight, ServiceTicket serviceTicket, Client client) {
        this.serviceFlight = serviceFlight;
        this.serviceTicket = serviceTicket;
        this.client = client;

        // Adding the departure and landing locations.
        this.departureComboBox.getItems().addAll(this.serviceFlight.getDepartureLocations());
        this.landingComboBox.getItems().addAll(this.serviceFlight.getLandingLocations());

        this.flightTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.departureTableColumn.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getFlight().getFrom()));
        this.landingTableColumn.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getFlight().getTo()));
        this.departureTimeTableColumn.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().getFlight().getDepartureTime().truncatedTo(ChronoUnit.MINUTES)));
        this.seatsTableColumn.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().getFlight().getSeats()));
        this.ticketsTableColumn.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().getNumberOfTickets()));
    }

    public void handleDeparture() {
        String landingLocation = this.landingComboBox.getSelectionModel().getSelectedItem();
        LocalDate date = this.departureDateDatePicker.getValue();

        if (landingLocation != null && date != null) {
            this.getInitialPage();
        }
    }

    public void handleLanding() {
        String depatureLocation = this.departureComboBox.getSelectionModel().getSelectedItem();
        LocalDate date = this.departureDateDatePicker.getValue();

        if (depatureLocation != null && date != null) {
            this.getInitialPage();
        }
    }

    public void handleDepartureDate() {
        String depatureLocation = this.departureComboBox.getSelectionModel().getSelectedItem();
        String landingLocation = this.landingComboBox.getSelectionModel().getSelectedItem();

        if (depatureLocation != null && landingLocation != null) {
            this.getInitialPage();
        }
    }

    public void handleBuy() {
        if (this.flightTableView.getSelectionModel().getSelectedItem() == null) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "You need to select a flight!");
            return;
        }

        FlightDTO flightDTO = this.flightTableView.getSelectionModel().getSelectedItem();

        if (flightDTO.getNumberOfTickets() >= flightDTO.getFlight().getSeats()) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "There are no more tickets available for this flight!");
            return;
        }

        try {
            Ticket ticket = new Ticket(this.client.getUsername(), flightDTO.getFlight().getId(), LocalDateTime.now());
            this.serviceTicket.buyTicket(ticket);
        } catch (Exception e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", e.getMessage());
        }
    }

    private void getInitialPage() {
        this.pageNumber = 0;

        String depatureLocation = this.departureComboBox.getSelectionModel().getSelectedItem();
        String landingLocation = this.landingComboBox.getSelectionModel().getSelectedItem();
        LocalDate date = LocalDate.parse(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(this.departureDateDatePicker.getValue()));

        List<Flight> flights = this.serviceFlight.getFlightsFromToOnDate(depatureLocation, landingLocation, date.atStartOfDay(), new Pageable(this.pageNumber, this.pageSize));
        List<FlightDTO> flightDTOS = flights.stream().map(flight -> new FlightDTO(flight, this.serviceTicket.getNumberOfTicketsOfFlight(flight))).toList();

        this.flightTableView.getItems().clear();
        this.flightTableView.getItems().addAll(flightDTOS);
    }

    public void handlePreviousPage() {
        String depatureLocation = this.departureComboBox.getSelectionModel().getSelectedItem();
        String landingLocation = this.landingComboBox.getSelectionModel().getSelectedItem();
        LocalDate date = this.departureDateDatePicker.getValue();

        if (this.pageNumber - 1 >= 0) {
            List<Flight> flights = this.serviceFlight.getFlightsFromToOnDate(depatureLocation, landingLocation, date.atStartOfDay(), new Pageable(this.pageNumber - 1, this.pageSize));
            if (!flights.isEmpty()) {
                this.pageNumber -= 1;

                List<FlightDTO> flightDTOS = flights.stream().map(flight -> new FlightDTO(flight, this.serviceTicket.getNumberOfTicketsOfFlight(flight))).toList();

                this.flightTableView.getItems().clear();
                this.flightTableView.getItems().addAll(flightDTOS);
            }
        }
    }

    public void handleNextPage() {
        String depatureLocation = this.departureComboBox.getSelectionModel().getSelectedItem();
        String landingLocation = this.landingComboBox.getSelectionModel().getSelectedItem();
        LocalDate date = this.departureDateDatePicker.getValue();

        if (depatureLocation != null && landingLocation != null && date != null) {
            List<Flight> flights = this.serviceFlight.getFlightsFromToOnDate(depatureLocation, landingLocation, date.atStartOfDay(), new Pageable(this.pageNumber + 1, this.pageSize));
            if (!flights.isEmpty()) {
                this.pageNumber += 1;

                List<FlightDTO> flightDTOS = flights.stream().map(flight -> new FlightDTO(flight, this.serviceTicket.getNumberOfTicketsOfFlight(flight))).toList();

                this.flightTableView.getItems().clear();
                this.flightTableView.getItems().addAll(flightDTOS);
            }
        }
    }

    @Override
    public void update(TicketEvent ticketEvent) {
        if (ticketEvent.getEventType().equals(EventType.TICKET_BOUGHT)) {
            if (this.flightTableView.getItems().contains(ticketEvent.getOldEntity())) {
                this.flightTableView.getItems().set(this.flightTableView.getItems().indexOf(ticketEvent.getOldEntity()), ticketEvent.getNewEntity());
                this.flightTableView.refresh();
            }
        }
    }
}
