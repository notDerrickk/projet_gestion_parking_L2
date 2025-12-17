// Rodéric
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FenetreAdmin extends JFrame implements ActionListener {

    // composants globaux
    JPanel content;
    CardLayout gestionnaireFenetre;

    JMenuBar barreMenus;
    JMenu menuActions, menuDeconnexion;
    JMenuItem itemAccueil, itemGererEntrees, itemGererSorties, itemEtatParking,
    itemListeAbonnes, itemRecettes, itemTickets, itemCreerAbonne, itemQuitter, itemDeconnexion;

    JPanel carteAccueil, carteGererEntrees, carteGererSorties, carteEtatParking,
    carteListeAbonnes, carteRecettes, carteTickets, carteCreerAbonne;

    // composants accueil
    JButton bCreation, bGererEntrees, bGererSorties, bRafraichirVue;
    JPanel panelVueParking;

    // composants tickets
    JTextArea taTickets;
    JButton btnRafraichirTickets, btnRetourTickets;

    // composants recettes
    JTextArea afficheRecettes;
    JButton btnRafraichirRecettes, btnRetourRecettes;

    // composants creer abonne
    JTextField tfNom, tfPrenom, tfEmail, tfImmat, tfDuree;
    JPasswordField pfMotDePasse;
    JLabel lblMessage;
    JButton btnCreer, btnAnnuler;

    // composants liste abonnes
    JTextArea taListeAbonnes;
    JButton btnRafraichirAbonnes, btnRetourAbonnes;

    // composants etat parking 
    JTextArea taEtatParking;
    JButton btnRafraichirEtatParking, btnRetourEtatParking;

    // composants gerer entrees
    JTextField tfImmatEntree;
    JTextArea taResultatEntree;
    JButton btnValiderEntree, btnRetourEntree;

    // composants gerer sorties
    JTextField tfTicketIdSortie;
    JComboBox<String> cbMoyenPaiement;
    JTextArea taResultatSortie;
    JButton btnValiderSortie, btnRetourSortie;

    public FenetreAdmin() {
        // config de base de la fenetre
        setTitle("Administration - Tableau de bord");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initialiserMenu();
        initialiserCartes();

        setContentPane(content);
        gestionnaireFenetre.show(content, "accueil");

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // creation du menu et des items
    private void initialiserMenu() {
        barreMenus = new JMenuBar();
        menuActions = new JMenu("Actions");
        menuDeconnexion = new JMenu("Déconnexion");

        itemAccueil = new JMenuItem("Accueil");
        itemGererEntrees = new JMenuItem("Gérer entrées");
        itemGererSorties = new JMenuItem("Gérer sorties");
        itemEtatParking = new JMenuItem("État parking");
        itemListeAbonnes = new JMenuItem("Liste abonnés");
        itemRecettes = new JMenuItem("Recettes");
        itemTickets = new JMenuItem("Tickets en cours");
        itemCreerAbonne = new JMenuItem("Créer abonné");
        itemQuitter = new JMenuItem("Quitter");
        itemDeconnexion = new JMenuItem("Se déconnecter");

        // tous les items sur le meme actionlistener
        itemAccueil.addActionListener(this);
        itemGererEntrees.addActionListener(this);
        itemGererSorties.addActionListener(this);
        itemEtatParking.addActionListener(this);
        itemListeAbonnes.addActionListener(this);
        itemRecettes.addActionListener(this);
        itemTickets.addActionListener(this);
        itemCreerAbonne.addActionListener(this);
        itemQuitter.addActionListener(this);
        itemDeconnexion.addActionListener(this);

        menuActions.add(itemAccueil);
        menuActions.addSeparator();
        menuActions.add(itemGererEntrees);
        menuActions.add(itemGererSorties);
        menuActions.add(itemEtatParking);
        menuActions.add(itemListeAbonnes);
        menuActions.add(itemRecettes);
        menuActions.add(itemTickets);
        menuActions.add(itemCreerAbonne);
        menuActions.addSeparator();
        menuActions.add(itemQuitter);

        menuDeconnexion.add(itemDeconnexion);

        barreMenus.add(menuActions);
        barreMenus.add(menuDeconnexion);
        setJMenuBar(barreMenus);
    }

    // création des cartes du cardlayout
    private void initialiserCartes() {
        gestionnaireFenetre = new CardLayout();
        content = new JPanel(gestionnaireFenetre);

        initialiserCarteAccueil();
        initialiserCarteTickets();
        initialiserCarteRecettes();
        initialiserCarteListeAbonnes();
        initialiserCarteCreerAbonne();
        initialiserCarteEtatParking();
        initialiserCarteGererEntrees();
        initialiserCarteGererSorties();


        content.add(carteAccueil, "accueil");
        content.add(carteGererEntrees, "gerer_entrees");
        content.add(carteGererSorties, "gerer_sorties");
        content.add(carteEtatParking, "etat_parking");
        content.add(carteListeAbonnes, "liste_abonnes");
        content.add(carteRecettes, "recettes");
        content.add(carteTickets, "tickets");
        content.add(carteCreerAbonne, "creer_abonne");
    }

    // carte d'accueil avec resume et vue du parking
    private void initialiserCarteAccueil() {
        carteAccueil = new JPanel(null);

        JLabel lblAccueil = new JLabel("Tableau de bord administrateur", SwingConstants.CENTER);
        lblAccueil.setBounds(20, 10, 740, 30);
        carteAccueil.add(lblAccueil);

        bCreation = new JButton("Créer un abonné");
        bCreation.setBounds(20, 50, 180, 30);
        carteAccueil.add(bCreation);

        bGererEntrees = new JButton("Gérer entrées");
        bGererEntrees.setBounds(210, 50, 180, 30);
        carteAccueil.add(bGererEntrees);

        bGererSorties = new JButton("Gérer sorties");
        bGererSorties.setBounds(400, 50, 180, 30);
        carteAccueil.add(bGererSorties);

        bRafraichirVue = new JButton("Rafraîchir vue");
        bRafraichirVue.setBounds(590, 50, 180, 30);
        carteAccueil.add(bRafraichirVue);

        // panneau qui contiendra la grille des places
        panelVueParking = new JPanel();
        panelVueParking.setBounds(20, 90, 750, 430);
        panelVueParking.setLayout(new BorderLayout());
        carteAccueil.add(panelVueParking);

        bCreation.addActionListener(this);
        bGererEntrees.addActionListener(this);
        bGererSorties.addActionListener(this);
        bRafraichirVue.addActionListener(this);

        chargerVueParking();
    }

    // carte pour afficher les tickets actifs
    private void initialiserCarteTickets() {
        carteTickets = new JPanel(null);

        taTickets = new JTextArea();
        taTickets.setEditable(false);

        JScrollPane spTickets = new JScrollPane(taTickets);
        spTickets.setBounds(10, 10, 760, 460);
        carteTickets.add(spTickets);

        btnRafraichirTickets = new JButton("Rafraîchir");
        btnRafraichirTickets.setBounds(10, 480, 120, 30);
        carteTickets.add(btnRafraichirTickets);

        btnRetourTickets = new JButton("Retour");
        btnRetourTickets.setBounds(650, 480, 120, 30);
        carteTickets.add(btnRetourTickets);

        btnRafraichirTickets.addActionListener(this);
        btnRetourTickets.addActionListener(this);
    }

    // carte pour afficher les recettes
    private void initialiserCarteRecettes() {
        carteRecettes = new JPanel(null);

        afficheRecettes = new JTextArea();
        afficheRecettes.setEditable(false);

        JScrollPane spRecettes = new JScrollPane(afficheRecettes);
        spRecettes.setBounds(10, 10, 760, 460);
        carteRecettes.add(spRecettes);

        btnRafraichirRecettes = new JButton("Rafraîchir");
        btnRafraichirRecettes.setBounds(10, 480, 120, 30);
        carteRecettes.add(btnRafraichirRecettes);

        btnRetourRecettes = new JButton("Retour");
        btnRetourRecettes.setBounds(650, 480, 120, 30);
        carteRecettes.add(btnRetourRecettes);

        btnRafraichirRecettes.addActionListener(this);
        btnRetourRecettes.addActionListener(this);
    }

    // carte pour afficher liste des abonnes
    private void initialiserCarteListeAbonnes() {
        carteListeAbonnes = new JPanel(null);

        taListeAbonnes = new JTextArea();
        taListeAbonnes.setEditable(false);

        JScrollPane spAbonnes = new JScrollPane(taListeAbonnes);
        spAbonnes.setBounds(10, 10, 760, 460);
        carteListeAbonnes.add(spAbonnes);

        btnRafraichirAbonnes = new JButton("Rafraîchir");
        btnRafraichirAbonnes.setBounds(10, 480, 120, 30);
        carteListeAbonnes.add(btnRafraichirAbonnes);

        btnRetourAbonnes = new JButton("Retour");
        btnRetourAbonnes.setBounds(650, 480, 120, 30);
        carteListeAbonnes.add(btnRetourAbonnes);

        btnRafraichirAbonnes.addActionListener(this);
        btnRetourAbonnes.addActionListener(this);
    }

    // carte pour creer un abonne
    private void initialiserCarteCreerAbonne() {
        carteCreerAbonne = new JPanel(null);

        JLabel lblTitre = new JLabel("Créer un nouvel abonné", SwingConstants.CENTER);
        lblTitre.setBounds(20, 10, 740, 30);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 16));
        carteCreerAbonne.add(lblTitre);

        JLabel lblNom = new JLabel("Nom :");
        lblNom.setBounds(50, 60, 100, 25);
        carteCreerAbonne.add(lblNom);

        tfNom = new JTextField();
        tfNom.setBounds(200, 60, 250, 25);
        carteCreerAbonne.add(tfNom);

        JLabel lblPrenom = new JLabel("Prénom :");
        lblPrenom.setBounds(50, 100, 100, 25);
        carteCreerAbonne.add(lblPrenom);

        tfPrenom = new JTextField();
        tfPrenom.setBounds(200, 100, 250, 25);
        carteCreerAbonne.add(tfPrenom);

        JLabel lblEmail = new JLabel("Email :");
        lblEmail.setBounds(50, 140, 100, 25);
        carteCreerAbonne.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(200, 140, 250, 25);
        carteCreerAbonne.add(tfEmail);

        JLabel lblMotDePasse = new JLabel("Mot de passe :");
        lblMotDePasse.setBounds(50, 180, 150, 25);
        carteCreerAbonne.add(lblMotDePasse);

        pfMotDePasse = new JPasswordField();
        pfMotDePasse.setBounds(200, 180, 250, 25);
        carteCreerAbonne.add(pfMotDePasse);

        JLabel lblImmat = new JLabel("Immatriculation :");
        lblImmat.setBounds(50, 220, 150, 25);
        carteCreerAbonne.add(lblImmat);

        tfImmat = new JTextField();
        tfImmat.setBounds(200, 220, 250, 25);
        carteCreerAbonne.add(tfImmat);

        JLabel lblDuree = new JLabel("Durée (mois) :");
        lblDuree.setBounds(50, 260, 150, 25);
        carteCreerAbonne.add(lblDuree);

        tfDuree = new JTextField("6");
        tfDuree.setBounds(200, 260, 250, 25);
        carteCreerAbonne.add(tfDuree);

        btnCreer = new JButton("Créer abonné");
        btnCreer.setBounds(200, 320, 150, 30);
        carteCreerAbonne.add(btnCreer);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBounds(370, 320, 150, 30);
        carteCreerAbonne.add(btnAnnuler);

        lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setBounds(50, 370, 680, 25);
        carteCreerAbonne.add(lblMessage);

        btnCreer.addActionListener(this);
        btnAnnuler.addActionListener(this);
    }

    private void initialiserCarteEtatParking() {
        carteEtatParking = new JPanel(null);
        
        taEtatParking = new JTextArea();
        taEtatParking.setEditable(false);

        JScrollPane spEtatParking = new JScrollPane(taEtatParking);
        spEtatParking.setBounds(10, 10, 760, 460);
        carteEtatParking.add(spEtatParking);

        btnRafraichirEtatParking = new JButton("Rafraîchir");
        btnRafraichirEtatParking.setBounds(10, 480, 120, 30);
        carteEtatParking.add(btnRafraichirEtatParking);

        btnRetourEtatParking = new JButton("Retour");
        btnRetourEtatParking.setBounds(650, 480, 120, 30);
        carteEtatParking.add(btnRetourEtatParking);

        btnRafraichirEtatParking.addActionListener(this);
        btnRetourEtatParking.addActionListener(this);
    }

    // affichage visuel du parking accueil
    private void chargerVueParking() {
        panelVueParking.removeAll();

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 10, 5, 5));
        gridPanel.setBackground(Color.WHITE);

        ArrayList<Administrateur.PlaceInfo> places = Administrateur.recupererPlaces();

        for (Administrateur.PlaceInfo p : places) {
            JPanel placePanel = new JPanel();
            placePanel.setLayout(new BorderLayout());
            placePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            placePanel.setPreferredSize(new Dimension(70, 60));

            // couleur selon l'état de la place
            if (p.type.equals("Reservee")) {
                placePanel.setBackground(new Color(100, 150, 255)); // bleu
            } else if (p.etat == 1) {
                placePanel.setBackground(new Color(255, 100, 100)); // rouge
            } else {
                placePanel.setBackground(new Color(100, 255, 100)); // vert
            }

            JLabel lblNumero = new JLabel(String.valueOf(p.numero), SwingConstants.CENTER);
            lblNumero.setFont(new Font("Arial", Font.BOLD, 14));
            placePanel.add(lblNumero, BorderLayout.NORTH);

            // pour une place reservee occupee on affiche user et immat
            if (p.type.equals("Reservee") && p.immat != null && !p.immat.isEmpty()) {
                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new GridLayout(2, 1));
                infoPanel.setOpaque(false);

                JLabel lblUserId = new JLabel("User:" + p.userId, SwingConstants.CENTER);
                lblUserId.setFont(new Font("Arial", Font.PLAIN, 9));

                JLabel lblImmat = new JLabel(p.immat, SwingConstants.CENTER);
                lblImmat.setFont(new Font("Arial", Font.PLAIN, 9));

                infoPanel.add(lblUserId);
                infoPanel.add(lblImmat);
                placePanel.add(infoPanel, BorderLayout.CENTER);
            }
            else if (p.etat == 1 && p.type.equals("Normale") && p.immat != null && !p.immat. isEmpty()) {
                JLabel lblImmat = new JLabel(p. immat, SwingConstants.CENTER);
                lblImmat.setFont(new Font("Arial", Font.PLAIN, 10));
                placePanel.add(lblImmat, BorderLayout.CENTER);
            }

            gridPanel.add(placePanel);
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        panelVueParking.add(scrollPane, BorderLayout.CENTER);

        // légende 
        JPanel legendePanel = new JPanel();
        legendePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel legLibre = new JLabel("  Libre  ");
        legLibre.setOpaque(true);
        legLibre.setBackground(new Color(100, 255, 100));
        legLibre.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel legOccupee = new JLabel("  Occupée  ");
        legOccupee.setOpaque(true);
        legOccupee.setBackground(new Color(255, 100, 100));
        legOccupee.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel legReservee = new JLabel("  Réservée  ");
        legReservee.setOpaque(true);
        legReservee.setBackground(new Color(100, 150, 255));
        legReservee.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        legendePanel.add(legLibre);
        legendePanel.add(legOccupee);
        legendePanel.add(legReservee);

        panelVueParking.add(legendePanel, BorderLayout.SOUTH);

        panelVueParking.revalidate();
        panelVueParking.repaint();
    }

    private void initialiserCarteGererEntrees() {
        carteGererEntrees = new JPanel(null);

        JLabel lblTitre = new JLabel("Gérer les entrées", SwingConstants.CENTER);
        lblTitre.setBounds(20, 10, 740, 30);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 16));
        carteGererEntrees.add(lblTitre);

        JLabel lblImmat = new JLabel("Immatriculation :");
        lblImmat.setBounds(50, 60, 150, 25);
        carteGererEntrees.add(lblImmat);

        tfImmatEntree = new JTextField();
        tfImmatEntree.setBounds(210, 60, 250, 25);
        carteGererEntrees.add(tfImmatEntree);

        btnValiderEntree = new JButton("Valider entrée");
        btnValiderEntree.setBounds(480, 60, 150, 25);
        carteGererEntrees.add(btnValiderEntree);

        taResultatEntree = new JTextArea();
        taResultatEntree.setEditable(false);
        JScrollPane spResultat = new JScrollPane(taResultatEntree);
        spResultat.setBounds(50, 110, 700, 360);
        carteGererEntrees.add(spResultat);

        btnRetourEntree = new JButton("Retour");
        btnRetourEntree.setBounds(650, 480, 100, 30);
        carteGererEntrees.add(btnRetourEntree);

        btnValiderEntree.addActionListener(this);
        btnRetourEntree.addActionListener(this);
    }

    private void initialiserCarteGererSorties() {
        carteGererSorties = new JPanel(null);

        JLabel lblTitre = new JLabel("Gérer les sorties", SwingConstants.CENTER);
        lblTitre.setBounds(20, 10, 740, 30);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 16));
        carteGererSorties.add(lblTitre);

        JLabel lblTicketId = new JLabel("Numéro ticket :");
        lblTicketId.setBounds(50, 60, 150, 25);
        carteGererSorties.add(lblTicketId);

        tfTicketIdSortie = new JTextField();
        tfTicketIdSortie.setBounds(210, 60, 150, 25);
        carteGererSorties.add(tfTicketIdSortie);

        JLabel lblMoyen = new JLabel("Moyen paiement :");
        lblMoyen.setBounds(380, 60, 150, 25);
        carteGererSorties.add(lblMoyen);

        String[] moyens = {"Carte bancaire", "Espèces", "Chèque"};
        cbMoyenPaiement = new JComboBox<>(moyens);
        cbMoyenPaiement.setBounds(530, 60, 150, 25);
        carteGererSorties.add(cbMoyenPaiement);

        btnValiderSortie = new JButton("Valider sortie");
        btnValiderSortie.setBounds(350, 100, 150, 25);
        carteGererSorties.add(btnValiderSortie);

        taResultatSortie = new JTextArea();
        taResultatSortie.setEditable(false);
        JScrollPane spResultat = new JScrollPane(taResultatSortie);
        spResultat.setBounds(50, 140, 700, 330);
        carteGererSorties.add(spResultat);

        btnRetourSortie = new JButton("Retour");
        btnRetourSortie.setBounds(650, 480, 100, 30);
        carteGererSorties.add(btnRetourSortie);

        btnValiderSortie.addActionListener(this);
        btnRetourSortie.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == itemAccueil) {
            gestionnaireFenetre.show(content, "accueil");

        } else if (src == itemGererEntrees || src == bGererEntrees) {
            gestionnaireFenetre.show(content, "gerer_entrees");

        } else if (src == btnValiderEntree) {
            String immat = tfImmatEntree.getText();
            if (immat.isEmpty()) {
                taResultatEntree.setText("Erreur : immatriculation vide");
                return;
            }
            String resultat = Administrateur.gererEntree(immat, 10);
            taResultatEntree.setText(resultat);
            String lower = resultat.toLowerCase();
            if (!lower.contains("erreur") && !lower.contains("aucune place")) {
                tfImmatEntree.setText("");
                chargerVueParking();
            }

        } else if (src == btnRetourEntree) {
            tfImmatEntree.setText("");
            taResultatEntree.setText("");
            gestionnaireFenetre.show(content, "accueil");

        } else if (src == itemGererSorties || src == bGererSorties) {
            gestionnaireFenetre.show(content, "gerer_sorties");
            taResultatSortie.setText(Administrateur.chargerTicketsActifs());
        } else if (src == btnValiderSortie) {
            String ticketIdStr = tfTicketIdSortie.getText();
            if (ticketIdStr.isEmpty()) {
                taResultatSortie.setText("Erreur : numéro de ticket vide");
                return;
            }

            int ticketId;
            try {
                ticketId = Integer.parseInt(ticketIdStr);
            } catch (NumberFormatException nfe) {
                taResultatSortie.setText("Erreur : numéro de ticket invalide");
                return;
            }

            String moyenPaiement = (String) cbMoyenPaiement.getSelectedItem();
            String resultat = Administrateur.gererSortie(ticketId, moyenPaiement, 10);
            taResultatSortie.setText(resultat);
            String lower = resultat.toLowerCase();
            if (!lower.contains("erreur") && !lower.contains("ticket introuvable") && !lower.contains("déjà")) {
                tfTicketIdSortie.setText("");
                chargerVueParking();
            }

        } else if (src == btnRetourSortie) {
            tfTicketIdSortie.setText("");
            taResultatSortie.setText("");
            gestionnaireFenetre.show(content, "accueil");

        } else if (src == itemEtatParking) {
            gestionnaireFenetre.show(content, "etat_parking");

        } else if (src == itemListeAbonnes) {
            gestionnaireFenetre.show(content, "liste_abonnes");
            taListeAbonnes.setText(Administrateur.afficherListeAbonnes());

        } else if (src == itemRecettes) {
            gestionnaireFenetre.show(content, "recettes");
            afficheRecettes.setText(Administrateur.afficherRecettes());

        } else if (src == itemTickets) {
            gestionnaireFenetre.show(content, "tickets");
            taTickets.setText(Administrateur.chargerTicketsActifs());

        } else if (src == itemCreerAbonne || src == bCreation) {
            gestionnaireFenetre.show(content, "creer_abonne");

        } else if (src == btnCreer) {
            String nom = tfNom.getText();
            String prenom = tfPrenom.getText();
            String email = tfEmail.getText();
            String motDePasse = new String(pfMotDePasse.getPassword());
            String immat = tfImmat.getText();
            String dureeStr = tfDuree.getText();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || immat.isEmpty()) {
                lblMessage.setText("Tous les champs sont obligatoires");
                lblMessage.setForeground(Color.RED);
                return;
            }

            int duree;
            try {
                duree = Integer.parseInt(dureeStr.isEmpty() ? "0" : dureeStr);
            } catch (NumberFormatException nfe) {
                lblMessage.setText("Durée invalide");
                lblMessage.setForeground(Color.RED);
                return;
            }

            if (duree <= 0) {
                lblMessage.setText("La durée doit être supérieure à 0");
                lblMessage.setForeground(Color.RED);
                return;
            }

            String resultat = Administrateur.creerNouvelAbonne(nom, prenom, email, motDePasse, immat, duree, 10);

            if (resultat.toLowerCase().contains("erreur") || resultat.toLowerCase().contains("aucune place")) {
                lblMessage.setText("Erreur lors de la création");
                lblMessage.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, resultat, "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                lblMessage.setText("Abonné créé avec succès");
                lblMessage.setForeground(new Color(0, 150, 0));
                tfNom.setText("");
                tfPrenom.setText("");
                tfEmail.setText("");
                pfMotDePasse.setText("");
                tfImmat.setText("");
                tfDuree.setText("6");
                JOptionPane.showMessageDialog(this, resultat, "Info", JOptionPane.INFORMATION_MESSAGE);
                chargerVueParking();
            }

        } else if (src == btnAnnuler) {
            tfNom.setText("");
            tfPrenom.setText("");
            tfEmail.setText("");
            pfMotDePasse.setText("");
            tfImmat.setText("");
            tfDuree.setText("6");
            lblMessage.setText("");
            gestionnaireFenetre.show(content, "accueil");

        } else if (src == itemQuitter) {
            dispose();

        } else if (src == itemDeconnexion) {
            dispose();
            FenetreConnexion.main(null);

        } else if (src == bRafraichirVue) {
            chargerVueParking();

        } else if (src == btnRafraichirTickets) {
            taTickets.setText(Administrateur.chargerTicketsActifs());

        } else if (src == btnRetourTickets) {
            gestionnaireFenetre.show(content, "accueil");

        } else if (src == btnRafraichirRecettes) {
            afficheRecettes.setText(Administrateur.afficherRecettes());

        } else if (src == btnRetourRecettes) {
            gestionnaireFenetre.show(content, "accueil");

        } else if (src == btnRafraichirAbonnes) {
            taListeAbonnes.setText(Administrateur.afficherListeAbonnes());

        } else if (src == btnRetourAbonnes) {
            gestionnaireFenetre.show(content, "accueil");
        } else if (src == btnRafraichirEtatParking) {
            taEtatParking.setText(Administrateur.afficherEtatParking(10));
        } else if (src == btnRetourEtatParking) {
            gestionnaireFenetre.show(content, "accueil");
        }
    }

    public static void main(String[] args) {
        new FenetreAdmin();
    }
}