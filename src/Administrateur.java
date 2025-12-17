//Rodéric
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Administrateur extends Utilisateur {
    private int idAdmin;
    private String login;

    public Administrateur(int idUtilisateur, String nom, String prenom, String email, String motDePasse, int idAdmin, String login) {
        super(idUtilisateur, nom, prenom, email, motDePasse);
        this.idAdmin = idAdmin;
        this.login = login;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void gererParking() {
        System.out.println("L'administrateur gère le parking.");
    }

    public void gererAbonnes() {
        System.out.println("L'administrateur gère les abonnés.");
    }

    public void gererPlaces() {
        System.out.println("L'administrateur gère les places du parking.");
    }

    public void afficherInfos() {
        System.out.println("Administrateur : " + nom + " " + prenom + " (" + email + ")");
    }

    public static String afficherEtatParking(int parkingId) {
        String resultat = "";
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return "Erreur :  impossible de se connecter a la base. ";
            }
            
            //compte les places et taux d'occupation
            String sql = "SELECT p.id, p.nom, p.adresse, p.nb_places_totales, " +
                "(SELECT COUNT(*) FROM places pl WHERE pl.parking_id = p.id AND pl.etat = 0) AS libres, " +
                "(SELECT COUNT(*) FROM places pl WHERE pl.parking_id = p.id AND pl.type = 'Normale') AS normales, " +
                "(SELECT COUNT(*) FROM places pl WHERE pl.parking_id = p.id AND pl.type = 'Normale' AND pl.etat = 0) AS normales_libres, " +
                "(SELECT COUNT(*) FROM places pl WHERE pl.parking_id = p.id AND pl.type = 'Reservee') AS reservees, " +
                "(SELECT COUNT(*) FROM places pl WHERE pl.parking_id = p.id AND pl.type = 'Reservee' AND pl.etat = 0) AS reservees_libres " +
                "FROM parking p WHERE p.id = " + parkingId;
            
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                String nom = rs.getString("nom");
                String adresse = rs.getString("adresse");
                int total = rs.getInt("nb_places_totales");
                int libres = rs.getInt("libres");
                int normales = rs.getInt("normales");
                int normalesLibres = rs.getInt("normales_libres");
                int reservees = rs.getInt("reservees");
                int reserveesLibres = rs.getInt("reservees_libres");
                
                int occupees = total - libres;
                double taux = (1 - (double)libres / total) * 100;
                
                resultat = "Nom : " + nom + "\n";
                resultat += "Adresse : " + adresse + "\n";
                resultat += "Capacite : " + total + "\n";
                resultat += "Libres : " + libres + "\n";
                resultat += "Occupees : " + occupees + "\n";
                resultat += "Taux occupation : " + String.format("%.1f", taux) + "%\n";
                resultat += "Normales : " + normalesLibres + "/" + normales + " libres\n";
                resultat += "Reservees : " + reserveesLibres + "/" + reservees + " libres\n";
            } else {
                resultat = "Parking introuvable.\n";
            }
            
        } catch (Exception e) {
            resultat = "Erreur lors de la lecture de l'etat du parking : " + e. getMessage() + "\n";
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
            } catch (Exception ignore) {}
        }
        
        return resultat;
    }


    public static String afficherListeAbonnes() {
        String resultat = "";
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return "Erreur : impossible de se connecter a la base.";
            }
            
            String sql = "SELECT u.id, u.nom, u.prenom, u.email, u. place_id, " +
                "a.id AS abo_id, a.date_debut, a.date_fin, a.montant, pl.numero " +
                "FROM utilisateurs u " +
                "LEFT JOIN abonnements a ON a.utilisateur_id = u.id " +
                "LEFT JOIN places pl ON pl.id = u.place_id " +
                "WHERE u.role != 'ADMIN'";
            
            rs = stmt.executeQuery(sql);
            
            boolean auMoinsUn = false;
            while (rs.next()) {
                auMoinsUn = true;

                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");

                int placeNum = rs.getInt("numero");
                boolean placeNull = rs.wasNull();

                int aboId = rs.getInt("abo_id");
                boolean aboNull = rs.wasNull();

                resultat += "ID : " + id + "\n";
                resultat += "Nom : " + prenom + " " + nom + "\n";
                resultat += "Email : " + email + "\n";

                if (!placeNull) {
                    resultat += "Place : #" + placeNum + "\n";
                } else {
                    resultat += "Place : aucune\n";
                }

                if (!aboNull) {
                    Date dateDeb = rs.getDate("date_debut");
                    Date dateFin = rs.getDate("date_fin");

                    LocalDate debut = null;
                    LocalDate fin = null;

                    if (dateDeb != null) {
                        debut = dateDeb.toLocalDate();
                    }
                    if (dateFin != null) {
                        fin = dateFin.toLocalDate();
                    }

                    boolean actif = false;

                    if (debut != null && fin != null) {
                        LocalDate today = LocalDate.now();

                        boolean commence = today.isAfter(debut) || today.isEqual(debut);
                        boolean pasFini = today.isBefore(fin) || today.isEqual(fin);

                        if (commence && pasFini) {
                            actif = true;
                        }
                    }

                    if (actif) {
                        resultat += "Statut : actif\n";
                    } else {
                        resultat += "Statut : expiré\n";
                    }

                    resultat += "Validité : " + debut + " -> " + fin + "\n";

                } else {
                    resultat += "Statut : aucun abonnement\n";
                }

                resultat += "\n";
            }

            if (!auMoinsUn) {
                resultat = "Aucun abonné.\n";
            }

        } catch (Exception e) {
            resultat = "Erreur lors de la lecture des abonnés : " + e.getMessage() + "\n";
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
            } catch (Exception ignore) {}
        }

        return resultat;
    }

    public static String afficherRecettes() {
        String resultat = "";
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return "Erreur : impossible de se connecter a la base.";
            }
            
            double totalTickets = 0;
            double totalAbos = 0;
            
            resultat += "Paiements des tickets :\n";
            String sqlPaiements = "SELECT id, montant, moyen FROM paiements";
            rs = stmt.executeQuery(sqlPaiements);
            
            boolean aucunPaiement = true;
            while (rs.next()) {
                aucunPaiement = false;
                int id = rs. getInt("id");
                double montant = rs.getDouble("montant");
                String moyen = rs.getString("moyen");
                
                resultat += "  Paiement #" + id + " : " + String.format("%.2f", montant) + " EUR (" + moyen + ")\n";
                totalTickets += montant;
            }
            
            if (aucunPaiement) {
                resultat += "  Aucun paiement\n";
            }
            rs.close();
            
            resultat += "\nAbonnements actifs :\n";
            String sqlAbos = "SELECT u.nom, a.montant, a.date_debut, a.date_fin " +
                "FROM abonnements a " +
                "JOIN utilisateurs u ON u.id = a.utilisateur_id";
            rs = stmt.executeQuery(sqlAbos);
            
            while (rs.next()) {
                String nom = rs.getString("nom");
                double montant = rs.getDouble("montant");
                Date dateDeb = rs. getDate("date_debut");
                Date dateFin = rs. getDate("date_fin");
                
                LocalDate debut;
                if (dateDeb != null) {
                    debut = dateDeb.toLocalDate();
                } else {
                    debut = null;
                }

                LocalDate fin;
                if (dateFin != null) {
                    fin = dateFin.toLocalDate();
                } else {
                    fin = null;
                }

                boolean actif = false;
                if (debut != null && fin != null) {
                    LocalDate aujourdhui = LocalDate.now();
                    actif = (aujourdhui.isAfter(debut) || aujourdhui.isEqual(debut)) &&
                           (aujourdhui.isBefore(fin) || aujourdhui.isEqual(fin));
                }
                
                if (actif) {
                    resultat += "  " + nom + " : " + String.format("%.2f", montant) + " EUR\n";
                    totalAbos += montant;
                }
            }
            rs.close();
            
            resultat += "\nRecapitulatif :\n";
            resultat += "Tickets :  " + String.format("%.2f", totalTickets) + " EUR\n";
            resultat += "Abonnements : " + String.format("%.2f", totalAbos) + " EUR\n";
            resultat += "Total : " + String.format("%.2f", (totalTickets + totalAbos)) + " EUR\n";
            
        } catch (Exception e) {
            resultat = "Erreur lors de la lecture des recettes : " + e. getMessage() + "\n";
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
            } catch (Exception ignore) {}
        }
        
        return resultat;
    }

    public static String chargerTicketsActifs() {
        String resultat = "";
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return "Erreur : impossible de se connecter à la base.";
            }
            
            String sql = "SELECT id, immatriculation, heure_entree FROM tickets WHERE heure_sortie IS NULL";
            rs = stmt. executeQuery(sql);

            boolean auMoinsUnTicket = false;
            
            while (rs.next()) {
                auMoinsUnTicket = true;
                int id = rs. getInt("id");
                String immat = rs.getString("immatriculation");
                Timestamp entree = rs.getTimestamp("heure_entree");

                if (immat == null) {
                    immat = "-";
                }
                
                String entreeTexte = "?";
                if (entree != null) {
                    entreeTexte = entree.toString();
                }

                resultat = resultat + "Ticket #" + id + "  immatriculation: " + immat + "  entrée: " + entreeTexte + "\n";
            }

            if (!auMoinsUnTicket) {
                resultat = "Aucun ticket actif.\n";
            }
            
        } catch (Exception e) {
            resultat = "Erreur lors de la lecture des tickets : " + e.getMessage() + "\n";
        }finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
            } catch (Exception ignore) {}
        }
        
        return resultat;
    }

    public static String creerNouvelAbonne(String nom, String prenom, String email, String motDePasse, String immatriculation, int dureeMois, int parkingId) {
        Statement stmt = null;
        ResultSet rs = null;
        String resultat = "";
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                resultat += "Erreur : impossible de se connecter a la base.\n";
                return resultat;
            }
            
            String sqlPlace = "SELECT id FROM places WHERE parking_id = " + parkingId + 
                " AND type = 'Reservee' AND etat = 0 LIMIT 1";
            rs = stmt.executeQuery(sqlPlace);
            
            if (!rs.next()) {
                resultat += "Aucune place reservee libre\n";
                rs.close();
                DbConnexion.closeConnexion();
                return resultat;
            }
            
            int placeId = rs.getInt("id");
            rs.close();
            
            String nomEsc = nom.replace("'", "''");
            String prenomEsc = prenom.replace("'", "''");
            String emailEsc = email.replace("'", "''");
            String mdpEsc = motDePasse.replace("'", "''");
            String immatEsc = (immatriculation == null) ? "" : immatriculation.replace("'", "''");
            
            String sqlUser = "INSERT INTO utilisateurs (nom, prenom, email, immatriculation, mot_de_passe, role, place_id) " + 
                "VALUES ('"+ nomEsc + "', '" + prenomEsc + "', '" + emailEsc + "', '" + immatEsc + "', '" + mdpEsc + "', 'CLIENT', " + placeId + ")";
            stmt.executeUpdate(sqlUser);
            
            String sqlLastId = "SELECT LAST_INSERT_ID() AS id";
            rs = stmt.executeQuery(sqlLastId);
            int userId = -1;
            if (rs.next()) {
                userId = rs.getInt("id");
            }
            rs.close();
            
            double montant = dureeMois * 75.0;
            LocalDate debut = LocalDate.now();
            LocalDate fin = debut.plusMonths(dureeMois);
            
            String sqlAbo = "INSERT INTO abonnements (utilisateur_id, date_debut, date_fin, montant) VALUES (" + userId + ", '" + debut + "', '" + fin + "', " + montant + ")";
            stmt.executeUpdate(sqlAbo);
            
            String sqlUpdatePlace = "UPDATE places SET etat = 1 WHERE id = " + placeId;
            stmt.executeUpdate(sqlUpdatePlace);
            
            resultat += "Abonné créé.\n";
            resultat += "ID : " + userId + "\n";
            resultat += "Place : #" + placeId + "\n";
            resultat += "Montant : " + String.format("%.2f", montant) + " EUR\n";
            
            return resultat;
            
        } catch (Exception e) {
            resultat += "Erreur lors de la creation de l'abonne : " + e.getMessage() + "\n";
            return resultat;
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
            } catch (Exception ignore) {}
        }
    }

    public static class PlaceInfo {
        public int numero;
        public int etat;
        public String type;
        public int userId;
        public String immat;
    }

    public static ArrayList<PlaceInfo> recupererPlaces() {
        ArrayList<PlaceInfo> places = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                return places;
            }
            
            String sql = "SELECT p.numero, p.etat, p.type, p.id as place_id, " + "u.id as user_id, u.immatriculation as user_immat, " + "t.immatriculation as ticket_immat " +
                "FROM places p " +
                "LEFT JOIN utilisateurs u ON u.place_id = p.id " +
                "LEFT JOIN tickets t ON t.place_id = p.id AND t.heure_sortie IS NULL " +
                "WHERE p.parking_id = 10 " +
                "ORDER BY p.numero";
            
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                PlaceInfo info = new PlaceInfo();
                info.numero = rs.getInt("numero");
                info.etat = rs.getInt("etat");
                info.type = rs.getString("type");
                info.userId = rs.getInt("user_id");
                

                String userImmat = rs.getString("user_immat");
                String ticketImmat = rs.getString("ticket_immat");
                
                if (userImmat != null && ! userImmat.isEmpty()) {
                    info.immat = userImmat;
                } else if (ticketImmat != null && !ticketImmat.isEmpty()) {
                    info.immat = ticketImmat;
                }
                
                places.add(info);
            }
            
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des places : " + e. getMessage());
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt. close();
                }
            } catch (Exception ignore) {}
        }
        
        return places;
    }

    public static String gererEntree(String immatriculation, int parkingId) {
        Statement stmt = null;
        ResultSet rs = null;
        String resultat=""; 
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                resultat += "Erreur :  impossible de se connecter a la base.\n";
                return resultat;
            }
            
            String immatEsc = immatriculation.replace("'", "''");
            
            // abonné actif?
            String sqlAbonne = "SELECT u.id, u.nom, u.prenom, pl.id as place_id, pl.numero, a.date_debut, a.date_fin " +
                "FROM utilisateurs u " +
                "JOIN abonnements a ON a.utilisateur_id = u.id " +
                "JOIN places pl ON pl.id = u.place_id " +
                "WHERE u. role = 'CLIENT' AND pl.parking_id = " + parkingId + 
                " AND u.immatriculation = '" + immatEsc + "'";
            rs = stmt.executeQuery(sqlAbonne);
            
            if (rs.next()) {
                Date dateDeb = rs.getDate("date_debut");
                Date dateFin = rs.getDate("date_fin");
                LocalDate debut = null;
                if (dateDeb != null) {
                    debut = dateDeb. toLocalDate();
                }
                LocalDate fin = null;
                if (dateFin != null) {
                    fin = dateFin. toLocalDate();
                }
                
                boolean actif = false;
                if (debut != null && fin != null) {
                    LocalDate aujourdhui = LocalDate.now();
                    actif = (aujourdhui.isAfter(debut) || aujourdhui.isEqual(debut)) &&
                        (aujourdhui.isBefore(fin) || aujourdhui.isEqual(fin));
                }
                
                if (actif) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    int placeId = rs.getInt("place_id");
                    int placeNum = rs.getInt("numero");
                    
                    String sqlUpdatePlace = "UPDATE places SET etat = 1 WHERE id = " + placeId;
                    stmt. executeUpdate(sqlUpdatePlace);
                    
                    resultat += "Abonné reconnu : " + prenom + " " + nom + "\n";
                    resultat += "Place réservée : #" + placeNum + "\n";
                    resultat += "Place marquée comme occupée\n";
                    resultat += "Pas de ticket\n";
                    rs.close();
                    DbConnexion.closeConnexion();
                    return resultat;
                }
            }
            rs.close();
            
            // création ticket 
            LocalDateTime maintenant = LocalDateTime.now();

            String sqlPlace = "SELECT id, numero FROM places WHERE parking_id = " + parkingId + " AND type = 'Normale' AND etat = 0 LIMIT 1";
            rs = stmt.executeQuery(sqlPlace);

            if (rs.next()) {
                int placeId = rs.getInt("id");
                int placeNum = rs.getInt("numero");
                rs.close();
                
                String sqlTicket = "INSERT INTO tickets (immatriculation, heure_entree, montant, est_paye, place_id) VALUES ('" + immatEsc + "', '" + maintenant + "', 0.0, 0, " + placeId + ")";
                stmt.executeUpdate(sqlTicket);
                
                String sqlLastId = "SELECT LAST_INSERT_ID() AS id";
                rs = stmt. executeQuery(sqlLastId);
                rs.next();
                int ticketId = rs.getInt("id");
                rs.close();
                
                String sqlUpdatePlace = "UPDATE places SET etat = 1 WHERE id = " + placeId;
                stmt.executeUpdate(sqlUpdatePlace);
                
                resultat += "Ticket généré\n";
                resultat += "Numéro :  " + ticketId + "\n";
                resultat += "Véhicule : " + immatriculation + "\n";
                resultat += "Entrée : " + maintenant + "\n";
                resultat += "Place : #" + placeNum + "\n";
            } else {
                resultat += "Aucune place normale disponible\n";
                DbConnexion.closeConnexion();
                return resultat;
            }
            rs.close();
            
            return resultat;
            
        } catch (Exception e) {
            resultat += "Erreur lors de la gestion de l'entree : " + e.getMessage() + "\n";
            return resultat;
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
            } catch (Exception ignore) {}
            DbConnexion.closeConnexion();
        }
    }

    public static String gererSortie(int ticketId, String moyenPaiement, int parkingId) {
        Statement stmt = null;
        ResultSet rs = null;
        String resultat="";
        try {
            stmt = DbConnexion. connexion();
            if (stmt == null) {
                resultat += "Erreur : impossible de se connecter a la base.\n";
                return resultat;
            }
            
            String sqlTicket = "SELECT immatriculation, heure_entree, heure_sortie, place_id FROM tickets WHERE id = " + ticketId;
            rs = stmt.executeQuery(sqlTicket);
            
            if (!rs.next()) {
                resultat += "Ticket introuvable\n";
                rs.close();
                DbConnexion.closeConnexion();
                return resultat;
            }
            
            String immat = rs.getString("immatriculation");
            Timestamp entree = rs.getTimestamp("heure_entree");
            Timestamp sortie = rs.getTimestamp("heure_sortie");
            int placeId = rs.getInt("place_id");  
            boolean placeIdIsNull = rs.wasNull();  
            rs.close();
            
            if (sortie != null) {
                resultat += "Ticket déjà traité\n";
                DbConnexion.closeConnexion();
                return resultat;
            }
            
            LocalDateTime maintenant = LocalDateTime.now();
            LocalDateTime heureEntree = entree.toLocalDateTime();
            long duree = java.time.Duration.between(heureEntree, maintenant).toMinutes();
            
            double heures = duree / 60.0;
            double montant = Math.ceil(heures) * 2.5;
            
            String moyenEsc = moyenPaiement.replace("'", "''");
            
            String sqlUpdateTicket = "UPDATE tickets SET heure_sortie = '" + maintenant + "', montant = " + montant + ", est_paye = 1 WHERE id = " + ticketId;
            stmt.executeUpdate(sqlUpdateTicket);
            
            String sqlPaiement = "INSERT INTO paiements (ticket_id, montant, date_paiement, moyen) VALUES (" +
                ticketId + ", " + montant + ", '" + maintenant + "', '" + moyenEsc + "')";
            stmt.executeUpdate(sqlPaiement);
            
            if (!placeIdIsNull && placeId > 0) {
                String sqlPlace = "UPDATE places SET etat = 0 WHERE id = " + placeId;
                stmt.executeUpdate(sqlPlace);
                resultat += "Place #" + placeId + " liberee\n";
            } 
            
            resultat += "Facture :\n";
            resultat += "Ticket #" + ticketId + "\n";
            resultat += "Véhicule :  " + immat + "\n";
            resultat += "Durée : " + duree + " min\n";
            resultat += "Montant : " + String.format("%.2f", montant) + " EUR\n";
            resultat += "Paiement OK.  Sortie autorisée.\n";
            
            return resultat;
            
        } catch (Exception e) {
            resultat += "Erreur lors de la gestion de la sortie : " + e. getMessage() + "\n";
            return resultat;
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
            } catch (Exception ignore) {}
            DbConnexion.closeConnexion();
        }
    }

}
