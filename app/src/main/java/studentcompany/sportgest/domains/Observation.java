package studentcompany.sportgest.domains;
//TODO all

public class Observation extends DomainPojo {
        private long id = -1;
        private String title;
        private String description;
        private int date;
        private ObsCategory observationcategory = null;
        private Player player = null;
        private User user = null;
        private Game game = null;


        public Observation (long id,String title, String description,int date,ObsCategory observationcategory,Player player,User user,Game game) {
            this.id = id;
            this.title=title;
            this.description = description;
            this.date = date;
            this.observationcategory = observationcategory;
            this.player = player;
            this.user=user;
            this.game = game;

        }

    public Observation (String title, String description,int date,ObsCategory observationcategory,Player player,User user,Game game) {
        this.title=title;
        this.description = description;
        this.date = date;
        this.observationcategory = observationcategory;
        this.player = player;
        this.user=user;
        this.game = game;

    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public ObsCategory getObservationcategory() {
        return observationcategory;
    }

    public void setObservationcategory(ObsCategory observationcategory) {
        this.observationcategory = observationcategory;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Observation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", observationcategory=" + observationcategory +
                ", player=" + player +
                ", user=" + user +
                ", game=" + game +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Observation)) return false;

        Observation that = (Observation) o;

        if (getId() != that.getId()) return false;
        if (getDate() != that.getDate()) return false;
        if (!getTitle().equals(that.getTitle())) return false;
        if (!getDescription().equals(that.getDescription())) return false;
        if (!getObservationcategory().equals(that.getObservationcategory())) return false;
        if (!getPlayer().equals(that.getPlayer())) return false;
        if (!getUser().equals(that.getUser())) return false;
        return getGame().equals(that.getGame());

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + date;
        result = 31 * result + (observationcategory != null ? observationcategory.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (game != null ? game.hashCode() : 0);
        return result;
    }
}
