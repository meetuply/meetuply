package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LanguageDAO implements IDAO<Language>, RowMapper<Language> {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public Language mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Language(
                resultSet.getInt("uid"),
                resultSet.getString("name")
        );
    }

    public Language getLanguageByName(String name) {
        List<Language> language = jdbcTemplate.query("SELECT * FROM language WHERE name = ?", new Object[] { name }, this);
        return language.size() == 0 ? null : language.get(0);
    }

    public List<Language> getUserLanguages(Integer id){
        List<Language> languageList = jdbcTemplate.query("SELECT * FROM language WHERE uid in (select language_id from user_language where user_id = ?)", new Object[]{id}, this);
        return languageList;
    }

    @Override
    public Language get(Integer id) {
        List<Language> languages = jdbcTemplate.query("SELECT * FROM language WHERE uid = ?", new Object[]{id}, this);
        return languages.size() == 0 ? null : languages.get(0);
    }



    @Override
    public List<Language> getAll() {
        List<Language> languages = jdbcTemplate.query("SELECT * FROM language", this);
        return languages;
    }

    @Override
    public void save(Language language) {
        jdbcTemplate.update("INSERT INTO language (name) " + "VALUES (?)",
                language.getName());
    }

    @Override
    public void update(Language language) {
        jdbcTemplate.update("UPDATE language SET name = ? WHERE uid = ?", language.getName(), language.getLanguageId());

    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM language WHERE uid = ?", id);
    }
}
