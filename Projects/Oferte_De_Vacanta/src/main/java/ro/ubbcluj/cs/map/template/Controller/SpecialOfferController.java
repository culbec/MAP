package ro.ubbcluj.cs.map.template.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.ubbcluj.cs.map.template.Domain.Hotel;
import ro.ubbcluj.cs.map.template.Domain.SpecialOffer;
import ro.ubbcluj.cs.map.template.Service.ServiceSpecialOffer;

import java.time.LocalDate;

public class SpecialOfferController {
    public DatePicker datePicker;
    @FXML
    private TableView<SpecialOffer> specialOfferTableView;
    @FXML
    private TableColumn<SpecialOffer, String> startDateTableColumn;
    @FXML
    private TableColumn<SpecialOffer, String> endDateTableColumn;
    @FXML
    private TableColumn<SpecialOffer, Integer> percentsTableColumn;

    private ServiceSpecialOffer serviceSpecialOffer;
    private Hotel hotel;

    public void initController(Hotel hotel, ServiceSpecialOffer serviceSpecialOffer) {
        this.hotel = hotel;
        this.serviceSpecialOffer = serviceSpecialOffer;

        this.startDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        this.endDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        this.percentsTableColumn.setCellValueFactory(new PropertyValueFactory<>("percents"));
    }

    public void handleDatePicker() {
        LocalDate date = this.datePicker.getValue();
        this.specialOfferTableView.getItems().clear();

        this.specialOfferTableView.getItems().addAll(this.serviceSpecialOffer.getSpecialOffersOfHotelOnDate(this.hotel.getHotelId(), date));
    }
}
