package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Topic;
import ua.meetuply.backend.service.TopicService;

import javax.validation.Valid;

@RequestMapping("api/topics")
@Transactional
@RestController
public class TopicController {
	
	@Autowired
	private TopicService topicService;
	
	@GetMapping()
    public @ResponseBody Iterable<Topic> getAllTopics() {
        return topicService.getAll();
    }
	
	@GetMapping("/{topicId}")
	public Topic getOneTopic(@PathVariable("topicId") Integer topicId) {
	    return topicService.get(topicId);
	}

    @GetMapping("/name/{topicName}")
    public Integer getTopicIdByName(@PathVariable("topicName") String topicName) {
	    return topicService.getIdByName(topicName);
    }

    @GetMapping("/quantity/{topicId}")
    public Integer getTopicIdByName(@PathVariable("topicId") Integer topicId) {
        return topicService.getTopicQuantity(topicId);
    }

    @GetMapping("topicName/{topicName}")
    public Iterable<Topic> getTopicByName(@PathVariable("topicName") String topicName) {
        return topicService.getByName(topicName);
    }

    @GetMapping("achievement/{achievementId}")
    public Iterable<Topic> getAchievementTopics(@PathVariable("achievementId") Integer achievementId) {
        return topicService.getAchievementTopics(achievementId);
    }

    @PostMapping()
    public ResponseEntity createNewTopic(@Valid @RequestBody Topic topic){
        topicService.create(topic);
		return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{topicId}")
    public ResponseEntity updateTopic(@PathVariable("topicId") Integer topicId, @RequestBody Topic topic) {
    	if (topicService.get(topicId) == null) {
            return ResponseEntity.notFound().build();
        }
    	topic.setTopicId(topicId);
        topicService.update(topic);
    	return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity deleteTopic(@PathVariable("topicId") Integer topicId){
    	if (topicService.get(topicId) == null) {
            return ResponseEntity.notFound().build();
        }
        topicService.delete(topicId);
    	return ResponseEntity.ok().build();
    }
}