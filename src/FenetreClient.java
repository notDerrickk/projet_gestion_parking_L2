//Hugo
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FenetreClient extends JFrame implements ActionListener {
    private static String emailUtilisateurConnecte = null;

    JMenuBar barreMenus;
    JMenu menuActions, menuDeconnexion;
    JMenuItem itemAccueil, itemConsulterAbonnement, itemModifierInfos, itemSeDeconnecter;

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            emailUtilisateurConnecte = args[0];
        } else {
            emailUtilisateurConnecte = "test@test.com";
        }
        
        ClientAbonne.nettoyerPlaces();
        
        int jours = ClientAbonne.joursAvantExpiration(emailUtilisateurConnecte);
        if (jours > 0 && jours <= 7) {
            JOptionPane.showMessageDialog(null, 
                "Votre abonnement expire dans " + jours + " jour(s) !\n\nN'oubliez pas de le prolonger.",
                "Alerte abonnement", 
                JOptionPane.WARNING_MESSAGE);
        } else if (jours == 0) {
            JOptionPane.showMessageDialog(null,
                "Votre abonnement expire AUJOURD'HUI !",
                "Alerte abonnement",
                JOptionPane.WARNING_MESSAGE);
        } else if (jours < 0) {
            JOptionPane.showMessageDialog(null,
                "ERREUR\n\nVotre abonnement a expiré !",
                "Abonnement expiré",
                JOptionPane.ERROR_MESSAGE);
        }
        
        new FenetreClient();
    }

    public FenetreClient() {
        setTitle("Client - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(null);

        initialiserMenu();

        // Label titre
        JLabel titre = new JLabel("Accueil Client");
        titre.setHorizontalAlignment(JLabel.CENTER);
        titre.setFont(titre.getFont().deriveFont(20f));
        titre.setBounds(10, 10, 670, 40);
        add(titre);

        // Bouton 1 : Consulter abonnement
        JButton btn1 = new JButton("Consulter abonnement");
        btn1.setBounds(100, 100, 500, 60);
        btn1.setFont(btn1.getFont().deriveFont(14f));
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ouvrirFenetreConsulterAbonnement();
            }
        });
        add(btn1);

        // Bouton 2 : Modifier infos
        JButton btn2 = new JButton("Modifier infos");
        btn2.setBounds(100, 200, 500, 60);
        btn2.setFont(btn2.getFont().deriveFont(14f));
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ouvrirFenetreModifierInfos();
            }
        });
        add(btn2);

        // Bouton Déconnexion
        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.setBounds(100, 300, 500, 60);
        btnDeconnexion.setFont(btnDeconnexion.getFont().deriveFont(14f));
        btnDeconnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                FenetreConnexion.main(null);
            }
        });
        add(btnDeconnexion);

        setJMenuBar(barreMenus);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initialiserMenu() {
        barreMenus = new JMenuBar();
        menuActions = new JMenu("Actions");
        menuDeconnexion = new JMenu("Déconnexion");

        itemAccueil = new JMenuItem("Accueil");
        itemConsulterAbonnement = new JMenuItem("Consulter abonnement");
        itemModifierInfos = new JMenuItem("Modifier infos");
        itemSeDeconnecter = new JMenuItem("Se déconnecter");

        itemAccueil.addActionListener(this);
        itemConsulterAbonnement.addActionListener(this);
        itemModifierInfos.addActionListener(this);
        itemSeDeconnecter.addActionListener(this);

        menuActions.add(itemAccueil);
        menuActions.addSeparator();
        menuActions.add(itemConsulterAbonnement);
        menuActions.add(itemModifierInfos);

        menuDeconnexion.add(itemSeDeconnecter);

        barreMenus.add(menuActions);
        barreMenus.add(menuDeconnexion);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemAccueil) {
            // Rester à l'accueil (refresh)
            setTitle("Client - Menu");
        } else if (e.getSource() == itemConsulterAbonnement) {
            ouvrirFenetreConsulterAbonnement();
        } else if (e.getSource() == itemModifierInfos) {
            ouvrirFenetreModifierInfos();
        } else if (e.getSource() == itemSeDeconnecter) {
            dispose();
            FenetreConnexion.main(null);
        }
    }

    private static void ouvrirFenetreConsulterAbonnement() {
        // Charger les infos de l'abonnement
        String[] infosAbonnement = ClientAbonne.chargerAbonnement(emailUtilisateurConnecte);
        
        JFrame fenetreAbo = new JFrame("Consulter abonnement");
        fenetreAbo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetreAbo.setSize(700, 600);
        fenetreAbo.setLayout(null);

        // Créer et ajouter le menu bar
        JMenuBar barreMenusAbo = new JMenuBar();
        JMenu menuActionsAbo = new JMenu("Actions");
        JMenu menuDeconnexionAbo = new JMenu("Déconnexion");

        JMenuItem itemAccueilAbo = new JMenuItem("Accueil");
        JMenuItem itemConsulterAbonnementAbo = new JMenuItem("Consulter abonnement");
        JMenuItem itemModifierInfosAbo = new JMenuItem("Modifier infos");
        JMenuItem itemSeDeconnecterAbo = new JMenuItem("Se déconnecter");

        itemAccueilAbo.addActionListener(e -> fenetreAbo.dispose());
        itemConsulterAbonnementAbo.addActionListener(e -> {
            fenetreAbo.dispose();
            ouvrirFenetreConsulterAbonnement();
        });
        itemModifierInfosAbo.addActionListener(e -> {
            fenetreAbo.dispose();
            ouvrirFenetreModifierInfos();
        });
        itemSeDeconnecterAbo.addActionListener(e -> {
            fenetreAbo.dispose();
            FenetreConnexion.main(null);
        });

        menuActionsAbo.add(itemAccueilAbo);
        menuActionsAbo.addSeparator();
        menuActionsAbo.add(itemConsulterAbonnementAbo);
        menuActionsAbo.add(itemModifierInfosAbo);

        menuDeconnexionAbo.add(itemSeDeconnecterAbo);

        barreMenusAbo.add(menuActionsAbo);
        barreMenusAbo.add(menuDeconnexionAbo);

        fenetreAbo.setJMenuBar(barreMenusAbo);

        if (infosAbonnement != null) {
            String id = infosAbonnement[0];
            String dateDebut = infosAbonnement[1];
            String dateFin = infosAbonnement[2];
            String montant = infosAbonnement[3];
            String numeroPlace = infosAbonnement[4];

            // Label titre
            JLabel titre = new JLabel("Vos informations d'abonnement");
            titre.setHorizontalAlignment(JLabel.CENTER);
            titre.setBounds(10, 10, 670, 35);
            fenetreAbo.add(titre);

            // Label ID
            JLabel lId = new JLabel("ID Abonnement :");
            lId.setBounds(30, 70, 150, 35);
            lId.setFont(lId.getFont().deriveFont(14f));
            fenetreAbo.add(lId);
            JLabel valId = new JLabel(id);
            valId.setBounds(200, 70, 450, 35);
            valId.setFont(valId.getFont().deriveFont(14f));
            fenetreAbo.add(valId);

            // Label Date Début
            JLabel lDateDebut = new JLabel("Date début :");
            lDateDebut.setBounds(30, 120, 150, 35);
            lDateDebut.setFont(lDateDebut.getFont().deriveFont(14f));
            fenetreAbo.add(lDateDebut);
            JLabel valDateDebut = new JLabel(dateDebut);
            valDateDebut.setBounds(200, 120, 450, 35);
            valDateDebut.setFont(valDateDebut.getFont().deriveFont(14f));
            fenetreAbo.add(valDateDebut);

            // Label Date Fin
            JLabel lDateFin = new JLabel("Date fin :");
            lDateFin.setBounds(30, 170, 150, 35);
            lDateFin.setFont(lDateFin.getFont().deriveFont(14f));
            fenetreAbo.add(lDateFin);
            JLabel valDateFin = new JLabel(dateFin);
            valDateFin.setBounds(200, 170, 450, 35);
            valDateFin.setFont(valDateFin.getFont().deriveFont(14f));
            fenetreAbo.add(valDateFin);

            // Label Montant mensuel
            JLabel lMontant = new JLabel("Montant mensuel :");
            lMontant.setBounds(30, 220, 150, 35);
            lMontant.setFont(lMontant.getFont().deriveFont(14f));
            fenetreAbo.add(lMontant);
            JLabel valMontant = new JLabel(montant + "€");
            valMontant.setBounds(200, 220, 450, 35);
            valMontant.setFont(valMontant.getFont().deriveFont(14f));
            fenetreAbo.add(valMontant);

            // Label Numéro de place
            JLabel lPlace = new JLabel("Votre place :");
            lPlace.setBounds(30, 270, 150, 35);
            lPlace.setFont(lPlace.getFont().deriveFont(14f));
            fenetreAbo.add(lPlace);
            JLabel valPlace = new JLabel(numeroPlace);
            valPlace.setBounds(200, 270, 450, 35);
            valPlace.setFont(valPlace.getFont().deriveFont(14f));
            fenetreAbo.add(valPlace);

            // Bouton Résilier (à gauche)
            JButton btnResilier = new JButton("Résilier");
            btnResilier.setBounds(50, 330, 120, 45);
            fenetreAbo.add(btnResilier);

            btnResilier.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(fenetreAbo, "Êtes-vous sûr de vouloir résilier votre abonnement ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int resultat = ClientAbonne.resilierAbonnement(emailUtilisateurConnecte);
                        if (resultat == 1) {
                            JOptionPane.showMessageDialog(fenetreAbo, "Abonnement résilié.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                            fenetreAbo.dispose();
                        } else {
                            JOptionPane.showMessageDialog(fenetreAbo, "Erreur lors de la résiliation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            // Bouton Prolonger (au centre)
            JButton btnProlonger = new JButton("Prolonger");
            btnProlonger.setBounds(290, 330, 120, 45);
            fenetreAbo.add(btnProlonger);

            btnProlonger.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ouvrirFenetreProlonger();
                }
            });

            // Bouton Fermer 
            JButton btnFermer = new JButton("Fermer");
            btnFermer.setBounds(530, 330, 120, 45);
            fenetreAbo.add(btnFermer);

            btnFermer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fenetreAbo.dispose();
                }
            });
        } else {
            // L'utilisateur n'a pas d'abonnement - afficher les formules disponibles
            JLabel titre = new JLabel("Vous n'avez pas d'abonnement");
            titre.setHorizontalAlignment(JLabel.CENTER);
            titre.setBounds(10, 10, 670, 35);
            fenetreAbo.add(titre);

            JLabel soustitre = new JLabel("Choisissez une formule :");
            soustitre.setBounds(20, 55, 650, 30);
            soustitre.setFont(soustitre.getFont().deriveFont(14f));
            fenetreAbo.add(soustitre);

            // Formule 1 mois - 75,99€/mois
            JPanel panel1 = creerPanelFormule(20, 110, "1 mois", 75.99, 1, fenetreAbo);
            fenetreAbo.add(panel1);

            // Formule 3 mois - 66,99€/mois
            JPanel panel3 = creerPanelFormule(20, 190, "3 mois", 66.99, 3, fenetreAbo);
            fenetreAbo.add(panel3);

            // Formule 6 mois - 63,99€/mois
            JPanel panel6 = creerPanelFormule(20, 270, "6 mois", 63.99, 6, fenetreAbo);
            fenetreAbo.add(panel6);

            // Formule 12 mois - 58,99€/mois
            JPanel panel12 = creerPanelFormule(20, 350, "12 mois", 58.99, 12, fenetreAbo);
            fenetreAbo.add(panel12);
        }

        fenetreAbo.setLocationRelativeTo(null);
        fenetreAbo.setVisible(true);
    }

    private static void ouvrirFenetreProlonger() {
        JFrame fenetreProlonger = new JFrame("Prolonger abonnement");
        fenetreProlonger.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetreProlonger.setSize(600, 450);
        fenetreProlonger.setLayout(null);

        // Label titre
        JLabel titre = new JLabel("Choisissez la durée de prolongation");
        titre.setHorizontalAlignment(JLabel.CENTER);
        titre.setBounds(10, 10, 570, 35);
        titre.setFont(titre.getFont().deriveFont(16f));
        fenetreProlonger.add(titre);

        // Option 1 mois
        JPanel panel1 = creerPanelProlongation(20, 60, "1 mois", 1, fenetreProlonger);
        fenetreProlonger.add(panel1);

        // Option 3 mois
        JPanel panel3 = creerPanelProlongation(20, 140, "3 mois", 3, fenetreProlonger);
        fenetreProlonger.add(panel3);

        // Option 6 mois
        JPanel panel6 = creerPanelProlongation(20, 220, "6 mois", 6, fenetreProlonger);
        fenetreProlonger.add(panel6);

        // Option 12 mois
        JPanel panel12 = creerPanelProlongation(20, 300, "12 mois", 12, fenetreProlonger);
        fenetreProlonger.add(panel12);

        fenetreProlonger.setLocationRelativeTo(null);
        fenetreProlonger.setVisible(true);
    }

    private static JPanel creerPanelProlongation(int x, int y, String duree, int mois, JFrame parent) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(x, y, 560, 70);
        panel.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));

        JLabel label = new JLabel("Ajouter " + duree);
        label.setBounds(15, 15, 350, 40);
        label.setFont(label.getFont().deriveFont(14f));
        panel.add(label);

        JButton btnChoisir = new JButton("Ajouter");
        btnChoisir.setBounds(415, 15, 130, 40);
        btnChoisir.setFont(btnChoisir.getFont().deriveFont(13f));
        panel.add(btnChoisir);

        btnChoisir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resultat = ClientAbonne.prolongerAbonnement(emailUtilisateurConnecte, mois);
                if (resultat == 1) {
                    JOptionPane.showMessageDialog(parent, "Abonnement prolongé de " + duree + ".", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    parent.dispose();
                } else {
                    JOptionPane.showMessageDialog(parent, "Erreur lors de la prolongation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private static JPanel creerPanelFormule(int x, int y, String duree, double montant, int mois, JFrame parent) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(x, y, 640, 70);
        panel.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));

        JLabel label = new JLabel(duree + " - " + String.format("%.2f", montant) + "€/mois");
        label.setBounds(15, 15, 400, 40);
        label.setFont(label.getFont().deriveFont(14f));
        panel.add(label);

        JButton btnChoisir = new JButton("Choisir");
        btnChoisir.setBounds(515, 15, 110, 40);
        btnChoisir.setFont(btnChoisir.getFont().deriveFont(13f));
        panel.add(btnChoisir);

        btnChoisir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resultat = ClientAbonne.creerAbonnement(emailUtilisateurConnecte, mois, montant);
                if (resultat == 1) {
                    JOptionPane.showMessageDialog(parent, "Abonnement créé avec succès !\nDurée : " + duree + "\nMontant : " + montant + "€", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    parent.dispose();
                } else {
                    JOptionPane.showMessageDialog(parent, "Erreur lors de la création de l'abonnement.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private static void ouvrirFenetreModifierInfos() {
        // Charger les infos actuelles de l'utilisateur
        String[] infosUtilisateur = ClientAbonne.chargerInfosUtilisateur(emailUtilisateurConnecte);
        
        if (infosUtilisateur == null) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des infos.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifier si l'utilisateur a un abonnement
        String[] infosAbonnement = ClientAbonne.chargerAbonnement(emailUtilisateurConnecte);
        boolean aUnAbonnement = infosAbonnement != null;

        String nomActuel = infosUtilisateur[0];
        String prenomActuel = infosUtilisateur[1];
        String emailActuel = infosUtilisateur[2];
        String motDePasseActuel = infosUtilisateur[3];
        String placeActuelle = aUnAbonnement ? infosUtilisateur[4] : "Non attribué";
        String immatriculationActuelle = infosUtilisateur[5];

        JFrame fenetreModif = new JFrame("Modifier mes informations");
        fenetreModif.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetreModif.setSize(650, 650);
        fenetreModif.setLayout(null);

        // Créer et ajouter le menu bar
        JMenuBar barreMenusModif = new JMenuBar();
        JMenu menuActionsModif = new JMenu("Actions");
        JMenu menuDeconnexionModif = new JMenu("Déconnexion");

        JMenuItem itemAccueilModif = new JMenuItem("Accueil");
        JMenuItem itemConsulterAbonnementModif = new JMenuItem("Consulter abonnement");
        JMenuItem itemModifierInfosModif = new JMenuItem("Modifier infos");
        JMenuItem itemSeDeconnecterModif = new JMenuItem("Se déconnecter");

        itemAccueilModif.addActionListener(e -> fenetreModif.dispose());
        itemConsulterAbonnementModif.addActionListener(e -> {
            fenetreModif.dispose();
            ouvrirFenetreConsulterAbonnement();
        });
        itemModifierInfosModif.addActionListener(e -> {
            fenetreModif.dispose();
            ouvrirFenetreModifierInfos();
        });
        itemSeDeconnecterModif.addActionListener(e -> {
            fenetreModif.dispose();
            FenetreConnexion.main(null);
        });

        menuActionsModif.add(itemAccueilModif);
        menuActionsModif.addSeparator();
        menuActionsModif.add(itemConsulterAbonnementModif);
        menuActionsModif.add(itemModifierInfosModif);

        menuDeconnexionModif.add(itemSeDeconnecterModif);

        barreMenusModif.add(menuActionsModif);
        barreMenusModif.add(menuDeconnexionModif);

        fenetreModif.setJMenuBar(barreMenusModif);

        // Label Nom
        JLabel lNom = new JLabel("Nom :");
        lNom.setBounds(30, 30, 120, 35);
        lNom.setFont(lNom.getFont().deriveFont(13f));
        fenetreModif.add(lNom);
        JTextField tfNom = new JTextField(nomActuel);
        tfNom.setBounds(160, 30, 450, 35);
        tfNom.setFont(tfNom.getFont().deriveFont(13f));
        fenetreModif.add(tfNom);

        // Label Prénom
        JLabel lPrenom = new JLabel("Prénom :");
        lPrenom.setBounds(30, 90, 120, 35);
        lPrenom.setFont(lPrenom.getFont().deriveFont(13f));
        fenetreModif.add(lPrenom);
        JTextField tfPrenom = new JTextField(prenomActuel);
        tfPrenom.setBounds(160, 90, 450, 35);
        tfPrenom.setFont(tfPrenom.getFont().deriveFont(13f));
        fenetreModif.add(tfPrenom);

        // Label Email
        JLabel lEmail = new JLabel("Email :");
        lEmail.setBounds(30, 150, 120, 35);
        lEmail.setFont(lEmail.getFont().deriveFont(13f));
        fenetreModif.add(lEmail);
        JTextField tfEmail = new JTextField(emailActuel);
        tfEmail.setBounds(160, 150, 450, 35);
        tfEmail.setFont(tfEmail.getFont().deriveFont(13f));
        fenetreModif.add(tfEmail);

        // Label Mot de passe
        JLabel lMotDePasse = new JLabel("Mot de passe :");
        lMotDePasse.setBounds(30, 210, 120, 35);
        lMotDePasse.setFont(lMotDePasse.getFont().deriveFont(13f));
        fenetreModif.add(lMotDePasse);
        JPasswordField pfMotDePasse = new JPasswordField(motDePasseActuel);
        pfMotDePasse.setBounds(160, 210, 450, 35);
        pfMotDePasse.setFont(pfMotDePasse.getFont().deriveFont(13f));
        fenetreModif.add(pfMotDePasse);

        // Label Immatriculation
        JLabel lImmat = new JLabel("Immatriculation :");
        lImmat.setBounds(30, 270, 120, 35);
        lImmat.setFont(lImmat.getFont().deriveFont(13f));
        fenetreModif.add(lImmat);
        JTextField tfImmat = new JTextField(immatriculationActuelle);
        tfImmat.setBounds(160, 270, 450, 35);
        tfImmat.setFont(tfImmat.getFont().deriveFont(13f));
        fenetreModif.add(tfImmat);

        // Label Place (non modifiable)
        JLabel lPlace = new JLabel("Place :");
        lPlace.setBounds(30, 330, 120, 35);
        lPlace.setFont(lPlace.getFont().deriveFont(13f));
        fenetreModif.add(lPlace);
        JLabel lblPlace = new JLabel(placeActuelle);
        lblPlace.setBounds(160, 330, 200, 35);
        lblPlace.setFont(lblPlace.getFont().deriveFont(13f));
        fenetreModif.add(lblPlace);

        // Bouton Choisir place (actif seulement si abonnement)
        JButton btnChoisirPlace = new JButton("Choisir");
        btnChoisirPlace.setBounds(370, 330, 100, 35);
        btnChoisirPlace.setFont(btnChoisirPlace.getFont().deriveFont(12f));
        btnChoisirPlace.setEnabled(aUnAbonnement);
        fenetreModif.add(btnChoisirPlace);

        btnChoisirPlace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ouvrirFenetreChoisirPlace(fenetreModif, lblPlace);
            }
        });

        // Bouton Sauvegarder
        JButton btnSauvegarder = new JButton("Sauvegarder");
        btnSauvegarder.setBounds(225, 410, 200, 50);
        btnSauvegarder.setFont(btnSauvegarder.getFont().deriveFont(14f));
        fenetreModif.add(btnSauvegarder);

        btnSauvegarder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = tfNom.getText().trim();
                String prenom = tfPrenom.getText().trim();
                String email = tfEmail.getText().trim();
                String motDePasse = new String(pfMotDePasse.getPassword());
                String immatriculation = tfImmat.getText().trim();

                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
                    JOptionPane.showMessageDialog(fenetreModif, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean succes = ClientAbonne.sauvegarderModifications(emailActuel, nom, prenom, email, motDePasse, immatriculation);
                if (succes) {
                    JOptionPane.showMessageDialog(fenetreModif, "Informations mises à jour avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    fenetreModif.dispose();
                } else {
                    JOptionPane.showMessageDialog(fenetreModif, "Erreur lors de la mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        fenetreModif.setLocationRelativeTo(null);
        fenetreModif.setVisible(true);
    }

    private static void ouvrirFenetreChoisirPlace(JFrame fenetreParent, JLabel lblPlace) {
        JFrame fenetrePlace = new JFrame("Choisir une place");
        fenetrePlace.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fenetrePlace.setSize(800, 550);
        fenetrePlace.setLayout(new BorderLayout());

        // Titre
        JLabel titre = new JLabel("Sélectionnez une place réservée aux abonnés (BLEUE)", SwingConstants.CENTER);
        titre.setFont(titre.getFont().deriveFont(16f));
        fenetrePlace.add(titre, BorderLayout.NORTH);

        // Panel pour les places
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(0, 10, 5, 5));
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ArrayList<Administrateur.PlaceInfo> places = Administrateur.recupererPlaces();

        for (Administrateur.PlaceInfo p : places) {
            JPanel placePanel = new JPanel();
            placePanel.setLayout(new BorderLayout());
            placePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            placePanel.setPreferredSize(new Dimension(70, 60));

            
            Color couleur;
            if (p.type.equals("Reservee") && p.etat == 1) {
                couleur = new Color(255, 100, 100);
            } else if (p.type.equals("Reservee") && p.etat == 0) {
                couleur = new Color(100, 150, 255); 
            } else if (p.etat == 1) {
                couleur = new Color(255, 100, 100);
            } else {
                couleur = new Color(100, 255, 100); 
            }
            placePanel.setBackground(couleur);

            JLabel lblNumero = new JLabel(String.valueOf(p.numero), SwingConstants.CENTER);
            lblNumero.setFont(new Font("Arial", Font.BOLD, 14));
            placePanel.add(lblNumero, BorderLayout.NORTH);

            // Ajouter des infos si occupée
            if (p.etat == 1 && p.type.equals("Normale") && p.immat != null && !p.immat.isEmpty()) {
                JLabel lblImmat = new JLabel(p.immat, SwingConstants.CENTER);
                lblImmat.setFont(new Font("Arial", Font.PLAIN, 9));
                placePanel.add(lblImmat, BorderLayout.CENTER);
            }

            final int numeroPlace = p.numero;
            final JPanel panelPlace = placePanel;
            
            if (p.type.equals("Reservee") && p.etat == 0) {
                placePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                placePanel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        // Assigner la place à l'utilisateur
                        int resultat = ClientAbonne.assignerPlace(emailUtilisateurConnecte, numeroPlace);
                        if (resultat == 1) {                            
                            // Changer la couleur du panel en rouge pour montrer le changement
                            panelPlace.setBackground(new Color(255, 100, 100));
                            panelPlace.revalidate();
                            panelPlace.repaint();
                            
                            lblPlace.setText(String.valueOf(numeroPlace));
                            JOptionPane.showMessageDialog(fenetrePlace, "Place " + numeroPlace + " assignée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                            
                            // Attendre un peu avant de fermer pour que l'utilisateur voie le changement
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ie) {}
                            
                            fenetrePlace.dispose();
                        } else {
                            JOptionPane.showMessageDialog(fenetrePlace, "Erreur lors de l'assignation de la place.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }

            gridPanel.add(placePanel);
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        fenetrePlace.add(scrollPane, BorderLayout.CENTER);

        // Légende
        JPanel legendePanel = new JPanel();
        legendePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel legLibre = new JLabel("  Libre  ");
        legLibre.setOpaque(true);
        legLibre.setBackground(new Color(100, 255, 100));
        legLibre.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel legOccupee = new JLabel("  Occupée  ");
        legOccupee.setOpaque(true);
        legOccupee.setBackground(new Color(255, 100, 100));
        legOccupee.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel legReservee = new JLabel("  Réservée (cliquable)  ");
        legReservee.setOpaque(true);
        legReservee.setBackground(new Color(100, 150, 255));
        legReservee.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        legendePanel.add(legLibre);
        legendePanel.add(legOccupee);
        legendePanel.add(legReservee);

        fenetrePlace.add(legendePanel, BorderLayout.SOUTH);

        fenetrePlace.setLocationRelativeTo(null);
        fenetrePlace.setVisible(true);
    }
}