package com.teamuniverse.drtmobile.support;

import java.util.Calendar;

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
	
	/** Empty constructor to do operations locally */
	private IncidentHelper() {
	}
	
	/**
	 * The method that extracts all of the available infos from the incident
	 * that calls it.
	 * 
	 * @return An IncidentInfo[] that has all of the informations.
	 */
	public static IncidentInfo[] getInfos(Incident inc) {
		boolean[] whichs = new boolean[] { !(inc.getAssessNotes() == null), !(inc.getBuildingAddress() == null), !(inc.getBuildingName() == null), !(inc.getBuildingStatus() == null), !(inc.getBuildingType() == null), !(inc.getCompltnDate() == null), !(inc.getComPowerIndicator() == null), !(inc.getContactPhone() == null), !(inc.getCreLead() == null), !(inc.getDamageIndicator() == null), !(inc.getElecIssueClsdIndicator() == null), !(inc.getElecIssueIndicator() == null), !(inc.getEnvIssueClsdIndicator() == null), !(inc.getEnvIssueIndicator() == null), !(inc.getEstCapCost() == 0), !(inc.getEstExpenseCost() == 0), !(inc.getEventName() == null), !(inc.getFenceGateIssueClsdIndicator() == null), !(inc.getFenceGateIssueIndicator() == null), !(inc.getGenIssueClsdIndicator() == null), !(inc.getGenIssueIndicator() == null), !(inc.getGeoLoc() == 0), !(inc.getGroundsIssueClsdIndicator() == null), !(inc.getGroundsIssueIndicator() == null), !(inc.getIncidentCompltnDate() == null), !(inc.getIncidentNotes() == null), !(inc.getIncidentStatus() == null), !(inc.getIncidentYear() == 0), !(inc.getInitialRptDate() == null), !(inc.getMechIssueClsdIndicator() == null), !(inc.getMechIssueIndicator() == null), !(inc.getMobCOIndicator() == null), !(inc.getOnGeneratorIndicator() == null), !(inc.getOtherIssueClsdIndicator() == null), !(inc.getOtherIssueIndicator() == null), !(inc.getPlumbIssueClsdIndicator() == null), !(inc.getPlumbIssueIndicator() == null), !(inc.getPMAttuid() == null), !(inc.getRecNumber() == 0), !(inc.getReqATTUID() == null), !(inc.getRoofsIssueClsdIndicator() == null), !(inc.getRoofsIssueIndicator() == null), !(inc.getSafetyIssueClsdIndicator() == null), !(inc.getSafetyIssueIndicator() == null), !(inc.getState() == null), !(inc.getStatusNotes() == null), !(inc.getStructIssueClsdIndicator() == null), !(inc.getStructIssueIndicator() == null), !(inc.getUnOccupiableIndicator() == null), !(inc.getWaterIssueClsdIndicator() == null), !(inc.getWaterIssueIndicator() == null), !(inc.getWorkReqNumber() == null) };
		
		int sizeCount = 0;
		for (int i = 0; i < whichs.length; i++)
			if (whichs[i]) sizeCount++;
		final int size = sizeCount;
		
		IncidentInfo[] info = new IncidentInfo[size];
		int index = 0, stepper = 0;
		
		final IncidentHelper me = new IncidentHelper();
		
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STR00Assess Notes", inc.getAssessNotes());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "ADR00Building Address", inc.getBuildingAddress());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STR00Bulding Name", inc.getBuildingName());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "OOC00Building Status", inc.getBuildingStatus());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STR00Building Type", inc.getBuildingType());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "DAT00Completion Date", inc.getCompltnDate());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Com Power Indicator", inc.getComPowerIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "NUM10Contact Phone Number", inc.getContactPhone());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STR00CRE Lead", inc.getCreLead());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Damage Indicator", inc.getDamageIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Elec Issue Closed Indicator", inc.getElecIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Elec Issue Indicator", inc.getElecIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Env Issue Closed Indicator", inc.getEnvIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00EnvIssue Indicator", inc.getEnvIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "NUM00Estimated Cap Cost", inc.getEstCapCost());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "NUM00Estimated Expense Cost", inc.getEstExpenseCost());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STR00Event Name", inc.getEventName());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Fence Gate Issue Closed Indicator", inc.getFenceGateIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Fence Gate Issue Indicator", inc.getFenceGateIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00General Issue Closed Indicator", inc.getGenIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00General Issue Indicator", inc.getGenIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "NUM05ZIP Code", inc.getGeoLoc());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Grounds Issue Closed Indicator", inc.getGroundsIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Grounds Issue Indicator", inc.getGroundsIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "DAT00Incident Completion Date", inc.getIncidentCompltnDate());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STR00Incident Notes", inc.getIncidentNotes());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "OOC00Incident Status", inc.getIncidentStatus());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "NUM04Incident Year", inc.getIncidentYear());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "DAT00Initial Report Date", inc.getInitialRptDate());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Mechanical Issue Closed Indicator", inc.getMechIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Mechanical Issue Indicator", inc.getMechIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Mob CO Indicator", inc.getMobCOIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00On Generator Indicator", inc.getOnGeneratorIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Other Issue Closed Indicator", inc.getOtherIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Other Issue Indicator", inc.getOtherIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Plumb Issue Closed Indicator", inc.getPlumbIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Plumb Issue Indicator", inc.getPlumbIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STR06PM ATTUID", inc.getPMAttuid());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "NUM04Record Number", inc.getRecNumber());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STR06Req ATTUID", inc.getReqATTUID());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Roofs Issue Closed Indicator", inc.getRoofsIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Roofs Issue Indicator", inc.getRoofsIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Safety Issue Closed Indicator", inc.getSafetyIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Safety Issue Indicator", inc.getSafetyIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STA02State", inc.getState());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "STR00Status Notes", inc.getStatusNotes());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Structural Issue Closed Indicator", inc.getStructIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Structural Issue Indicator", inc.getStructIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Unoccupiable Indicator", inc.getUnOccupiableIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Water Issue Closed Indicator", inc.getWaterIssueClsdIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "YON00Water Issue Indicator", inc.getWaterIssueIndicator());
		if (whichs[stepper++]) info[index++] = me.new IncidentInfo((stepper > 10 ? (stepper - 1)
																				: "0" + (stepper - 1)) + "NUM16Work Req Number", inc.getWorkReqNumber());
		
		return info;
	}
	
	public static void setFieldByLabel(Incident inc, int which, String newContents) {
		switch (which) {
			case 0:
				inc.setAssessNotes(newContents);
				break;
			case 1:
				inc.setBuildingAddress(newContents);
				break;
			case 2:
				inc.setBuildingName(newContents);
				break;
			case 3:
				inc.setBuildingStatus(newContents);
				break;
			case 4:
				inc.setBuildingType(newContents);
				break;
			case 5:
				inc.setCompltnDate(newContents);
				break;
			case 6:
				inc.setComPowerIndicator(newContents);
				break;
			case 7:
				inc.setContactPhone(newContents);
				break;
			case 8:
				inc.setCreLead(newContents);
				break;
			case 9:
				inc.setDamageIndicator(newContents);
				break;
			case 10:
				inc.setElecIssueClsdIndicator(newContents);
				break;
			case 11:
				inc.setElecIssueIndicator(newContents);
				break;
			case 12:
				inc.setEnvIssueClsdIndicator(newContents);
				break;
			case 13:
				inc.setEnvIssueIndicator(newContents);
				break;
			case 14:
				inc.setEstCapCost((int) Long.parseLong(newContents));
				break;
			case 15:
				inc.setEstExpenseCost((int) Long.parseLong(newContents));
				break;
			case 16:
				inc.setEventName(newContents);
				break;
			case 17:
				inc.setFenceGateIssueClsdIndicator(newContents);
				break;
			case 18:
				inc.setFenceGateIssueIndicator(newContents);
				break;
			case 19:
				inc.setGenIssueClsdIndicator(newContents);
				break;
			case 20:
				inc.setGenIssueIndicator(newContents);
				break;
			case 21:
				inc.setGeoLoc(Integer.parseInt(newContents));
				break;
			case 22:
				inc.setGroundsIssueClsdIndicator(newContents);
				break;
			case 23:
				inc.setGroundsIssueIndicator(newContents);
				break;
			case 24:
				inc.setIncidentCompltnDate(newContents);
				break;
			case 25:
				inc.setIncidentNotes(newContents);
				break;
			case 26:
				inc.setIncidentStatus(newContents);
				break;
			case 27:
				inc.setIncidentYear(Integer.parseInt(newContents));
				break;
			case 28:
				inc.setInitialRptDate(newContents);
				break;
			case 29:
				inc.setMechIssueClsdIndicator(newContents);
				break;
			case 30:
				inc.setMechIssueIndicator(newContents);
				break;
			case 31:
				inc.setMobCOIndicator(newContents);
				break;
			case 32:
				inc.setOnGeneratorIndicator(newContents);
				break;
			case 33:
				inc.setOtherIssueClsdIndicator(newContents);
				break;
			case 34:
				inc.setOtherIssueIndicator(newContents);
				break;
			case 35:
				inc.setPlumbIssueClsdIndicator(newContents);
				break;
			case 36:
				inc.setPlumbIssueIndicator(newContents);
				break;
			case 37:
				inc.setPMAttuid(newContents);
				break;
			// Rec number doesn't ever manually get set
			// case 38:
			// inc.setRecNumber((int) Long.parseLong(newContents));
			// break;
			case 39:
				inc.setReqATTUID(newContents);
				break;
			case 40:
				inc.setRoofsIssueClsdIndicator(newContents);
				break;
			case 41:
				inc.setRoofsIssueIndicator(newContents);
				break;
			case 42:
				inc.setSafetyIssueClsdIndicator(newContents);
				break;
			case 43:
				inc.setSafetyIssueIndicator(newContents);
				break;
			case 44:
				inc.setState(newContents);
				break;
			case 45:
				inc.setStatusNotes(newContents);
				break;
			case 46:
				inc.setStructIssueClsdIndicator(newContents);
				break;
			case 47:
				inc.setStructIssueIndicator(newContents);
				break;
			case 48:
				inc.setUnOccupiableIndicator(newContents);
				break;
			case 49:
				inc.setWaterIssueClsdIndicator(newContents);
				break;
			case 50:
				inc.setWaterIssueIndicator(newContents);
				break;
			case 51:
				inc.setWorkReqNumber(newContents);
				break;
			default:
				break;
		}
	}
	
	public static String isValidInfoForField(Incident inc, int which, String newContents) {
		String message = "", oldReportDate;
		int incidentYear, incidentCompletionYear;
		Calendar initialReportDate;
		switch (which) {
		// case 0:
		// // Assess Notes - anything goes
		// break;
		// case 1:
		// // Building Address - anything goes
		// break;
		// case 2:
		// // Building Name - anything goes
		// break;
		// case 3:
		// // Building Status - not typeable
		// break;
		// case 4:
		// // Building Type - anything goes
		// break;
			case 5:
				/**
				 * Rules: completion date cannot be before initial report date
				 */
				
				initialReportDate = Calendar.getInstance();
				oldReportDate = inc.getInitialRptDate();
				initialReportDate.set(Integer.parseInt(oldReportDate.substring(0, 4)), Integer.parseInt(oldReportDate.substring(5, 7)) - 1, Integer.parseInt(oldReportDate.substring(8)));
				
				Calendar completionDate = Calendar.getInstance();
				completionDate.set(Integer.parseInt(newContents.substring(0, 4)), Integer.parseInt(newContents.substring(5, 7)) - 1, Integer.parseInt(newContents.substring(8)));
				
				if (initialReportDate.after(completionDate)) {
					message = "The completion date cannot be before the initial report date!";
				}
				break;
			// case 6:
			// // inc.setComPowerIndicator(newContents);
			// break;
			case 7:
				// Contact Phone - match format ###-###-####
				if (!newContents.matches("[1-9][0-9]{2}-[0-9]{3}-[0-9]{4}")) message = "The supplied number is not valid. Please type a valid ten digit number in the form ##########.";
				break;
			// case 8:
			// // CreLead(newContents);
			// break;
			// case 9:
			// // inc.setDamageIndicator(newContents);
			// break;
			// case 10:
			// // inc.setElecIssueClsdIndicator(newContents);
			// break;
			// case 11:
			// // inc.setElecIssueIndicator(newContents);
			// break;
			// case 12:
			// // inc.setEnvIssueClsdIndicator(newContents);
			// break;
			// case 13:
			// // inc.setEnvIssueIndicator(newContents);
			// break;
			// case 14:
			// // inc.setEstCapCost((int) Long.parseLong(newContents));
			// break;
			// case 15:
			// // inc.setEstExpenseCost((int) Long.parseLong(newContents));
			// break;
			// case 16:
			// // inc.setEventName(newContents);
			// break;
			// case 17:
			// // inc.setFenceGateIssueClsdIndicator(newContents);
			// break;
			// case 18:
			// // inc.setFenceGateIssueIndicator(newContents);
			// break;
			// case 19:
			// // inc.setGenIssueClsdIndicator(newContents);
			// break;
			// case 20:
			// // inc.setGenIssueIndicator(newContents);
			// break;
			case 21:
				// GeoLoc
				if (!newContents.equals("27685") && !newContents.equals("74685") && !newContents.equals("88534") && !newContents.equals("27689") && !newContents.equals("63784") && !newContents.equals("78549")) message = "New ZIP must be one of: 27685, 74685, 88534, 27689, 63784, or 78549 to be successfully updated!";
				break;
			// case 22:
			// // inc.setGroundsIssueClsdIndicator(newContents);
			// break;
			// case 23:
			// // inc.setGroundsIssueIndicator(newContents);
			// break;
			case 24:
				/**
				 * Rules: IncidentCompltnDate must not be less than IncidentYear
				 */
				// Incident Completion Date
				incidentYear = inc.getIncidentYear();
				if (incidentYear != 0) {
					incidentCompletionYear = Integer.parseInt(newContents.substring(0, 4));
					if (incidentCompletionYear < incidentYear) message = "The incident completion date cannot be before the incident year!";
				}
				break;
			// case 25:
			// // inc.setIncidentNotes(newContents);
			// break;
			// case 26:
			// // inc.setIncidentStatus(newContents);
			// break;
			case 27:
				/**
				 * Rules:
				 * 
				 * Incident year must not be after incident completion date
				 * IncidentYear must not be greater than initial report date
				 */
				// inc.setIncidentYear((int) Long.parseLong(newContents));
				incidentCompletionYear = Integer.parseInt(inc.getIncidentCompltnDate().substring(0, 4));
				if (incidentCompletionYear != 0) {
					incidentYear = Integer.parseInt(newContents);
					if (incidentCompletionYear < incidentYear) message = "The incident year cannot be after the incident completion date!";
					if (incidentYear > Calendar.getInstance().get(Calendar.YEAR)) message = "The incident year cannot be in the future!";
				} else {
					initialReportDate = Calendar.getInstance();
					oldReportDate = inc.getInitialRptDate();
					initialReportDate.set(Integer.parseInt(oldReportDate.substring(0, 4)), Integer.parseInt(oldReportDate.substring(5, 7)) - 1, Integer.parseInt(oldReportDate.substring(8)));
					if (initialReportDate.after(Calendar.getInstance())) {
						message = "The incident year cannot be after the initial report date!";
					}
				}
				break;
			case 28:
				/**
				 * Rules:
				 * 
				 * Initial report date has to be before or on today
				 * Initial report date must not be after incident year
				 */
				// inc.setInitialRptDate(newContents);
				initialReportDate = Calendar.getInstance();
				initialReportDate.set(Integer.parseInt(newContents.substring(0, 4)), Integer.parseInt(newContents.substring(5, 7)) - 1, Integer.parseInt(newContents.substring(8)));
				if (initialReportDate.after(Calendar.getInstance())) {
					message = "The initial report date cannot be in the future!!";
				} else {
					incidentYear = inc.getIncidentYear();
					if (incidentYear != 0) {
						int incidentReportYear = Integer.parseInt(newContents.substring(0, 4));
						if (incidentReportYear > incidentYear) message = "The initial report date cannot be before the incident year!";
					}
				}
				break;
			// case 29:
			// // inc.setMechIssueClsdIndicator(newContents);
			// break;
			// case 30:
			// // inc.setMechIssueIndicator(newContents);
			// break;
			// case 31:
			// // inc.setMobCOIndicator(newContents);
			// break;
			// case 32:
			// // inc.setOnGeneratorIndicator(newContents);
			// break;
			// case 33:
			// // inc.setOtherIssueClsdIndicator(newContents);
			// break;
			// case 34:
			// // inc.setOtherIssueIndicator(newContents);
			// break;
			// case 35:
			// // inc.setPlumbIssueClsdIndicator(newContents);
			// break;
			// case 36:
			// // inc.setPlumbIssueIndicator(newContents);
			// break;
			case 37:
				// inc.setPMAttuid(newContents);
				if (!newContents.matches("[a-zA-Z]{2}[0-9]{3}[a-zA-Z0-9]")) message = "The supplied ATTUID is not valid.";
				break;
			// Rec number doesn't ever manually get set
			// case 38:
			// inc.setRecNumber((int) Long.parseLong(newContents));
			// break;
			case 39:
				// inc.setReqATTUID(newContents);
				if (!newContents.matches("[a-zA-Z]{2}[0-9]{3}[a-zA-Z0-9]")) message = "The supplied ATTUID is not valid.";
				break;
			// case 40:
			// // inc.setRoofsIssueClsdIndicator(newContents);
			// break;
			// case 41:
			// // inc.setRoofsIssueIndicator(newContents);
			// break;
			// case 42:
			// // inc.setSafetyIssueClsdIndicator(newContents);
			// break;
			// case 43:
			// // inc.setSafetyIssueIndicator(newContents);
			// break;
			// case 44:
			// // inc.setState(newContents);
			// break;
			// case 45:
			// // inc.setStatusNotes(newContents);
			// break;
			// case 46:
			// // inc.setStructIssueClsdIndicator(newContents);
			// break;
			// case 47:
			// // inc.setStructIssueIndicator(newContents);
			// break;
			// case 48:
			// // inc.setUnOccupiableIndicator(newContents);
			// break;
			// case 49:
			// // inc.setWaterIssueClsdIndicator(newContents);
			// break;
			// case 50:
			// // inc.setWaterIssueIndicator(newContents);
			// break;
			case 51:
				// inc.setWorkReqNumber(newContents);
				if (!newContents.matches("[0-9]{16}")) message = "The supplied work request number is not valid. It should be a 16 digit numerical id";
				break;
			default:
				break;
		}
		return message;
	}
	
	/**
	 * The class that defines the object that contains each field of
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
