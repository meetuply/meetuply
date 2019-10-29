package ua.meetuply.backend.model;

import java.time.LocalDateTime;

public class Ban {
	
	public Ban() { }
	
	public Ban(BanReason banReason, AppUser author, AppUser reported, String description, LocalDateTime time) {
		this.banReason = banReason;
		this.author = author;
		this.reported = reported;
		this.description = description;
		this.time = time;
	}
	
	private BanReason banReason;
	private AppUser author;
	private AppUser reported;
	private String description;
	private LocalDateTime time;
	
	public BanReason getBanReason() {
		return banReason;
	}
	public void setBanReason(BanReason banReason) {
		this.banReason = banReason;
	}
	public AppUser getAuthor() {
		return author;
	}
	public void setAuthor(AppUser author) {
		this.author = author;
	}
	public AppUser getReported() {
		return reported;
	}
	public void setReported(AppUser reported) {
		this.reported = reported;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
