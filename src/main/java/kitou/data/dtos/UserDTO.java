package kitou.data.dtos;

public class UserDTO{

    String email;
    Integer role;

    public String getEmail() {
        return email;
    }

    public Integer getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}