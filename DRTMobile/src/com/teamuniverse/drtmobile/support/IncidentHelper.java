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
	public IncidentInfo[] getInfos() {
		boolean[] whichs = new boolean[] { !(getAssessNotes() == null), !(getBuildingAddress() == null), !(getBuildingName() == null), !(getBuildingStatus() == null), !(getBuildingType() == null), !(getCompltnDate() == null), !(getComPowerIndicator() == null), !(getContactPhone() == null), !(getCreLead() == null), !(getDamageIndicator() == null), !(getElecIssueClsdIndicator() == null), !(getElecIssueIndicator() == null), !(getEnvIssueClsdIndicator() == null), !(getEnvIssueIndicator() == null), // done
		!(getEstCapCost() == 0), // TODO int check
		!(getEstExpenseCost() == 0), // TODO int check
		!(getEventName() == null), !(getFenceGateIssueClsdIndicator() == null), !(getFenceGateIssueIndicator() == null), !(getGenIssueClsdIndicator() == null), !(getGenIssueIndicator() == null), // done
		!(getGeoLoc() == 0), // TODO int check
		!(getGroundsIssueClsdIndicator() == null), !(getGroundsIssueIndicator() == null), !(getIncidentCompltnDate() == null), !(getIncidentNotes() == null), !(getIncidentStatus() == null), // done
		!(getIncidentYear() == 0), // TODO int check
		!(getInitialRptDate() == null), !(getMechIssueClsdIndicator() == null), !(getMechIssueIndicator() == null), !(getMobCOIndicator() == null), !(getOnGeneratorIndicator() == null), !(getOtherIssueClsdIndicator() == null), !(getOtherIssueIndicator() == null), !(getPlumbIssueClsdIndicator() == null), !(getPlumbIssueIndicator() == null), !(getPMAttuid() == null), // done
		!(getRecNumber() == 0), // TODO int check
		!(getReqATTUID() == null), !(getRoofsIssueClsdIndicator() == null), !(getRoofsIssueIndicator() == null), !(getSafetyIssueClsdIndicator() == null), !(getSafetyIssueIndicator() == null), !(getState() == null), !(getStatusNotes() == null), !(getStructIssueClsdIndicator() == null), !(getStructIssueIndicator() == null), !(getUnOccupiableIndicator() == null), !(getWaterIssueClsdIndicator() == null), !(getWaterIssueIndicator() == null), !(getWorkReqNumber() == null) };
		
		int sizeCount = 0;
		for (int i = 0; i < whichs.length; i++)
			if (whichs[i]) sizeCount++;
		final int size = sizeCount;
		
		IncidentInfo[] info = new IncidentInfo[size];
		int index = 0, stepper = 0;
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Assess Notes", getAssessNotes());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Building Address", getBuildingAddress());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Bulding Name", getBuildingName());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Building Status", getBuildingStatus());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Building Type", getBuildingType());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Completion Date", getCompltnDate());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Com Power Indicator", getComPowerIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Contact Phone Number", getContactPhone());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("CRE Lead", getCreLead());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Damage Indicator", getDamageIndicator());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Elec Issue Closed Indicator", getElecIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Elec Issue Indicator", getElecIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Env Issue Closed Indicator", getEnvIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("EnvIssue Indicator", getEnvIssueIndicator());
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Estimated Cap Cost", getEstCapCost());
		} // TODO int return from incident call
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Estimated Expense Cost", getEstExpenseCost());
		} // TODO int return from incident call
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Event Name", getEventName());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Fence Gate Issue Closed Indicator", getFenceGateIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Fence Gate Issue Indicator", getFenceGateIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("General Issue Closed Indicator", getGenIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("General Issue Indicator", getGenIssueIndicator());
		
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("ZIP Code", getGeoLoc());
		} // TODO int return from incident call
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Grounds Issue Closed Indicator", getGroundsIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Grounds Issue Indicator", getGroundsIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Incident Completion Date", getIncidentCompltnDate());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Incident Notes", getIncidentNotes());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Incident Status", getIncidentStatus());
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Incident Year", getIncidentYear());
		} // TODO int return from incident call
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Initial Report Date", getInitialRptDate());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Mechanical Issue Closed Indicator", getMechIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Mechanical Issue Indicator", getMechIssueIndicator());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Mob CO Indicator", getMobCOIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("On Generator Indicator", getOnGeneratorIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Other Issue Closed Indicator", getOtherIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Other Issue Indicator", getOtherIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Plumb Issue Closed Indicator", getPlumbIssueClsdIndicator());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Plumb Issue Indicator", getPlumbIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("PM ATTUID", getPMAttuid());
		if (whichs[stepper++]) {
			info[index++] = new IncidentInfo("Record Number", getRecNumber());
		} // TODO int return from incident call
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Req ATTUID", getReqATTUID());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Roofs Issue Closed Indicator", getRoofsIssueClsdIndicator());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Roofs Issue Indicator", getRoofsIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Safety Issue Closed Indicator", getSafetyIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Safety Issue Indicator", getSafetyIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("State", getState());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Status Notes", getStatusNotes());
		
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Structural Issue Closed Indicator", getStructIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Structural Issue Indicator", getStructIssueIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Unoccupiable Indicator", getUnOccupiableIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Water Issue Closed Indicator", getWaterIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = new IncidentInfo("Water Issue Indicator", getWaterIssueIndicator());
		
		if (whichs[stepper]) info[index++] = new IncidentInfo("Work Req Number", getWorkReqNumber());
		
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
