package kitou.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_entity")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", columnDefinition = "integer default 1")
    private Integer role;

    public User(){
        this.role = 1;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.role = 1;
        if(this.username.equals("admin"))
            role = 2;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /** 0 = Privilegios removidos, 1 = Privelegio de accesos, 2 = Privilegios de administrador */
    public Integer getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void promote(){
        if(this.role < 2)
            this.role++;
    }

    public void demote(){
        if(this.role > 0)
            this.role--;
    }
}