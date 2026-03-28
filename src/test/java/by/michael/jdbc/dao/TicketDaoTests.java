package by.michael.jdbc.dao;

import static org.junit.jupiter.api.Assertions.*;

import by.michael.jdbc.entity.Flight;
import by.michael.jdbc.entity.Ticket;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.michael.jdbc.utils.ConnectionManager;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TicketDaoTests {
  private static TicketDao ticketDao;

  @BeforeAll
  static void setUp() {
    ticketDao = TicketDao.getInstance();
  }

  @AfterEach
  void resetSequences() throws SQLException {
    try (Connection conn = ConnectionManager.get()) {
      conn.createStatement().execute("ALTER SEQUENCE ticket_id_seq RESTART WITH 1");
    }
  }

  @Test
  @Order(1)
  @DisplayName("Find ticket by existing ID")
  void testFindByIdWithCorrectId() {
    Optional<Ticket> ticket = ticketDao.findById(1L);

    assertTrue(ticket.isPresent());
    assertEquals("Иван Иванов", ticket.get().getPassengerName());
    assertEquals("112233", ticket.get().getPassportNo());
    assertEquals(1L, ticket.get().getFlight().getId());
  }

  @Test
  @Order(2)
  @DisplayName("Find ticket by non-existing ID")
  void testFindByIdWithIncorrectId() {
    Optional<Ticket> ticket = ticketDao.findById(999999L);
    assertTrue(ticket.isEmpty());
  }

  @Test
  @Order(3)
  @DisplayName("Find all tickets")
  void testFindAll() {
    List<Ticket> tickets = ticketDao.findAll();

    assertNotNull(tickets);
    assertTrue(tickets.size() >= 3);
  }

  //  @Test
  //  @Order(4)
  //  @DisplayName("Save new ticket")
  //  void testSave() {
  //    Flight newFlight = new Flight();
  //    newFlight.setId(0L);
  //
  //    Ticket newTicket =
  //        new Ticket(100L, "ZZ9999", "Test Passenger", newFlight, "Z99", new BigDecimal("99.99"));
  //
  //    Ticket saved = ticketDao.save(newTicket);
  //
  //    assertNotNull(saved.getId());
  //    assertEquals("Test Passenger", saved.getPassengerName());
  //    assertEquals("ZZ9999", saved.getPassportNo());
  //  }

  //  @Test
  //  @Order(5)
  //  @DisplayName("Delete ticket")
  //  void testDeleteCorrectId() {
  //    boolean deleted = ticketDao.delete(100L);
  //
  //    assertTrue(deleted);
  //
  //    Optional<Ticket> found = ticketDao.findById(100L);
  //    assertTrue(found.isEmpty());
  //  }

  @Test
  @Order(6)
  @DisplayName("Update existing ticket")
  void testUpdate() {
    Ticket ticket = ticketDao.findById(1L).orElseThrow();
    String originalName = ticket.getPassengerName();
    ticket.setPassengerName("Updated Name");

    boolean updated = ticketDao.update(ticket);
    assertTrue(updated);

    Ticket updatedTicket = ticketDao.findById(1L).orElseThrow();
    assertEquals("Updated Name", updatedTicket.getPassengerName());

    // Вернуть обратно для других тестов
    ticket.setPassengerName(originalName);
    ticketDao.update(ticket);
  }

  //  @Test
  //  @Order(7)
  //  @DisplayName("Delete ticket")
  //  void testDelete() {
  //    Flight newFlight = new Flight();
  //    newFlight.setId(0L);
  //    // Создаем билет специально для удаления
  //    Ticket newTicket = new Ticket(null, "DELETE123", "To Delete", newFlight, "D1",
  // BigDecimal.TEN);
  //    Ticket saved = ticketDao.save(newTicket);
  //    Long idToDelete = saved.getId();
  //
  //    boolean deleted = ticketDao.delete(idToDelete);
  //    assertTrue(deleted);
  //
  //    Optional<Ticket> found = ticketDao.findById(idToDelete);
  //    assertTrue(found.isEmpty());
  //  }

  @Test
  @Order(8)
  @DisplayName("find most popular names")
  void testFindMostPopularNames() {
    List<String> mostPopularPassengerNames = ticketDao.findMostPopularPassengerNames();

    assertFalse(mostPopularPassengerNames.isEmpty());
    assertEquals("Иван", mostPopularPassengerNames.getFirst());
  }

  @Test
  @Order(9)
  @DisplayName("find names and count tickets")
  void testFindPassengerNameAndTicketCount() {
    Map<String, Long> passengerNameAndTicketCount = ticketDao.findPassengerNameAndTicketCount();

    assertFalse(passengerNameAndTicketCount.isEmpty());
    assertEquals(4, passengerNameAndTicketCount.get("Иван"));
  }
}
