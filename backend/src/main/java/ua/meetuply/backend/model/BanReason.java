package ua.meetuply.backend.model;

public class BanReason {
	
	public BanReason() { }
	
	public BanReason(Integer banReasonId, String name) {
		this.banReasonId = banReasonId;
		this.name = name;
	}
	
	private Integer banReasonId;
	private String name;
	
	public Integer getBanReasonId() {
		return banReasonId;
	}
	public void setBanReasonId(Integer banReasonId) {
		this.banReasonId = banReasonId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
