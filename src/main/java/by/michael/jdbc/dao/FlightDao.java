package by.michael.jdbc.dao;

import by.michael.jdbc.entity.Flight;
import by.michael.jdbc.entity.FlightStatus;
import by.michael.jdbc.exception.DaoException;
import by.michael.jdbc.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {
  private static final FlightDao INSTANCE = new FlightDao();

  private FlightDao() {}

  public static FlightDao getInstance() {
    return INSTANCE;
  }

  private static final String UPDATE_SQL =
      """
        update flight set flight_no = ?,
                          departure_date = ?,
                          departure_airport_code = ?,
                          arrival_date = ?,
                          arrival_airport_code = ?,
                          aircraft_id = ?,
                          status = ?
                          where id = ?
        """;

  private static final String FIND_ALL_SQL =
      """
            select id,
                   flight_no,
                   departure_date,
                   departure_airport_code,
                   arrival_date,
                   arrival_airport_code,
                   aircraft_id,
                   status from flight
          """;

  private static final String FIND_BY_ID_SQL =
      FIND_ALL_SQL
          + """
          where id = ?
          """;

  private static final String DELETE_SQL =
      """
          delete
          from flight
          where id = ?
          """;

  private static final String SAVE_SQL =
      """
                    insert into flight (
                           flight_no,
                           departure_date,
                           departure_airport_code,
                           arrival_date,
                           arrival_airport_code,
                           aircraft_id,
                           status) values (?, ?, ?, ?, ?, ?, ?)
                  """;

  @Override
  public boolean update(Flight flight) {
    try (var connection = ConnectionManager.get();
        PreparedStatement pst = connection.prepareStatement(UPDATE_SQL)) {

      pst.setString(1, flight.getFlightNo());
      pst.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
      pst.setString(3, flight.getDepartureAirportCode());
      pst.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
      pst.setString(5, flight.getArrivalAirportCode());
      pst.setLong(6, flight.getAircraftId());
      pst.setString(7, String.valueOf(flight.getStatus()));

      pst.setLong(8, flight.getId());

      return pst.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Optional<Flight> findById(Long id) {
    try (Connection connection = ConnectionManager.get(); ) {
      return findById(id, connection);
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  private static Flight buildFlight(ResultSet resultSet) throws SQLException {
    return new Flight(
        resultSet.getLong("id"),
        resultSet.getString("flight_no"),
        resultSet.getTimestamp("departure_date").toLocalDateTime(),
        resultSet.getString("departure_airport_code"),
        resultSet.getTimestamp("arrival_date").toLocalDateTime(),
        resultSet.getString("arrival_airport_code"),
        resultSet.getLong("aircraft_id"),
        FlightStatus.valueOf(resultSet.getString("status")));
  }

  @Override
  public List<Flight> findAll() {
    try (Connection connection = ConnectionManager.get();
        PreparedStatement pst = connection.prepareStatement(FIND_ALL_SQL)) {
      List<Flight> flights = new ArrayList<>();
      ResultSet resultSet = pst.executeQuery();

      while (resultSet.next()) flights.add(buildFlight(resultSet));

      return flights;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Flight save(Flight flight) {
    try (Connection connection = ConnectionManager.get();
        PreparedStatement pst = connection.prepareStatement(SAVE_SQL)) {
      pst.setString(1, flight.getFlightNo());
      pst.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
      pst.setString(3, flight.getDepartureAirportCode());
      pst.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
      pst.setString(5, flight.getArrivalAirportCode());
      pst.setLong(6, flight.getAircraftId());
      pst.setString(7, String.valueOf(flight.getStatus()));

      pst.executeUpdate();
      var keys = pst.getGeneratedKeys();
      if (keys.next()) flight.setId(keys.getLong("id"));

      return flight;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean delete(Long id) {
    try (Connection connection = ConnectionManager.get();
        PreparedStatement pst = connection.prepareStatement(DELETE_SQL)) {
      pst.setLong(1, id);
      int result = pst.executeUpdate();
      return result > 0;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<Flight> findById(Long id, Connection connection) {
    try (var pst = connection.prepareStatement(FIND_BY_ID_SQL)) {
      pst.setLong(1, id);
      ResultSet resultSet = pst.executeQuery();
      Flight flight = null;
      if (resultSet.next()) flight = buildFlight(resultSet);

      return Optional.ofNullable(flight);
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }
}
