
package sport.model;

public class AthleteProfile {

    private String username;
    private String sport;
    private String position;
    private String achievements;
    private String location;

    public AthleteProfile(String username, String sport,
                          String position, String achievements,
                          String location) {

        this.username = username;
        this.sport = sport;
        this.position = position;
        this.achievements = achievements;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public String getSport() {
        return sport;
    }

    public String getPosition() {
        return position;
    }

    public String getAchievements() {
        return achievements;
    }

    public String getLocation() {
        return location;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
