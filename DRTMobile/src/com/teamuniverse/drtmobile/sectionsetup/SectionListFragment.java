package com.teamuniverse.drtmobile.sectionsetup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.teamuniverse.drtmobile.support.SectionAdder;

/**
 * A list fragment representing a list of Sections. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link SectionDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class SectionListFragment extends ListFragment {
	
	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks	mCallbacks			= sectionCallbacks;
	
	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int			mActivatedPosition	= ListView.INVALID_POSITION;
	
	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(String id);
	}
	
	/**
	 * An implementation of the {@link Callbacks} interface that does nothing.
	 * Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks	sectionCallbacks	= new Callbacks() {
														@Override
														public void onItemSelected(String id) {
														}
													};
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SectionListFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<SectionAdder.Section>(getActivity(), android.R.layout.simple_list_item_activated_1, android.R.id.text1, SectionAdder.ITEMS));
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException("Activity must implement fragment's callbacks.");
		}
		
		mCallbacks = (Callbacks) activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sectionCallbacks;
	}
	
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		
		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		mCallbacks.onItemSelected(SectionAdder.ITEMS.get(position).getId());
	}
	
	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(activateOnItemClick	? ListView.CHOICE_MODE_SINGLE
														: ListView.CHOICE_MODE_NONE);
	}
	
	public void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}
		
		mActivatedPosition = position;
	}
}