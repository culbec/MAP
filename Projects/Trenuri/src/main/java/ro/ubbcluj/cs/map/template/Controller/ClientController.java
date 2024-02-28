package ro.ubbcluj.cs.map.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import ro.ubbcluj.cs.map.template.Domain.City;
import ro.ubbcluj.cs.map.template.Domain.TicketDTO;
import ro.ubbcluj.cs.map.template.Domain.TrainStation;
import ro.ubbcluj.cs.map.template.Domain.Tuple;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Service.ServiceCity;
import ro.ubbcluj.cs.map.template.Service.ServiceTicket;
import ro.ubbcluj.cs.map.template.Service.ServiceTrainStation;
import ro.ubbcluj.cs.map.template.Utilities.Constants;
import ro.ubbcluj.cs.map.template.Utilities.Event.EventType;
import ro.ubbcluj.cs.map.template.Utilities.Event.SelectionEvent;
import ro.ubbcluj.cs.map.template.Utilities.MessageAlert;
import ro.ubbcluj.cs.map.template.Utilities.Observer.Observer;

import java.time.LocalDate;
import java.util.List;

public class ClientController implements Observer<SelectionEvent> {
    private ServiceCity serviceCity;
    private ServiceTrainStation serviceTrainStation;
    private ServiceTicket serviceTicket;

    @FXML
    private ComboBox<City> departureComboBox;
    @FXML
    private ComboBox<City> destinationComboBox;
    @FXML
    private TextArea routesTextArea;
    @FXML
    private HBox viewersHBox;
    @FXML
    private ComboBox<String> trainComboBox;
    @FXML
    private DatePicker ticketDatePicker;
    @FXML
    private TableView<TicketDTO> ticketTableView;
    @FXML
    private TableColumn<TicketDTO, String> cityTableColumn;
    @FXML
    private TableColumn<TicketDTO, String> trainTableColumn;
    @FXML
    private TableColumn<TicketDTO, Integer> numberOfTicketsTableColumn;
    @FXML
    private HBox mostSoldTicketHBox;

    private void initComboBoxes() {
        this.departureComboBox.getItems().addAll(this.serviceCity.getCities());
        this.destinationComboBox.getItems().addAll(this.serviceCity.getCities());

        // Adding events for the combo boxes.
        this.departureComboBox.setOnAction(event -> {
            City destinationCity = destinationComboBox.getValue();
            if (destinationCity != null) {
                this.serviceTrainStation.computeViewers();
            }

            // Retrieving all the trains that depart from departure city and adds them to the train combo box.
            this.trainComboBox.getItems().clear();
            try {
                this.trainComboBox.getItems().addAll(this.serviceTrainStation.getTrainsFromCity(this.getDepartureCity().getId()));
            } catch (ServiceException e) {
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        });

        this.destinationComboBox.setOnAction(event -> {
            City departureCity = departureComboBox.getValue();
            if (departureCity != null) {
                this.serviceTrainStation.computeViewers();
            }
        });
    }

    private void initTicketModel() {
        this.cityTableColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        this.trainTableColumn.setCellValueFactory(new PropertyValueFactory<>("trainId"));
        this.numberOfTicketsTableColumn.setCellValueFactory(new PropertyValueFactory<>("noTickets"));
    }

    private void initMostSoldTicketLabel() {
        try {
            Tuple<String, LocalDate> tuple = this.serviceTicket.getMostSoldTicket();
            if (tuple != null) {
                this.mostSoldTicketHBox.getChildren().clear();
                String label = Constants.MOST_SOLD_TICKET_LABEL + tuple.getFirst() + " on " + Constants.DATE_TIME_FORMATTER.format(tuple.getSecond());
                this.mostSoldTicketHBox.getChildren().add(new Label(label));
            }
        } catch (ServiceException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    public void initController(ServiceCity serviceCity, ServiceTrainStation serviceTrainStation, ServiceTicket serviceTicket) {
        this.serviceCity = serviceCity;
        this.serviceTrainStation = serviceTrainStation;
        this.serviceTicket = serviceTicket;

        this.initComboBoxes();
        this.initTicketModel();
        this.initMostSoldTicketLabel();
    }

    public City getDepartureCity() {
        return departureComboBox.getValue();
    }

    public City getDestinationCity() {
        return destinationComboBox.getValue();
    }

    private void populateRoutes(List<List<TrainStation>> routes) {
        this.routesTextArea.clear();

        for (List<TrainStation> route : routes) {
            StringBuilder routeString = new StringBuilder();
            for (TrainStation trainStation : route) {
                routeString.append(this.serviceCity.getCity(trainStation.getDepartureCityId())).append(" - ").append(trainStation.getTrainId()).append(" -> ");
            }
            routeString.append(this.serviceCity.getCity(route.get(route.size() - 1).getDestinationCityId()));
            routeString.append("\n");
            this.routesTextArea.appendText(routeString.toString());
        }
    }

    public void handleSearch() {
        City departureCity = departureComboBox.getValue();
        City destinationCity = destinationComboBox.getValue();

        if (departureCity == null || destinationCity == null) {
            return;
        }

        List<List<TrainStation>> routes = this.serviceTrainStation.getRoutesBetweenCities(departureCity.getId(), destinationCity.getId());
        this.populateRoutes(routes);
    }

    public void handleBuyTicket() {
        if (this.getDepartureCity() == null) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "You must select a departure city!");
            return;
        }
        if (this.trainComboBox.getValue() == null) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "You must select a train!");
            return;
        }
        if (this.ticketDatePicker.getValue() == null) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", "You must select a date!");
            return;
        }

        String departureCityId = this.getDepartureCity().getId();
        String trainId = this.trainComboBox.getValue();
        LocalDate date = this.ticketDatePicker.getValue();

        try {
            this.serviceTicket.addTicket(trainId, departureCityId, date);
        } catch (ServiceException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error", e.getMessage());
        }

        // Populating the ticket table view with all the tickets bought on the selected date from the departure city.
        List<TicketDTO> ticketDTOS = this.serviceTicket.countTickets(departureCityId, date).entrySet().stream()
                .map(entry -> {
                    String cityName = this.serviceCity.getCity(entry.getKey().getDepartureCityId()).getName();
                    String _trainId = entry.getKey().getTrainId();
                    int noTickets = entry.getValue();
                    return new TicketDTO(cityName, _trainId, noTickets);
                })
                .toList();
        this.ticketTableView.getItems().clear();
        this.ticketTableView.getItems().addAll(ticketDTOS);
        this.initMostSoldTicketLabel();
    }

    @Override
    public void update(SelectionEvent selectionEvent) {
        City departureCity = departureComboBox.getValue();
        City destinationCity = destinationComboBox.getValue();

        if (departureCity == null || destinationCity == null) {
            return;
        }

        if (selectionEvent.getEventType().equals(EventType.RECALCULATE_VIEWERS)) {
            Tuple<City, City> tuple = new Tuple<>(departureComboBox.getValue(), destinationComboBox.getValue());
            Integer viewers = selectionEvent.getRoutes().get(tuple);

            this.viewersHBox.getChildren().clear();
            if (viewers != 0) {
                String label = viewers + Constants.VIEWER_LABEL;
                this.viewersHBox.getChildren().add(new Label(label));
            }
        }
    }
}
