package com.teamuniverse.drtmobile.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;

import com.teamuniverse.drtmobile.BuildingSearchFragment;
import com.teamuniverse.drtmobile.BuildingSearchResultsFragment;
import com.teamuniverse.drtmobile.DamageAssessmentFragment;
import com.teamuniverse.drtmobile.IncidentSearchFragment;
import com.teamuniverse.drtmobile.IncidentSearchResultsFragment;
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
	public static final int			INCIDENT_SEARCH			= 0;
	public static final int			BUILDING_SEARCH			= 1;
	public static final int			DAMAGE_ASSESSMENT		= 2;
	public static final int			REPORT_SELECTION		= 3;
	public static final int			INCIDENT_SEARCH_RESULTS	= 4;
	public static final int			BUILDING_SEARCH_RESULTS	= 5;
	public static final String[][]	SECTIONS_IN_LIST		= { { "Incident Search", INCIDENT_SEARCH + "" }, { "Building Search", BUILDING_SEARCH + "" }, { "Damage Assessment", DAMAGE_ASSESSMENT + "" }, { "Report Selection", REPORT_SELECTION + "" } };
	public static final int[]		SECTION_PARENTS			= { 0, 1, 2, 3, 0, 1 };
	public static final int[]		PARENTS_RPT_FIXER		= { 9, 9, 9, 0, 9, 9 };
	
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
			case INCIDENT_SEARCH_RESULTS:
				return new IncidentSearchResultsFragment();
			case BUILDING_SEARCH_RESULTS:
				return new BuildingSearchResultsFragment();
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
	
	public static void start(String authorization) {
		ITEMS = new ArrayList<Section>();
		ITEM_MAP = new HashMap<String, Section>();
		
		if (authorization.equals("ADM")) {
			for (int i = 0; i < SECTIONS_IN_LIST.length; i++)
				addSection(new Section(SECTIONS_IN_LIST[i][0], SECTIONS_IN_LIST[i][1]));
		} else addSection(new Section(SECTIONS_IN_LIST[3][0], SECTIONS_IN_LIST[3][1]));
	}
	
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
		public Section(String title, String id) {
			this.id = id;
			this.title = title;
		}
		
		@Override
		public String toString() {
			return title;
		}
		
		public String getId() {
			return id;
		}
	}
}