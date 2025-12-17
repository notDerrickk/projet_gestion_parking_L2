//Hugo
import java.time.LocalDate;

public class Abonnement {
    private int idAbonnement;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double montant;

    public Abonnement(int idAbonnement, LocalDate dateDebut, LocalDate dateFin, double montant) {
        this.idAbonnement = idAbonnement;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montant = montant;
    }

    public int getIdAbonnement() { 
        return idAbonnement; 
    }
    public void setIdAbonnement(int idAbonnement) {
        this.idAbonnement = idAbonnement; 
    }

    public LocalDate getDateDebut() { 
        return dateDebut; 
    }
    public void setDateDebut(LocalDate dateDebut) { 
        this.dateDebut = dateDebut; 
    }

    public LocalDate getDateFin() { 
        return dateFin; 
    }
    public void setDateFin(LocalDate dateFin) { 
        this.dateFin = dateFin; 
    }

    public double getMontant() { 
        return montant; 
    }
    public void setMontant(double montant) { 
        this.montant = montant;
    }


    public boolean estActif() {
        LocalDate aujourdHui = LocalDate.now();
        return (aujourdHui.isAfter(dateDebut) || aujourdHui.isEqual(dateDebut))
                && (aujourdHui.isBefore(dateFin) || aujourdHui.isEqual(dateFin));
    }


    public String toString() {
        return "Abonnement #" + idAbonnement + " [" + dateDebut + " → " + dateFin + "] - " + montant + "€";
    }

    public long getDureeEnJours() {
        return java.time.temporal.ChronoUnit.DAYS.between(dateDebut, dateFin);
    }

    public void renouveler(int mois) {
        this.dateFin = this.dateFin.plusMonths(mois);
    }

}
