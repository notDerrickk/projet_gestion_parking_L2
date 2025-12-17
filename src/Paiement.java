//Rodéric
import java.time.LocalDateTime;

public class Paiement {
    private int idPaiement;
    private int idTicket;
    private double montant;
    private LocalDateTime datePaiement;
    private String moyenPaiement;
    
    public Paiement(Ticket ticket, int idPaiement, int idTicket, double montant, String moyenPaiement) {
        if (!ticket.estFerme()) {
            System.out.println("Impossible de créer un paiement : le véhicule n'est pas encore sorti du parking.");
        }

        if (ticket.estPaye()) {
            System.out.println("Impossible de créer un paiement : le ticket a déjà été payé.");
        }
        this.idPaiement = idPaiement;
        this.idTicket = idTicket;
        this.montant = montant;
        this.moyenPaiement = moyenPaiement;
        this.datePaiement = LocalDateTime.now();
        ticket.setEstPaye(true);
    }

    public int getIdPaiement() {
        return idPaiement;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public String getMoyenPaiement() {
        return moyenPaiement;
    }

    public void afficherInfos() {
        System.out.println("Paiement #" + idPaiement + " pour Ticket #" + idTicket);
        System.out.println("Montant : " + montant + " € - Moyen : " + moyenPaiement);
        System.out.println("Date : " + datePaiement);
    }
}
