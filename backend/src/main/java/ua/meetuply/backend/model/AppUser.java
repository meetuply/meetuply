package ua.meetuply.backend.model;

public class AppUser {
    
	 
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private boolean deactivated;
    private String encrytedPassword;
 
    public AppUser() {
 
    }
 
    public AppUser(Long userId, String email, String firstName, String lastName,
            String role, boolean deactivated, String encrytedPassword) {
        super();
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.deactivated = deactivated;
        this.encrytedPassword = encrytedPassword;
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
    
    public String getRole() {
        return role;
    }
 
    public void setRole(String role) {
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

