//Rodéric
import java.time.LocalDateTime;
import java.sql.*;


public class Ticket {
    private int idTicket;
    private String immatriculation;
    private LocalDateTime heureEntree;
    private LocalDateTime heureSortie;
    private double montant;
    private boolean estPaye;

    public Ticket(int idTicket, String immatriculation, LocalDateTime heureEntree) {
        this.idTicket = idTicket;
        this.immatriculation = immatriculation;
        this.heureEntree = heureEntree;
        this.estPaye = false;
    }
    
    public int getIdTicket() {
        return idTicket;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public LocalDateTime getHeureEntree() {
        return heureEntree;
    }

    public LocalDateTime getHeureSortie() {
        return heureSortie;
    }

    public double getMontant() {
        return montant;
    }

    public boolean estPaye() {
        return estPaye;
    }

    public boolean estFerme() {
        return heureSortie != null;
    }
    
    public void setHeureSortie(LocalDateTime heureSortie) {
        this.heureSortie = heureSortie;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setEstPaye(boolean estPaye) {
        this.estPaye = estPaye;
    }

    public long calculerDureeMinutes() {
        if (heureSortie == null){
            return java.time.Duration.between(heureEntree, LocalDateTime.now()).toMinutes();
        }
        return java.time.Duration.between(heureEntree, heureSortie).toMinutes();
    }



    public void afficherInfos() {
        System.out.println("Ticket #" + idTicket + " - Véhicule : " + immatriculation);
        System.out.println("Entrée : " + heureEntree);
        if (heureSortie != null) {
            System.out.println("Sortie : " + heureSortie);
        } else {
            System.out.println("Sortie : encore présent");
        }
        if (estPaye) {
            System.out.println("Montant : " + montant + " € - Payé : Oui");
        } else {
            System.out.println("Montant : " + montant + " € - Payé : Non");
        }
    }

    
}


