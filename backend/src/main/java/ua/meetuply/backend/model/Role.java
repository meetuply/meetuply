package ua.meetuply.backend.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Role {

    public static Set<Role> roles = new TreeSet<>();
    public static final RoleMapper roleMapper = new RoleMapper();

    private String roleName;
    private Integer roleId;

    public Role(String roleName, Integer roleId) {
        this.roleName = roleName;
        this.roleId = roleId;
        roles.add(this);
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }
}

class RoleMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Role(
                resultSet.getString("name"),
                resultSet.getInt("uid")
        );
    }
}