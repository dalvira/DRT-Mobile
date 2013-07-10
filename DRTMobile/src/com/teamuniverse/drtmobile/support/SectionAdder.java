package com.teamuniverse.drtmobile.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;

import com.teamuniverse.drtmobile.BuildingResultsFragment;
import com.teamuniverse.drtmobile.BuildingSearchFragment;
import com.teamuniverse.drtmobile.DamageAssessmentFragment;
import com.teamuniverse.drtmobile.DamageGetFragment;
import com.teamuniverse.drtmobile.IncidentResultsFragment;
import com.teamuniverse.drtmobile.IncidentSearchFragment;
import com.teamuniverse.drtmobile.ReportSelectionFragment;

/**
 * Helper class for adding sections and linking to their fragments.
 * 
 * To add a new section:
 * 
 * 1. Add its ID as a 'public static final int'
 * 2. Add it to the getSection(id) method.
 * 3. Add its parent's id to SECTION_PARENTS (itself if it no parent)
 * 
 * To make it show in the section list:
 * 
 * 1. add it to SECTIONS_IN_LIST.
 * 
 * If the section is a subsection of another section:
 * 
 * 1. Add its parent's id to the parents array in the corresponding spot.
 * 2. Put all of the listed sections before the subsections.
 */
public class SectionAdder {
	public static final int			INCIDENT_SEARCH		= 0;
	public static final int			BUILDING_SEARCH		= 1;
	public static final int			DAMAGE_ASSESSMENT	= 2;
	public static final int			REPORT_SELECTION	= 3;
	public static final int			INCIDENT_RESULTS	= 4;
	public static final int			BUILDING_RESULTS	= 5;
	public static final int			DAMAGE_GET			= 6;
	public static final int			DAMAGE_ADD			= 7;
	public static final String[][]	SECTIONS_IN_LIST	= { { "Incident Search", INCIDENT_SEARCH + "" }, { "Building Search", BUILDING_SEARCH + "" }, { "Damage Assessment", DAMAGE_ASSESSMENT + "" }, { "Report Selection", REPORT_SELECTION + "" } };
	public static final int[]		SECTION_PARENTS		= { 0, 1, 2, 3, 0, 1, 2, 2 };
	public static final int[]		PARENTS_RPT_FIXER	= { 9, 9, 9, 0, 9, 9, 9, 9 };
	
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
			case INCIDENT_SEARCH:
				return new IncidentSearchFragment();
			case BUILDING_SEARCH:
				return new BuildingSearchFragment();
			case DAMAGE_ASSESSMENT:
				return new DamageAssessmentFragment();
			case REPORT_SELECTION:
				return new ReportSelectionFragment();
			case INCIDENT_RESULTS:
				return new IncidentResultsFragment();
			case BUILDING_RESULTS:
				return new BuildingResultsFragment();
			case DAMAGE_GET:
				return new DamageGetFragment();
				// case DAMAGE_ADD:
				// return new DamageAddFragment();
			default:
				return new IncidentSearchFragment();
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
	 * @param authorization
	 *            The authorization level of the current user. It should be
	 *            either "ADM" or "RPT".
	 */
	public static void start(String authorization) {
		ITEMS = new ArrayList<Section>();
		ITEM_MAP = new HashMap<String, Section>();
		
		if (authorization.equals("ADM")) {
			for (int i = 0; i < SECTIONS_IN_LIST.length; i++)
				addSection(new Section(SECTIONS_IN_LIST[i][0], SECTIONS_IN_LIST[i][1]));
		} else addSection(new Section(SECTIONS_IN_LIST[3][0], SECTIONS_IN_LIST[3][1]));
	}
	
	/**
	 * Method to add a section to the mapping, in order for it to be shown in
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
		private String	id;
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