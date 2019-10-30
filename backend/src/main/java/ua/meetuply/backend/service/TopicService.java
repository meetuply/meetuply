package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.TopicDAO;
import ua.meetuply.backend.model.Topic;

import java.util.List;

@Component
public class TopicService {

    @Autowired
    TopicDAO topicDAO;

    public Topic get(Integer id){
        return topicDAO.get(id);
    }

    public Integer getIdByName(String name){
        return topicDAO.getIdByName(name);
    }

    public void create(Topic topic){
        topicDAO.save(topic);
    }

    public void update(Topic topic){
        topicDAO.update(topic);
    }

    public void delete(Integer id){
        topicDAO.delete(id);
    }

    public List<Topic> getAll(){
        return topicDAO.getAll();
    }
}