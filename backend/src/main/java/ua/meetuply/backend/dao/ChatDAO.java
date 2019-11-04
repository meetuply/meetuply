package ua.meetuply.backend.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Message;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ChatDAO implements RowMapper<Message> {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Message(
                resultSet.getInt("uid"),
                resultSet.getTimestamp("date_time").toLocalDateTime(),
                resultSet.getString("content"),
                resultSet.getInt("from"),
                resultSet.getInt("to_user_id"),
                resultSet.getInt("to_room_id")

        );
    }

    public List<Integer> getChatRooms() {
        return jdbcTemplate.queryForList("SELECT * FROM chat_room", Integer.class);
    }

    public List<Integer> getChatRoomsByUser(Integer userId) {
        return jdbcTemplate.queryForList("SELECT room_id FROM user_chat_room WHERE uid = ?", new Object[]{
                userId
        }, Integer.class);
    }

    public List<Message> getRoomMessages(Integer roomId) {
        List<Message> messageList = jdbcTemplate.query("SELECT * FROM message WHERE to_room_id = ? ORDER BY date_time DESC", new Object[]{roomId}, this);
        return messageList;
    }

    public Message getLastMessage(Integer roomId) {
        List<Message> messageList = jdbcTemplate.query("SELECT * FROM message WHERE to_room_id = ? ORDER BY date_time DESC LIMIT 0,1", new Object[]{roomId}, this);
        return messageList.isEmpty() ? null : messageList.get(0);
    }


    public List<Integer> getRoomMembers(Integer roomId) {
        return jdbcTemplate.queryForList("SELECT uid FROM user_chat_room WHERE room_id = ?", new Object[]{
                roomId
        }, Integer.class);
    }

    public void addMessage(Message m) {

        jdbcTemplate.update(

                "INSERT INTO message (`date_time`, `content`, `from`, `to_user_id`,`to_room_id`) " +
                        "VALUES (?, ?, ?, ?, ?)",
                m.getDate_time(), m.getContent(), m.getFrom(), m.getTo_user_id(), m.getTo_room_id()
        );

    }

    public Integer createCommonRoom(Integer usr1, Integer usr2) {

        Integer before = jdbcTemplate.queryForObject("SELECT max(uid) FROM chat_room", new Object[]{}, Integer.class);


        jdbcTemplate.update(

                "INSERT INTO chat_room () " +
                        "VALUES ()"
        );

        Integer roomId = jdbcTemplate.queryForObject("SELECT max(uid) FROM chat_room", new Object[]{}, Integer.class);
        //if before == room id then error


        System.out.println("created:" + roomId);

        jdbcTemplate.update(

                "INSERT INTO user_chat_room (room_id,uid) " +
                        "VALUES (?, ?),(?,?)",
                roomId, usr1, roomId, usr2
        );


        return roomId;
    }


}