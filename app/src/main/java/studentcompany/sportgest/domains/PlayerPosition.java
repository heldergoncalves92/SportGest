package studentcompany.sportgest.domains;


public class PlayerPosition extends DomainPojo{

    private int id;
    private Player player;
    private Position position;
    private int value;

    public PlayerPosition(int id, Player player, Position position, int value) {
        this.id = id;
        this.player = player;
        this.position = position;
        this.value = value;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        int result = getId();
        result = 31 * result + getPlayer().hashCode();
        result = 31 * result + getPosition().hashCode();
        result = 31 * result + getValue();
        return result;
    }
}
