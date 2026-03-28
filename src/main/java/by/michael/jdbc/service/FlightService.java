package by.michael.jdbc.service;

import by.michael.jdbc.dao.FlightDao;
import by.michael.jdbc.dto.FlightDto;

import java.lang.reflect.Array;
import java.util.List;

public class FlightService {
  private static final FlightService INSTANCE = new FlightService();
  private final FlightDao flightDao = FlightDao.getInstance();

  private FlightService() {}

  public static FlightService getInstance() {
    return INSTANCE;
  }

  public List<FlightDto> findAll() {
    return flightDao.findAll().stream()
        .map(
            flight ->
                new FlightDto(
                    flight.getId(),
                    "%s - %s - %s - %s"
                        .formatted(
                            flight.getFlightNo(),
                            flight.getArrivalAirportCode(),
                            flight.getDepartureAirportCode(),
                            flight.getStatus())))
        .toList();
  }
}
