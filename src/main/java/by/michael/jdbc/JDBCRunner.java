package by.michael.jdbc;

import by.michael.jdbc.dao.FlightDao;
import by.michael.jdbc.dao.TicketDao;
import by.michael.jdbc.entity.Flight;
import by.michael.jdbc.entity.FlightStatus;
import by.michael.jdbc.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JDBCRunner {
  static void main() {
    //    FlightDao flightDao = FlightDao.getInstance();

    // 9,QS8712,2020-12-18 03:35:00.000000,MNK,2020-12-18 06:46:00.000000,LDN,2,ARRIVED
    //    Flight flight = new Flight();
    //    flight.setId(10L);
    //    flight.setFlightNo("QS8712");
    //    flight.setDepartureDate(LocalDateTime.of(LocalDate.of(2020, 12, 18), LocalTime.of(3,
    // 35)));
    //    flight.setDepartureAirportCode("MNK");
    //    flight.setArrivalDate(LocalDateTime.of(LocalDate.of(2020, 12, 18), LocalTime.of(6, 46)));
    //    flight.setArrivalAirportCode("LDN");
    //    flight.setAircraftId(2L);
    //    flight.setStatus(FlightStatus.ARRIVED);

    //    System.out.println(flightDao.save(flight));

    //    System.out.println(flightDao.findAll());

    TicketDao ticketDao = TicketDao.getInstance();
    System.out.println(ticketDao.findById(58L));
  }

  public static List<Long> getTicketsByFlightId(Long flightId) {
    List<Long> result = new ArrayList<>();

    String sql =
        """
              select id
              from ticket
              where flight_id = ?;
              """;

    try (var connection = ConnectionManager.get();
        var pst = connection.prepareStatement(sql)) {

      pst.setLong(1, flightId);

      ResultSet resultSet = pst.executeQuery();
      while (resultSet.next()) {
        result.add(resultSet.getLong("id"));
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  public static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) {
    List<Long> flights = new ArrayList<>();
    String sql =
        """
                select id
                from flight
                where departure_date > ? and arrival_date <= ?;
                """;
    try (var connection = ConnectionManager.get();
        var pst = connection.prepareStatement(sql)) {
      pst.setTimestamp(1, Timestamp.valueOf(start));
      pst.setTimestamp(2, Timestamp.valueOf(end));
      ResultSet resultSet = pst.executeQuery();
      while (resultSet.next()) flights.add(resultSet.getLong("id"));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return flights;
  }
}
