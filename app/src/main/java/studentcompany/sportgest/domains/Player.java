package studentcompany.sportgest.domains;
//TODO all

public class Player extends DomainPojo{

    private int id = 0;
    private String nickname;
    private String name;
    private String nationality;
    private String marital_status;
    private int birthDate;
    private int height;
    private float weight;
    private String address;
    private String gender;
    private String photo;
    private String email;
    private String preferedFoot;
    private int number;
    private Team team;
    private Position position;

    public Player(int id,String nickname, String name, String nationality, String marital_status, int birthDate, int height, float weight, String address, String gender, String photo, String email, String preferedFoot, int number, Team team, Position position) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.nationality = nationality;
        this.marital_status = marital_status;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.address = address;
        this.gender = gender;
        this.photo = photo;
        this.email = email;
        this.preferedFoot = preferedFoot;
        this.number = number;
        this.team = team;
        this.position=position;
    }
    public Player(String nickname, String name, String nationality, String marital_status, int birthDate, int height, float weight, String address, String gender, String photo, String email, String preferedFoot, int number, Team team, Position position) {
        this.nickname = nickname;
        this.name = name;
        this.nationality = nationality;
        this.marital_status = marital_status;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.address = address;
        this.gender = gender;
        this.photo = photo;
        this.email = email;
        this.preferedFoot = preferedFoot;
        this.number = number;
        this.team = team;
        this.position=position;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferredFoot() {
        return preferedFoot;
    }

    public void setPreferredFoot(String preferedFoot) {
        this.preferedFoot = preferedFoot;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (getId() != player.getId()) return false;
        if (getBirthDate() != player.getBirthDate()) return false;
        if (Float.compare(player.getHeight(), getHeight()) != 0) return false;
        if (Float.compare(player.getWeight(), getWeight()) != 0) return false;
        if (getNumber() != player.getNumber()) return false;
        if (!getNickname().equals(player.getNickname())) return false;
        if (!getName().equals(player.getName())) return false;
        if (!getNationality().equals(player.getNationality())) return false;
        if (!getMarital_status().equals(player.getMarital_status())) return false;
        if (!getAddress().equals(player.getAddress())) return false;
        if (!getGender().equals(player.getGender())) return false;
        if (!getPhoto().equals(player.getPhoto())) return false;
        if (!getEmail().equals(player.getEmail())) return false;
        if (!getPreferredFoot().equals(player.getPreferredFoot())) return false;
        return getTeam().equals(player.getTeam());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getNickname().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getNationality().hashCode();
        result = 31 * result + getMarital_status().hashCode();
        result = 31 * result + getBirthDate();
        result = 31 * result + (getHeight() != +0.0f ? Float.floatToIntBits(getHeight()) : 0);
        result = 31 * result + (getWeight() != +0.0f ? Float.floatToIntBits(getWeight()) : 0);
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getGender().hashCode();
        result = 31 * result + getPhoto().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPreferredFoot().hashCode();
        result = 31 * result + getNumber();
        result = 31 * result + getTeam().hashCode();
        return result;
    }
}
