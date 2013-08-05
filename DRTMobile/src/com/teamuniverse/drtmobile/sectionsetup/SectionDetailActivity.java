package com.teamuniverse.drtmobile.sectionsetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.teamuniverse.drtmobile.LogonActivity;
import com.teamuniverse.drtmobile.R;

/**
 * An activity representing a single Section detail screen. This activity is
 * only used on handset devices. On tablet-size devices, item details are
 * presented side-by-side with a list of items in a {@link SectionListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link SectionDetailFragment}.
 */
public class SectionDetailActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_section_detail_catalyst);
		
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually attach_picture it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and attach_picture it to the activity
			// using a fragment transaction.
			try {
				int id = (int) Long.parseLong(getIntent().getStringExtra(SectionListActivity.FRAG_ID));
				SectionListActivity.setFragmentManager(getSupportFragmentManager());
				SectionListActivity.m.putSection(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.in_app_menu, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if (SectionListActivity.backStackFragment.size() > 1) {
			SectionListActivity.setFragmentManager(getSupportFragmentManager());
			SectionListActivity.m.popSection();
		} else super.onBackPressed();
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.log_out:
				Intent intent = new Intent(getApplicationContext(), LogonActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			case android.R.id.home:
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. Use NavUtils to allow users
				// to navigate up one level in the application structure. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				//
				NavUtils.navigateUpTo(this, new Intent(this, SectionListActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}