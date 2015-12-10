package studentcompany.sportgest.domains;

public class Team extends DomainPojo {
    private long id;
    private String name;
    private String description;
    private String logo;
    private int season;
    private int is_com;

    public Team(long id, String name, String description, String logo, int season, int is_com) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.season = season;
        this.is_com = is_com;
    }

    public Team(String name, String description, String logo, int season, int is_com) {
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.season = season;
        this.is_com = is_com;
    }

    public Team(int id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLogo() {
        return logo;
    }

    public int getSeason() {
        return season;
    }

    public int getIs_com() {
        return is_com;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setIs_com(int is_com) {
        this.is_com = is_com;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (id != team.id) return false;
        if (season != team.season) return false;
        if (is_com != team.is_com) return false;
        if (name != null ? !name.equals(team.name) : team.name != null) return false;
        if (description != null ? !description.equals(team.description) : team.description != null)
            return false;
        return !(logo != null ? !logo.equals(team.logo) : team.logo != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + season;
        result = 31 * result + is_com;
        return result;
    }
}
