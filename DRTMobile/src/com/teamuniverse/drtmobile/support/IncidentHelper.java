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
	
	public static boolean[] isSetArray(Incident inc) {
		return new boolean[] { !(inc.getAssessNotes() == null), !(inc.getBuildingAddress() == null), !(inc.getBuildingName() == null), !(inc.getBuildingStatus() == null), !(inc.getBuildingType() == null), !(inc.getCompltnDate() == null), !(inc.getComPowerIndicator() == null), !(inc.getContactPhone() == null), !(inc.getCreLead() == null), !(inc.getDamageIndicator() == null), !(inc.getElecIssueClsdIndicator() == null), !(inc.getElecIssueIndicator() == null), !(inc.getEnvIssueClsdIndicator() == null), !(inc.getEnvIssueIndicator() == null), !(inc.getEstCapCost() == 0), !(inc.getEstExpenseCost() == 0), !(inc.getEventName() == null), !(inc.getFenceGateIssueClsdIndicator() == null), !(inc.getFenceGateIssueIndicator() == null), !(inc.getGenIssueClsdIndicator() == null), !(inc.getGenIssueIndicator() == null), !(inc.getGeoLoc() == 0), !(inc.getGroundsIssueClsdIndicator() == null), !(inc.getGroundsIssueIndicator() == null), !(inc.getIncidentCompltnDate() == null), !(inc.getIncidentNotes() == null), !(inc.getIncidentStatus() == null), !(inc.getIncidentYear() == 0), !(inc.getInitialRptDate() == null), !(inc.getMechIssueClsdIndicator() == null), !(inc.getMechIssueIndicator() == null), !(inc.getMobCOIndicator() == null), !(inc.getOnGeneratorIndicator() == null), !(inc.getOtherIssueClsdIndicator() == null), !(inc.getOtherIssueIndicator() == null), !(inc.getPlumbIssueClsdIndicator() == null), !(inc.getPlumbIssueIndicator() == null), !(inc.getPMAttuid() == null), !(inc.getRecNumber() == 0), !(inc.getReqATTUID() == null), !(inc.getRoofsIssueClsdIndicator() == null), !(inc.getRoofsIssueIndicator() == null), !(inc.getSafetyIssueClsdIndicator() == null), !(inc.getSafetyIssueIndicator() == null), !(inc.getState() == null), !(inc.getStatusNotes() == null), !(inc.getStructIssueClsdIndicator() == null), !(inc.getStructIssueIndicator() == null), !(inc.getUnOccupiableIndicator() == null), !(inc.getWaterIssueClsdIndicator() == null), !(inc.getWaterIssueIndicator() == null), !(inc.getWorkReqNumber() == null) };
	}
	
	public static IncidentInfo getSingleInfo(Incident inc, int which) {
		final IncidentHelper me = new IncidentHelper();
		switch (which) {
			case 0:
				return me.new IncidentInfo("00STR00Assess Notes", inc.getAssessNotes());
			case 1:
				return me.new IncidentInfo("01ADR00Building Address", inc.getBuildingAddress());
			case 2:
				return me.new IncidentInfo("02STR00Bulding Name", inc.getBuildingName());
			case 3:
				return me.new IncidentInfo("03OOC00Building Status", inc.getBuildingStatus());
			case 4:
				return me.new IncidentInfo("04STR00Building Type", inc.getBuildingType());
			case 5:
				return me.new IncidentInfo("05DAT00Completion Date", inc.getCompltnDate());
			case 6:
				return me.new IncidentInfo("06YON00Com Power Indicator", inc.getComPowerIndicator());
			case 7:
				return me.new IncidentInfo("07NUM10Contact Phone Number", inc.getContactPhone());
			case 8:
				return me.new IncidentInfo("08STR00CRE Lead", inc.getCreLead());
			case 9:
				return me.new IncidentInfo("09YON00Damage Indicator", inc.getDamageIndicator());
			case 10:
				return me.new IncidentInfo("10YON00Elec Issue Closed Indicator", inc.getElecIssueClsdIndicator());
			case 11:
				return me.new IncidentInfo("11YON00Elec Issue Indicator", inc.getElecIssueIndicator());
			case 12:
				return me.new IncidentInfo("12YON00Env Issue Closed Indicator", inc.getEnvIssueClsdIndicator());
			case 13:
				return me.new IncidentInfo("13YON00EnvIssue Indicator", inc.getEnvIssueIndicator());
			case 14:
				return me.new IncidentInfo("14NUM00Estimated Cap Cost", inc.getEstCapCost());
			case 15:
				return me.new IncidentInfo("15NUM00Estimated Expense Cost", inc.getEstExpenseCost());
			case 16:
				return me.new IncidentInfo("16STR00Event Name", inc.getEventName());
			case 17:
				return me.new IncidentInfo("17YON00Fence Gate Issue Closed Indicator", inc.getFenceGateIssueClsdIndicator());
			case 18:
				return me.new IncidentInfo("18YON00Fence Gate Issue Indicator", inc.getFenceGateIssueIndicator());
			case 19:
				return me.new IncidentInfo("19YON00General Issue Closed Indicator", inc.getGenIssueClsdIndicator());
			case 20:
				return me.new IncidentInfo("20YON00General Issue Indicator", inc.getGenIssueIndicator());
			case 21:
				return me.new IncidentInfo("21NUM05ZIP Code", inc.getGeoLoc());
			case 22:
				return me.new IncidentInfo("22YON00Grounds Issue Closed Indicator", inc.getGroundsIssueClsdIndicator());
			case 23:
				return me.new IncidentInfo("23YON00Grounds Issue Indicator", inc.getGroundsIssueIndicator());
			case 24:
				return me.new IncidentInfo("24DAT00Incident Completion Date", inc.getIncidentCompltnDate());
			case 25:
				return me.new IncidentInfo("25STR00Incident Notes", inc.getIncidentNotes());
			case 26:
				return me.new IncidentInfo("26OOC00Incident Status", inc.getIncidentStatus());
			case 27:
				return me.new IncidentInfo("27NUM04Incident Year", inc.getIncidentYear());
			case 28:
				return me.new IncidentInfo("28DAT00Initial Report Date", inc.getInitialRptDate());
			case 29:
				return me.new IncidentInfo("29YON00Mechanical Issue Closed Indicator", inc.getMechIssueClsdIndicator());
			case 30:
				return me.new IncidentInfo("30YON00Mechanical Issue Indicator", inc.getMechIssueIndicator());
			case 31:
				return me.new IncidentInfo("31YON00Mob CO Indicator", inc.getMobCOIndicator());
			case 32:
				return me.new IncidentInfo("32YON00On Generator Indicator", inc.getOnGeneratorIndicator());
			case 33:
				return me.new IncidentInfo("33YON00Other Issue Closed Indicator", inc.getOtherIssueClsdIndicator());
			case 34:
				return me.new IncidentInfo("34YON00Other Issue Indicator", inc.getOtherIssueIndicator());
			case 35:
				return me.new IncidentInfo("35YON00Plumb Issue Closed Indicator", inc.getPlumbIssueClsdIndicator());
			case 36:
				return me.new IncidentInfo("36YON00Plumb Issue Indicator", inc.getPlumbIssueIndicator());
			case 37:
				return me.new IncidentInfo("37STR06PM ATTUID", inc.getPMAttuid());
			case 38:
				return me.new IncidentInfo("38NUM04Record Number", inc.getRecNumber());
			case 39:
				return me.new IncidentInfo("39STR06Req ATTUID", inc.getReqATTUID());
			case 40:
				return me.new IncidentInfo("40YON00Roofs Issue Closed Indicator", inc.getRoofsIssueClsdIndicator());
			case 41:
				return me.new IncidentInfo("41YON00Roofs Issue Indicator", inc.getRoofsIssueIndicator());
			case 42:
				return me.new IncidentInfo("42YON00Safety Issue Closed Indicator", inc.getSafetyIssueClsdIndicator());
			case 43:
				return me.new IncidentInfo("43YON00Safety Issue Indicator", inc.getSafetyIssueIndicator());
			case 44:
				return me.new IncidentInfo("44STA02State", inc.getState());
			case 45:
				return me.new IncidentInfo("45STR00Status Notes", inc.getStatusNotes());
			case 46:
				return me.new IncidentInfo("46YON00Structural Issue Closed Indicator", inc.getStructIssueClsdIndicator());
			case 47:
				return me.new IncidentInfo("47YON00Structural Issue Indicator", inc.getStructIssueIndicator());
			case 48:
				return me.new IncidentInfo("48YON00Unoccupiable Indicator", inc.getUnOccupiableIndicator());
			case 49:
				return me.new IncidentInfo("49YON00Water Issue Closed Indicator", inc.getWaterIssueClsdIndicator());
			case 50:
				return me.new IncidentInfo("50YON00Water Issue Indicator", inc.getWaterIssueIndicator());
			case 51:
				return me.new IncidentInfo("51NUM16Work Req Number", inc.getWorkReqNumber());
			default:
				return me.new IncidentInfo(null, null);
		}
	}
	
	/**
	 * The method that extracts all of the available infos from the incident
	 * that calls it.
	 * 
	 * @return An IncidentInfo[] that has all of the informations.
	 */
	public static IncidentInfo[] getInfos(Incident inc) {
		boolean[] whichs = isSetArray(inc);
		
		int sizeCount = 0;
		for (int i = 0; i < whichs.length; i++)
			if (whichs[i]) sizeCount++;
		final int size = sizeCount;
		
		IncidentInfo[] info = new IncidentInfo[size];
		
		for (int i = 0, counter = 0; i < whichs.length; i++)
			if (whichs[i]) info[counter++] = getSingleInfo(inc, i);
		
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
