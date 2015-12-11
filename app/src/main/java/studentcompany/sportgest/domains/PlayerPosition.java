package studentcompany.sportgest.domains;


public class PlayerPosition extends DomainPojo{

    private long id = -1;
    private Player player = null;
    private Position position = null;
    private int value;

    public PlayerPosition(long id, Player player, Position position, int value) {
        this.id = id;
        this.player = player;
        this.position = position;
        this.value = value;
    }
    public PlayerPosition(Player player, Position position, int value) {
        this.player = player;
        this.position = position;
        this.value = value;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerPosition)) return false;

        PlayerPosition that = (PlayerPosition) o;

        if (getId() != that.getId()) return false;
        if (getValue() != that.getValue()) return false;
        if (!getPlayer().equals(that.getPlayer())) return false;
        return getPosition().equals(that.getPosition());

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + value;
        return result;
    }
}
