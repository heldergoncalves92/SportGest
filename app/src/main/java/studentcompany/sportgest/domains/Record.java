package studentcompany.sportgest.domains;

public class Record extends DomainPojo {
    private int id;
    private int date;
    private float value;
    private int state;
    private Training training;
    private Exercise exercise;
    private Attribute attribute;
    private Player player;
    private User user;

    public Record(int id, int date, float value, int state, Training training, Exercise exercise, Attribute attribute, Player player, User user) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.state = state;
        this.training = training;
        this.exercise = exercise;
        this.attribute = attribute;
        this.player = player;
        this.user = user;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getDate() {
        return date;
    }

    public float getValue() {
        return value;
    }

    public int getState() {
        return state;
    }

    public Training getTraining() {
        return training;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public Player getPlayer() {
        return player;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", date=" + date +
                ", value=" + value +
                ", state=" + state +
                ", training=" + training +
                ", exercise=" + exercise +
                ", attribute=" + attribute +
                ", player=" + player +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (id != record.id) return false;
        if (date != record.date) return false;
        if (Float.compare(record.value, value) != 0) return false;
        if (state != record.state) return false;
        if (training != null ? !training.equals(record.training) : record.training != null)
            return false;
        if (exercise != null ? !exercise.equals(record.exercise) : record.exercise != null)
            return false;
        if (attribute != null ? !attribute.equals(record.attribute) : record.attribute != null)
            return false;
        if (player != null ? !player.equals(record.player) : record.player != null) return false;
        return !(user != null ? !user.equals(record.user) : record.user != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + date;
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
        result = 31 * result + state;
        result = 31 * result + (training != null ? training.hashCode() : 0);
        result = 31 * result + (exercise != null ? exercise.hashCode() : 0);
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
