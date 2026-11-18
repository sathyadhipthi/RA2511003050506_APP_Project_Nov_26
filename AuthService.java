package skillmatch.service;

import skillmatch.dao.StudentDAO;
import skillmatch.exception.DatabaseException;
import skillmatch.exception.ValidationException;
import skillmatch.util.PasswordUtil;
import java.util.Optional;
import java.util.regex.Pattern;

public class AuthService {
    private static final Pattern EMAIL =
            Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private final StudentDAO studentDao = new StudentDAO();

    public int registerStudent(String name, String email, String password,
                               String degree, double cgpa)
            throws ValidationException, DatabaseException {
        if (name == null || name.isBlank())
            throw new ValidationException("name", "Name is required.");
        if (email == null || !EMAIL.matcher(email).matches())
            throw new ValidationException("email", "Enter a valid e-mail address.");
        if (password == null || password.length() < 8)
            throw new ValidationException("password", "Use at least 8 characters.");
        if (cgpa < 0 || cgpa > 10)
            throw new ValidationException("cgpa", "CGPA must be between 0 and 10.");

        String salt = PasswordUtil.newSalt();
        return studentDao.insert(name.trim(), email.trim().toLowerCase(),
                PasswordUtil.hash(password, salt), salt, degree, cgpa);
    }

    /** Returns the student id when the credentials are correct. */
    public Optional<Integer> loginStudent(String email, String password)
            throws DatabaseException {
        Optional<String[]> cred = studentDao.findCredentials(email.trim().toLowerCase());
        if (cred.isEmpty()) return Optional.empty();
        String[] c = cred.get();
        return PasswordUtil.verify(password, c[2], c[1])
                ? Optional.of(Integer.parseInt(c[0])) : Optional.empty();
    }
}
