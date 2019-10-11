package ua.meetuply.backend.model;

public class Topic {
	
	public Topic () {}
	
	public Topic(Integer topicId, String name) {
		this.topicId = topicId;
		this.name = name;
	}
	
	private Integer topicId;
	private String name;
	
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}