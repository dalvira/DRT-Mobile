package com.teamuniverse.drtmobile;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.att.intern.webservice.Incident;
import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.IncidentHelper;
import com.teamuniverse.drtmobile.support.IncidentInfo;
import com.teamuniverse.drtmobile.support.SetterUpper;
// import com.example.dreamteamdisasterrecovery.GLCSearchActivity;
// import com.example.dreamteamdisasterrecovery.MainActivity;
// import com.example.dreamteamdisasterrecovery.R;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */

public class AddIncidentFragment extends Fragment {
	private Activity	m;
	private Incident	inc;
	private final int	COLUMNS	= 2;
	
	// private File storageDir;
	Context				context;
	Toast				fail1;
	Toast				fail2;
	Toast				success;
	int					recNum;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public AddIncidentFragment() {
	}
	
	/**
	 * Define the Activity object m to facilitate use later in the activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	/**
	 * Call SetterUpper.setup() to fill in the ATTUID and authorization of the
	 * current user.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vGroup, Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.fragment_add_incident, vGroup, false);
		SetterUpper.setup(m, mainView);
		
		LinearLayout container = (LinearLayout) mainView.findViewById(R.id.container);
		// 5 prepopulated fields
		int zipValue;
		DatabaseManager db = new DatabaseManager(m);
		String zipString = db.sessionUnset("adding_zip");
		db.close();
		
		if (zipString.equals("")) {
			/**
			 * The pick ZIP interface is setup here:
			 */
			mainView.findViewById(R.id.the_buttons_container).setVisibility(View.INVISIBLE);
			container.addView(new LinearLayout(m));
			((LinearLayout) container.getChildAt(container.getChildCount() - 1)).setOrientation(LinearLayout.HORIZONTAL);
			((LinearLayout) container.getChildAt(container.getChildCount() - 1)).addView(new TextView(m));
			((TextView) ((LinearLayout) container.getChildAt(container.getChildCount() - 1)).getChildAt(((LinearLayout) container.getChildAt(container.getChildCount() - 1)).getChildCount() - 1)).setText(m.getString(R.string.pick_a_valid_zip));
			((LinearLayout) container.getChildAt(container.getChildCount() - 1)).addView(new Spinner(m));
			ArrayAdapter<CharSequence> zipAdapter = ArrayAdapter.createFromResource(m, R.array.valid_zips, android.R.layout.simple_spinner_item);
			zipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			((Spinner) ((LinearLayout) container.getChildAt(container.getChildCount() - 1)).getChildAt(((LinearLayout) container.getChildAt(container.getChildCount() - 1)).getChildCount() - 1)).setAdapter(zipAdapter);
			((Spinner) ((LinearLayout) container.getChildAt(container.getChildCount() - 1)).getChildAt(((LinearLayout) container.getChildAt(container.getChildCount() - 1)).getChildCount() - 1)).setOnItemSelectedListener(new OnItemSelectedListener() {
				LinearLayout	container;
				View			mainView;
				
				@Override
				public void onItemSelected(AdapterView<?> arg0, View view, int pos, long id) {
					if (pos != 0) {
						container.removeAllViews();
						mainView.findViewById(R.id.the_buttons_container).setVisibility(View.VISIBLE);
						setupAdd(mainView, container, Integer.parseInt(m.getResources().getStringArray(R.array.valid_zips)[pos]));
					}
				}
				
				public OnItemSelectedListener passValues(LinearLayout container, View mainView) {
					this.container = container;
					this.mainView = mainView;
					return this;
				}
				
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			}.passValues(container, mainView));
		} else {
			zipValue = Integer.parseInt(zipString);
			setupAdd(mainView, container, zipValue);
		}
		return mainView;
	}
	
	public void setupAdd(final View mainView, final LinearLayout container, final int zipValue) {
		@SuppressWarnings("unused")
		View[] allInfos = setupAllFields(mainView, container, zipValue);
		/** Attach Picture button setup */
		((Button) mainView.findViewById(R.id.attach_picture)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO setup add picture
			}
		});
		/** Add button setup */
		((Button) mainView.findViewById(R.id.add)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO setup add incident
				
			}
		});
	}
	
	public View[] setupAllFields(View mainView, LinearLayout container, int zipValue) {
		TextView labelForEach;
		LinearLayout each;
		inc = IncidentHelper.init(m, zipValue);
		
		IncidentInfo[] infos = IncidentHelper.getInfos(inc, IncidentHelper.ADD_ORDER);
		View[] fields = new View[infos.length];
		
		int colorCoordinator = 0;
		for (int i = 0; i < infos.length; i++) {
			int which = infos[i].getId();
			
			if (i != 0) m.getLayoutInflater().inflate(R.layout.divider_line, container);
			
			each = new LinearLayout(m);
			each.setPadding(3, 3, 3, 3);
			each.setOrientation(LinearLayout.HORIZONTAL);
			each.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			for (int j = 0; j < COLUMNS; j++) {
				labelForEach = new TextView(m);
				labelForEach.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
				if (j == 0) {
					labelForEach.setText(infos[i].getDescriptor());
					each.addView(labelForEach);
				} else if (j == 1) {
					if (setupSingleField(infos[i], which).length > 1) {
						each.addView(setupSingleField(infos[i], which)[1]);
					} else each.addView(setupSingleField(infos[i], which)[0]);
					fields[i] = setupSingleField(infos[i], which)[0];
					fields[i].setTag(which);
				}
			}
			
			if (colorCoordinator++ % 2 == 1) {
				each.setBackgroundColor(Color.rgb(220, 220, 220));
			}
			
			container.addView(each);
		}
		
		return fields;
	}
	
	public View[] setupSingleField(IncidentInfo info, int which) {
		EditText editText;
		Spinner spinner;
		ScrollViewCompatibleDatePicker datePicker;
		View unaryContainer = null;
		
		EditText dummyEditText = null;
		
		final int limit = info.getMaxLength();
		
		String oldContents;
		try {
			oldContents = (String) info.getValue();
		} catch (ClassCastException e) {
			oldContents = (Integer) info.getValue() + "";
		}
		
		switch (which) {
			case IncidentInfo.BUILDING_STATUS:
				spinner = new Spinner(m);
				ArrayAdapter<CharSequence> buildingStatusAdapter = ArrayAdapter.createFromResource(m, R.array.building_statuses, android.R.layout.simple_spinner_item);
				buildingStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(buildingStatusAdapter);
				final String[] buildingStatuses = m.getResources().getStringArray(R.array.building_statuses);
				dummyEditText = new EditText(m);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					EditText	dummyEditText;
					
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
						dummyEditText.setText(buildingStatuses[pos]);
					}
					
					public OnItemSelectedListener passValues(EditText dummyEditText) {
						this.dummyEditText = dummyEditText;
						return this;
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				}.passValues(dummyEditText));
				for (int i = 0; i < buildingStatuses.length; i++) {
					if (oldContents.equals(buildingStatuses[i])) spinner.setSelection(i);
				}
				unaryContainer = spinner;
				break;
			case IncidentInfo.BUILDING_TYPE:
				spinner = new Spinner(m);
				ArrayAdapter<CharSequence> buildingTypeAdapter = ArrayAdapter.createFromResource(m, R.array.building_types, android.R.layout.simple_spinner_item);
				buildingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(buildingTypeAdapter);
				final String[] buildingTypes = m.getResources().getStringArray(R.array.building_types);
				dummyEditText = new EditText(m);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					EditText	dummyEditText;
					
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
						dummyEditText.setText(buildingTypes[pos]);
					}
					
					public OnItemSelectedListener passValues(EditText dummyEditText) {
						this.dummyEditText = dummyEditText;
						return this;
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				}.passValues(dummyEditText));
				for (int i = 0; i < buildingTypes.length; i++) {
					if (oldContents.equals(buildingTypes[i])) spinner.setSelection(i);
				}
				unaryContainer = spinner;
				break;
			case IncidentInfo.INCIDENT_STATUS:
				spinner = new Spinner(m);
				ArrayAdapter<CharSequence> incidentStatusAdapter = ArrayAdapter.createFromResource(m, R.array.OOC, android.R.layout.simple_spinner_item);
				incidentStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(incidentStatusAdapter);
				dummyEditText = new EditText(m);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					EditText	dummyEditText;
					
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
						dummyEditText.setText(pos == 0 ? "Open" : "Closed");
					}
					
					public OnItemSelectedListener passValues(EditText dummyEditText) {
						this.dummyEditText = dummyEditText;
						return this;
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				}.passValues(dummyEditText));
				spinner.setSelection(oldContents.toLowerCase(Locale.getDefault()).equals("open") ? 0
																								: 1);
				unaryContainer = spinner;
				break;
			case IncidentInfo.EVENT_NAME:
				spinner = new Spinner(m);
				ArrayAdapter<CharSequence> eventNameAdapter = ArrayAdapter.createFromResource(m, R.array.names_array, android.R.layout.simple_spinner_item);
				eventNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(eventNameAdapter);
				final String[] eventNames = m.getResources().getStringArray(R.array.names_array);
				dummyEditText = new EditText(m);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					EditText	dummyEditText;
					
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
						dummyEditText.setText(eventNames[pos]);
					}
					
					public OnItemSelectedListener passValues(EditText dummyEditText) {
						this.dummyEditText = dummyEditText;
						return this;
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				}.passValues(dummyEditText));
				for (int i = 0; i < eventNames.length; i++) {
					if (oldContents.equals(eventNames[i])) spinner.setSelection(i);
				}
				unaryContainer = spinner;
				break;
			case IncidentInfo.STATE:
				spinner = new Spinner(m);
				ArrayAdapter<CharSequence> stateAdapter = new ArrayAdapter<CharSequence>(m, android.R.layout.simple_spinner_item);
				stateAdapter.addAll(IncidentInfo.STATE_NAMES);
				stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(stateAdapter);
				dummyEditText = new EditText(m);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					EditText	dummyEditText;
					
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
						dummyEditText.setText(IncidentInfo.STATE_POSTALS[pos]);
					}
					
					public OnItemSelectedListener passValues(EditText dummyEditText) {
						this.dummyEditText = dummyEditText;
						return this;
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				}.passValues(dummyEditText));
				for (int i = 0; i < IncidentInfo.STATE_NAMES.length; i++) {
					if (oldContents.equals(IncidentInfo.STATE_POSTALS[i])) {
						spinner.setSelection(i);
						break;
					}
				}
				unaryContainer = spinner;
				break;
			case IncidentInfo.INCIDENT_YEAR:
				spinner = new Spinner(m);
				ArrayAdapter<CharSequence> incidentYearAdapter = ArrayAdapter.createFromResource(m, R.array.years, android.R.layout.simple_spinner_item);
				incidentYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(incidentYearAdapter);
				final String[] incidentYears = m.getResources().getStringArray(R.array.years);
				dummyEditText = new EditText(m);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					EditText	dummyEditText;
					
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
						dummyEditText.setText(incidentYears[pos]);
					}
					
					public OnItemSelectedListener passValues(EditText dummyEditText) {
						this.dummyEditText = dummyEditText;
						return this;
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				}.passValues(dummyEditText));
				for (int i = 0; i < incidentYears.length; i++) {
					if (oldContents.equals(incidentYears[i])) spinner.setSelection(i);
				}
				unaryContainer = spinner;
				break;
			case IncidentInfo.COMMERCIAL_POWER_INDICATOR:
			case IncidentInfo.DAMAGE_INDICATOR:
			case IncidentInfo.ELECTRICAL_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.ELECTRICAL_ISSUE_INDICATOR:
			case IncidentInfo.ENVIRONMENTAL_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.ENVIRONMENTAL_ISSUE_INDICATOR:
			case IncidentInfo.FENCE_GATE_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.FENCE_GATE_ISSUE_INDICATOR:
			case IncidentInfo.GENERATOR_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.GENERATOR_ISSUE_INDICATOR:
			case IncidentInfo.GROUNDS_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.GROUNDS_ISSUE_INDICATOR:
			case IncidentInfo.MECHANICAL_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.MECHANICAL_ISSUE_INDICATOR:
			case IncidentInfo.MOBILITY_CO_INDICATOR:
			case IncidentInfo.ON_GENERATOR_INDICATOR:
			case IncidentInfo.OTHER_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.OTHER_ISSUE_INDICATOR:
			case IncidentInfo.PLUMB_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.PLUMB_ISSUE_INDICATOR:
			case IncidentInfo.ROOFS_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.ROOFS_ISSUE_INDICATOR:
			case IncidentInfo.SAFETY_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.SAFETY_ISSUE_INDICATOR:
			case IncidentInfo.STRUCTURAL_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.STRUCTURAL_ISSUE_INDICATOR:
			case IncidentInfo.UNOCCUPIABLE_INDICATOR:
			case IncidentInfo.WATER_ISSUE_CLOSED_INDICATOR:
			case IncidentInfo.WATER_ISSUE_INDICATOR:
				spinner = new Spinner(m);
				ArrayAdapter<CharSequence> indicatorAdapter = ArrayAdapter.createFromResource(m, R.array.YON, android.R.layout.simple_spinner_item);
				indicatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(indicatorAdapter);
				dummyEditText = new EditText(m);
				spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					EditText	dummyEditText;
					
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
						dummyEditText.setText(pos == 0 ? "Y" : "N");
					}
					
					public OnItemSelectedListener passValues(EditText dummyEditText) {
						this.dummyEditText = dummyEditText;
						return this;
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				}.passValues(dummyEditText));
				spinner.setSelection(oldContents.toLowerCase(Locale.getDefault()).equals("y")	? 0
																								: 1);
				unaryContainer = spinner;
				break;
			case IncidentInfo.COMPLETION_DATE:
			case IncidentInfo.INCIDENT_COMPLETION_DATE:
			case IncidentInfo.INITIAL_REPORT_DATE:
				datePicker = new ScrollViewCompatibleDatePicker(m);
				datePicker.setCalendarViewShown(false);
				dummyEditText = new EditText(m);
				dummyEditText.setText(oldContents);
				if (oldContents.length() == 10) {
					datePicker.init((int) Long.parseLong(oldContents.substring(0, 4)), (int) Long.parseLong(oldContents.substring(5, 7)) - 1, (int) Long.parseLong(oldContents.substring(8)), new OnDateChangedListener() {
						EditText	dummyEditText;
						
						@Override
						public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
							dummyEditText.setText(y + "-" + (m < 9 ? "0" : "") + (m + 1) + "-" + (d < 10 ? "0"
																										: "") + d);
						}
						
						public OnDateChangedListener passValues(EditText dummyEditText) {
							this.dummyEditText = dummyEditText;
							return this;
						}
					}.passValues(dummyEditText));
				}
				unaryContainer = datePicker;
				break;
			case IncidentInfo.ASSESSMENT_NOTES:
			case IncidentInfo.INCIDENT_NOTES:
			case IncidentInfo.STATUS_NOTES:
			case IncidentInfo.BUILDING_ADDRESS:
				editText = new EditText(m);
				editText.setMinLines(3);
				editText.setEms(10);
				editText.setText(oldContents);
				unaryContainer = editText;
				break;
			case IncidentInfo.CONTACT_PHONE_NUMBER:
				editText = new EditText(m);
				editText.setImeActionLabel(m.getString(R.string.go), EditorInfo.IME_ACTION_GO);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				editText.setText(oldContents.replaceAll("-", ""));
				if (limit != 0) {
					InputFilter[] filters = new InputFilter[1];
					filters[0] = new InputFilter.LengthFilter(limit);
					editText.setFilters(filters);
					editText.setMinEms(limit * 3 / 5);
				}
				unaryContainer = editText;
				break;
			case IncidentInfo.ESTIMATED_CAP_COST:
			case IncidentInfo.ESTIMATED_EXPENSE_COST:
			case IncidentInfo.WORK_REQUEST_NUMBER:
				editText = new EditText(m);
				editText.setImeActionLabel(m.getString(R.string.go), EditorInfo.IME_ACTION_GO);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				editText.setText(oldContents);
				if (limit != 0) {
					InputFilter[] filters = new InputFilter[1];
					filters[0] = new InputFilter.LengthFilter(limit);
					editText.setFilters(filters);
					editText.setMinEms(limit * 3 / 5);
				}
				unaryContainer = editText;
				break;
			default:
				editText = new EditText(m);
				editText.setImeActionLabel(m.getString(R.string.go), EditorInfo.IME_ACTION_GO);
				editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
				editText.setText(oldContents);
				if (limit != 0) {
					InputFilter[] filters = new InputFilter[1];
					filters[0] = new InputFilter.LengthFilter(limit);
					editText.setFilters(filters);
					editText.setMinEms(limit * 3 / 5);
				}
				unaryContainer = editText;
				break;
		}
		
		switch (which) {
			case IncidentInfo.ZIP_CODE:
			case IncidentInfo.STATE:
			case IncidentInfo.PM_ATTUID:
			case IncidentInfo.BUILDING_NAME:
			case IncidentInfo.BUILDING_ADDRESS:
			case IncidentInfo.RECORD_NUMBER:
				unaryContainer.setEnabled(false);
				break;
			default:
				break;
		}
		
		if (dummyEditText == null) {
			return new View[] { unaryContainer };
		} else return new View[] { dummyEditText, unaryContainer };
	}
	
	// public void onClick(View v) {
	// final Context con = m.getApplicationContext();
	// Webservice test = new Webservice(con);
	//
	// if (v.getId() == (attach_picture.getId())) {
	// /*
	// * public void dispatchTakePictureIntent(int actionCode) {
	// * Intent takePictureIntent = new
	// * Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	// * startActivityForResult(takePictureIntent, actionCode);
	// * }
	// * storageDir = new File (
	// * Environment.getExternalStorageDirectory()
	// * + PICTURES_DIR
	// * + getAlbumName()
	// * );
	// */
	// // CameraTwoActivity.aIncident = aIncident;
	// // Intent i = new Intent();
	// // i.setClassName("com.example.dreamteamdisasterrecovery",
	// // "com.example.dreamteamdisasterrecovery.CameraTwoActivity");
	// // startActivity(i);
	// picture();
	//
	// } else if (v.getId() == (save.getId())) {
	// String eventYear = date.getOnItemSelectedListener().toString();
	// String requesterId = id.getText().toString();
	// String contactNumber = contactNum.getText().toString();
	// String buildingType =
	// propType.getOnItemSelectedListener().toString();
	// String workReqNumber = workRequest.getText().toString();
	// String comPowerIndicator =
	// commercialPower.getOnItemSelectedListener().toString();
	// String onGeneratorIndicator =
	// onGenerator.getOnItemSelectedListener().toString();
	// String c_Lead = creLead.getOnItemSelectedListener().toString();
	// String buildingStat =
	// buildingStatus.getOnItemSelectedListener().toString();
	// String unOccupiableIndicator =
	// unoccupiable.getOnItemSelectedListener().toString();
	// String mobCOIndicator = mobCO.getOnItemSelectedListener().toString();
	// String damageIndicator =
	// damage.getOnItemSelectedListener().toString();
	// String estCapCost = capital.getText().toString();
	// String estExpenseCost = expense.getText().toString();
	//
	// /******************************************************/
	// String elecIssueIndicator =
	// elecIssue.getOnItemSelectedListener().toString();
	// String elecIssueClsdIndicator =
	// elecClosed.getOnItemSelectedListener().toString();
	// String envIssueIndicator =
	// envirIssue.getOnItemSelectedListener().toString();
	// String envIssueClsdIndicator =
	// envirClosed.getOnItemSelectedListener().toString();
	// String fenceGateIssueIndicator =
	// fgIssue.getOnItemSelectedListener().toString();
	// String fenceGateIssueClsdIndicator =
	// fgClosed.getOnItemSelectedListener().toString();
	// String genIssueIndicator =
	// genIssue.getOnItemSelectedListener().toString();
	// String genIssueClsdIndicator =
	// genClosed.getOnItemSelectedListener().toString();
	// String waterIssueIndicator =
	// waterIssue.getOnItemSelectedListener().toString();
	// String waterIssueClsdIndicator =
	// waterClosed.getOnItemSelectedListener().toString();
	// String groundsIssueIndicator =
	// groundsIssue.getOnItemSelectedListener().toString();
	// String groundsIssueClsdIndicator =
	// groundsClosed.getOnItemSelectedListener().toString();
	// String mechIssueIndicator =
	// mechIssue.getOnItemSelectedListener().toString();
	// String mechIssueClsdIndicator =
	// mechClosed.getOnItemSelectedListener().toString();
	// String otherIssueIndicator =
	// otherIssue.getOnItemSelectedListener().toString();
	// String otherIssueClsdIndicator =
	// otherClosed.getOnItemSelectedListener().toString();
	// String plumbIssueIndicator =
	// plumbIssue.getOnItemSelectedListener().toString();
	// String plumbIssueClsdIndicator =
	// plumClosed.getOnItemSelectedListener().toString();
	// String roofsIssueIndicator =
	// roofIssue.getOnItemSelectedListener().toString();
	// String roofsIssueClsdIndicator =
	// roofClosed.getOnItemSelectedListener().toString();
	// String safetyIssueIndicator =
	// safeIssue.getOnItemSelectedListener().toString();
	// String safetyIssueClsdIndicator =
	// safeClosed.getOnItemSelectedListener().toString();
	// String structIssueIndicator =
	// structIssue.getOnItemSelectedListener().toString();
	// String structIssueClsdIndicator =
	// structClosed.getOnItemSelectedListener().toString();
	//
	// String assessNotes = assessment.getText().toString();
	// String statusNotes = status.getText().toString();
	// String compltnDate = compDate.getText().toString();
	// String incidentNotes = notes.getText().toString();
	// String incidentStatus =
	// rep_OC.getOnItemSelectedListener().toString();
	//
	// // save all the fields for this new incident
	// // aIncident.setGeoLoc(theZip);
	// aIncident.setInitialRptDate(eventYear);
	// aIncident.setReqATTUID(requesterId);
	// aIncident.setContactPhone(contactNumber);
	// aIncident.setBuildingType(buildingType);
	// aIncident.setWorkReqNumber(workReqNumber);
	// aIncident.setComPowerIndicator(comPowerIndicator);
	// aIncident.setOnGeneratorIndicator(onGeneratorIndicator);
	// aIncident.setCreLead(c_Lead);
	// aIncident.setBuildingStatus(buildingStat);
	// aIncident.setUnOccupiableIndicator(unOccupiableIndicator);
	// aIncident.setMobCOIndicator(mobCOIndicator);
	// aIncident.setDamageIndicator(damageIndicator);
	// aIncident.setEstCapCost(Integer.parseInt(estCapCost));
	// aIncident.setEstExpenseCost(Integer.parseInt(estExpenseCost));
	// /**********************************************************/
	// aIncident.setElecIssueIndicator(elecIssueIndicator);
	// aIncident.setElecIssueClsdIndicator(elecIssueClsdIndicator);
	// aIncident.setEnvIssueIndicator(envIssueIndicator);
	// aIncident.setEnvIssueClsdIndicator(envIssueClsdIndicator);
	// aIncident.setFenceGateIssueIndicator(fenceGateIssueIndicator);
	// aIncident.setFenceGateIssueClsdIndicator(fenceGateIssueClsdIndicator);
	// aIncident.setGenIssueIndicator(genIssueIndicator);
	// aIncident.setGenIssueClsdIndicator(genIssueClsdIndicator);
	// aIncident.setWaterIssueIndicator(waterIssueIndicator);
	// aIncident.setWaterIssueClsdIndicator(waterIssueClsdIndicator);
	// aIncident.setGroundsIssueIndicator(groundsIssueIndicator);
	// aIncident.setGroundsIssueClsdIndicator(groundsIssueClsdIndicator);
	// aIncident.setMechIssueIndicator(mechIssueIndicator);
	// aIncident.setMechIssueClsdIndicator(mechIssueClsdIndicator);
	// aIncident.setOtherIssueIndicator(otherIssueIndicator);
	// aIncident.setOtherIssueClsdIndicator(otherIssueClsdIndicator);
	// aIncident.setPlumbIssueIndicator(plumbIssueIndicator);
	// aIncident.setPlumbIssueClsdIndicator(plumbIssueClsdIndicator);
	// aIncident.setRoofsIssueIndicator(roofsIssueIndicator);
	// aIncident.setRoofsIssueClsdIndicator(roofsIssueClsdIndicator);
	// aIncident.setSafetyIssueIndicator(safetyIssueIndicator);
	// aIncident.setSafetyIssueClsdIndicator(safetyIssueClsdIndicator);
	// aIncident.setStructIssueIndicator(structIssueIndicator);
	// aIncident.setStructIssueClsdIndicator(structIssueClsdIndicator);
	//
	// aIncident.setAssessNotes(assessNotes);
	// aIncident.setStatusNotes(statusNotes);
	// aIncident.setCompltnDate(compltnDate);
	// aIncident.setIncidentNotes(incidentNotes);
	// aIncident.setIncidentStatus(incidentStatus);
	//
	// // how to save the incident?
	// try {
	// test.addIncident(MainActivity.sessionToken, aIncident);
	// success.show();
	// GLCSearchActivity.aIncid = aIncident;
	// Intent i = new Intent();
	// i.setClassName("com.example.dreamteamdisasterrecovery",
	// "com.example.dreamteamdisasterrecovery.GLCSearchActivity");
	// startActivity(i);
	// } catch (TokenInvalidException e) {
	// fail1.show();
	// e.printStackTrace();
	// } catch (GeolocInvalidException e) {
	// fail2.show(); // goes here
	// e.printStackTrace();
	// }
	// } else { // cancel button
	// Intent i = new Intent();
	// i.setClassName("com.example.dreamteamdisasterrecovery",
	// "com.example.dreamteamdisasterrecovery.GLCSearchActivity");
	// // Start GLC search activity screen
	// startActivity(i);
	// }
	//
	// }
	
	public void picture() {
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		
		startActivityForResult(cameraIntent, 0);
		Log.d("UpdateDamage", "Camera End");
	}
	
	/**
	 * Saves the picture after a picture is taken
	 * 
	 * @param requestCode
	 *            the request code of the Activity
	 * @param resultCode
	 *            the result code of the Activity
	 * @param data
	 *            the data returned by the Activity
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			Log.d("UpdateDamage", "Camera Data Recieved");
			if (data != null && data.getExtras() != null) {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				MediaStore.Images.Media.insertImage(m.getContentResolver(), thumbnail, "" + inc.getRecNumber(), "Incident Picture");
			}
		}
		Toast picture = Toast.makeText(context, "Picture taken!", Toast.LENGTH_SHORT);
		picture.show();
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
}

class ScrollViewCompatibleDatePicker extends DatePicker {
	public ScrollViewCompatibleDatePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public ScrollViewCompatibleDatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ScrollViewCompatibleDatePicker(Context context) {
		super(context);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		/*
		 * Prevent parent controls from stealing our events once we've
		 * gotten a touch down
		 */
		if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
			ViewParent p = getParent();
			if (p != null) p.requestDisallowInterceptTouchEvent(true);
		}
		
		return false;
	}
}