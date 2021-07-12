package kitou.data.entities;

import kitou.config.ConstConfig;

import javax.persistence.*;

@Entity
@Table(name = "user_entity")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "role", columnDefinition = "integer default 1")
    private Integer role;

    public User(){
        this.role = 1;
    }

    public User(String email){
        this.email = email;
        this.role = 1;
        if(this.email.equals(ConstConfig.ADMIN_EMAIL))
            role = 2;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    /** 0 = Privilegios removidos, 1 = Privelegio de accesos, 2 = Privilegios de administrador */
    public Integer getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
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