package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Role;

import java.util.List;

public class RoleDAO {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    public static final RoleDAO instance = new RoleDAO();

    public Role getRoleById(Integer id) {
        List<Role> role = jdbcTemplate.query("SELECT * FROM role WHERE uid = ?", new Object[] { id }, Role.roleMapper);
        return role.size() == 0 ? null : role.get(0);
    }

    public Role getRoleByName(String name) {
        List<Role> role = jdbcTemplate.query("SELECT * FROM role WHERE name = ?", new Object[] { name }, Role.roleMapper);
        return role.size() == 0 ? null : role.get(0);
    }
}
