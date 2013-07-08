package com.teamuniverse.drtmobile.support;

import com.att.intern.webservice.Incident;

public class IncidentInfo {
	String	key;
	Object	value;
	
	private IncidentInfo(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public Object getValue() {
		return value;
	}
	
	public static IncidentInfo[] getInfos(Incident incident) {
		
		boolean[] whichs = new boolean[] { !(incident.getAssessNotes() == null), !(incident.getBuildingAddress() == null), !(incident.getBuildingName() == null), !(incident.getBuildingStatus() == null), !(incident.getBuildingType() == null), !(incident.getCompltnDate() == null), !(incident.getComPowerIndicator() == null), !(incident.getContactPhone() == null), !(incident.getCreLead() == null), !(incident.getDamageIndicator() == null), !(incident.getElecIssueClsdIndicator() == null), !(incident.getElecIssueIndicator() == null), !(incident.getEnvIssueClsdIndicator() == null), !(incident.getEnvIssueIndicator() == null), // done
		!(incident.getEstCapCost() == 0), // TODO int check
		!(incident.getEstExpenseCost() == 0), // TODO int check
		!(incident.getEventName() == null), !(incident.getFenceGateIssueClsdIndicator() == null), !(incident.getFenceGateIssueIndicator() == null), !(incident.getGenIssueClsdIndicator() == null), !(incident.getGenIssueIndicator() == null), // done
		!(incident.getGeoLoc() == 0), // TODO int check
		!(incident.getGroundsIssueClsdIndicator() == null), !(incident.getGroundsIssueIndicator() == null), !(incident.getIncidentCompltnDate() == null), !(incident.getIncidentNotes() == null), !(incident.getIncidentStatus() == null), // done
		!(incident.getIncidentYear() == 0), // TODO int check
		!(incident.getInitialRptDate() == null), !(incident.getMechIssueClsdIndicator() == null), !(incident.getMechIssueIndicator() == null), !(incident.getMobCOIndicator() == null), !(incident.getOnGeneratorIndicator() == null), !(incident.getOtherIssueClsdIndicator() == null), !(incident.getOtherIssueIndicator() == null), !(incident.getPlumbIssueClsdIndicator() == null), !(incident.getPlumbIssueIndicator() == null), !(incident.getPMAttuid() == null), // done
		!(incident.getRecNumber() == 0), // TODO int check
		!(incident.getReqATTUID() == null), !(incident.getRoofsIssueClsdIndicator() == null), !(incident.getRoofsIssueIndicator() == null), !(incident.getSafetyIssueClsdIndicator() == null), !(incident.getSafetyIssueIndicator() == null), !(incident.getState() == null), !(incident.getStatusNotes() == null), !(incident.getStructIssueClsdIndicator() == null), !(incident.getStructIssueIndicator() == null), !(incident.getUnOccupiableIndicator() == null), !(incident.getWaterIssueClsdIndicator() == null), !(incident.getWaterIssueIndicator() == null), !(incident.getWorkReqNumber() == null) };
		
		int sizeCount = 0;
		for (int i = 0; i < whichs.length; i++)
			if (whichs[i]) sizeCount++;
		final int size = sizeCount;
		
		IncidentInfo[] info = new IncidentInfo[size];
		int index = 0, stepper = 0;
		
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Assess Notes", incident.getAssessNotes());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Building Address", incident.getBuildingAddress());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Bulding Name", incident.getBuildingName());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Building Status", incident.getBuildingStatus());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Building Type", incident.getBuildingType());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Completion Date", incident.getCompltnDate());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Com Power Indicator", incident.getComPowerIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Contact Phone Number", incident.getContactPhone());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("CRE Lead", incident.getCreLead());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Damage Indicator", incident.getDamageIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Elec Issue Closed Indicator", incident.getElecIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Elec Issue Indicator", incident.getElecIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Env Issue Closed Indicator", incident.getEnvIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("EnvIssue Indicator", incident.getEnvIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Estimated Cap Cost", incident.getEstCapCost());
		} // TODO int return from incident call
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Estimated Expense Cost", incident.getEstExpenseCost());
		} // TODO int return from incident call
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Event Name", incident.getEventName());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Fence Gate Issue Closed Indicator", incident.getFenceGateIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Fence Gate Issue Indicator", incident.getFenceGateIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("General Issue Closed Indicator", incident.getGenIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("General Issue Indicator", incident.getGenIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("ZIP Code", incident.getGeoLoc());
		} // TODO int return from incident call
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Grounds Issue Closed Indicator", incident.getGroundsIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Grounds Issue Indicator", incident.getGroundsIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Incident Completion Date", incident.getIncidentCompltnDate());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Incident Notes", incident.getIncidentNotes());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Incident Status", incident.getIncidentStatus());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Incident Year", incident.getIncidentYear());
		} // TODO int return from incident call
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Initial Report Date", incident.getInitialRptDate());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Mechanical Issue Closed Indicator", incident.getMechIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Mechanical Issue Indicator", incident.getMechIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Mob CO Indicator", incident.getMobCOIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("On Generator Indicator", incident.getOnGeneratorIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Other Issue Closed Indicator", incident.getOtherIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Other Issue Indicator", incident.getOtherIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Plumb Issue Closed Indicator", incident.getPlumbIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Plumb Issue Indicator", incident.getPlumbIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("PM ATTUID", incident.getPMAttuid());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Record Number", incident.getRecNumber());
		} // TODO int return from incident call
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Req ATTUID", incident.getReqATTUID());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Roofs Issue Closed Indicator", incident.getRoofsIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Roofs Issue Indicator", incident.getRoofsIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Safety Issue Closed Indicator", incident.getSafetyIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Safety Issue Indicator", incident.getSafetyIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("State", incident.getState());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Status Notes", incident.getStatusNotes());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Structural Issue Closed Indicator", incident.getStructIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Structural Issue Indicator", incident.getStructIssueIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Unoccupiable Indicator", incident.getUnOccupiableIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Water Issue Closed Indicator", incident.getWaterIssueClsdIndicator());
		}
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Water Issue Indicator", incident.getWaterIssueIndicator());
		}
		if (whichs[stepper]) {
			info[index++] = new IncidentInfo("Work Req Number", incident.getWorkReqNumber());
		}
		
		return info;
	}
}
