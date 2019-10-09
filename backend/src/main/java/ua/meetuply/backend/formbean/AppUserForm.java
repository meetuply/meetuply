package ua.meetuply.backend.formbean;

public class AppUserForm {
	 
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private boolean deactivated;
    private String password;
    private String confirmPassword;
 
    public AppUserForm() {
 
    }
 
    public AppUserForm(Long userId, String email,
            String firstName, String lastName, boolean deactivated,
            String password, String confirmPassword) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deactivated = deactivated;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
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
}

