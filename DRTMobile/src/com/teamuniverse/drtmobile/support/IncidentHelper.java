package com.teamuniverse.drtmobile.support;

import com.att.intern.webservice.Incident;

/**
 * This class greatly facilitates the extraction of info from an incident
 * object. Its getInfos() goes through all of the possible fields and returns an
 * array of IncidentInfo objects containing only the fields that were filled in.
 * USe it effectively by type casting the Incident from which you want to
 * extract information into an IncidentHelper object.
 * 
 * @author ef183v
 * 
 */
public class IncidentHelper extends Incident {
	/**
	 * A generated serialVersionUID
	 */
	private static final long	serialVersionUID	= -4119664774967242074L;
	
	/**
	 * The method that extracts all of the available infos from the incident
	 * that calls it.
	 * 
	 * @return An IncidentInfo[] that has all of the informations.
	 */
	public IncidentInfo[] getInfos(Incident inc) {
		boolean[] whichs = new boolean[] {
				!(inc.getAssessNotes() == null),
				!(inc.getBuildingAddress() == null),
				!(inc.getBuildingName() == null),
				!(inc.getBuildingStatus() == null),
				!(inc.getBuildingType() == null),
				!(inc.getCompltnDate() == null),
				!(inc.getComPowerIndicator() == null),
				!(inc.getContactPhone() == null),
				!(inc.getCreLead() == null),
				!(inc.getDamageIndicator() == null),
				!(inc.getElecIssueClsdIndicator() == null),
				!(inc.getElecIssueIndicator() == null),
				!(inc.getEnvIssueClsdIndicator() == null),
				!(inc.getEnvIssueIndicator() == null), // done
				!(inc.getEstCapCost() == 0), // TODO int check
				!(inc.getEstExpenseCost() == 0), // TODO int check
				!(inc.getEventName() == null),
				!(inc.getFenceGateIssueClsdIndicator() == null),
				!(inc.getFenceGateIssueIndicator() == null),
				!(inc.getGenIssueClsdIndicator() == null),
				!(inc.getGenIssueIndicator() == null), // done
				!(inc.getGeoLoc() == 0), // TODO int check
				!(inc.getGroundsIssueClsdIndicator() == null),
				!(inc.getGroundsIssueIndicator() == null),
				!(inc.getIncidentCompltnDate() == null),
				!(inc.getIncidentNotes() == null),
				!(inc.getIncidentStatus() == null), // done
				!(inc.getIncidentYear() == 0), // TODO int check
				!(inc.getInitialRptDate() == null),
				!(inc.getMechIssueClsdIndicator() == null),
				!(inc.getMechIssueIndicator() == null),
				!(inc.getMobCOIndicator() == null),
				!(inc.getOnGeneratorIndicator() == null),
				!(inc.getOtherIssueClsdIndicator() == null),
				!(inc.getOtherIssueIndicator() == null),
				!(inc.getPlumbIssueClsdIndicator() == null),
				!(inc.getPlumbIssueIndicator() == null),
				!(inc.getPMAttuid() == null), // done
				!(inc.getRecNumber() == 0), // TODO int check
				!(inc.getReqATTUID() == null),
				!(inc.getRoofsIssueClsdIndicator() == null),
				!(inc.getRoofsIssueIndicator() == null),
				!(inc.getSafetyIssueClsdIndicator() == null),
				!(inc.getSafetyIssueIndicator() == null),
				!(inc.getState() == null), !(inc.getStatusNotes() == null),
				!(inc.getStructIssueClsdIndicator() == null),
				!(inc.getStructIssueIndicator() == null),
				!(inc.getUnOccupiableIndicator() == null),
				!(inc.getWaterIssueClsdIndicator() == null),
				!(inc.getWaterIssueIndicator() == null),
				!(inc.getWorkReqNumber() == null) };
		
		int sizeCount = 0;
		for (int i = 0; i < whichs.length; i++)
			if (whichs[i]) sizeCount++;
		final int size = sizeCount;
		
		IncidentInfo[] info = new IncidentInfo[size];
		int index = 0, stepper = 0;
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Assess Notes", inc.getAssessNotes());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Building Address", inc.getBuildingAddress());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Bulding Name", inc.getBuildingName());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Building Status", inc.getBuildingStatus());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Building Type", inc.getBuildingType());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Completion Date", inc.getCompltnDate());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Com Power Indicator", inc.getComPowerIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Contact Phone Number", inc.getContactPhone());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("CRE Lead", inc.getCreLead());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Damage Indicator", inc.getDamageIndicator());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Elec Issue Closed Indicator", inc.getElecIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Elec Issue Indicator", inc.getElecIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Env Issue Closed Indicator", inc.getEnvIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("EnvIssue Indicator", inc.getEnvIssueIndicator());
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Estimated Cap Cost", inc.getEstCapCost());
		} // TODO int return from incident call
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Estimated Expense Cost", inc.getEstExpenseCost());
		} // TODO int return from incident call
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Event Name", inc.getEventName());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Fence Gate Issue Closed Indicator", inc.getFenceGateIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Fence Gate Issue Indicator", inc.getFenceGateIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("General Issue Closed Indicator", inc.getGenIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("General Issue Indicator", inc.getGenIssueIndicator());
		
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("ZIP Code", inc.getGeoLoc());
		} // TODO int return from incident call
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Grounds Issue Closed Indicator", inc.getGroundsIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Grounds Issue Indicator", inc.getGroundsIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Incident Completion Date", inc.getIncidentCompltnDate());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Incident Notes", inc.getIncidentNotes());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Incident Status", inc.getIncidentStatus());
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Incident Year", inc.getIncidentYear());
		} // TODO int return from incident call
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Initial Report Date", inc.getInitialRptDate());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Mechanical Issue Closed Indicator", inc.getMechIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Mechanical Issue Indicator", inc.getMechIssueIndicator());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Mob CO Indicator", inc.getMobCOIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("On Generator Indicator", inc.getOnGeneratorIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Other Issue Closed Indicator", inc.getOtherIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Other Issue Indicator", inc.getOtherIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Plumb Issue Closed Indicator", inc.getPlumbIssueClsdIndicator());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Plumb Issue Indicator", inc.getPlumbIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("PM ATTUID", inc.getPMAttuid());
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Record Number", inc.getRecNumber());
		} // TODO int return from incident call
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Req ATTUID", inc.getReqATTUID());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Roofs Issue Closed Indicator", inc.getRoofsIssueClsdIndicator());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Roofs Issue Indicator", inc.getRoofsIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Safety Issue Closed Indicator", inc.getSafetyIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Safety Issue Indicator", inc.getSafetyIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("State", inc.getState());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Status Notes", inc.getStatusNotes());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Structural Issue Closed Indicator", inc.getStructIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Structural Issue Indicator", inc.getStructIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Unoccupiable Indicator", inc.getUnOccupiableIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Water Issue Closed Indicator", inc.getWaterIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Water Issue Indicator", inc.getWaterIssueIndicator());
		
		if (whichs[stepper]) info[index++] = new IncidentInfo("Work Req Number", inc.getWorkReqNumber());
		
		return info;
	}
	
	/**
	 * The class thet defines the object that contains each field of
	 * information. The object is defined of a String key and an Object value.
	 * 
	 * @author ef183v
	 * 
	 */
	public class IncidentInfo {
		String	key;
		Object	value;
		
		private IncidentInfo(String key, Object value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Returns the key of the relevant key-value pair.
		 * 
		 * @return
		 */
		public String getKey() {
			return key;
		}
		
		/**
		 * Returns the value of the relevant key-value pair.
		 * 
		 * @return
		 */
		public Object getValue() {
			return value;
		}
	}
}
