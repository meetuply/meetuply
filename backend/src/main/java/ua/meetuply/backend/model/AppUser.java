package ua.meetuply.backend.model;

import java.util.Objects;

public class AppUser {
	 
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean deactivated;
    private String encrytedPassword;

    public AppUser() { }

//    public AppUser(String email, String firstName, String lastName,
//                   Role role, boolean deactivated, String encrytedPassword) {
//        this.email = email;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.role = role;
//        this.deactivated = deactivated;
//        this.encrytedPassword = encrytedPassword;
//    }
    public AppUser(Integer userId, String email, String firstName, String lastName,
                   Role role, boolean deactivated, String encrytedPassword) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.deactivated = deactivated;
        this.encrytedPassword = encrytedPassword;
    }


        public Integer getUserId () {
            return userId;
        }

        public void setUserId (Integer userId){
            this.userId = userId;
        }

        public String getEmail () {
            return email;
        }

        public void setEmail (String email){
            this.email = email;
        }

        public String getFirstName () {
            return firstName;
        }

        public void setFirstName (String firstName){
            this.firstName = firstName;
        }

        public String getLastName () {
            return lastName;
        }

        public void setLastName (String lastName){
            this.lastName = lastName;
        }

        public Role getRole () {
            return role;
        }

        public void setRole (Role role){
            this.role = role;
        }

        public boolean isDeactivated () {
            return deactivated;
        }

        public void setDeactivated ( boolean deactivated){
            this.deactivated = deactivated;
        }

        public String getEncrytedPassword () {
            return encrytedPassword;
        }

        public void setEncrytedPassword (String encrytedPassword){
            this.encrytedPassword = encrytedPassword;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return getUserId().equals(appUser.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}