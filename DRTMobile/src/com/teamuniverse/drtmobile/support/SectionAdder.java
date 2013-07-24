package com.teamuniverse.drtmobile.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;

import com.teamuniverse.drtmobile.AddIncidentFragment;
import com.teamuniverse.drtmobile.AdminConditionCannedReportFragment;
import com.teamuniverse.drtmobile.CREBuildingClosureDelayedOpenCannedReportFragment;
import com.teamuniverse.drtmobile.IncidentRecNumResultsFragment;
import com.teamuniverse.drtmobile.IncidentRecNumSearchFragment;
import com.teamuniverse.drtmobile.IncidentZIPResultsFragment;
import com.teamuniverse.drtmobile.IncidentZIPSearchFragment;
import com.teamuniverse.drtmobile.ReportSelectionFragment;

/**
 * Helper class for adding sections and linking to their fragments.
 * 
 * To attach_picture a new section:
 * 
 * 1. Add its ID as a 'public static final int'
 * 2. Add it to the getSection(id) method.
 * 3. Add its parent's id to SECTION_PARENTS (itself if it no parent)
 * 
 * To make it show in the section list:
 * 
 * 1. attach_picture it to SECTIONS_IN_LIST.
 * 
 * If the section is a subsection of another section:
 * 
 * 1. Add its parent's id to the parents array in the corresponding spot.
 * 2. Put all of the listed sections before the subsections.
 */
public class SectionAdder {
	public static final int			NONE											= 99;
	
	public static final int			INCIDENT_ZIP_SEARCH								= 0;
	public static final int			INCIDENT_REC_NUM_SEARCH							= 1;
	public static final int			ADD_INCIDENT									= 2;
	public static final int			REPORT_SELECTION								= 3;
	public static final int			INCIDENT_ZIP_RESULTS							= 4;
	public static final int			INCIDENT_REC_NUM_RESULTS						= 5;
	public static final int			ADMIN_CONDITION_CANNED_REPORT					= 6;
	public static final int			CRE_BUILDING_CLOSURE_DELAYED_OPEN_CANNED_REPORT	= 7;
	
	/** All of the sections that can be shown in the section list */
	public static final String[][]	SECTIONS_IN_LIST								= { { "Open Incidents by ZIP", INCIDENT_ZIP_SEARCH + "" }, { "Incident by Record Number", INCIDENT_REC_NUM_SEARCH + "" }, { "Add New Incident", ADD_INCIDENT + "" }, { "Report Selection", REPORT_SELECTION + "" } };
	public static final int[]		SECTION_PARENTS									= { INCIDENT_ZIP_SEARCH, INCIDENT_REC_NUM_SEARCH, ADD_INCIDENT, REPORT_SELECTION, INCIDENT_ZIP_SEARCH, INCIDENT_REC_NUM_SEARCH, REPORT_SELECTION, REPORT_SELECTION };
	public static final int[]		PARENTS_RPT_FIXER								= { NONE, NONE, NONE, REPORT_SELECTION - 3, NONE, NONE, REPORT_SELECTION - 3, REPORT_SELECTION - 3 };
	
	public static final String[]	AUTHORIZATION_NAMES								= { "ADM", "RPT" };
	public static final int[][]		AUTHORIZATION_PAGES								= { { INCIDENT_ZIP_SEARCH, INCIDENT_REC_NUM_SEARCH, ADD_INCIDENT, REPORT_SELECTION }, { REPORT_SELECTION } };
	
	/**
	 * This method facilitates dynamic fragment changing based on user input by
	 * simplifying the initialization of different fragment types into one line
	 * at the call location.
	 * 
	 * @param id
	 *            The numerical ID of the desired fragment, as found as a final
	 *            int of SectionAdder.
	 * @return The fragment that will be put into the appropriate activity.
	 */
	public static Fragment getSection(int id) {
		switch (id) {
			case INCIDENT_ZIP_SEARCH:
				return new IncidentZIPSearchFragment();
			case INCIDENT_REC_NUM_SEARCH:
				return new IncidentRecNumSearchFragment();
			case ADD_INCIDENT:
				return new AddIncidentFragment();
			case REPORT_SELECTION:
				return new ReportSelectionFragment();
			case INCIDENT_ZIP_RESULTS:
				return new IncidentZIPResultsFragment();
			case INCIDENT_REC_NUM_RESULTS:
				return new IncidentRecNumResultsFragment();
			case ADMIN_CONDITION_CANNED_REPORT:
				return new AdminConditionCannedReportFragment();
			case CRE_BUILDING_CLOSURE_DELAYED_OPEN_CANNED_REPORT:
				return new CREBuildingClosureDelayedOpenCannedReportFragment();
			default:
				return new IncidentZIPSearchFragment();
		}
	}
	
	/**
	 * An array of sections, each of which corresponds to a fragment.
	 */
	public static List<Section>			ITEMS;
	
	/**
	 * A map of the sections, by ID.
	 */
	public static Map<String, Section>	ITEM_MAP;
	
	/**
	 * This method takes the current user's authorization and displays only the
	 * relevant section options. Call this method before going to the
	 * SectionListActivity in order to have sections listed.
	 * 
	 * @param currentAuthorization
	 *            The authorization level of the current user. It should be
	 *            either "ADM" or "RPT".
	 */
	public static void start(String currentAuthorization) {
		ITEMS = new ArrayList<Section>();
		ITEM_MAP = new HashMap<String, Section>();
		
		for (int i = 0; i < AUTHORIZATION_NAMES.length; i++) {
			if (currentAuthorization.equals(AUTHORIZATION_NAMES[i])) {
				for (int j = 0; j < AUTHORIZATION_PAGES[i].length; j++) {
					addSection(new Section(SECTIONS_IN_LIST[AUTHORIZATION_PAGES[i][j]][0], SECTIONS_IN_LIST[AUTHORIZATION_PAGES[i][j]][1]));
				}
				break;
			}
		}
	}
	
	/**
	 * Method to attach_picture a section to the mapping, in order for it to be
	 * shown in
	 * the list.
	 * 
	 * @param section
	 */
	private static void addSection(Section section) {
		ITEMS.add(section);
		ITEM_MAP.put(section.id, section);
	}
	
	/**
	 * A section that points to the respective fragment.
	 */
	public static class Section {
		/** The id that will correspond to the Section's id */
		private String	id;
		/** The title that will be shown in the section list */
		private String	title;
		
		/***
		 * The first argument must be the title displayed in the section list,
		 * the second will be an integer stored inside a String.
		 */
		private Section(String title, String id) {
			this.id = id;
			this.title = title;
		}
		
		/**
		 * Returns the title of the section, in order for it to display properly
		 * in the list.
		 */
		@Override
		public String toString() {
			return title;
		}
		
		/**
		 * Returns a String containing the int id of the calling section.
		 * 
		 * @return The int id contained in a String.
		 */
		public String getId() {
			return id;
		}
	}
}