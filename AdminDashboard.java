package skillmatch.ui;

import skillmatch.dao.CompanyDAO;
import skillmatch.dao.InternshipDAO;
import skillmatch.exception.DatabaseException;
import javax.swing.*;
import java.awt.*;

/** Administrator verifies companies and internship postings. */
public class AdminDashboard extends JFrame {
    private final CompanyDAO companyDao = new CompanyDAO();
    private final InternshipDAO internshipDao = new InternshipDAO();
    private final JTextField idField = new JTextField(8);

    public AdminDashboard() {
        super("SkillMatch - Administrator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel p = new JPanel(new FlowLayout());
        p.add(new JLabel("ID:"));
        p.add(idField);
        JButton vc = new JButton("Verify company");
        JButton vi = new JButton("Verify internship");
        vc.addActionListener(e -> run(true));
        vi.addActionListener(e -> run(false));
        p.add(vc);
        p.add(vi);
        setContentPane(p);
        pack();
        setLocationRelativeTo(null);
    }

    private void run(boolean company) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            if (company) companyDao.verify(id); else internshipDao.verify(id);
            JOptionPane.showMessageDialog(this, "Verified.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a numeric ID.");
        } catch (DatabaseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
