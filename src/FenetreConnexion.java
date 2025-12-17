//Rodéric
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class FenetreConnexion {

    public static void main(String[] args) {
        System.out.println("FenetreConnexion lancée");
        final JFrame f = new JFrame("Connexion");
        f.setSize(380, 210);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        final JRadioButton rbAdmin = new JRadioButton("Administrateur");
        final JRadioButton rbClient = new JRadioButton("Client abonné");
        rbClient.setSelected(true);

        final ButtonGroup bg = new ButtonGroup();
        bg.add(rbAdmin);
        bg.add(rbClient);

        final JLabel lType = new JLabel("Type :");
        lType.setBounds(10, 10, 50, 25);
        rbAdmin.setBounds(70, 10, 120, 25);
        rbClient.setBounds(200, 10, 140, 25);

        final JLabel lEmail = new JLabel("Email :");
        lEmail.setBounds(10, 45, 80, 25);
        final JTextField tfEmail = new JTextField();
        tfEmail.setBounds(80, 45, 270, 25);

        final JLabel lPass = new JLabel("Mot de passe :");
        lPass.setBounds(10, 80, 100, 25);
        final JPasswordField pf = new JPasswordField();
        pf.setBounds(110, 80, 240, 25);

        final JButton btnConnect = new JButton("Connexion");
        btnConnect.setBounds(200, 125, 120, 30);

        // Bouton S'inscrire remplace Annuler
        final JButton btnInscrire = new JButton("S'inscrire");
        btnInscrire.setBounds(70, 125, 100, 30);

        f.add(lType);
        f.add(rbAdmin);
        f.add(rbClient);
        f.add(lEmail);
        f.add(tfEmail);
        f.add(lPass);
        f.add(pf);
        f.add(btnConnect);
        f.add(btnInscrire);

        // Action bouton S'inscrire
        btnInscrire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                FenetreAccueil.main(null);
            }
        });

        btnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText().trim();
                String pass = new String(pf.getPassword());
                String wantedType;
                if (rbAdmin.isSelected()) {
                    wantedType = "admin";
                } else {
                    wantedType = "client";
                }

                if (email.length() == 0 || pass.length() == 0) {
                    JOptionPane.showMessageDialog(f, "Email et mot de passe requis.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean ok = verifyInDatabase(email, pass, wantedType);
                if (ok) {
                    JOptionPane.showMessageDialog(f, "Connexion OK (" + wantedType + ")", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    f.dispose();
                    if ("admin".equals(wantedType)) {
                        FenetreAdmin.main(null);
                    } else {
                        FenetreClient.main(new String[]{email});
                    }
                } else {
                    JOptionPane.showMessageDialog(f, "Identifiants incorrects ou rôle incompatible.", "Échec", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static boolean verifyInDatabase(String email, String pass, String wantedType) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = DbConnexion.connexion();
            if (stmt == null) {
                JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base.", "Erreur BD", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String emailEsc = email.replace("'", "''");
            String passEsc = pass.replace("'", "''");

            String sql = "SELECT id, role FROM utilisateurs WHERE email = '" + emailEsc +
                         "' AND mot_de_passe = '" + passEsc + "' LIMIT 1";
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                return false;
            }
            String role = rs.getString("role");
            if (role == null) role = "";

            if ("admin".equals(wantedType)) {
                if (!"ADMIN".equalsIgnoreCase(role)) {
                    return false;
                }
            } else {
                if ("ADMIN".equalsIgnoreCase(role)) {
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur BD: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignore) {
            }
            DbConnexion.closeConnexion();
        }
    }
}