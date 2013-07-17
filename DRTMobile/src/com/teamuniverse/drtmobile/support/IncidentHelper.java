package com.teamuniverse.drtmobile.support;

import java.util.Calendar;

import android.app.Activity;

import com.att.intern.webservice.Incident;
import com.att.intern.webservice.Webservice;

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
	
	public static boolean[] isSet(Incident inc) {
		return new boolean[] { !(inc.getAssessNotes() == null), !(inc.getBuildingAddress() == null), !(inc.getBuildingName() == null), !(inc.getBuildingStatus() == null), !(inc.getBuildingType() == null), !(inc.getCompltnDate() == null), !(inc.getComPowerIndicator() == null), !(inc.getContactPhone() == null), !(inc.getCreLead() == null), !(inc.getDamageIndicator() == null), !(inc.getElecIssueClsdIndicator() == null), !(inc.getElecIssueIndicator() == null), !(inc.getEnvIssueClsdIndicator() == null), !(inc.getEnvIssueIndicator() == null), !(inc.getEstCapCost() == 0), !(inc.getEstExpenseCost() == 0), !(inc.getEventName() == null), !(inc.getFenceGateIssueClsdIndicator() == null), !(inc.getFenceGateIssueIndicator() == null), !(inc.getGenIssueClsdIndicator() == null), !(inc.getGenIssueIndicator() == null), !(inc.getGroundsIssueClsdIndicator() == null), !(inc.getGroundsIssueIndicator() == null), !(inc.getIncidentCompltnDate() == null), !(inc.getIncidentNotes() == null), !(inc.getIncidentStatus() == null), !(inc.getIncidentYear() == 0), !(inc.getInitialRptDate() == null), !(inc.getMechIssueClsdIndicator() == null), !(inc.getMechIssueIndicator() == null), !(inc.getMobCOIndicator() == null), !(inc.getOnGeneratorIndicator() == null), !(inc.getOtherIssueClsdIndicator() == null), !(inc.getOtherIssueIndicator() == null), !(inc.getPlumbIssueClsdIndicator() == null), !(inc.getPlumbIssueIndicator() == null), !(inc.getPMAttuid() == null), !(inc.getRecNumber() == 0), !(inc.getReqATTUID() == null), !(inc.getRoofsIssueClsdIndicator() == null), !(inc.getRoofsIssueIndicator() == null), !(inc.getSafetyIssueClsdIndicator() == null), !(inc.getSafetyIssueIndicator() == null), !(inc.getState() == null), !(inc.getStatusNotes() == null), !(inc.getStructIssueClsdIndicator() == null), !(inc.getStructIssueIndicator() == null), !(inc.getUnOccupiableIndicator() == null), !(inc.getWaterIssueClsdIndicator() == null), !(inc.getWaterIssueIndicator() == null), !(inc.getWorkReqNumber() == null), !(inc.getGeoLoc() == 0) };
	}
	
	public static IncidentInfo getSingleInfo(Incident inc, int which) {
		Object value = null;
		switch (which) {
			case IncidentInfo.ASSESSMENT_NOTES:
				value = inc.getAssessNotes();
				break;
			case IncidentInfo.BUILDING_ADDRESS:
				value = inc.getBuildingAddress();
				break;
			case IncidentInfo.BUILDING_STATUS:
				value = inc.getBuildingStatus();
				break;
			case IncidentInfo.BUILDING_TYPE:
				value = inc.getBuildingType();
				break;
			case IncidentInfo.BUILDING_NAME:
				value = inc.getBuildingName();
				break;
			case IncidentInfo.COMMUNICATIONS_POWER_INDICATOR:
				value = inc.getComPowerIndicator();
				break;
			case IncidentInfo.COMPLETION_DATE:
				value = inc.getCompltnDate();
				break;
			case IncidentInfo.CONTACT_PHONE_NUMBER:
				value = inc.getContactPhone();
				break;
			case IncidentInfo.CRE_LEAD:
				value = inc.getCreLead();
				break;
			case IncidentInfo.DAMAGE_INDICATOR:
				value = inc.getDamageIndicator();
				break;
			case IncidentInfo.ELECETRICAL_ISSUE_CLOSED_INDICATOR:
				value = inc.getElecIssueClsdIndicator();
				break;
			case IncidentInfo.ELECTRICAL_ISSUE_INDICATOR:
				value = inc.getElecIssueIndicator();
				break;
			case IncidentInfo.ENVIRONMENTAL_ISSUE_CLOSED_INDICATOR:
				value = inc.getEnvIssueClsdIndicator();
				break;
			case IncidentInfo.ENVIRONMENTAL_ISSUE_INDICATOR:
				value = inc.getEnvIssueIndicator();
				break;
			case IncidentInfo.ESTIMATED_CAP_COST:
				value = inc.getEstCapCost();
				break;
			case IncidentInfo.ESTIMATED_EXPENSE_COST:
				value = inc.getEstExpenseCost();
				break;
			case IncidentInfo.EVENT_NAME:
				value = inc.getEventName();
				break;
			case IncidentInfo.FENCE_GATE_ISSUE_CLOSED_INDICATOR:
				value = inc.getFenceGateIssueClsdIndicator();
				break;
			case IncidentInfo.FENCE_GATE_ISSUE_INDICATOR:
				value = inc.getFenceGateIssueIndicator();
				break;
			case IncidentInfo.GENERAL_ISSUE_CLOSED_INDICATOR:
				value = inc.getGenIssueClsdIndicator();
				break;
			case IncidentInfo.GENERAL_ISSUE_INDICATOR:
				value = inc.getGenIssueIndicator();
				break;
			case IncidentInfo.GROUNDS_ISSUE_CLOSED_INDICATOR:
				value = inc.getGroundsIssueClsdIndicator();
				break;
			case IncidentInfo.GROUNDS_ISSUE_INDICATOR:
				value = inc.getGroundsIssueIndicator();
				break;
			case IncidentInfo.INCIDENT_COMPLETION_DATE:
				value = inc.getIncidentCompltnDate();
				break;
			case IncidentInfo.INCIDENT_NOTES:
				value = inc.getIncidentNotes();
				break;
			case IncidentInfo.INCIDENT_STATUS:
				value = inc.getIncidentStatus();
				break;
			case IncidentInfo.INCIDENT_YEAR:
				value = inc.getIncidentYear();
				break;
			case IncidentInfo.INITIAL_REPORT_DATE:
				value = inc.getInitialRptDate();
				break;
			case IncidentInfo.MECHANICAL_ISSUE_CLOSED_INDICATOR:
				value = inc.getMechIssueClsdIndicator();
				break;
			case IncidentInfo.MECHANICAL_ISSUE_INDICATOR:
				value = inc.getMechIssueIndicator();
				break;
			case IncidentInfo.MOB_CO_INDICATOR:
				value = inc.getMobCOIndicator();
				break;
			case IncidentInfo.ON_GENERATOR_INDICATOR:
				value = inc.getOnGeneratorIndicator();
				break;
			case IncidentInfo.OTHER_ISSUE_CLOSED_INDICATOR:
				value = inc.getOtherIssueClsdIndicator();
				break;
			case IncidentInfo.OTHER_ISSUE_INDICATOR:
				value = inc.getOtherIssueIndicator();
				break;
			case IncidentInfo.PLUMB_ISSUE_CLOSED_INDICATOR:
				value = inc.getPlumbIssueClsdIndicator();
				break;
			case IncidentInfo.PLUMB_ISSUE_INDICATOR:
				value = inc.getPlumbIssueIndicator();
				break;
			case IncidentInfo.PM_ATTUID:
				value = inc.getPMAttuid();
				break;
			case IncidentInfo.RECORD_NUMBER:
				value = inc.getRecNumber();
				break;
			case IncidentInfo.REQ_ATTUID:
				value = inc.getReqATTUID();
				break;
			case IncidentInfo.ROOFS_ISSUE_CLOSED_INDICATOR:
				value = inc.getRoofsIssueClsdIndicator();
				break;
			case IncidentInfo.ROOFS_ISSUE_INDICATOR:
				value = inc.getRoofsIssueIndicator();
				break;
			case IncidentInfo.SAFETY_ISSUE_CLOSED_INDICATOR:
				value = inc.getSafetyIssueClsdIndicator();
				break;
			case IncidentInfo.SAFETY_ISSUE_INDICATOR:
				value = inc.getSafetyIssueIndicator();
				break;
			case IncidentInfo.STATE:
				value = inc.getState();
				break;
			case IncidentInfo.STATUS_NOTES:
				value = inc.getStatusNotes();
				break;
			case IncidentInfo.STRUCTURAL_ISSUE_CLOSED_INDICATOR:
				value = inc.getStructIssueClsdIndicator();
				break;
			case IncidentInfo.STRUCTURAL_ISSUE_INDICATOR:
				value = inc.getStructIssueIndicator();
				break;
			case IncidentInfo.UNOCCUPIABLE_INDICATOR:
				value = inc.getUnOccupiableIndicator();
				break;
			case IncidentInfo.WATER_ISSUE_CLOSED_INDICATOR:
				value = inc.getWaterIssueClsdIndicator();
				break;
			case IncidentInfo.WATER_ISSUE_INDICATOR:
				value = inc.getWaterIssueIndicator();
				break;
			case IncidentInfo.WORK_REQUEST_NUMBER:
				value = inc.getWorkReqNumber();
				break;
			case IncidentInfo.ZIP_CODE:
				value = inc.getGeoLoc();
				break;
			default:
				return new IncidentInfo();
		}
		return new IncidentInfo(which, value);
	}
	
	/**
	 * The method that extracts all of the available infos from the incident
	 * that calls it.
	 * 
	 * @return An IncidentInfo[] that has all of the informations.
	 */
	public static IncidentInfo[] getInfos(Incident inc) {
		boolean[] whichs = isSet(inc);
		
		int sizeCount = 0;
		for (int i = 0; i < whichs.length; i++)
			if (whichs[i]) sizeCount++;
		final int size = sizeCount;
		
		IncidentInfo[] info = new IncidentInfo[size];
		
		for (int i = 0, counter = 0; i < whichs.length; i++)
			if (whichs[i]) info[counter++] = getSingleInfo(inc, i);
		
		return info;
	}
	
	public static Incident init(Activity m, int zip) {
		String[] startValues = new Webservice(m).getGLCInfo().get(zip).split("~");
		Incident inc = new Incident();
		inc.setAssessNotes("");
		inc.setBuildingAddress(startValues[3]);
		inc.setBuildingName(startValues[2]);
		inc.setBuildingStatus("");
		inc.setBuildingType("");
		inc.setCompltnDate("");
		inc.setComPowerIndicator("");
		inc.setContactPhone("");
		inc.setCreLead("");
		inc.setDamageIndicator("");
		inc.setElecIssueClsdIndicator("");
		inc.setElecIssueIndicator("");
		inc.setEnvIssueClsdIndicator("");
		inc.setEnvIssueIndicator("");
		inc.setEstCapCost(0);
		inc.setEstExpenseCost(0);
		inc.setEventName("");
		inc.setFenceGateIssueClsdIndicator("");
		inc.setFenceGateIssueIndicator("");
		inc.setGenIssueClsdIndicator("");
		inc.setGenIssueIndicator("");
		inc.setGeoLoc(zip);
		inc.setGroundsIssueClsdIndicator("");
		inc.setGroundsIssueIndicator("");
		inc.setIncidentCompltnDate("");
		inc.setIncidentNotes("");
		inc.setIncidentStatus("Open");
		inc.setIncidentYear(0);
		inc.setInitialRptDate("");
		inc.setMechIssueClsdIndicator("");
		inc.setMechIssueIndicator("");
		inc.setMobCOIndicator("");
		inc.setOnGeneratorIndicator("");
		inc.setOtherIssueClsdIndicator("");
		inc.setOtherIssueIndicator("");
		inc.setPlumbIssueClsdIndicator("");
		inc.setPlumbIssueIndicator("");
		inc.setPMAttuid(startValues[1]);
		inc.setReqATTUID("");
		inc.setRoofsIssueClsdIndicator("");
		inc.setRoofsIssueIndicator("");
		inc.setSafetyIssueClsdIndicator("");
		inc.setSafetyIssueIndicator("");
		inc.setState(startValues[0]);
		inc.setStatusNotes("");
		inc.setStructIssueClsdIndicator("");
		inc.setStructIssueIndicator("");
		inc.setUnOccupiableIndicator("");
		inc.setWaterIssueClsdIndicator("");
		inc.setWaterIssueIndicator("");
		inc.setWorkReqNumber("");
		return inc;
	}
	
	public static void setFieldByLabel(Incident inc, int which, String newContents) {
		switch (which) {
			case IncidentInfo.ASSESSMENT_NOTES:
				inc.setAssessNotes(newContents);
				break;
			case IncidentInfo.BUILDING_ADDRESS:
				inc.setBuildingAddress(newContents);
				break;
			case IncidentInfo.BUILDING_NAME:
				inc.setBuildingName(newContents);
				break;
			case IncidentInfo.BUILDING_STATUS:
				inc.setBuildingStatus(newContents);
				break;
			case IncidentInfo.BUILDING_TYPE:
				inc.setBuildingType(newContents);
				break;
			case IncidentInfo.COMPLETION_DATE:
				inc.setCompltnDate(newContents);
				break;
			case IncidentInfo.COMMUNICATIONS_POWER_INDICATOR:
				inc.setComPowerIndicator(newContents);
				break;
			case IncidentInfo.CONTACT_PHONE_NUMBER:
				inc.setContactPhone(newContents);
				break;
			case IncidentInfo.CRE_LEAD:
				inc.setCreLead(newContents);
				break;
			case IncidentInfo.DAMAGE_INDICATOR:
				inc.setDamageIndicator(newContents);
				break;
			case IncidentInfo.ELECETRICAL_ISSUE_CLOSED_INDICATOR:
				inc.setElecIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.ELECTRICAL_ISSUE_INDICATOR:
				inc.setElecIssueIndicator(newContents);
				break;
			case IncidentInfo.ENVIRONMENTAL_ISSUE_CLOSED_INDICATOR:
				inc.setEnvIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.ENVIRONMENTAL_ISSUE_INDICATOR:
				inc.setEnvIssueIndicator(newContents);
				break;
			case IncidentInfo.ESTIMATED_CAP_COST:
				inc.setEstCapCost((int) Long.parseLong(newContents));
				break;
			case IncidentInfo.ESTIMATED_EXPENSE_COST:
				inc.setEstExpenseCost((int) Long.parseLong(newContents));
				break;
			case IncidentInfo.EVENT_NAME:
				inc.setEventName(newContents);
				break;
			case IncidentInfo.FENCE_GATE_ISSUE_CLOSED_INDICATOR:
				inc.setFenceGateIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.FENCE_GATE_ISSUE_INDICATOR:
				inc.setFenceGateIssueIndicator(newContents);
				break;
			case IncidentInfo.GENERAL_ISSUE_CLOSED_INDICATOR:
				inc.setGenIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.GENERAL_ISSUE_INDICATOR:
				inc.setGenIssueIndicator(newContents);
				break;
			case IncidentInfo.ZIP_CODE:
				inc.setGeoLoc(Integer.parseInt(newContents));
				break;
			case IncidentInfo.GROUNDS_ISSUE_CLOSED_INDICATOR:
				inc.setGroundsIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.GROUNDS_ISSUE_INDICATOR:
				inc.setGroundsIssueIndicator(newContents);
				break;
			case IncidentInfo.INCIDENT_COMPLETION_DATE:
				inc.setIncidentCompltnDate(newContents);
				break;
			case IncidentInfo.INCIDENT_NOTES:
				inc.setIncidentNotes(newContents);
				break;
			case IncidentInfo.INCIDENT_STATUS:
				inc.setIncidentStatus(newContents);
				break;
			case IncidentInfo.INCIDENT_YEAR:
				inc.setIncidentYear(Integer.parseInt(newContents));
				break;
			case IncidentInfo.INITIAL_REPORT_DATE:
				inc.setInitialRptDate(newContents);
				break;
			case IncidentInfo.MECHANICAL_ISSUE_CLOSED_INDICATOR:
				inc.setMechIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.MECHANICAL_ISSUE_INDICATOR:
				inc.setMechIssueIndicator(newContents);
				break;
			case IncidentInfo.MOB_CO_INDICATOR:
				inc.setMobCOIndicator(newContents);
				break;
			case IncidentInfo.ON_GENERATOR_INDICATOR:
				inc.setOnGeneratorIndicator(newContents);
				break;
			case IncidentInfo.OTHER_ISSUE_CLOSED_INDICATOR:
				inc.setOtherIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.OTHER_ISSUE_INDICATOR:
				inc.setOtherIssueIndicator(newContents);
				break;
			case IncidentInfo.PLUMB_ISSUE_CLOSED_INDICATOR:
				inc.setPlumbIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.PLUMB_ISSUE_INDICATOR:
				inc.setPlumbIssueIndicator(newContents);
				break;
			case IncidentInfo.PM_ATTUID:
				inc.setPMAttuid(newContents);
				break;
			// // Rec number doesn't ever manually get set
			// case IncidentInfo.RECORD_NUMBER:
			// inc.setRecNumber((int) Long.parseLong(newContents));
			// break;
			case IncidentInfo.REQ_ATTUID:
				inc.setReqATTUID(newContents);
				break;
			case IncidentInfo.ROOFS_ISSUE_CLOSED_INDICATOR:
				inc.setRoofsIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.ROOFS_ISSUE_INDICATOR:
				inc.setRoofsIssueIndicator(newContents);
				break;
			case IncidentInfo.SAFETY_ISSUE_CLOSED_INDICATOR:
				inc.setSafetyIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.SAFETY_ISSUE_INDICATOR:
				inc.setSafetyIssueIndicator(newContents);
				break;
			case IncidentInfo.STATE:
				inc.setState(newContents);
				break;
			case IncidentInfo.STATUS_NOTES:
				inc.setStatusNotes(newContents);
				break;
			case IncidentInfo.STRUCTURAL_ISSUE_CLOSED_INDICATOR:
				inc.setStructIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.STRUCTURAL_ISSUE_INDICATOR:
				inc.setStructIssueIndicator(newContents);
				break;
			case IncidentInfo.UNOCCUPIABLE_INDICATOR:
				inc.setUnOccupiableIndicator(newContents);
				break;
			case IncidentInfo.WATER_ISSUE_CLOSED_INDICATOR:
				inc.setWaterIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.WATER_ISSUE_INDICATOR:
				inc.setWaterIssueIndicator(newContents);
				break;
			case IncidentInfo.WORK_REQUEST_NUMBER:
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
			case IncidentInfo.COMPLETION_DATE:
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
			case IncidentInfo.CONTACT_PHONE_NUMBER:
				if (!newContents.matches("[1-9][0-9]{2}-[0-9]{3}-[0-9]{4}")) message = "The supplied number is not valid. Please type a valid ten digit number in the form ##########.";
				break;
			case IncidentInfo.ZIP_CODE:
				if (!newContents.equals("27685") && !newContents.equals("74685") && !newContents.equals("88534") && !newContents.equals("27689") && !newContents.equals("63784") && !newContents.equals("78549")) message = "New ZIP must be one of: 27685, 74685, 88534, 27689, 63784, or 78549 to be successfully updated!";
				break;
			case IncidentInfo.INCIDENT_COMPLETION_DATE:
				/**
				 * Rules: IncidentCompltnDate must not be less than IncidentYear
				 */
				incidentYear = inc.getIncidentYear();
				if (incidentYear != 0) {
					incidentCompletionYear = Integer.parseInt(newContents.substring(0, 4));
					if (incidentCompletionYear < incidentYear) message = "The incident completion date cannot be before the incident year!";
				}
				break;
			case IncidentInfo.INCIDENT_YEAR:
				/**
				 * Rules:
				 * 
				 * Incident year must not be after incident completion date
				 * IncidentYear must not be greater than initial report date
				 */
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
			case IncidentInfo.INITIAL_REPORT_DATE:
				/**
				 * Rules:
				 * 
				 * Initial report date has to be before or on today
				 * Initial report date must not be after incident year
				 */
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
			case IncidentInfo.PM_ATTUID:
				if (!newContents.matches("[a-zA-Z]{2}[0-9]{3}[a-zA-Z0-9]")) message = "The supplied ATTUID is not valid.";
				break;
			case IncidentInfo.RECORD_NUMBER:
				// TODO add check
				break;
			case IncidentInfo.REQ_ATTUID:
				if (!newContents.matches("[a-zA-Z]{2}[0-9]{3}[a-zA-Z0-9]")) message = "The supplied ATTUID is not valid.";
				break;
			case IncidentInfo.WORK_REQUEST_NUMBER:
				if (!newContents.matches("[0-9]{16}")) message = "The supplied work request number is not valid. It should be a 16 digit numerical id";
				break;
			default:
				break;
		}
		return message;
	}
}