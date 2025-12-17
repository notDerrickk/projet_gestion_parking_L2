//Rodéric
import java.util.ArrayList;

public class Parking {
    private int idParking;
    private String nom;
    private String adresse;
    private int nbPlacesTotales;
    private ArrayList<Place> listePlaces;
    private double tarifAbonnement;
    public Parking(int idParking, String nom, String adresse, int nbPlacesTotales, double tarifAbonnement) {
        this.idParking = idParking;
        this.nom = nom;
        this.adresse = adresse;
        this.nbPlacesTotales = nbPlacesTotales;
        this.tarifAbonnement = tarifAbonnement;
        this.listePlaces = new ArrayList<>();
    }
    public int getIdParking() {
        return idParking;
    }

    public void setIdParking(int idParking) {
        this.idParking = idParking;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNbPlacesTotales() {
        return nbPlacesTotales;
    }

    public void setNbPlacesTotales(int nbPlacesTotales) {
        this.nbPlacesTotales = nbPlacesTotales;
    }

    public ArrayList<Place> getListePlaces() {
        return listePlaces;
    }

    public void setListePlaces(ArrayList<Place> listePlaces) {
        this.listePlaces = listePlaces;
    }

    public double getTarifAbonnement() {
        return tarifAbonnement;
    }

    public void setTarifAbonnement(double tarifAbonnement) {
        this.tarifAbonnement = tarifAbonnement;
    }
    public void ajouterPlace(Place p){
        if (listePlaces.size()<nbPlacesTotales) {
            listePlaces.add(p);          
        }
        else{
            System.out.println("Le parking est plein");
        }
    }
    public void afficherPlaces() {
        for (Place p : listePlaces) {
            System.out.println(p.toString());
        }
    }
    public int compterPlacesLibres() {
        int count = 0;
        for (Place p : listePlaces) {
            if (p.estLibre()) {
                count++;
            }
        }
        return count;
    }
}
