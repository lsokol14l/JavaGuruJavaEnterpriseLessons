package by.michael.jdbc.dao;

import by.michael.jdbc.dto.TicketFilter;
import by.michael.jdbc.entity.Ticket;
import by.michael.jdbc.exception.DaoException;
import by.michael.jdbc.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TicketDao implements Dao<Long, Ticket> {
  private static final TicketDao INSTANCE = new TicketDao();

  private static String SAVE_SQL =
      """
              insert into ticket (passport_no, passenger_name, flight_id, seat_no, cost)
              values (?, ?, ?, ?, ?)
              """;
  private static String DELETE_SQL =
      """
                delete from ticket where id = ?
              """;
  private static String FIND_ALL_SQL =
      """
              select id, passport_no, passenger_name, flight_id, seat_no, cost
              from ticket
          """;
  private static String FIND_BY_ID_SQL =
      """
              select id, passport_no, passenger_name, flight_id, seat_no, cost
              from ticket
              where id = ?
              """;
  private static String UPDATE_SQL =
      """
              update ticket
              set passport_no = ?,
                  passenger_name = ?,
                  flight_id = ?,
                  seat_no = ?,
                  cost = ?
              where id = ?
              """;

  private TicketDao() {}

  public static TicketDao getInstance() {
    return INSTANCE;
  }

  public boolean update(Ticket ticket) {
    try (Connection connection = ConnectionManager.get();
        PreparedStatement pst = connection.prepareStatement(UPDATE_SQL)) {
      pst.setString(1, ticket.getPassportNo());
      pst.setString(2, ticket.getPassengerName());
      pst.setLong(3, ticket.getFlightId());
      pst.setString(4, ticket.getSeatNo());
      pst.setBigDecimal(5, ticket.getCost());
      pst.setLong(6, ticket.getId());

      return pst.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  public Optional<Ticket> findById(Long id) {
    try (Connection connection = ConnectionManager.get();
        PreparedStatement pst = connection.prepareStatement(FIND_BY_ID_SQL)) {
      pst.setLong(1, id);

      ResultSet resultSet = pst.executeQuery();
      Ticket ticket = null;

      if (resultSet.next()) ticket = buildTicket(resultSet);

      return Optional.ofNullable(ticket);
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  private static Ticket buildTicket(ResultSet resultSet) throws SQLException {
    return new Ticket(
        resultSet.getLong("id"),
        resultSet.getString("passport_no"),
        resultSet.getString("passenger_name"),
        resultSet.getLong("flight_id"),
        resultSet.getString("seat_no"),
        resultSet.getBigDecimal("cost"));
  }

  public List<Ticket> findAll(TicketFilter ticketFilter) {
    List<Object> parameters = new ArrayList<>();
    List<String> whereSql = new ArrayList<>();

    if (ticketFilter.passengerName() != null) {
      parameters.add(ticketFilter.passengerName());
      whereSql.add("passenger_name = ?");
    }
    if (ticketFilter.seatNo() != null) {
      parameters.add("%" + ticketFilter.seatNo() + "%");
      whereSql.add("seat_no ilike ?");
    }
    parameters.add(ticketFilter.limit());
    parameters.add(ticketFilter.offset());
    String sqlString =
        whereSql.stream()
            .collect(
                Collectors.joining(
                    " AND ", parameters.size() > 2 ? " WHERE " : "", " LIMIT ? OFFSET ?"));

    try (Connection connection = ConnectionManager.get();
        PreparedStatement pst = connection.prepareStatement(FIND_ALL_SQL + sqlString)) {
      List<Ticket> tickets = new ArrayList<>();
      for (int i = 0; i < parameters.size(); i++) {
        pst.setObject(i + 1, parameters.get(i));
      }

      ResultSet resultSet = pst.executeQuery();
      while (resultSet.next()) tickets.add(buildTicket(resultSet));

      return tickets;
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  public List<Ticket> findAll() {
    try (Connection connection = ConnectionManager.get();
        PreparedStatement pst = connection.prepareStatement(FIND_ALL_SQL)) {
      List<Ticket> tickets = new ArrayList<>();
      ResultSet resultSet = pst.executeQuery();

      while (resultSet.next()) tickets.add(buildTicket(resultSet));

      return tickets;
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }

  public Ticket save(Ticket ticket) {
    try (var connection = ConnectionManager.get();
        var pst = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
      pst.setString(1, ticket.getPassportNo());
      pst.setString(2, ticket.getPassengerName());
      pst.setLong(3, ticket.getFlightId());
      pst.setString(4, ticket.getSeatNo());
      pst.setBigDecimal(5, ticket.getCost());

      pst.executeUpdate();

      var keys = pst.getGeneratedKeys();
      if (keys.next()) ticket.setId(keys.getLong("id"));

    } catch (SQLException e) {
      throw new DaoException(e);
    }

    return ticket;
  }

  public boolean delete(Long id) {
    try (Connection connection = ConnectionManager.get();
        PreparedStatement pst = connection.prepareStatement(DELETE_SQL)) {
      pst.setLong(1, id);

      return pst.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new DaoException(e);
    }
  }
}
