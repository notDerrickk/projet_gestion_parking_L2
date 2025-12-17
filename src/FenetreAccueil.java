//Hugo
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class FenetreAccueil {

    public static void main(String[] args) {
        JFrame fenetre = new JFrame("Parking - Accueil");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(700, 700);
        fenetre.setLayout(null);

        JLabel titre = new JLabel("Bienvenue dans le Parking");
        titre.setHorizontalAlignment(JLabel.CENTER);
        titre.setFont(titre.getFont().deriveFont(24f));
        titre.setBounds(10, 20, 660, 40);
        fenetre.add(titre);

        JLabel sousTitre = new JLabel("Créez votre compte pour accéder au service");
        sousTitre.setHorizontalAlignment(JLabel.CENTER);
        sousTitre.setFont(sousTitre.getFont().deriveFont(14f));
        sousTitre.setBounds(10, 70, 660, 30);
        fenetre.add(sousTitre);

        // Label Nom
        JLabel lNom = new JLabel("Nom :");
        lNom.setBounds(80, 130, 100, 35);
        lNom.setFont(lNom.getFont().deriveFont(13f));
        fenetre.add(lNom);
        JTextField tfNom = new JTextField();
        tfNom.setBounds(200, 130, 400, 35);
        tfNom.setFont(tfNom.getFont().deriveFont(13f));
        fenetre.add(tfNom);

        // Label Prénom
        JLabel lPrenom = new JLabel("Prénom :");
        lPrenom.setBounds(80, 180, 100, 35);
        lPrenom.setFont(lPrenom.getFont().deriveFont(13f));
        fenetre.add(lPrenom);
        JTextField tfPrenom = new JTextField();
        tfPrenom.setBounds(200, 180, 400, 35);
        tfPrenom.setFont(tfPrenom.getFont().deriveFont(13f));
        fenetre.add(tfPrenom);

        // Label Email
        JLabel lEmail = new JLabel("Email :");
        lEmail.setBounds(80, 230, 100, 35);
        lEmail.setFont(lEmail.getFont().deriveFont(13f));
        fenetre.add(lEmail);
        JTextField tfEmail = new JTextField();
        tfEmail.setBounds(200, 230, 400, 35);
        tfEmail.setFont(tfEmail.getFont().deriveFont(13f));
        fenetre.add(tfEmail);

        // Label Mot de passe
        JLabel lMotDePasse = new JLabel("Mot de passe :");
        lMotDePasse.setBounds(80, 280, 100, 35);
        lMotDePasse.setFont(lMotDePasse.getFont().deriveFont(13f));
        fenetre.add(lMotDePasse);
        JPasswordField pfMotDePasse = new JPasswordField();
        pfMotDePasse.setBounds(200, 280, 400, 35);
        pfMotDePasse.setFont(pfMotDePasse.getFont().deriveFont(13f));
        fenetre.add(pfMotDePasse);

        // Label Confirmer mot de passe
        JLabel lConfirmMotDePasse = new JLabel("Confirmer :");
        lConfirmMotDePasse.setBounds(80, 330, 100, 35);
        lConfirmMotDePasse.setFont(lConfirmMotDePasse.getFont().deriveFont(13f));
        fenetre.add(lConfirmMotDePasse);
        JPasswordField pfConfirmMotDePasse = new JPasswordField();
        pfConfirmMotDePasse.setBounds(200, 330, 400, 35);
        pfConfirmMotDePasse.setFont(pfConfirmMotDePasse.getFont().deriveFont(13f));
        fenetre.add(pfConfirmMotDePasse);

        // Label Immatriculation (optionnel)
        JLabel lImmatriculation = new JLabel("Immatriculation :");
        lImmatriculation.setBounds(80, 380, 100, 35);
        lImmatriculation.setFont(lImmatriculation.getFont().deriveFont(13f));
        fenetre.add(lImmatriculation);
        JTextField tfImmatriculation = new JTextField();
        tfImmatriculation.setBounds(200, 380, 400, 35);
        tfImmatriculation.setFont(tfImmatriculation.getFont().deriveFont(13f));
        fenetre.add(tfImmatriculation);

        // Bouton S'inscrire
        JButton btnInscrire = new JButton("S'inscrire");
        btnInscrire.setBounds(175, 480, 150, 50);
        btnInscrire.setFont(btnInscrire.getFont().deriveFont(14f));
        fenetre.add(btnInscrire);

        // Bouton Connexion
        JButton btnConnexion = new JButton("Connexion");
        btnConnexion.setBounds(375, 480, 150, 50);
        btnConnexion.setFont(btnConnexion.getFont().deriveFont(14f));
        fenetre.add(btnConnexion);

        // Action bouton S'inscrire
        btnInscrire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = tfNom.getText().trim();
                String prenom = tfPrenom.getText().trim();
                String email = tfEmail.getText().trim();
                String motDePasse = new String(pfMotDePasse.getPassword());
                String confirmMotDePasse = new String(pfConfirmMotDePasse.getPassword());
                String immatriculation = tfImmatriculation.getText().trim();

                if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
                    JOptionPane.showMessageDialog(fenetre, "Tous les champs obligatoires doivent être remplis.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!motDePasse.equals(confirmMotDePasse)) {
                    JOptionPane.showMessageDialog(fenetre, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (motDePasse.length() < 4) {
                    JOptionPane.showMessageDialog(fenetre, "Le mot de passe doit contenir au moins 4 caractères.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean succes = creerUtilisateur(nom, prenom, email, motDePasse, immatriculation);
                if (succes) {
                    JOptionPane.showMessageDialog(fenetre, "Inscription réussie ! Vous pouvez maintenant vous connecter.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    fenetre.dispose();
                    FenetreConnexion.main(null);
                } else {
                    JOptionPane.showMessageDialog(fenetre, "Erreur lors de l'inscription. L'email existe peut-être déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action bouton Connexion
        btnConnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fenetre.dispose();
                FenetreConnexion.main(null);
            }
        });

        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }

    private static boolean creerUtilisateur(String nom, String prenom, String email, String motDePasse, String immatriculation) {
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

            String sql = "INSERT INTO utilisateurs (nom, prenom, email, mot_de_passe, role, immatriculation) " +
                         "VALUES ('" + nom2 + "', '" + prenom2 + "', '" + email2 + "', '" + pass2 + "', 'CLIENT', '" + immat2 + "')";
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            DbConnexion.closeConnexion();
        }
    }
}
