package com.ssafy.pjt.model.dto;

public class Match {
	
    private int matchId;
    private int team1Id;
    private int team2Id;
    private String scheduledTime;
    private String location;
    private String sportsType;
    private String status;			// pending or confirmed
    private String createdAt;
    private String updatedAt;
    
    public Match() {
    	
    }
    
	public Match(int matchId, int team1Id, int team2Id, String scheduledTime, String location, String sportsType,
			String status, String createdAt, String updatedAt) {
		super();
		this.matchId = matchId;
		this.team1Id = team1Id;
		this.team2Id = team2Id;
		this.scheduledTime = scheduledTime;
		this.location = location;
		this.sportsType = sportsType;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public int getTeam1Id() {
		return team1Id;
	}

	public void setTeam1Id(int team1Id) {
		this.team1Id = team1Id;
	}

	public int getTeam2Id() {
		return team2Id;
	}

	public void setTeam2Id(int team2Id) {
		this.team2Id = team2Id;
	}

	public String getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getSportsType() {
		return sportsType;
	}

	public void setSportsType(String sportsType) {
		this.sportsType = sportsType;
	}

	@Override
	public String toString() {
		return "Match [matchId=" + matchId + ", team1Id=" + team1Id + ", team2Id=" + team2Id + ", scheduledTime="
				+ scheduledTime + ", location=" + location + ", sportsType=" + sportsType + ", status=" + status
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
