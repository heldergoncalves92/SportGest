package studentcompany.sportgest.domains;

import java.util.Comparator;

public class Attribute extends DomainPojo implements Comparator<Attribute>{
    private long id;
    private String type;
    private String name;
    private int deleted;

    public static final String RATIO = "Ratio";
    public static final String QUANTITATIVE = "Quantitative";
    public static final String QUALITATIVE = "Qualitative";

    public enum QUALITATIVE_TYPE {
        Poor, Fair, Average, Good, Excellent
    }

    public Attribute(long id, String type, String name, int deleted) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.deleted = deleted;
    }

    @Override
    public long getId() {
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

    public void setId(long id) {
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
    public int compare(Attribute lhs, Attribute rhs) {
        //Sorting
        return lhs.getName().compareTo(rhs.getName());
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
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + deleted;
        return result;
    }
}
