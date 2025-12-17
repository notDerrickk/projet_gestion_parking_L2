//Rodéric
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class PaiementTest {
    
    @Test
    public void testPaiementAvecTicketValide() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(2);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        ticket.setMontant(15.0);
        
        Paiement paiement = new Paiement(ticket, 1, 1, 15.0, "Carte bancaire");
        
        assertEquals(1, paiement.getIdPaiement());
        assertEquals(1, paiement.getIdTicket());
        assertEquals(15.0, paiement.getMontant(), 0.01);
        assertEquals("Carte bancaire", paiement.getMoyenPaiement());
        assertNotNull(paiement.getDatePaiement());
        assertTrue(ticket.estPaye());
    }
    
    @Test
    public void testGetIdPaiement() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(1);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        ticket.setMontant(10.0);
        
        Paiement paiement = new Paiement(ticket, 5, 1, 10.0, "Espèces");
        assertEquals(5, paiement.getIdPaiement());
    }
    
    @Test
    public void testGetIdTicket() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(1);
        Ticket ticket = new Ticket(3, "AB-123-CD", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        ticket.setMontant(10.0);
        
        Paiement paiement = new Paiement(ticket, 1, 3, 10.0, "Espèces");
        assertEquals(3, paiement.getIdTicket());
    }
    
    @Test
    public void testGetMontant() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(1);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        ticket.setMontant(25.50);
        
        Paiement paiement = new Paiement(ticket, 1, 1, 25.50, "Carte bancaire");
        assertEquals(25.50, paiement.getMontant(), 0.01);
    }
    
    @Test
    public void testGetMoyenPaiement() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(1);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        ticket.setMontant(10.0);
        
        Paiement paiement = new Paiement(ticket, 1, 1, 10.0, "Chèque");
        assertEquals("Chèque", paiement.getMoyenPaiement());
    }
    
    @Test
    public void testPaiementMarqueTicketCommePaye() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(1);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        ticket.setMontant(12.0);
        
        assertFalse(ticket.estPaye());
        
        Paiement paiement = new Paiement(ticket, 1, 1, 12.0, "Carte bancaire");
        
        assertTrue(ticket.estPaye());
    }
    
    @Test(expected = IllegalStateException.class)
    public void testPaiementTicketNonFerme() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(1);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        ticket.setMontant(10.0);
        
        Paiement paiement = new Paiement(ticket, 1, 1, 10.0, "Carte bancaire");
    }
    
    @Test(expected = IllegalStateException.class)
    public void testPaiementTicketDejaPaye() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(2);
        Ticket ticket = new Ticket(1, "AB-123-CD", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        ticket.setMontant(15.0);
        ticket.setEstPaye(true);
        
        Paiement paiement = new Paiement(ticket, 1, 1, 15.0, "Espèces");
    }
    
    @Test
    public void testPaiementAvecEspeces() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(1);
        Ticket ticket = new Ticket(2, "EF-456-GH", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        ticket.setMontant(10.0);
        
        Paiement paiement = new Paiement(ticket, 2, 2, 10.0, "Espèces");
        assertEquals(2, paiement.getIdPaiement());
        assertEquals(10.0, paiement.getMontant(), 0.01);
        assertEquals("Espèces", paiement.getMoyenPaiement());
    }
    
    @Test
    public void testPaiementAvecCheque() {
        LocalDateTime heureEntree = LocalDateTime.now().minusHours(1);
        Ticket ticket = new Ticket(3, "IJ-789-KL", heureEntree);
        ticket.setHeureSortie(LocalDateTime.now());
        ticket.setMontant(25.75);
        
        Paiement paiement = new Paiement(ticket, 3, 3, 25.75, "Chèque");
        assertEquals(3, paiement.getIdPaiement());
        assertEquals(25.75, paiement.getMontant(), 0.01);
        assertEquals("Chèque", paiement.getMoyenPaiement());
    }
}