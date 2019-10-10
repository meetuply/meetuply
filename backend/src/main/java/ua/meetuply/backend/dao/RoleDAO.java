package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RoleDAO implements RowMapper<Role> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public Role getRoleById(Integer id) {
        List<Role> role = jdbcTemplate.query("SELECT * FROM role WHERE uid = ?", new Object[] { id }, this);
        return role.size() == 0 ? null : role.get(0);
    }

    public Role getRoleByName(String name) {
        System.out.println(jdbcTemplate == null? "NULL":"NO");
        List<Role> role = jdbcTemplate.query("SELECT * FROM role WHERE name = ?", new Object[] { name }, this);
        return role.size() == 0 ? null : role.get(0);
    }

    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Role(
                resultSet.getString("name"),
                resultSet.getInt("uid")
        );
    }
}
