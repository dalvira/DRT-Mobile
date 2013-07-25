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
	
	public static boolean[] isSet(Incident inc, int[] order) {
		boolean[] whichs = new boolean[52];
		for (int i = 0; i < order.length; i++)
			whichs[i] = isSet(inc, order[i]);
		return whichs;
	}
	
	private static boolean isSet(Incident inc, int id) {
		switch (id) {
			case IncidentInfo.ESTIMATED_CAP_COST:
			case IncidentInfo.ESTIMATED_EXPENSE_COST:
			case IncidentInfo.ZIP_CODE:
			case IncidentInfo.INCIDENT_YEAR:
			case IncidentInfo.RECORD_NUMBER:
				return (Integer) getSingleInfo(inc, id).getValue() != 0;
			default:
				return getSingleInfo(inc, id).getValue() != null;
		}
	}
	
	public static int getIndicatorChild(int which) {
		switch (which) {
			case IncidentInfo.ELECTRICAL_ISSUE_INDICATOR:
				return IncidentInfo.ELECTRICAL_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.ENVIRONMENTAL_ISSUE_INDICATOR:
				return IncidentInfo.ENVIRONMENTAL_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.FENCE_GATE_ISSUE_INDICATOR:
				return IncidentInfo.FENCE_GATE_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.GENERATOR_ISSUE_INDICATOR:
				return IncidentInfo.GENERATOR_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.GROUNDS_ISSUE_INDICATOR:
				return IncidentInfo.GROUNDS_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.MECHANICAL_ISSUE_INDICATOR:
				return IncidentInfo.MECHANICAL_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.PLUMB_ISSUE_INDICATOR:
				return IncidentInfo.PLUMB_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.ROOFS_ISSUE_INDICATOR:
				return IncidentInfo.ROOFS_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.SAFETY_ISSUE_INDICATOR:
				return IncidentInfo.SAFETY_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.STRUCTURAL_ISSUE_INDICATOR:
				return IncidentInfo.STRUCTURAL_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.WATER_ISSUE_INDICATOR:
				return IncidentInfo.WATER_ISSUE_CLOSED_INDICATOR;
			case IncidentInfo.OTHER_ISSUE_INDICATOR:
				return IncidentInfo.OTHER_ISSUE_CLOSED_INDICATOR;
			default:
				return -1;
		}
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
			case IncidentInfo.COMMERCIAL_POWER_INDICATOR:
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
			case IncidentInfo.ELECTRICAL_ISSUE_CLOSED_INDICATOR:
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
			case IncidentInfo.GENERATOR_ISSUE_CLOSED_INDICATOR:
				value = inc.getGenIssueClsdIndicator();
				break;
			case IncidentInfo.GENERATOR_ISSUE_INDICATOR:
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
			case IncidentInfo.MOBILITY_CO_INDICATOR:
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
			case IncidentInfo.REQUESTOR_ATTUID:
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
	 * that is passed.
	 * 
	 * @param inc
	 *            The incident from which to extract data.
	 * @param ADD_ORDER
	 *            Either IncidentHelper.ADD_ORDER or IncidentHelper.UPDATE_ORDER
	 * @return An IncidentInfo[] that has all of the informations.
	 */
	public static IncidentInfo[] getInfos(Incident inc, int[] order) {
		boolean[] whichs = isSet(inc, order);
		
		int sizeCount = 0;
		for (int i = 0; i < whichs.length; i++)
			if (whichs[i]) sizeCount++;
		final int size = sizeCount;
		
		IncidentInfo[] info = new IncidentInfo[size];
		
		for (int i = 0, counter = 0; i < whichs.length; i++)
			if (whichs[i]) info[counter++] = getSingleInfo(inc, order[i]);
		
		return info;
	}
	
	public static Incident init(Activity m, int zip) {
		String[] startValues = new Webservice(m).getGLCInfo().get(zip).split("~");
		Incident inc = new Incident();
		inc.setAssessNotes("");
		inc.setBuildingAddress(startValues[3]);
		inc.setBuildingName(startValues[2]);
		inc.setBuildingStatus("Open");
		inc.setBuildingType("ADM");
		inc.setCompltnDate(Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1 > 9 ? ""
																																: "0") + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 9 ? ""
																																																														: "0") + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		inc.setComPowerIndicator("Y");
		inc.setContactPhone("");
		inc.setCreLead("PM");
		inc.setDamageIndicator("N");
		inc.setElecIssueClsdIndicator("N");
		inc.setElecIssueIndicator("N");
		inc.setEnvIssueClsdIndicator("N");
		inc.setEnvIssueIndicator("N");
		inc.setEstCapCost(0);
		inc.setEstExpenseCost(0);
		inc.setEventName("Type or Name");
		inc.setFenceGateIssueClsdIndicator("N");
		inc.setFenceGateIssueIndicator("N");
		inc.setGenIssueClsdIndicator("");
		inc.setGenIssueIndicator("");
		inc.setGeoLoc(zip);
		inc.setGroundsIssueClsdIndicator("N");
		inc.setGroundsIssueIndicator("N");
		inc.setIncidentCompltnDate(Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1 > 9 ? ""
																																		: "0") + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 9 ? ""
																																																																: "0") + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		inc.setIncidentNotes("");
		inc.setIncidentStatus("Open");
		inc.setIncidentYear(Calendar.getInstance().get(Calendar.YEAR));
		inc.setInitialRptDate(Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1 > 9	? ""
																																	: "0") + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 9 ? ""
																																																															: "0") + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		inc.setMechIssueClsdIndicator("N");
		inc.setMechIssueIndicator("N");
		inc.setMobCOIndicator("N");
		inc.setOnGeneratorIndicator("N");
		inc.setOtherIssueClsdIndicator("N");
		inc.setOtherIssueIndicator("N");
		inc.setPlumbIssueClsdIndicator("N");
		inc.setPlumbIssueIndicator("N");
		inc.setPMAttuid(startValues[1]);
		DatabaseManager db = new DatabaseManager(m);
		inc.setReqATTUID(db.sessionGet("attuid"));
		db.close();
		inc.setRoofsIssueClsdIndicator("N");
		inc.setRoofsIssueIndicator("N");
		inc.setSafetyIssueClsdIndicator("N");
		inc.setSafetyIssueIndicator("N");
		inc.setState(startValues[0]);
		inc.setStatusNotes("");
		inc.setStructIssueClsdIndicator("N");
		inc.setStructIssueIndicator("N");
		inc.setUnOccupiableIndicator("N");
		inc.setWaterIssueClsdIndicator("N");
		inc.setWaterIssueIndicator("N");
		inc.setWorkReqNumber("");
		return inc;
	}
	
	public static void setFieldById(Incident inc, int which, String newContents) {
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
			case IncidentInfo.COMMERCIAL_POWER_INDICATOR:
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
			case IncidentInfo.ELECTRICAL_ISSUE_CLOSED_INDICATOR:
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
			case IncidentInfo.GENERATOR_ISSUE_CLOSED_INDICATOR:
				inc.setGenIssueClsdIndicator(newContents);
				break;
			case IncidentInfo.GENERATOR_ISSUE_INDICATOR:
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
			case IncidentInfo.MOBILITY_CO_INDICATOR:
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
			case IncidentInfo.REQUESTOR_ATTUID:
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
		String message = "";
		
		String alreadySetInitialReportDate;
		int incidentYear, incidentCompletionYear;
		Calendar initialReportDate;
		
		switch (which) {
			case IncidentInfo.EVENT_NAME:
				if (newContents.equals("")) message = "An event name or storm type must be picked!";
				break;
			case IncidentInfo.COMPLETION_DATE:
				/** Completion date cannot be before initial report date */
				initialReportDate = Calendar.getInstance();
				alreadySetInitialReportDate = inc.getInitialRptDate();
				if (alreadySetInitialReportDate.length() == 10) initialReportDate.set(Integer.parseInt(alreadySetInitialReportDate.substring(0, 4)), Integer.parseInt(alreadySetInitialReportDate.substring(5, 7)) - 1, Integer.parseInt(alreadySetInitialReportDate.substring(8)));
				
				Calendar completionDate = Calendar.getInstance();
				if (newContents.length() == 10) completionDate.set(Integer.parseInt(newContents.substring(0, 4)), Integer.parseInt(newContents.substring(5, 7)) - 1, Integer.parseInt(newContents.substring(8)));
				
				if (completionDate.before(initialReportDate)) {
					message = "The report completion date cannot be before the initial report date!";
				}
				/** Cannot be before incident year */
				incidentYear = inc.getIncidentYear();
				if (completionDate.get(Calendar.YEAR) < incidentYear) message = "The report completion date cannot be before the incident year!";
				break;
			case IncidentInfo.CONTACT_PHONE_NUMBER:
				if (newContents.equals("")) message = "A contact phone number must be supplied!";
				else if (!newContents.matches("[1-9][0-9]{2}-[0-9]{3}-[0-9]{4}")) message = "The supplied number is not valid. Please type a valid ten digit number in the form ##########.";
				break;
			case IncidentInfo.ZIP_CODE:
				if (!newContents.equals("27685") && !newContents.equals("74685") && !newContents.equals("88534") && !newContents.equals("27689") && !newContents.equals("63784") && !newContents.equals("78549")) message = "ZIP must be one of: 27685, 74685, 88534, 27689, 63784, or 78549 to be successfully updated!";
				break;
			case IncidentInfo.INCIDENT_COMPLETION_DATE:
				/** IncidentCompltnDate must not be less than IncidentYear */
				incidentYear = inc.getIncidentYear();
				if (incidentYear != 0) {
					if (newContents.length() == 10) {
						incidentCompletionYear = Integer.parseInt(newContents.substring(0, 4));
						if (incidentCompletionYear < incidentYear) message = "The incident completion date cannot be before the incident year!";
					} else message = "An incident completion date must be provided";
				}
				break;
			case IncidentInfo.INCIDENT_YEAR:
				if (newContents.length() == 4) incidentYear = Integer.parseInt(newContents);
				else incidentYear = 0;
				
				/** Incident year must not be after incident completion date */
				if (inc.getIncidentCompltnDate().length() == 10) incidentCompletionYear = Integer.parseInt(inc.getIncidentCompltnDate().substring(0, 4));
				else incidentCompletionYear = 0;
				if (incidentCompletionYear != 0) {
					if (incidentCompletionYear < incidentYear) message = "The incident year cannot be after the incident completion date!";
				}
				
				/** IncidentYear cannot be in the future */
				if (incidentYear > Calendar.getInstance().get(Calendar.YEAR)) message = "The incident year cannot be in the future!";
				
				/** IncidentYear must not be greater than initial report date */
				initialReportDate = Calendar.getInstance();
				alreadySetInitialReportDate = inc.getInitialRptDate();
				if (alreadySetInitialReportDate.length() == 10) initialReportDate.set(Integer.parseInt(alreadySetInitialReportDate.substring(0, 4)), Integer.parseInt(alreadySetInitialReportDate.substring(5, 7)) - 1, Integer.parseInt(alreadySetInitialReportDate.substring(8)));
				
				if (incidentYear > initialReportDate.get(Calendar.YEAR)) {
					message = "The incident year cannot be after the initial report date!";
				}
				
				break;
			case IncidentInfo.INITIAL_REPORT_DATE:
				initialReportDate = Calendar.getInstance();
				if (newContents.length() == 10) initialReportDate.set(Integer.parseInt(newContents.substring(0, 4)), Integer.parseInt(newContents.substring(5, 7)) - 1, Integer.parseInt(newContents.substring(8)));
				
				/** Initial report date cannot be in the future */
				if (initialReportDate.after(Calendar.getInstance())) message = "The initial report date cannot be in the future!!";
				
				/** Initial report date must not be after incident year */
				incidentYear = inc.getIncidentYear();
				if (incidentYear != 0) {
					int incidentReportYear = initialReportDate.get(Calendar.YEAR);
					if (incidentReportYear < incidentYear) message = "The initial report date cannot be before the incident year!";
				}
				
				/** Initial report date must not be after incident year */
				break;
			case IncidentInfo.PM_ATTUID:
				if (!newContents.matches("[a-zA-Z]{2}[0-9]{3}[a-zA-Z0-9]")) message = "The supplied ATTUID is not valid.";
				break;
			case IncidentInfo.RECORD_NUMBER:
				if (!newContents.matches("[1-9][0-9]{3}")) message = "The record number must be a 4 digit number with no leading 0s!";
				break;
			case IncidentInfo.REQUESTOR_ATTUID:
				if (newContents.equals("")) message = "Must supply a requestor ID!";
				break;
			case IncidentInfo.WORK_REQUEST_NUMBER:
				if (!newContents.equals("") && !newContents.matches("[0-9]{16}")) message = "The supplied work request number is not valid. It should be a 16 digit numerical id";
				break;
			default:
				break;
		}
		return message;
	}
}