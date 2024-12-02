package com.ssafy.pjt.model.dto;

public class Team {
	
	private int teamId;
    private String teamName;
    private int leaderId;
    private String createdAt;
    private String updatedAt;
    
    public Team() {
    	
    }

	public Team(int teamId, String teamName, int leaderId, String createdAt, String updatedAt) {
		super();
		this.teamId = teamId;
		this.teamName = teamName;
		this.leaderId = leaderId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
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

	@Override
	public String toString() {
		return "Team [teamId=" + teamId + ", teamName=" + teamName + ", leaderId=" + leaderId + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    
}
