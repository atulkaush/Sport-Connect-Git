
package sport.model;

public class User {

    private String name;
    private String username;
    private String password;
    private int age;
    private String role;

    public User(String name, String username,
                String password, int age, String role) {

        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getRole() {
        return role;
    }
}
