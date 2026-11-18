package skillmatch.model;

/** Abstract base class for every kind of account (inheritance + polymorphism). */
public abstract class User {
    private int id;
    private String name;
    private String email;

    protected User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId()       { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }

    /** Each subclass reports its own role. */
    public abstract String getRole();

    @Override
    public String toString() { return getRole() + ": " + name; }
}
