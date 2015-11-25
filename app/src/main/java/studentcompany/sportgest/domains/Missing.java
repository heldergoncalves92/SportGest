package studentcompany.sportgest.domains;

public class Missing extends DomainPojo {
    private int id;
    private Player player;
    private Training training;
    private String description;

    public Missing(int id, Player player, Training training, String description) {
        this.id = id;
        this.player = player;
        this.training = training;
        this.description = description;
    }

    @Override
    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Training getTraining() {
        return training;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Missing{" +
                "id=" + id +
                ", player=" + player +
                ", training=" + training +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Missing missing = (Missing) o;

        if (id != missing.id) return false;
        if (player != null ? !player.equals(missing.player) : missing.player != null) return false;
        if (training != null ? !training.equals(missing.training) : missing.training != null)
            return false;
        return !(description != null ? !description.equals(missing.description) : missing.description != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + (training != null ? training.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
