//Rodéric
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class TicketTest {
    
    @Test
    public void testConstructeur() {
        LocalDateTime heureEntree = LocalDateTime.of(2025, 10, 29, 10, 0);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        
        assertNotNull(ticket);
        assertEquals(1, ticket.getIdTicket());
        assertEquals("AB-123-CD", ticket.getImmatriculation());
        assertEquals(heureEntree, ticket.getHeureEntree());
        assertNull(ticket.getHeureSortie());
        assertEquals(0.0, ticket.getMontant(), 0.01);
        assertFalse(ticket.estPaye());
    }
    
    @Test
    public void testGetIdTicket() {
        LocalDateTime heureEntree = LocalDateTime.now();
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        assertEquals(1, ticket.getIdTicket());
    }
    
    @Test
    public void testGetImmatriculation() {
        LocalDateTime heureEntree = LocalDateTime.now();
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        assertEquals("AB-123-CD", ticket.getImmatriculation());
    }
    
    @Test
    public void testGetHeureEntree() {
        LocalDateTime heureEntree = LocalDateTime.of(2025, 10, 29, 10, 0);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        assertEquals(heureEntree, ticket.getHeureEntree());
    }
    
    @Test
    public void testSetHeureSortie() {
        LocalDateTime heureEntree = LocalDateTime.now();
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        
        LocalDateTime heureSortie = LocalDateTime.now().plusHours(2);
        ticket.setHeureSortie(heureSortie);
        assertEquals(heureSortie, ticket.getHeureSortie());
    }
    
    @Test
    public void testSetMontant() {
        LocalDateTime heureEntree = LocalDateTime.now();
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        
        ticket.setMontant(15.50);
        assertEquals(15.50, ticket.getMontant(), 0.01);
    }
    
    @Test
    public void testEstPaye() {
        LocalDateTime heureEntree = LocalDateTime.now();
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        assertFalse(ticket.estPaye());
    }
    
    @Test
    public void testSetEstPaye() {
        LocalDateTime heureEntree = LocalDateTime.now();
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        
        ticket.setEstPaye(true);
        assertTrue(ticket.estPaye());
    }
    
    @Test
    public void testEstFermeQuandTicketOuvert() {
        LocalDateTime heureEntree = LocalDateTime.now();
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        assertFalse(ticket.estFerme());
    }
    
    @Test
    public void testEstFermeQuandTicketFerme() {
        LocalDateTime heureEntree = LocalDateTime.now();
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        assertTrue(ticket.estFerme());
    }
        
    @Test
    public void testTicketComplet() {
        LocalDateTime heureEntree = LocalDateTime.of(2025, 10, 29, 10, 0);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        
        LocalDateTime heureSortie = heureEntree.plusHours(3);
        ticket.setHeureSortie(heureSortie);
        ticket.setMontant(20.0);
        ticket.setEstPaye(true);
        
        assertEquals(heureSortie, ticket.getHeureSortie());
        assertEquals(20.0, ticket.getMontant(), 0.01);
        assertTrue(ticket.estPaye());
        assertEquals(180, ticket.calculerDureeMinutes());
    }
}