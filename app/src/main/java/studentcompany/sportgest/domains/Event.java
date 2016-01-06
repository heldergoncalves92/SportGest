package studentcompany.sportgest.domains;

public class Event extends DomainPojo{
    private long id;
    private String description;
    private long date;
    private int posx;
    private int posy;
    private EventCategory eventCategory;
    private Game game;
    private Player player;

    public Event(long id, String description, long date, int posx, int posy, EventCategory eventCategory, Game game, Player player) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.posx = posx;
        this.posy = posy;
        this.eventCategory = eventCategory;
        this.game = game;
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public long getDate() {
        return date;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", posx=" + posx +
                ", posy=" + posy +
                ", eventCategory=" + eventCategory +
                ", game=" + game +
                ", player=" + player +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != event.id) return false;
        if (date != event.date) return false;
        if (Float.compare(event.posx, posx) != 0) return false;
        if (Float.compare(event.posy, posy) != 0) return false;
        if (description != null ? !description.equals(event.description) : event.description != null)
            return false;
        if (eventCategory != null ? !eventCategory.equals(event.eventCategory) : event.eventCategory != null)
            return false;
        if (game != null ? !game.equals(event.game) : event.game != null) return false;
        return !(player != null ? !player.equals(event.player) : event.player != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + posx;
        result = 31 * result + posy;
        result = 31 * result + (eventCategory != null ? eventCategory.hashCode() : 0);
        result = 31 * result + (game != null ? game.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}
