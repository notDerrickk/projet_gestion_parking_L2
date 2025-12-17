//Hugo
import java.sql.*;

public class ClientAbonne extends Utilisateur {
    private String plaqueImmatriculation;
    private Abonnement abonnement;
    private Place place;


    public ClientAbonne(int idUtilisateur, String nom, String prenom, String email, String motDePasse,
                        String plaqueImmatriculation, Abonnement abonnement, Place place) {
        super(idUtilisateur, nom, prenom, email, motDePasse);
        this.plaqueImmatriculation = plaqueImmatriculation;
        this.abonnement = abonnement;
        this.place = place;
    }

    public int getIdAbonne() {
        return getIdUtilisateur();
    }

    public void setIdAbonne(int idAbonne) {
        this.idUtilisateur = idAbonne;
    }

    public String getPlaqueImmatriculation() {
        return plaqueImmatriculation;
    }

    public void setPlaqueImmatriculation(String plaqueImmatriculation) {
        this.plaqueImmatriculation = plaqueImmatriculation;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public boolean estAbonneActif() {
        return abonnement != null && abonnement.estActif();
    }

    public String toString() {
        return getPrenom() + " " + getNom() + " (" + plaqueImmatriculation + ") - "
                + (estAbonneActif() ? "Abonné actif" : "Non actif") + " - Place: "
                + (place != null ? place.getNumero() : "Aucune");
    }

    public void afficherInfos() {
        System.out.println(toString());
        if (abonnement != null) {
            System.out.println(abonnement.toString());
        }
    }

    public void changerDePlace(Place nouvellePlace) {
        if (this.place != null) this.place.liberer();
        this.place = nouvellePlace;
        if (this.place != null) this.place.occuper();
    }

   
    public void seDesabonner() {
        this.abonnement = null;
    }

    public static String[] chargerInfosUtilisateur(String email) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return null;
            }

            String email2= email.replace("'", "''");
            String sql = "SELECT u.nom, u.prenom, u.email, u.mot_de_passe, p.numero, u.immatriculation " +
                         "FROM utilisateurs u " +
                         "LEFT JOIN places p ON u.place_id = p.id " +
                         "WHERE u.email = '" + email2  + "' LIMIT 1";
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                String[] infos = new String[6];
                infos[0] = rs.getString("nom");
                infos[1] = rs.getString("prenom");
                infos[2] = rs.getString("email");
                infos[3] = rs.getString("mot_de_passe");
                int placeNum = rs.getInt("numero");
                infos[4] = (placeNum > 0) ? String.valueOf(placeNum) : "Non attribué";
                infos[5] = rs.getString("immatriculation") != null ? rs.getString("immatriculation") : "";
                return infos;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ignore) {}
            DbConnexion.closeConnexion();
        }
    }

    public static boolean sauvegarderModifications(String emailActuel, String nom, String prenom, String email, String motDePasse, String immatriculation) {
        Statement stmt = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return false;
            }

            String nom2 = nom.replace("'", "''");
            String prenom2 = prenom.replace("'", "''");
            String email2 = email.replace("'", "''");
            String pass2 = motDePasse.replace("'", "''");
            String immat2 = immatriculation.replace("'", "''");
            String emailActuelEsc = emailActuel.replace("'", "''");

            String sql = "UPDATE utilisateurs SET nom = '" + nom2 + "', prenom = '" + prenom2    +
                         "', email = '" + email2 + "', mot_de_passe = '" + pass2 + "', immatriculation = '" + immat2 + "' WHERE email = '" + emailActuelEsc + "'";
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            DbConnexion.closeConnexion();
        }
    }

    public static String[] chargerAbonnement(String email) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return null;
            }

            String email2 = email.replace("'", "''");
            String sql = "SELECT a.id, a.date_debut, a.date_fin, a.montant, p.numero " +
                         "FROM abonnements a " +
                         "JOIN utilisateurs u ON a.utilisateur_id = u.id " +
                         "LEFT JOIN places p ON u.place_id = p.id " +
                         "WHERE u.email = '" + email2 + "' LIMIT 1";
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                String[] infos = new String[5];
                infos[0] = rs.getString("id");
                infos[1] = rs.getString("date_debut");
                infos[2] = rs.getString("date_fin");
                infos[3] = rs.getString("montant");
                int placeNum = rs.getInt("numero");
                infos[4] = (placeNum > 0) ? String.valueOf(placeNum) : "Pas de place assignée";
                return infos;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ignore) {}
            DbConnexion.closeConnexion();
        }
    }

    public static int creerAbonnement(String email, int mois, double montant) {
        Statement stmt = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return -1;
            }

            String email2 = email.replace("'", "''");
            
            // Récupérer l'ID utilisateur
            String sqlUser = "SELECT id FROM utilisateurs WHERE email = '" + email2  + "' LIMIT 1";
            ResultSet rs = stmt.executeQuery(sqlUser);
            if (!rs.next()) {
                return -1;
            }
            int userId = rs.getInt("id");

            java.time.LocalDate debut = java.time.LocalDate.now();
            java.time.LocalDate fin = debut.plusMonths(mois);
            
            String sql = "INSERT INTO abonnements (utilisateur_id, date_debut, date_fin, montant) VALUES (" +
                         userId + ", '" + debut + "', '" + fin + "', " + montant + ")";
            stmt.executeUpdate(sql);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            DbConnexion.closeConnexion();
        }
    }

    public static int resilierAbonnement(String email) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return -1;
            }

            String email2 = email.replace("'", "''");
            
            String sqlGetPlace = "SELECT place_id FROM utilisateurs WHERE email = '" + email2 + "' LIMIT 1";
            rs = stmt.executeQuery(sqlGetPlace);
            int placeId = -1;
            if (rs.next()) {
                placeId = rs.getInt("place_id");
            }
            
            if (placeId > 0) {
                String sqlLibererPlace = "UPDATE places SET etat = 0 WHERE id = " + placeId;
                stmt.executeUpdate(sqlLibererPlace);
            }
            
            String sqlRemovePlace = "UPDATE utilisateurs SET place_id = NULL WHERE email = '" + email2 + "'";
            stmt.executeUpdate(sqlRemovePlace);
            
            String sql = "DELETE FROM abonnements WHERE utilisateur_id = (SELECT id FROM utilisateurs WHERE email = '" + email2 + "' LIMIT 1)";
            stmt.executeUpdate(sql);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ignore) {}
            DbConnexion.closeConnexion();
        }
    }

    public static int prolongerAbonnement(String email, int mois) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return -1;
            }

            String email2 = email.replace("'", "''");
            
            String sqlSelect = "SELECT a.date_fin FROM abonnements a " +
                               "JOIN utilisateurs u ON a.utilisateur_id = u.id " +
                               "WHERE u.email = '" + email2 + "' LIMIT 1";
            rs = stmt.executeQuery(sqlSelect);
            
            if (!rs.next()) {
                return -1;
            }
            
            String dateFin = rs.getString("date_fin");
            java.time.LocalDate nouvelleFin = java.time.LocalDate.parse(dateFin).plusMonths(mois);
            
            String sql = "UPDATE abonnements SET date_fin = '" + nouvelleFin + "' " +
                        "WHERE utilisateur_id = (SELECT id FROM utilisateurs WHERE email = '" + email2 + "' LIMIT 1)";
            stmt.executeUpdate(sql);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ignore) {}
            DbConnexion.closeConnexion();
        }
    }

    public static int assignerPlace(String email, int numeroPlace) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return -1;
            }

            String email2 = email.replace("'", "''");
            
            String sqlGetOldPlace = "SELECT place_id FROM utilisateurs WHERE email = '" + email2 + "' LIMIT 1";
            rs = stmt.executeQuery(sqlGetOldPlace);
            int oldPlaceId = -1;
            if (rs.next()) {
                oldPlaceId = rs.getInt("place_id");
            }
            
            if (oldPlaceId > 0) {
                String sqlLibererOldPlace = "UPDATE places SET etat = 0 WHERE id = " + oldPlaceId;
                stmt.executeUpdate(sqlLibererOldPlace);
            }
            
            String sqlPlaceId = "SELECT id FROM places WHERE numero = " + numeroPlace + " LIMIT 1";
            rs = stmt.executeQuery(sqlPlaceId);
            
            if (!rs.next()) {
                return -1;
            }
            int placeId = rs.getInt("id");
            
            String sql = "UPDATE utilisateurs SET place_id = " + placeId + " WHERE email = '" + email2 + "'";
            stmt.executeUpdate(sql);
            
            String sqlOccuperPlace = "UPDATE places SET etat = 1 WHERE id = " + placeId;
            stmt.executeUpdate(sqlOccuperPlace);
            
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ignore) {}
            DbConnexion.closeConnexion();
        }
    }

    public static int mettreAJourEtatPlace(int numeroPlace, int nouvelEtat) {
        Statement stmt = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return -1;
            }

            String sql = "UPDATE places SET etat = " + nouvelEtat + " WHERE numero = " + numeroPlace;
            stmt.executeUpdate(sql);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            DbConnexion.closeConnexion();
        }
    }

    public static int nettoyerPlaces() {
        Statement stmt = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return -1;
            }
            String sql = "UPDATE places SET etat = 0 WHERE id NOT IN (SELECT DISTINCT place_id FROM utilisateurs WHERE place_id IS NOT NULL) AND etat = 1";
            int count = stmt.executeUpdate(sql);
            return count;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            DbConnexion.closeConnexion();
        }
    }

    public static int joursAvantExpiration(String email) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return -1;
            }

            String email2 = email.replace("'", "''");
            String sql = "SELECT a.date_fin FROM abonnements a " +
                         "JOIN utilisateurs u ON a.utilisateur_id = u.id " +
                         "WHERE u.email = '" + email2 + "' LIMIT 1";
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                String dateFin = rs.getString("date_fin");
                java.time.LocalDate fin = java.time.LocalDate.parse(dateFin);
                java.time.LocalDate maintenant = java.time.LocalDate.now();
                long jours = java.time.temporal.ChronoUnit.DAYS.between(maintenant, fin);
                return (int) jours;
            }
            return -1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ignore) {}
            DbConnexion.closeConnexion();
        }
    }

}
