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
public class RoleDAO implements IDAO<Role>, RowMapper<Role> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public Role getRoleByName(String name) {
        List<Role> role = jdbcTemplate.query("SELECT * FROM role WHERE name = ?", new Object[] { name }, this);
        return role.size() == 0 ? null : role.get(0);
    }

    @Override
    public Role get(Integer id) {
        List<Role> role = jdbcTemplate.query("SELECT * FROM role WHERE uid = ?", new Object[] { id }, this);
        return role.size() == 0 ? null : role.get(0);
    }

    @Override
    public List<Role> getAll() {
        return null;
    }

    @Override
    public void save(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void delete(Integer id) {

    }

    public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Role(
                resultSet.getInt("uid"),
                resultSet.getString("name")
        );
    }
}