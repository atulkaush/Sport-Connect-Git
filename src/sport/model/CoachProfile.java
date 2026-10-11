
package sport.model;

public class CoachProfile {

    private String username;
    private String specialization;
    private int experience;
    private String location;
    private String bio;

    public CoachProfile(String username, String specialization,
                        int experience, String location,
                        String bio) {

        this.username = username;
        this.specialization = specialization;
        this.experience = experience;
        this.location = location;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getExperience() {
        return experience;
    }

    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
