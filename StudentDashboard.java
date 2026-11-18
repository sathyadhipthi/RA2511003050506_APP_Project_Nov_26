package skillmatch.ui;

import skillmatch.dao.InternshipDAO;
import skillmatch.exception.DatabaseException;
import skillmatch.exception.ValidationException;
import skillmatch.model.Student;
import skillmatch.service.ApplicationService;
import skillmatch.service.MatchingService;
import skillmatch.service.MatchingService.MatchResult;
import skillmatch.service.ProfileService;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StudentDashboard extends JFrame {
    private final MatchingService matchingService = new MatchingService();
    private final ApplicationService applicationService = new ApplicationService();
    private final InternshipDAO internshipDao = new InternshipDAO();
    private final RecommendationTableModel tableModel = new RecommendationTableModel();
    private final JTable table = new JTable(tableModel);
    private final JLabel progressLabel = new JLabel("Loading matches...");
    private Student currentStudent;

    public StudentDashboard(int studentId) {
        super("SkillMatch - Student Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 480);
        setLocationRelativeTo(null);

        JButton apply = new JButton("Apply to selected");
        apply.addActionListener(e -> applySelected());
        JPanel south = new JPanel(new BorderLayout());
        south.add(progressLabel, BorderLayout.WEST);
        south.add(apply, BorderLayout.EAST);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        try {
            currentStudent = new ProfileService().load(studentId).orElseThrow();
            setTitle("Hi " + currentStudent.getName() + " - Top matches");
            loadMatches();
        } catch (DatabaseException ex) {
            showError("Could not load your profile.");
        }
    }

    /** Matching runs off the event-dispatch thread so the UI stays responsive. */
    private void loadMatches() {
        progressLabel.setVisible(true);
        new SwingWorker<List<MatchResult>, Void>() {
            @Override
            protected List<MatchResult> doInBackground() throws Exception {
                return matchingService.rank(currentStudent, internshipDao.findActive());
            }

            @Override
            protected void done() {
                try {
                    tableModel.setResults(get());
                } catch (InterruptedException | ExecutionException ex) {
                    showError("Could not load recommendations. Please try again.");
                } finally {
                    progressLabel.setVisible(false);
                }
            }
        }.execute();
    }

    private void applySelected() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Select an internship first."); return; }
        try {
            applicationService.apply(currentStudent,
                    tableModel.getRow(row).internship());
            JOptionPane.showMessageDialog(this, "Application submitted.");
        } catch (ValidationException ex) {
            showError(ex.getMessage());
        } catch (DatabaseException ex) {
            showError("Could not submit the application.");
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "SkillMatch", JOptionPane.ERROR_MESSAGE);
    }
}
