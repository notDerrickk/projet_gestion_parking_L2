//Rodéric
import org.junit.Test;
import static org.junit.Assert.*;

public class ParkingTest {
    
    @Test
    public void testConstructeur() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        
        assertNotNull(parking);
        assertEquals(1, parking.getIdParking());
        assertEquals("Parking Valin", parking.getNom());
        assertEquals("1 Place Valin", parking.getAdresse());
        assertEquals(50, parking.getNbPlacesTotales());
        assertEquals(75.0, parking.getTarifAbonnement(), 0.01);
        assertNotNull(parking.getListePlaces());
        assertEquals(0, parking.getListePlaces().size());
    }
    
    @Test
    public void testGetIdParking() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        assertEquals(1, parking.getIdParking());
    }
    
    @Test
    public void testSetIdParking() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        parking.setIdParking(2);
        assertEquals(2, parking.getIdParking());
    }
    
    @Test
    public void testGetNom() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        assertEquals("Parking Valin", parking.getNom());
    }
    
    @Test
    public void testSetNom() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        parking.setNom("Nouveau Parking");
        assertEquals("Nouveau Parking", parking.getNom());
    }
    
    @Test
    public void testGetAdresse() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        assertEquals("1 Place Valin", parking.getAdresse());
    }
    
    @Test
    public void testSetAdresse() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        parking.setAdresse("Nouvelle Adresse");
        assertEquals("Nouvelle Adresse", parking.getAdresse());
    }
    
    @Test
    public void testGetNbPlacesTotales() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        assertEquals(50, parking.getNbPlacesTotales());
    }
    
    @Test
    public void testSetNbPlacesTotales() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        parking.setNbPlacesTotales(100);
        assertEquals(100, parking.getNbPlacesTotales());
    }
    
    @Test
    public void testGetTarifAbonnement() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        assertEquals(75.0, parking.getTarifAbonnement(), 0.01);
    }
    
    @Test
    public void testSetTarifAbonnement() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        parking.setTarifAbonnement(80.0);
        assertEquals(80.0, parking.getTarifAbonnement(), 0.01);
    }
    
    @Test
    public void testCompterPlacesLibresAvecParkingVide() {
        Parking parking = new Parking(1, "Parking Valin", "1 Place Valin", 50, 75.0);
        assertEquals(0, parking.compterPlacesLibres());
    }
}