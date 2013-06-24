package com.teamuniverse.drtmobile.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;

import com.teamuniverse.drtmobile.DamageAssessmentFragment;
import com.teamuniverse.drtmobile.GLCSearchFragment;
import com.teamuniverse.drtmobile.ReportSelectionFragment;
import com.teamuniverse.drtmobile.ZIPSearchFragment;

/**
 * Helper class for adding sections and linking to their fragments.
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
	public static final int			ZIP_SEARCH			= 0;
	public static final int			GLC_SEARCH			= 1;
	public static final int			DAMAGE_ASSESSMENT	= 2;
	public static final int			REPORT_SELECTION	= 3;
	public static final String[][]	SECTIONS_IN_LIST	= { { "ZIP Search", ZIP_SEARCH + "" }, { "GLC Search", GLC_SEARCH + "" }, { "Damage Assessment", DAMAGE_ASSESSMENT + "" }, { "Report Selection", REPORT_SELECTION + "" } };
	public static final int[]		SECTION_PARENTS		= { 0, 1, 2, 3 };
	
	public static Fragment getSection(int id) {
		switch (id) {
			case ZIP_SEARCH:
				return new ZIPSearchFragment();
			case GLC_SEARCH:
				return new GLCSearchFragment();
			case DAMAGE_ASSESSMENT:
				return new DamageAssessmentFragment();
			case REPORT_SELECTION:
				return new ReportSelectionFragment();
			default:
				return new ZIPSearchFragment();
		}
	}
	
	/**
	 * An array of sections, each of which corresponds to a fragment.
	 */
	public static List<Section>			ITEMS		= new ArrayList<Section>();
	
	/**
	 * A map of the sections, by ID.
	 */
	public static Map<String, Section>	ITEM_MAP	= new HashMap<String, Section>();
	
	static {
		for (int i = 0; i < SECTIONS_IN_LIST.length; i++)
			addSection(new Section(SECTIONS_IN_LIST[i][0], SECTIONS_IN_LIST[i][1]));
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