package studentcompany.sportgest.domains;
//TODO all

public class User extends DomainPojo {

    private long id=-1;
    private String username;
    private String password;
    private String photo;
    private String name;
    private String email;
    private Role role;
    private Team team;


    public User(String username) {
        this.username = username;
    }

    public User(long id, String username, String password, String photo, String name, String email, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User(String username, String password, String photo, String name, String email, Role role) {
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.role = role;
    }
    public User(long id, String username, String password, String photo, String name, String email, Role role, Team team) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.role = role;
        this.team = team;
    }

    public User(String username, String password, String photo, String name, String email, Role role, Team team) {
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.role = role;
        this.team = team;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public void setId(long id) {this.id = id;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() { return this.role;}

    public void setRole(Role role) {
        this.role = role;
    }

    public Team getTeam() {return team;}

    public void setTeam(Team team) {this.team = team;}

    // Extra Methods
    public boolean hasPermission(Permission permission){
        return this.role.hasPermission(permission);
    }

    public boolean hasPermission(int permission_id){
        return this.role.hasPermission(permission_id);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        if (photo != null ? !photo.equals(user.photo) : user.photo != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return !(role != null ? !role.equals(user.role) : user.role != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return username;
    }
}
