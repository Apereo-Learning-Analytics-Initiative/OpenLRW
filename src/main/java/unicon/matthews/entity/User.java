package unicon.matthews.entity;

import java.util.List;

public class User {
    @org.springframework.data.annotation.Id
    private Long id;
    
    private String username;
    
    private String password;
    
    private List<Role> roles;
    
    public User() { }
    
    public User(Long id, String username, String password, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
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

    public List<Role> getRoles() {
        return roles;
    }
}
