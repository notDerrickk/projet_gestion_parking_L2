//Hugo
public class Place {
    private int idPlace;
    private boolean etat; 
    private int numero;
    private String type;


    public Place(int idPlace, boolean etat, int numero, String type) {
        this.idPlace = idPlace;
        this.etat = etat;
        this.numero = numero;
        this.type = type;
    }

    
    public int getIdPlace() { 
        return idPlace; 
    }
    public void setIdPlace(int idPlace) { 
        this.idPlace = idPlace; 
    }

    public boolean isEtat() { 
        return etat; 
    }
    public void setEtat(boolean etat) { 
        this.etat = etat; 
    }

    public int getNumero() { 
        return numero; 
    }
    public void setNumero(int numero) { 
        this.numero = numero; 
    }

    public String getType() { 
        return type; 
    }
    public void setType(String type) { 
        this.type = type; 
    }


    public void occuper() { 
        this.etat = true; 
    }
    public void liberer() { 
        this.etat = false; 
    }

    public String toString() {
        return "Place #" + numero + " (" + type + ") - " + (etat ? "Occupée" : "Libre");
    }

    public boolean estLibre() {
        return !etat;
    }

    

}
