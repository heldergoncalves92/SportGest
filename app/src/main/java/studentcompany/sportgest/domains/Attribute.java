package studentcompany.sportgest.domains;

public class Attribute extends DomainPojo {
    private int id;
    private String type;
    private String name;
    private int deleted;

    public Attribute(int id, String type, String name, int deleted) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.deleted = deleted;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (id != attribute.id) return false;
        if (deleted != attribute.deleted) return false;
        if (type != null ? !type.equals(attribute.type) : attribute.type != null) return false;
        return !(name != null ? !name.equals(attribute.name) : attribute.name != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + deleted;
        return result;
    }
}
