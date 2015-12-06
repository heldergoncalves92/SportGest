package studentcompany.sportgest.domains;
//TODO all

public class Permission extends DomainPojo {

    // VALUES NOT FINAL
    public static final int READ_USERS = 1;
    public static final int MANAGE_USERS = 2;
    public static final int READ_GAMES = 3;
    public static final int MANAGE_GAMES = 4;

    private long id;
    private String description;
    @Override
    public int getId() {
        return (int)id;
    }

    // Constructors
    public Permission(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Permission(String description) {
        this.description = description;
    }

    // Getters and Setters
    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission permission = (Permission) o;

        if (id != permission.id) return false;
        return !(description != null ? !description.equals(permission.description) : permission.description != null);

    }

    @Override
    public int hashCode() {
        int result = (int)id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return description;
    }
}
