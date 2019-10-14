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
	
	@PostMapping("/create")
    public ResponseEntity<Topic> createNewTopic(@Valid @RequestBody Topic topic){
        topicService.create(topic);
		return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{topicId}")
    public ResponseEntity<Topic> updateTopic(@PathVariable("topicId") Integer topicId, @RequestBody Topic topic) {
    	if (topicService.get(topicId) == null) {
            ResponseEntity.badRequest().build();
        }
        topicService.update(topic);
    	return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Topic> deleteTopic(@PathVariable("topicId") Integer topicId){
    	if (topicService.get(topicId) == null) {
            ResponseEntity.badRequest().build();
        }
        topicService.delete(topicId);
    	return ResponseEntity.ok().build();
    }
}