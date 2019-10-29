package ua.meetuply.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.meetuply.backend.model.Ban;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.BanReasonService;

@Repository
public class BanDAO implements RowMapper<Ban> {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    AppUserService appUserService;
	
	@Autowired
    BanReasonService banReasonService;
	
	public Ban get(Integer reason_id, Integer by_user_id, Integer reported_user_id) {
        List<Ban> bans = jdbcTemplate.query("SELECT * FROM ban WHERE reason_id = ? AND by_user_id = ? AND reported_user_id = ?", new Object[] { reason_id, by_user_id, reported_user_id }, this);
        return bans.size() == 0 ? null : bans.get(0);
    }

    public List<Ban> getByReason(Integer reason_id) {
        List<Ban> bans = jdbcTemplate.query("SELECT * FROM ban WHERE reason_id = ?", new Object[] { reason_id }, this);
        return bans;
    }

    public List<Ban> getByAuthor(Integer by_user_id) {
        List<Ban> bans = jdbcTemplate.query("SELECT * FROM ban WHERE by_user_id = ?", new Object[] { by_user_id }, this);
        return bans;
    }

    public List<Ban> getByReported(Integer reported_user_id) {
        List<Ban> bans = jdbcTemplate.query("SELECT * FROM ban WHERE reported_user_id = ?", new Object[] { reported_user_id }, this);
        return bans;
    }

    public List<Ban> getAll() {
        List<Ban> bans = jdbcTemplate.query("SELECT * FROM ban", this);
        return bans;
    }
	
	public void save(Ban ban) {
        jdbcTemplate.update("INSERT INTO `ban` (`reason_id`, `by_user_id`, `reported_user_id`, `description`, `date_time`) " + "VALUES (?, ?, ?, ?, ?)",
                ban.getBanReason().getBanReasonId(),
                ban.getAuthor().getUserId(),
                ban.getReported().getUserId(),
                ban.getDescription(),
                ban.getTime());
    }

    public void update(Ban ban) {
        jdbcTemplate.update("UPDATE ban SET description = ?"
        		+ " WHERE reason_id = ? AND by_user_id = ? AND reported_user_id = ?",
        		ban.getDescription(), ban.getBanReason().getBanReasonId(), ban.getAuthor().getUserId(), ban.getReported().getUserId());
    }

    public void delete(Integer reason_id, Integer by_user_id, Integer reported_user_id) {
        jdbcTemplate.update("DELETE FROM ban WHERE reason_id = ? AND by_user_id = ? AND reported_user_id = ?", reason_id, by_user_id, reported_user_id);
    }

    @Override
    public Ban mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Ban(
        		banReasonService.get(resultSet.getInt("reason_id")),
        		appUserService.getUser(resultSet.getInt("by_user_id")),
        		appUserService.getUser(resultSet.getInt("reported_user_id")),
                resultSet.getString("description"),
                resultSet.getTimestamp("date_time").toLocalDateTime()
        );
    }

}
