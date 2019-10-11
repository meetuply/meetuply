package ua.meetuply.backend.formbean;

import ua.meetuply.backend.dao.RoleDAO;
import ua.meetuply.backend.model.Role;

import java.util.Objects;

public class AppUserForm {

    RoleDAO roleDAO;

    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    Role role;
    private boolean deactivated;
    private String password;
    private String confirmPassword;

    public AppUserForm() {
    }

    public AppUserForm(Integer userId, String email,
                       String firstName, String lastName,boolean deactivated,
                       String password, String confirmPassword) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deactivated = deactivated;
        this.role = roleDAO.getRoleByName("user");
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public boolean isDeactivated() {
        return deactivated;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUserForm)) return false;
        AppUserForm that = (AppUserForm) o;
        return getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}