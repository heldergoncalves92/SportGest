package studentcompany.sportgest.domains;

public class Training extends DomainPojo {
    private int id;
    private String title;
    private String description;
    private int date;
    private int totalDuration;
    private Team team;

    public Training(int id, String title, String description, int date, int totalDuration, Team team) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.totalDuration = totalDuration;
        this.team = team;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDate() {
        return date;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public Team getTeam() {
        return team;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", totalDuration=" + totalDuration +
                ", team=" + team +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Training training = (Training) o;

        if (id != training.id) return false;
        if (date != training.date) return false;
        if (totalDuration != training.totalDuration) return false;
        if (title != null ? !title.equals(training.title) : training.title != null) return false;
        if (description != null ? !description.equals(training.description) : training.description != null)
            return false;
        return !(team != null ? !team.equals(training.team) : training.team != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + date;
        result = 31 * result + totalDuration;
        result = 31 * result + (team != null ? team.hashCode() : 0);
        return result;
    }
}
