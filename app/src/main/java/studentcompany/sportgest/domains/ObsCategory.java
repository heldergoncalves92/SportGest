package studentcompany.sportgest.domains;
//TODO all

public class ObsCategory extends DomainPojo {
        private int id;
        private String category;

    public ObsCategory(int id, String category) {
        this.id = id;
        this.category = category;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ObsCategory{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObsCategory)) return false;

        ObsCategory that = (ObsCategory) o;

        if (getId() != that.getId()) return false;
        return !(getCategory() != null ? !getCategory().equals(that.getCategory()) : that.getCategory() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        return result;
    }
}
