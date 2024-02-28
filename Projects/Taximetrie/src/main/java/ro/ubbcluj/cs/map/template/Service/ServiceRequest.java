package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Person;
import ro.ubbcluj.cs.map.template.Domain.Request;
import ro.ubbcluj.cs.map.template.Repository.RequestDBRepository;
import ro.ubbcluj.cs.map.template.Utilities.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceRequest {
    private final RequestDBRepository requestDBRepository;

    public ServiceRequest(RequestDBRepository requestDBRepository) {
        this.requestDBRepository = requestDBRepository;
    }

    public void saveRequest(Request request) {
        // No need to check for optional as a person can request many drives and a driver can do many requests.
        this.requestDBRepository.save(request);
    }

    public List<Person> getClientsOfDate(Long driverId, LocalDateTime date) {
        List<Request> requests = (List<Request>) this.requestDBRepository.getRequestsOfDriver(driverId);

        return requests.stream()
                .filter(request -> request.getDate().getYear() == date.getYear() &&
                        request.getDate().getMonth() == date.getMonth() &&
                        request.getDate().getDayOfMonth() == date.getDayOfMonth())
                .map(Request::getPerson)
                .distinct()
                .toList();
    }

    public List<Person> getClientsOfPage(Long driverId, Pageable pageable) {
        return (List<Person>) this.requestDBRepository.getClientsFromPage(this.requestDBRepository.getRequestsOfDriver(driverId), pageable);
    }

    public Double getMeanOfTheLast3Months(Long driverId) {
        return this.requestDBRepository.getMeanOfTheLast3Months(driverId);
    }

    public Person getMostActiveClient(Long driverId) {
        List<Person> personList = StreamSupport.stream(this.requestDBRepository.getRequestsOfDriver(driverId).spliterator(), false)
                .map(Request::getPerson)
                .toList();
        // Creating a map of Person + Number of entries.
        Map<Person, Long> requestCountByPerson = personList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        // Returning the entry with the maximum number of entries.
        Optional<Map.Entry<Person, Long>> maxEntry = requestCountByPerson.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        // Returning the maximum entry or null if it doesn't exist.
        return maxEntry.map(Map.Entry::getKey).orElse(null);
    }
}
