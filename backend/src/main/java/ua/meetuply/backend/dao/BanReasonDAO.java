package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.BanReason;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BanReasonDAO implements IDAO<BanReason>, RowMapper<BanReason> {
	
	@Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public BanReason get(Integer id) {
        List<BanReason> banReasons = jdbcTemplate.query("SELECT * FROM ban_reason WHERE uid = ?", new Object[] { id }, this);
        return banReasons.size() == 0 ? null : banReasons.get(0);
    }

    @Override
    public List<BanReason> getAll() {
        List<BanReason> banReasons = jdbcTemplate.query("SELECT * FROM ban_reason", this);
        return banReasons;
    }

    public List<BanReason> getByName(String term) {
        term = "%" + term + "%";
        return jdbcTemplate.query("SELECT * FROM ban_reason WHERE name LIKE ?", new Object[] { term },this);
    }

    @Override
    public void save(BanReason banReason) {
        jdbcTemplate.update("INSERT INTO `ban_reason` (`name`) " + "VALUES (?)",
        		banReason.getName());
    }

    @Override
    public void update(BanReason banReason) {
        jdbcTemplate.update("UPDATE ban_reason SET name = ? WHERE uid = ?", banReason.getName(), banReason.getBanReasonId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM ban_reason WHERE uid = ?", id);
    }

    @Override
    public BanReason mapRow(ResultSet resultSet, int i) throws SQLException {
        return new BanReason(
                resultSet.getInt("uid"),
                resultSet.getString("name")
        );
    }
}
