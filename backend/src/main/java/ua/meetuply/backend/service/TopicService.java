package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.TopicDAO;
import ua.meetuply.backend.model.Topic;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

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

    public List<Topic> getTopicListFromIdList(List<Integer> topicsId){
        return isNotEmpty(topicsId) ? topicsId.stream().map(this::get).collect(toList()) : null;
    }

    public List<Integer> getIdListFromTopicList(List<Topic> topics){
        return isNotEmpty(topics) ? topics.stream().map(Topic::getTopicId).collect(toList()) : null;
    }

    public List<Topic> getByName(String name){
        List<Topic> topics = topicDAO.getAll();
        List<Topic> result = new ArrayList<Topic>();
        for (Topic topic : topics) {
            if(topic.getName().contains(name)) result.add(topic);
        }
        return result;
    }

    public Integer getTopicQuantity (Integer topicId){
        return topicDAO.getTopicQuantity(topicId);
    }

    public List<Topic> getAchievementTopics(Integer achievementId){
        return topicDAO.getAchievementTopics(achievementId);
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