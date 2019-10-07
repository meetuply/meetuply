package ua.meetuply.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class ExampleDBController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String index() {
        List<String> list = jdbcTemplate.query("SELECT * FROM test;", new IdMapper());
        return "Number of objects: " + list.size();
    }

}

class IdMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong("id");
        return id.toString();
    }
}
