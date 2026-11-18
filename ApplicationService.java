package skillmatch.service;

import skillmatch.dao.ApplicationDAO;
import skillmatch.exception.DatabaseException;
import skillmatch.exception.ValidationException;
import skillmatch.model.Application;
import skillmatch.model.Internship;
import skillmatch.model.Student;
import java.util.List;
import java.util.OptionalDouble;

public class ApplicationService {
    private final ApplicationDAO dao = new ApplicationDAO();
    private final MatchingService matcher = new MatchingService();

    public void apply(Student student, Internship internship)
            throws ValidationException, DatabaseException {
        if (dao.exists(student.getId(), internship.getId()))
            throw new ValidationException("internship", "You have already applied.");
        OptionalDouble score = matcher.calculateMatch(student.getSkills(),
                                                      internship.getRequiredSkills());
        dao.insert(student.getId(), internship.getId(), score.orElse(0));
    }

    public List<Application> myApplications(int studentId) throws DatabaseException {
        return dao.findByStudent(studentId);
    }
}
