package skillmatch.ui;

import skillmatch.dao.InternshipDAO;
import skillmatch.exception.DatabaseException;
import javax.swing.*;
import java.awt.*;

/** Lets a company post an internship (it stays hidden until an admin verifies it). */
public class CompanyDashboard extends JFrame {
    private final JTextField title = new JTextField(24);
    private final JTextField minCgpa = new JTextField("0", 6);
    private final JTextArea description = new JTextArea(5, 24);
    private final InternshipDAO dao = new InternshipDAO();
    private final int companyId;

    public CompanyDashboard(int companyId) {
        super("SkillMatch - Company Dashboard");
        this.companyId = companyId;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel p = new JPanel(new GridLayout(0, 1, 6, 6));
        p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        p.add(new JLabel("Title"));        p.add(title);
        p.add(new JLabel("Minimum CGPA")); p.add(minCgpa);
        p.add(new JLabel("Description"));  p.add(new JScrollPane(description));
        JButton post = new JButton("Post internship");
        post.addActionListener(e -> post());
        p.add(post);
        setContentPane(p);
        pack();
        setLocationRelativeTo(null);
    }

    private void post() {
        try {
            dao.insert(companyId, title.getText().trim(), description.getText(),
                       Double.parseDouble(minCgpa.getText()));
            JOptionPane.showMessageDialog(this, "Posted. Waiting for admin verification.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "CGPA must be a number.");
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
