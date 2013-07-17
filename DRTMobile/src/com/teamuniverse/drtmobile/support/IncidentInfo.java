/**
 * 
 */
package com.teamuniverse.drtmobile.support;

/**
 * The class that defines the object that contains each field of
 * information. The object is defined of a String key and an Object value.
 * 
 * @author ef183v
 * 
 */
public class IncidentInfo {
	public static final int			ASSESSMENT_NOTES						= 0;
	public static final int			BUILDING_ADDRESS						= 1;
	public static final int			BUILDING_STATUS							= 2;
	public static final int			BUILDING_TYPE							= 3;
	public static final int			BUILDING_NAME							= 4;
	public static final int			COMMERCIAL_POWER_INDICATOR				= 5;
	public static final int			COMPLETION_DATE							= 6;
	public static final int			CONTACT_PHONE_NUMBER					= 7;
	public static final int			CRE_LEAD								= 8;
	public static final int			DAMAGE_INDICATOR						= 9;
	public static final int			ELECTRICAL_ISSUE_CLOSED_INDICATOR		= 10;
	public static final int			ELECTRICAL_ISSUE_INDICATOR				= 11;
	public static final int			ENVIRONMENTAL_ISSUE_CLOSED_INDICATOR	= 12;
	public static final int			ENVIRONMENTAL_ISSUE_INDICATOR			= 13;
	public static final int			ESTIMATED_CAP_COST						= 14;
	public static final int			ESTIMATED_EXPENSE_COST					= 15;
	public static final int			EVENT_NAME								= 16;
	public static final int			FENCE_GATE_ISSUE_CLOSED_INDICATOR		= 17;
	public static final int			FENCE_GATE_ISSUE_INDICATOR				= 18;
	public static final int			GENERATOR_ISSUE_CLOSED_INDICATOR		= 19;
	public static final int			GENERATOR_ISSUE_INDICATOR				= 20;
	public static final int			GROUNDS_ISSUE_CLOSED_INDICATOR			= 21;
	public static final int			GROUNDS_ISSUE_INDICATOR					= 22;
	public static final int			INCIDENT_COMPLETION_DATE				= 23;
	public static final int			INCIDENT_NOTES							= 24;
	public static final int			INCIDENT_STATUS							= 25;
	public static final int			INCIDENT_YEAR							= 26;
	public static final int			INITIAL_REPORT_DATE						= 27;
	public static final int			MECHANICAL_ISSUE_CLOSED_INDICATOR		= 28;
	public static final int			MECHANICAL_ISSUE_INDICATOR				= 29;
	public static final int			MOBILITY_CO_INDICATOR					= 30;
	public static final int			ON_GENERATOR_INDICATOR					= 31;
	public static final int			OTHER_ISSUE_CLOSED_INDICATOR			= 32;
	public static final int			OTHER_ISSUE_INDICATOR					= 33;
	public static final int			PLUMB_ISSUE_CLOSED_INDICATOR			= 34;
	public static final int			PLUMB_ISSUE_INDICATOR					= 35;
	public static final int			PM_ATTUID								= 36;
	public static final int			RECORD_NUMBER							= 37;
	public static final int			REQUESTOR_ATTUID						= 38;
	public static final int			ROOFS_ISSUE_CLOSED_INDICATOR			= 39;
	public static final int			ROOFS_ISSUE_INDICATOR					= 40;
	public static final int			SAFETY_ISSUE_CLOSED_INDICATOR			= 41;
	public static final int			SAFETY_ISSUE_INDICATOR					= 42;
	public static final int			STATE									= 43;
	public static final int			STATUS_NOTES							= 44;
	public static final int			STRUCTURAL_ISSUE_CLOSED_INDICATOR		= 45;
	public static final int			STRUCTURAL_ISSUE_INDICATOR				= 46;
	public static final int			UNOCCUPIABLE_INDICATOR					= 47;
	public static final int			WATER_ISSUE_CLOSED_INDICATOR			= 48;
	public static final int			WATER_ISSUE_INDICATOR					= 49;
	public static final int			WORK_REQUEST_NUMBER						= 50;
	public static final int			ZIP_CODE								= 51;
	
	private static final int[]		LENGTHS									= { 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 4, 6, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 16, 6 };
	private static final String[]	DESCRIPTORS								= { "Assessment Notes", "Building Address", "Building Status", "Building Type", "Building Name", "Commercial Power Indicator", "Completion Date", "Contact Phone Number", "CRE Lead", "Damage Indicator", "Electrical Issue Closed Indicator", "Electrical Issue Indicator", "Environmental Issue Closed Indicator", "Environmental Issue Indicator", "Estimated Cap Cost", "Estimated Expense Cost", "Event Name", "Fence Gate Issue Closed Indicator", "Fence Gate Issue Indicator", "Generator Issue Closed Indicator", "Generator Issue Indicator", "Grounds Issue Closed Indicator", "Grounds Issue Indicator", "Incident Completion Date", "Incident Notes", "Incident Status", "Incident Year", "Initial Report Date", "Mechanical Issue Closed Indicator", "Mechanical Issue Indicator", "Mobility CO Indicator", "On Generator Indicator", "Other Issue Closed Indicator", "Other Issue Indicator", "Plumb Issue Closed Indicator", "Plumb Issue Indicator", "PM ATTUID", "Record Number", "Requestor ATTUID", "Roofs Issue Closed Indicator", "Roofs Issue Indicator", "Safety Issue Closed Indicator", "Safety Issue Indicator", "State", "Status Notes", "Structural Issue Closed Indicator", "Structural Issue Indicator", "Unoccupiable Indicator", "Water Issue Closed Indicator", "Water Issue Indicator", "Work Request Number", "ZIP Code" };
	
	private final int				id;
	private final Object			value;
	
	private final int				length;
	private final String			descriptor;
	
	protected IncidentInfo() {
		id = -1;
		value = null;
		
		length = -1;
		descriptor = null;
	}
	
	protected IncidentInfo(int id, Object value) {
		this.id = id;
		this.value = value;
		
		length = LENGTHS[id];
		descriptor = DESCRIPTORS[id];
	}
	
	/**
	 * Returns the int that corresponds to the field that this IncidentInfo
	 * describes.
	 * 
	 * @return The int id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the length the valid text is for this field.
	 * 
	 * @return The int length.
	 */
	public int getMaxLength() {
		return length;
	}
	
	/**
	 * Returns the value of the relevant incident info.
	 * 
	 * @return The Object value.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Returns the descriptor of the relevant incident info, that it, its
	 * textual, human-readable description.
	 * 
	 * @return The String human-readable description.
	 */
	public String getDescriptor() {
		return descriptor;
	}
}