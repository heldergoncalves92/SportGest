package studentcompany.sportgest.domains;

public class EventCategory extends DomainPojo {
    private long id=0;
    private int color=1;
    private boolean hastimestamp = false;
    private String name;

    public EventCategory(long id, String name, int color, boolean hastimestamp) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.hastimestamp = hastimestamp;
    }

    public EventCategory(String name, int color,boolean hastimestamp) {
        this.name = name; this.color = color; this.hastimestamp = hastimestamp;
    }

    @Override
    public long getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasTimestamp() {
        return hastimestamp;
    }

    public void setHastimestamp(boolean hastimestamp) {
        this.hastimestamp = hastimestamp;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventCategory that = (EventCategory) o;

        if (id != that.id) return false;
        if (color != that.color) return false;
        if (hastimestamp != that.hastimestamp) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (color ^ (color >>> 32));
        result = 31 * result + (hastimestamp ? 1 : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
