package com.teamuniverse.drtmobile.support;

import com.att.intern.webservice.Incident;

public class IncidentInfo {
	String	key;
	Object	value;
	
	public IncidentInfo(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public static IncidentInfo[] getInfos(Incident incident) {
		int size = 0;
		boolean[] whichs = new boolean[] { incident.getAssessNotes() != null, incident.getBuildingAddress() != null, incident.getBuildingName() != null, incident.getBuildingStatus() != null, incident.getBuildingType() != null, incident.getCompltnDate() != null, incident.getComPowerIndicator() != null, incident.getContactPhone() != null, incident.getCreLead() != null, incident.getDamageIndicator() != null, incident.getElecIssueClsdIndicator() != null, incident.getElecIssueIndicator() != null, incident.getEnvIssueClsdIndicator() != null, incident.getEnvIssueIndicator() != null, // done
		incident.getEstCapCost() != 0, // TODO
		incident.getEstExpenseCost() != 0, // TODO
		incident.getEventName() != null, incident.getFenceGateIssueClsdIndicator() != null, incident.getFenceGateIssueIndicator() != null, incident.getGenIssueClsdIndicator() != null, incident.getGenIssueIndicator() != null, // done
		incident.getGeoLoc() != 0, // TODO
		incident.getGroundsIssueClsdIndicator() != null, incident.getGroundsIssueIndicator() != null, incident.getIncidentCompltnDate() != null, incident.getIncidentNotes() != null, incident.getIncidentStatus() != null, // done
		incident.getIncidentYear() != 0, // TODO
		incident.getInitialRptDate() != null, incident.getMechIssueClsdIndicator() != null, incident.getMechIssueIndicator() != null, incident.getMobCOIndicator() != null, incident.getOnGeneratorIndicator() != null, incident.getOtherIssueClsdIndicator() != null, incident.getOtherIssueIndicator() != null, incident.getPlumbIssueClsdIndicator() != null, incident.getPlumbIssueIndicator() != null, incident.getPMAttuid() != null, // done
		incident.getRecNumber() != 0, // TODO
		incident.getReqATTUID() != null, incident.getRoofsIssueClsdIndicator() != null, incident.getRoofsIssueIndicator() != null, incident.getSafetyIssueClsdIndicator() != null, incident.getSafetyIssueIndicator() != null, incident.getState() != null, incident.getStatusNotes() != null, incident.getStructIssueClsdIndicator() != null, incident.getStructIssueIndicator() != null, incident.getUnOccupiableIndicator() != null, incident.getWaterIssueClsdIndicator() != null, incident.getWaterIssueIndicator() != null, incident.getWorkReqNumber() != null };
		for (int i = 0; i < whichs.length; i++)
			if (whichs[i]) size++;
		return new IncidentInfo[] {};
	}
}
