//Hugo
import java.time.LocalDateTime;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Système de gestion de parking - Démonstration ===\n");


        Administrateur admin = new Administrateur(
            1,
            "Dupont",
            "Jean",
            "jean.dupont@mail.com",
            "admin123",
            100,
            "adminJD"
        );

        Parking parking = new Parking(
            1,
            "Parking Valin de la Vaissière",
            "1 Place Valin de la Vaissière",
            50,
            75.0
        );



        System.out.println("Informations administrateur:");
        admin.afficherInfos();
        System.out.println("Login: " + admin.getLogin());
        System.out.println();

        System.out.println("Informations parking:");
        System.out.println("Nom: " + parking.getNom());
        System.out.println("Adresse: " + parking.getAdresse());
        System.out.println("Capacité totale: " + parking.getNbPlacesTotales() + " places");
        System.out.println("Tarif abonnement: " + parking.getTarifAbonnement() + " €/mois");
        System.out.println();



        System.out.println("Actions administrateur:");
        admin.gererParking();
        admin.gererAbonnes();
        admin.gererPlaces();
        System.out.println();



        System.out.println("Simulation tickets:");

        // Ticket 1 - Véhicule sorti et a payé
        Ticket ticket1 = new Ticket(1, "AB-123-CD", LocalDateTime.now().minusHours(3));
        ticket1.setHeureSortie(LocalDateTime.now());
        ticket1.setMontant(15.0);

        System.out.println("\n--- Ticket 1 (véhicule sorti) ---");
        ticket1.afficherInfos();
        System.out.println("Durée de stationnement: " + ticket1.calculerDureeMinutes() + " minutes");

        // Ticket 2 - Véhicule sorti mais non payé
        Ticket ticket2 = new Ticket(2, "EF-456-GH", LocalDateTime.now().minusHours(1));
        ticket2.setHeureSortie(LocalDateTime.now());
        ticket2.setMontant(8.0);

        System.out.println("\n--- Ticket 2 (véhicule sorti) ---");
        ticket2.afficherInfos();
        System.out.println("Durée de stationnement: " + ticket2.calculerDureeMinutes() + " minutes");

        // Ticket 3 - Véhicule encore présent (paiement impossible)
        Ticket ticket3 = new Ticket(3, "IJ-789-KL", LocalDateTime.now().minusMinutes(45));
        ticket3.setMontant(5.0);
        
        System.out.println("\n--- Ticket 3 (véhicule présent) ---");
        ticket3.afficherInfos();
        System.out.println("Durée actuelle: " + ticket3.calculerDureeMinutes() + " minutes (en cours)");


        System.out.println("\nSimulation paiements:");

        Paiement paiement1 = new Paiement(ticket1, 1, ticket1.getIdTicket(), ticket1.getMontant(), "Carte bancaire");
        System.out.println("\n--- Paiement 1 ---");
        paiement1.afficherInfos();

        Paiement paiement2 = new Paiement(ticket2, 2, ticket2.getIdTicket(), ticket2.getMontant(), "Espèces");
        System.out.println("\n--- Paiement 2 ---");
        paiement2.afficherInfos();

        // Paiement impossible pour le ticket 3
        // Paiement paiement3 = new Paiement(ticket3, 3, ticket3.getIdTicket(), ticket3.getMontant(), "Carte bancaire");
        // System.out.println("\n--- Paiement 3 ---");
        // paiement3.afficherInfos();

        System.out.println("\n=== Tickets après paiement ===");
        
        System.out.println("\n--- Ticket 1 (mis à jour) ---");
        ticket1.afficherInfos();
        
        System.out.println("\n--- Ticket 2 (mis à jour) ---");
        ticket2.afficherInfos();

        

        // Création de quelques places et les ajouter au parking
        Place place1 = new Place(1, false, 1, "Standard");
        Place place2 = new Place(2, false, 2, "PMR");
        parking.ajouterPlace(place1);
        parking.ajouterPlace(place2);

        System.out.println("Places du parking :");
        parking.afficherPlaces();

        // Crétion d'un abonnement et d'un client abonné
        Abonnement ab1 = new Abonnement(1, LocalDate.now().minusMonths(1), LocalDate.now().plusMonths(1), parking.getTarifAbonnement());
        ClientAbonne client1 = new ClientAbonne(10, "Martin", "Paul", "paul.martin@mail.com", "pwd123", "AA-111-BB", ab1, null);

        // Attribution d'une place au client 
        client1.changerDePlace(place1);

        System.out.println("Client abonné (après attribution de place) :");
        client1.afficherInfos();

        System.out.println("Nombre de places libres : " + parking.compterPlacesLibres());

        // Changement de sa place
        parking.ajouterPlace(new Place(3, false, 3, "Standard")); // ajouter une 3ème place pour tester
        client1.changerDePlace(place2);
        System.out.println(client1.toString());
        System.out.println("Nombre de places libres après changement : " + parking.compterPlacesLibres());

        // Tester le désabonnement
        client1.seDesabonner();
        System.out.println(client1.toString());

        // Renouveler un abonnement et afficher la durée de cz dernier"
        System.out.println("\nRenouvellement de l'abonnement ab1 de 1 mois...");
        ab1.renouveler(1);
        System.out.println("Durée en jours de l'abonnement : " + ab1.getDureeEnJours());

    }
}