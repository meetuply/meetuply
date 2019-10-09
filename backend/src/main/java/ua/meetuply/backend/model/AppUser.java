package ua.meetuply.backend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.meetuply.backend.dao.RoleDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUser {
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean deactivated;
    private String encrytedPassword;

    public static final UserMapper userMapper = new UserMapper();

    public AppUser() { }
 
    public AppUser(Integer userId, String email, String firstName, String lastName,
            Role role, boolean deactivated, String encrytedPassword) {
        super();
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.deactivated = deactivated;
        this.encrytedPassword = encrytedPassword;
    }
 
    public Integer getUserId() {
        return userId;
    }
 
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Role getRole() {
        return role;
    }
 
    public void setRole(Role role) {
        this.role = role;
    }
 
    public boolean isDeactivated() {
        return deactivated;
    }
 
    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }
 
    public String getEncrytedPassword() {
        return encrytedPassword;
    }
 
    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }
}

class UserMapper implements RowMapper<AppUser> {
    @Override
    public AppUser mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AppUser(
                    resultSet.getInt("uid"),
                    resultSet.getString("email"),
                    resultSet.getString("firstname"),
                    resultSet.getString("surname"),
                    RoleDAO.instance.getRoleById(resultSet.getInt("role_id")),
                    resultSet.getBoolean("is_deactivated"),
                    resultSet.getString("password")
                );
    }
}

