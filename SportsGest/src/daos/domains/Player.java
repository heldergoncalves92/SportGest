/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.domains;

import java.util.Objects;

/**
 *
 * @author duarteduarte
 */
public class Player extends DomainPojo {

    private String nickname;
    private String name;
    private String nationality;
    private String marital_status;
    private String birthDate;
    private int height;
    private float weight;
    private String address;
    private String gender;
    private String photo;
    private String email;
    private String preferedFoot;
    private int number;
    private Team team;

    public Player(String nickname, String name, String nationality, String marital_status, String birthDate, int height, float weight, String address, String gender, String photo, String email, String preferedFoot, int number, Team team) {
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
    }

    public Player() {
        this.nickname = "";
        this.name = "";
        this.nationality = "";
        this.marital_status = "";
        this.birthDate = "";
        this.height = 0;
        this.weight = 0;
        this.address = "";
        this.gender = "";
        this.photo = "";
        this.email = "";
        this.preferedFoot = "";
        this.number = -1;
        this.team = null;
    }

    public int getId() {
        return this.nickname;
    }

    public void setIdPlayer(String idPlayer) {
        this.nickname = idPlayer;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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

    public void setWeight(float weight) {
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

    public String getPreferedFoot() {
        return preferedFoot;
    }

    public void setPreferedFoot(String preferedFoot) {
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

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.nickname, other.nickname)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nickname + " - " + this.nationality;
    }
}
