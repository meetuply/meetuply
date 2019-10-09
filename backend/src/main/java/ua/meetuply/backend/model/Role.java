package ua.meetuply.backend.model;

public class Role {
    private String roleName;
    private Integer roleId;

    public Role(String roleName, Integer roleId) {
        this.roleName = roleName;
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}