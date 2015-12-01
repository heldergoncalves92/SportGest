package studentcompany.sportgest.domains;
//TODO all

public class Role extends DomainPojo {

    private int id;
    private String name;


    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public int getId() { return this.id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (id != role.id) return false;
        return !(name != null ? !name.equals(role.name) : role.name != null);

    }

    @Override
    public String toString() {
        return name;
    }
}
