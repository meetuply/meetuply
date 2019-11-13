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

    private static String GET_ROOMS_QUERY = "SELECT * FROM chat_room";

    private static String GET_ROOMS_BY_USER_QUERY = "SELECT room_id FROM user_chat_room WHERE uid = ?";

    private static String GET_ROOM_MESSAGES_QUERY = "SELECT * FROM message WHERE to_room_id = ? ORDER BY date_time DESC";
    private static String GET_ROOM_MESSAGES_CHUNK_QUERY = "SELECT * FROM message WHERE to_room_id = ? order by uid desc LIMIT ?, ?";

    private static String GET_LAST_MESSAGE_QUERY = "SELECT * FROM message WHERE to_room_id = ? ORDER BY date_time DESC LIMIT 0,1";

    private static String GET_ROOM_MEMBERS_QUERY = "SELECT uid FROM user_chat_room WHERE room_id = ?";

    private static String UPDATE_QUERY = "INSERT INTO message (`date_time`, `content`, `from`, `to_user_id`,`to_room_id`) " +
            "VALUES (?, ?, ?, ?, ?)";

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
        return jdbcTemplate.queryForList(GET_ROOMS_QUERY, Integer.class);
    }

    public List<Integer> getChatRoomsByUser(Integer userId) {
        return jdbcTemplate.queryForList(GET_ROOMS_BY_USER_QUERY, new Object[]{
                userId
        }, Integer.class);
    }

    public List<Message> getRoomMessages(Integer roomId) {
        List<Message> messageList = jdbcTemplate.query(GET_ROOM_MESSAGES_QUERY, new Object[]{roomId}, this);
        return messageList;
    }

    public List<Message> getRoomMessagesChunk(Integer roomId, Integer start, Integer size) {

        return jdbcTemplate.query(GET_ROOM_MESSAGES_CHUNK_QUERY, new Object[]{roomId, start, size}, this);
    }


    public Message getLastMessage(Integer roomId) {
        List<Message> messageList = jdbcTemplate.query(GET_LAST_MESSAGE_QUERY, new Object[]{roomId}, this);
        return messageList.isEmpty() ? null : messageList.get(0);
    }


    public List<Integer> getRoomMembers(Integer roomId) {
        return jdbcTemplate.queryForList(GET_ROOM_MEMBERS_QUERY, new Object[]{
                roomId
        }, Integer.class);
    }

    public void addMessage(Message m) {

        jdbcTemplate.update(UPDATE_QUERY,
                m.getDate_time(), m.getContent(), m.getFrom(), m.getTo_user_id(), m.getTo_room_id()
        );

    }

    public Integer createCommonRoom(Integer usr1, Integer usr2) {

        Integer before = jdbcTemplate.queryForObject("SELECT max(uid) FROM chat_room", new Object[]{}, Integer.class);
        jdbcTemplate.update("INSERT INTO chat_room () VALUES ()");

        Integer roomId = jdbcTemplate.queryForObject("SELECT max(uid) FROM chat_room", new Object[]{}, Integer.class);

        jdbcTemplate.update("INSERT INTO user_chat_room (room_id,uid) VALUES (?, ?),(?,?)",
                roomId, usr1, roomId, usr2
        );

        return roomId;
    }


}