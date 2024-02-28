package ro.ubbcluj.cs.map.template.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ro.ubbcluj.cs.map.template.Domain.Driver;
import ro.ubbcluj.cs.map.template.Domain.Person;
import ro.ubbcluj.cs.map.template.Domain.PersonDTO;
import ro.ubbcluj.cs.map.template.Domain.Request;
import ro.ubbcluj.cs.map.template.Utilities.Event.*;
import ro.ubbcluj.cs.map.template.Utilities.MessageAlert;
import ro.ubbcluj.cs.map.template.Utilities.Observer.ObservableImplemented;
import ro.ubbcluj.cs.map.template.Utilities.Observer.Observer;

import java.time.LocalDateTime;

public class PersonController extends ObservableImplemented<AbstractEvent> implements Observer<AbstractEvent> {
    @FXML
    public TextField indicativTextField;
    public TextField timpMaximTextField;
    public TextField locatieTextField;
    private Person person;
    private Driver driver = null;

    public void initController(Person person) {
        this.person = person;
    }

    @Override
    public void update(AbstractEvent abstractEvent) {
        if (abstractEvent.getClass().equals(TimeEvent.class)) {
            TimeEvent timeEvent = (TimeEvent) abstractEvent;

            switch (timeEvent.getEventType()) {
                case REQUEST_ACCEPTED_TAXI -> {
                    if (timeEvent.getNewEntity().getSecond().equals(person)) {
                        this.driver = timeEvent.getNewEntity().getFirst();
                        this.indicativTextField.setText(timeEvent.getNewEntity().getFirst().getIndicativMasina());
                        this.timpMaximTextField.setText(timeEvent.getNewEntity().getThird().toString());
                    }
                }
            }
        }
    }

    public void handleAccept() {
        if (this.driver != null) {
            Request request = new Request(null, person, driver, LocalDateTime.now());
            this.notify(new RequestEvent(EventType.REQUEST_ACCEPTED_CLIENT, request, null));

            this.timpMaximTextField.clear();
            this.indicativTextField.clear();
            this.driver = null;
        }
    }

    public void handleCancel() {
        if (this.driver != null) {
            Request request = new Request(null, person, this.driver, null);
            this.notify(new RequestEvent(EventType.REQUEST_REJECTED_CLIENT, request, null));

            this.timpMaximTextField.clear();
            this.indicativTextField.clear();
            this.driver = null;
        }
    }

    public void handleRequest() {
        if (this.locatieTextField.getText().isEmpty()) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "You need to introduce a location!");
            return;
        }

        String location = this.locatieTextField.getText();
        this.notify(new SentRequestEvent(EventType.REQUEST_SENT, new PersonDTO(this.person, location), null));
    }
}
