package skillmatch.model;

public class Administrator extends User {
    public Administrator(int id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public String getRole() { return "ADMIN"; }
}
