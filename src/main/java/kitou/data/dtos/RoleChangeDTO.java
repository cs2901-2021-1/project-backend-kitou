package kitou.data.dtos;

public class RoleChangeDTO {
    String adminEmail;
    String userEmail;

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "roleChangeDTO{" +
                "adminEmail='" + adminEmail + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
