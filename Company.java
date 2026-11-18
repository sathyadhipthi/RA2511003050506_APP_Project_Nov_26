package skillmatch.model;

public class Company extends User {
    private boolean verified;

    public Company(int id, String name, String email, boolean verified) {
        super(id, name, email);
        this.verified = verified;
    }

    public boolean isVerified() { return verified; }

    @Override
    public String getRole() { return "COMPANY"; }
}
