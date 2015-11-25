package studentcompany.sportgest.domains;
//TODO all

public class Game extends DomainPojo {

    private int id;
    private Team home_team;
    private Team visitor_team;
    private int date;
    private String report;
    private int home_score;
    private int visitor_score;
    private float duration;


    public Game(int id, Team home_team, Team visitor_team, int date, String report, Integer home_score, Integer visitor_score, float duration) {
        this.id = id;
        this.home_team = home_team;
        this.visitor_team = visitor_team;
        this.date = date;
        this.report = report;
        this.home_score = home_score;
        this.visitor_score = visitor_score;
        this.duration = duration;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getHome_team() {
        return home_team;
    }

    public void setHome_team(Team home_team) {
        this.home_team = home_team;
    }

    public Team getVisitor_team() {
        return visitor_team;
    }

    public void setVisitor_team(Team visitor_team) {
        this.visitor_team = visitor_team;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public int getHome_score() {
        return home_score;
    }

    public void setHome_score(int home_score) {
        this.home_score = home_score;
    }

    public int getVisitor_score() {
        return visitor_score;
    }

    public void setVisitor_score(int visitor_score) {
        this.visitor_score = visitor_score;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        if (getId() != game.getId()) return false;
        if (getDate() != game.getDate()) return false;
        if (getHome_score() != game.getHome_score()) return false;
        if (getVisitor_score() != game.getVisitor_score()) return false;
        if (Float.compare(game.getDuration(), getDuration()) != 0) return false;
        if (!getHome_team().equals(game.getHome_team())) return false;
        if (!getVisitor_team().equals(game.getVisitor_team())) return false;
        return getReport().equals(game.getReport());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getHome_team().hashCode();
        result = 31 * result + getVisitor_team().hashCode();
        result = 31 * result + getDate();
        result = 31 * result + getReport().hashCode();
        result = 31 * result + getHome_score();
        result = 31 * result + getVisitor_score();
        result = 31 * result + (getDuration() != +0.0f ? Float.floatToIntBits(getDuration()) : 0);
        return result;
    }
}

