package studentcompany.sportgest.domains;
//TODO all

public class Game extends DomainPojo {

    private int id;
    private Team home_teamid;
    private Team visitor_teamid;
    private int date;
    private String report;
    private int home_score;
    private int visitor_score;
    private float duration;


    public Game(int id,Team home_teamid, Team visitor_teamid, int date, String report, Integer home_score, Integer visitor_score, float duration) {
        this.id=id;
        this.home_teamid = home_teamid;
        this.visitor_teamid = visitor_teamid;
        this.date = date;
        this.report=report;
        this.home_score = home_score;
        this.visitor_score = visitor_score;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getIdTeamHome() {
        return home_teamid;
    }

    public void setIdTeamHome(Team idHomeTeam) {
        this.home_teamid = idHomeTeam;
    }

    public Team getIdTeamVisitor() {
        return visitor_teamid;
    }

    public void setIdTeamVisitor(Team idVisitorTeam ) {
        this.visitor_teamid = idVisitorTeam;
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

    public void setDuration(int duration) {
        this.duration = duration;
    }



    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", Home team =" + home_teamid +
                ", Vistor team =" + visitor_teamid +
                ", date=" + date +
                ", report =" + report +
                ", Home Score=" + home_score +
                ", Visitor Score=" + visitor_score +
                ", Duration=" + duration +
                '}';
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
        if (!home_teamid.equals(game.home_teamid)) return false;
        if (!visitor_teamid.equals(game.visitor_teamid)) return false;
        return getReport().equals(game.getReport());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + home_teamid.hashCode();
        result = 31 * result + visitor_teamid.hashCode();
        result = 31 * result + getDate();
        result = 31 * result + getReport().hashCode();
        result = 31 * result + getHome_score();
        result = 31 * result + getVisitor_score();
        result = 31 * result + (getDuration() != +0.0f ? Float.floatToIntBits(getDuration()) : 0);
        return result;
    }
}

