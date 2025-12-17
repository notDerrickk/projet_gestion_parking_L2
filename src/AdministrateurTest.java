// Rodéric
import org.junit.Test;
import static org.junit.Assert.*;

public class AdministrateurTest {
    
    @Test
    public void testConstructeur() {
        Administrateur admin = new Administrateur(1, "Dupont", "Jean", "jean@mail.com", "admin123", 100, "adminJD");
        
        assertNotNull(admin);
        assertEquals(1, admin.getIdUtilisateur());
        assertEquals("Dupont", admin.getNom());
        assertEquals("Jean", admin.getPrenom());
        assertEquals("jean@mail.com", admin.getEmail());
        assertEquals("admin123", admin.getMotDePasse());
        assertEquals(100, admin.getIdAdmin());
        assertEquals("adminJD", admin.getLogin());
    }
    
    @Test
    public void testGetIdAdmin() {
        Administrateur admin = new Administrateur(1, "Dupont", "Jean", "jean@mail.com", "admin123", 100, "adminJD");
        assertEquals(100, admin.getIdAdmin());
    }
    
    @Test
    public void testSetIdAdmin() {
        Administrateur admin = new Administrateur(1, "Dupont", "Jean", "jean@mail.com", "admin123", 100, "adminJD");
        admin.setIdAdmin(200);
        assertEquals(200, admin.getIdAdmin());
    }
    
    @Test
    public void testGetLogin() {
        Administrateur admin = new Administrateur(1, "Dupont", "Jean", "jean@mail.com", "admin123", 100, "adminJD");
        assertEquals("adminJD", admin.getLogin());
    }
    
    @Test
    public void testSetLogin() {
        Administrateur admin = new Administrateur(1, "Dupont", "Jean", "jean@mail.com", "admin123", 100, "adminJD");
        admin.setLogin("newLogin");
        assertEquals("newLogin", admin.getLogin());
    }
    
    @Test
    public void testSetNom() {
        Administrateur admin = new Administrateur(1, "Dupont", "Jean", "jean@mail.com", "admin123", 100, "adminJD");
        admin.setNom("Martin");
        assertEquals("Martin", admin.getNom());
    }
    
    @Test
    public void testSetPrenom() {
        Administrateur admin = new Administrateur(1, "Dupont", "Jean", "jean@mail.com", "admin123", 100, "adminJD");
        admin.setPrenom("Paul");
        assertEquals("Paul", admin.getPrenom());
    }
    
    @Test
    public void testSetEmail() {
        Administrateur admin = new Administrateur(1, "Dupont", "Jean", "jean@mail.com", "admin123", 100, "adminJD");
        admin.setEmail("nouveau@mail.com");
        assertEquals("nouveau@mail.com", admin.getEmail());
    }
    
    @Test
    public void testSetMotDePasse() {
        Administrateur admin = new Administrateur(1, "Dupont", "Jean", "jean@mail.com", "admin123", 100, "adminJD");
        admin.setMotDePasse("newpass");
        assertEquals("newpass", admin.getMotDePasse());
    }
    
}