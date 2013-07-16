package com.teamuniverse.drtmobile;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.att.intern.webservice.Incident;
import com.att.intern.webservice.Webservice;
import com.att.intern.webservice.Webservice.TokenInvalidException;
import com.teamuniverse.drtmobile.sectionsetup.SectionDetailActivity;
import com.teamuniverse.drtmobile.sectionsetup.SectionListActivity;
import com.teamuniverse.drtmobile.support.DatabaseManager;
import com.teamuniverse.drtmobile.support.IncidentHelper;
import com.teamuniverse.drtmobile.support.IncidentInfo;
import com.teamuniverse.drtmobile.support.SectionAdder;
import com.teamuniverse.drtmobile.support.SetterUpper;

/**
 * A fragment representing a single Section detail screen. This fragment is
 * either contained in a {@link SectionListActivity} in two-pane mode (on
 * tablets) or a {@link SectionDetailActivity} on handsets.
 */
public class IncidentRecNumResultsFragment extends Fragment {
	private static final String[]	STATE_NAMES		= { "Alabama", "Alaska", "American Samoa", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Federated States of Micronesia", "Florida", "Georgia", "Guam", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Marshall Islands", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Northern Mariana Islands", "Ohio", "Oklahoma", "Oregon", "Palau", "Panama Canal Zone", "Pennsylvania", "Philippine Islands", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Trust Territory of the Pacific Islands", "U.S. Armed Forces – Americas", "U.S. Armed Forces – Europe", "U.S. Armed Forces – Pacific", "Utah", "Vermont", "Virgin Islands", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" };
	private static final String[]	STATE_POSTALS	= { "AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FM", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "CZ", "PA", "PI", "PR", "RI", "SC", "SD", "TN", "TX", "TT", "AA", "AE", "AP", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY" };
	
	/** The shortcut to the current activity */
	private static Activity			m;
	/** The progress bar that is shown to indicate background processes */
	private static ProgressBar		progress;
	/** A boolean that will stop many clicks from starting a bunch of threads */
	private static boolean			querying;
	/** The handler that will allow the multi-threading */
	private Handler					handler;
	private DatabaseManager			db;
	
	private final int				COLUMNS			= 2;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public IncidentRecNumResultsFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		m = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_incident_rec_num_results, container, false);
		SetterUpper.setup(m, view);
		
		progress = (ProgressBar) view.findViewById(R.id.progress);
		querying = false;
		handler = new Handler();
		
		db = new DatabaseManager(m);
		db.sessionUnset("goto_tab");
		db.close();
		
		Button backButton = (Button) view.findViewById(R.id.back_button);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				db = new DatabaseManager(m);
				db.sessionSet("back", "true");
				String which = db.sessionGet("from");
				db.sessionUnset("from");
				db.close();
				if (which.equals("incident")) SectionListActivity.m.putSection(SectionAdder.INCIDENT_ZIP_RESULTS);
				else SectionListActivity.m.putSection(SectionAdder.INCIDENT_REC_NUM_SEARCH);
			}
		});
		
		LinearLayout list = (LinearLayout) view.findViewById(R.id.list_container);
		search(list);
		
		return view;
	}
	
	Incident	result	= null;
	boolean		success	= false;
	
	private void search(LinearLayout containerRef) {
		final LinearLayout container = containerRef;
		// Get contents of the EditTexts
		if (!querying) {
			querying = true;
			new Thread(new Runnable() {
				public void run() {
					Webservice ws = new Webservice(m);
					
					db = new DatabaseManager(m);
					String token = db.sessionGet("token");
					int recordNumber = (int) Long.parseLong(db.sessionGet("record_number"));
					db.close();
					
					try {
						result = ws.incidByRecNum(token, recordNumber);
						success = true;
					} catch (TokenInvalidException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					handler.postDelayed(new Runnable() {
						public void run() {
							// Hide the progress bar
							progress.setVisibility(View.GONE);
							
							TextView temp;
							LinearLayout each;
							if (success) {
								if (result == null) {
									temp = new TextView(m);
									temp.setText(R.string.no_record);
									temp.setGravity(Gravity.CENTER_HORIZONTAL);
									container.addView(temp);
								} else {
									try {
										IncidentInfo[] infos = IncidentHelper.getInfos(result);
										for (int i = 0; i < infos.length; i++) {
											if (i != 0) m.getLayoutInflater().inflate(R.layout.divider_line, container);
											
											each = new LinearLayout(m);
											each.setPadding(0, 6, 0, 6);
											each.setOrientation(LinearLayout.HORIZONTAL);
											each.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
											
											for (int j = 0; j < COLUMNS; j++) {
												temp = new TextView(m);
												temp.setGravity(Gravity.CENTER);
												temp.setLayoutParams(new TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
												if (j == 0) temp.setText(infos[i].getDescriptor());
												else if (j == 1) {
													try {
														temp.setText((String) infos[i].getValue());
													} catch (ClassCastException e) {
														temp.setText((Integer) infos[i].getValue() + "");
													}
													each.setTag(R.id.field_label, temp);
												}
												each.addView(temp);
											}
											
											if (i % 2 == 1) {
												each.setBackgroundColor(Color.rgb(220, 220, 220));
												each.setTag(R.string.default_color, "color");
											} else each.setTag(R.string.default_color, "none");
											
											int which = infos[i].getId();
											switch (which) {
												case IncidentInfo.RECORD_NUMBER:
													break;
												default:
													each.setClickable(true);
													
													each.setTag(R.string.edit_in_place, infos[i]);
													
													each.setOnTouchListener(new OnTouchListener() {
														@Override
														public boolean onTouch(View v, MotionEvent event) {
															switch (event.getAction()) {
																case MotionEvent.ACTION_DOWN:
																	SetterUpper.setSelected(m, v);
																	break;
																case MotionEvent.ACTION_MOVE:
																	SetterUpper.setSelected(m, v);
																	break;
																case MotionEvent.ACTION_CANCEL:
																	if (v.getTag(R.string.default_color).equals("color")) SetterUpper.setUnSelected(m, v, false);
																	else SetterUpper.setUnSelected(m, v, true);
																	break;
																case MotionEvent.ACTION_UP:
																	if (v.getTag(R.string.default_color).equals("color")) SetterUpper.setUnSelected(m, v, false);
																	else SetterUpper.setUnSelected(m, v, true);
																	editInPlace(m, result, (IncidentInfo) v.getTag(R.string.edit_in_place), (TextView) v.getTag(R.id.field_label));
																	break;
															}
															return true;
														}
													});
											}
											container.addView(each);
										}
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							} else SetterUpper.timedOut(m);
							
						}
					}, 0);
				}
			}).start();
		}
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
	
	/** A boolean that will stop many clicks from starting a bunch of threads */
	private static boolean	editInPlaceQuerying	= false;
	private static boolean	editInPlaceFilling	= false;
	private static String	newDateData			= "";
	private static String	newSpinnerData		= "";
	
	public static void editInPlace(Activity activity, Incident incident, IncidentInfo current, TextView infoFieldInList) {
		final Activity m = activity;
		String oldContents;
		if (!editInPlaceFilling) {
			oldContents = infoFieldInList.getText().toString();
			editInPlaceFilling = true;
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(m);
			// 2. Chain together various methods to set the dialog
			// characteristics
			LayoutInflater inflater = m.getLayoutInflater();
			View view = inflater.inflate(R.layout.dialog_edit_in_place, null);
			
			ProgressBar progress = (ProgressBar) view.findViewById(R.id.progress);
			EditText newText = (EditText) view.findViewById(R.id.new_text);
			Spinner newSpin = (Spinner) view.findViewById(R.id.new_toggle);
			DatePicker newDate = (DatePicker) view.findViewById(R.id.new_date);
			
			((TextView) view.findViewById(R.id.field_label)).setText(current.getDescriptor());
			
			int which = current.getId();
			final String type = current.getType();
			final int limit = current.getMaxLength();
			boolean multiline = false;
			if (type.equals("OOC") || type.equals("YON") || type.equals("STA") || type.equals("OCD") || type.equals("BDG")) {
				ArrayAdapter<CharSequence> adapter;
				if (type.equals("STA")) {
					adapter = new ArrayAdapter<CharSequence>(m, android.R.layout.simple_spinner_item);
					adapter.addAll(STATE_NAMES);
				} else if (type.equals("YON") || type.equals("OOC")) {
					adapter = ArrayAdapter.createFromResource(m, type.equals("OOC")	? R.array.OOC
																					: R.array.YON, android.R.layout.simple_spinner_item);
				} else if (type.equals("OCD")) {
					adapter = ArrayAdapter.createFromResource(m, R.array.building_status_array, android.R.layout.simple_spinner_item);
					
				} else {
					adapter = ArrayAdapter.createFromResource(m, R.array.property_type, android.R.layout.simple_spinner_item);
					
				}
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				newSpin.setAdapter(adapter);
				
				// Set the default selected position!!!
				if (type.equals("STA")) {
					for (int i = 0; i < STATE_NAMES.length; i++) {
						if (oldContents.equals(STATE_POSTALS[i])) {
							newSpin.setSelection(i);
							break;
						}
					}
				} else {
					if (oldContents.equals("Y") || oldContents.toLowerCase(Locale.getDefault()).equals("open")) {
						newSpin.setSelection(0);
					} else newSpin.setSelection(1);
				}
				
				newSpin.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View view, int pos, long id) {
						if (type.equals("STA")) {
							newSpinnerData = STATE_POSTALS[pos];
						} else {
							newSpinnerData = pos == 0	? type.equals("YON") ? "Y"
																			
																			: "Open"
														: type.equals("YON") ? "N"
																			: "Closed";
						}
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
				newText.setVisibility(View.GONE);
				newDate.setVisibility(View.GONE);
			} else if (type.equals("DAT")) {
				newDateData = oldContents;
				newText.setVisibility(View.GONE);
				newSpin.setVisibility(View.GONE);
				newDate.init((int) Long.parseLong(oldContents.substring(0, 4)), (int) Long.parseLong(oldContents.substring(5, 7)) - 1, (int) Long.parseLong(oldContents.substring(8)), new OnDateChangedListener() {
					@Override
					public void onDateChanged(DatePicker arg0, int y, int m, int d) {
						newDateData = y + "-" + (m < 9 ? "0" : "") + (m + 1) + "-" + (d < 10 ? "0"
																							: "") + d;
					}
				});
			} else {
				if (which == 0 || which == 25 || which == 45) {
					multiline = true;
					newText.setMinLines(1);
				} else {
					newText.setImeActionLabel(m.getString(R.string.go), EditorInfo.IME_ACTION_GO);
					if (type.equals("STR")) {
						newText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
					} else if (type.equals("NUM")) {
						newText.setInputType(InputType.TYPE_CLASS_NUMBER);
					}
				}
				newSpin.setVisibility(View.GONE);
				newDate.setVisibility(View.GONE);
				if (which == 7) newText.setText(oldContents.replaceAll("-", ""));
				else newText.setText(oldContents);
				if (limit != 0) {
					InputFilter[] filters = new InputFilter[1];
					filters[0] = new InputFilter.LengthFilter(limit);
					newText.setFilters(filters);
				}
			}
			
			final AlertDialog dialog = builder.setView(view).setTitle(R.string.edit_in_place).setPositiveButton(R.string.go, null).setNegativeButton(R.string.cancel, null).create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			
			if (!multiline) {
				/*
				 * This sets the editor listener for the EditText; it clicks the
				 * go button when enter is pressed
				 */
				newText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
							dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
							return true;
						} else return false;
					}
				});
			}
			
			(dialog.getButton(DialogInterface.BUTTON_POSITIVE)).setOnClickListener((new IncidentRecNumResultsFragment()).new EditInPlaceDialogListener(dialog, dialog.getButton(DialogInterface.BUTTON_NEGATIVE), m, incident, current, progress, newText, newSpin, newDate, infoFieldInList));
			(dialog.getButton(DialogInterface.BUTTON_NEGATIVE)).setOnClickListener(new OnClickListener() {
				private Dialog	dialog;
				
				public OnClickListener setDialog(Dialog dialog) {
					this.dialog = dialog;
					return this;
				}
				
				@Override
				public void onClick(View v) {
					if (!editInPlaceQuerying) {
						editInPlaceFilling = false;
						dialog.dismiss();
					}
				}
			}.setDialog(dialog));
		}
	}
	
	class EditInPlaceDialogListener implements View.OnClickListener {
		private final Dialog		dialog;
		private final Activity		m;
		private final Incident		incident;
		private final int			which;
		private final ProgressBar	progress;
		private final EditText		newInfoField;
		private final Handler		handler;
		private final String		type;
		private final TextView		infoFieldInList;
		private final String		oldContents;
		private View[]				toDisable;
		
		public EditInPlaceDialogListener(Dialog dialog, Button negButton, Activity m, Incident incident, IncidentInfo current, ProgressBar progress, EditText newInfoField, Spinner newInfoSpinner, DatePicker newInfoDatePicker, TextView infoFieldInList) {
			this.dialog = dialog;
			this.m = m;
			this.incident = incident;
			this.which = current.getId();
			this.type = current.getType();
			this.progress = progress;
			this.newInfoField = newInfoField;
			this.handler = new Handler();
			this.infoFieldInList = infoFieldInList;
			this.oldContents = infoFieldInList.getText().toString();
			this.toDisable = new View[] { negButton, newInfoField, newInfoSpinner, newInfoDatePicker, null };
		}
		
		private boolean	success	= false;
		private boolean	timed	= false;
		
		@Override
		public void onClick(View v) {
			toDisable[4] = v;
			SetterUpper.hideKeys(m);
			String maybeNewContents = newInfoField.getText().toString();
			String newContentsTempContainer;
			if (type.equals("DAT")) newContentsTempContainer = newDateData;
			else if (type.equals("OOC") || type.equals("YON") || type.equals("STA") || type.equals("OCD") || type.equals("BDG")) newContentsTempContainer = newSpinnerData;
			else {
				newContentsTempContainer = maybeNewContents;
				if (which == IncidentInfo.CONTACT_PHONE_NUMBER && newContentsTempContainer.length() == 10) newContentsTempContainer = newContentsTempContainer.substring(0, 3) + "-" + newContentsTempContainer.substring(3, 6) + "-" + newContentsTempContainer.substring(6);
			}
			if (!editInPlaceQuerying) {
				editInPlaceQuerying = true;
				
				final String newContents = newContentsTempContainer;
				String message = IncidentHelper.isValidInfoForField(incident, which, newContents);
				if (message.equals("")) {
					for (int i = 0; i < toDisable.length; i++)
						toDisable[i].setEnabled(false);
					progress.setVisibility(View.VISIBLE);
					
					new Thread(new Runnable() {
						public void run() {
							IncidentHelper.setFieldByLabel(incident, which, newContents);
							
							DatabaseManager db = new DatabaseManager(m);
							String token = db.sessionGet("token");
							db.close();
							
							Webservice ws = new Webservice(m.getApplicationContext());
							try {
								ws.updateIncidentbyRecnum(token, incident);
								success = true;
							} catch (TokenInvalidException e) {
								IncidentHelper.setFieldByLabel(incident, which, oldContents);
								timed = true;
							} catch (Exception e) {
								IncidentHelper.setFieldByLabel(incident, which, oldContents);
								e.printStackTrace();
							}
							
							// Use the handler to execute a Runnable on the
							// m thread in order to have access to the
							// UI elements.
							handler.postDelayed(new Runnable() {
								
								public void run() {
									// Hide the progress bar
									if (timed) SetterUpper.timedOut(m);
									try {
										progress.setVisibility(View.INVISIBLE);
										editInPlaceQuerying = false;
										editInPlaceFilling = false;
										for (int i = 0; i < toDisable.length; i++)
											toDisable[i].setEnabled(true);
										if (success) {
											infoFieldInList.setText(newContents);
											Toast.makeText(m, "Successfully updated!", Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(m, "Failed to update!", Toast.LENGTH_SHORT).show();
										}
										dialog.dismiss();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}, 0);
						}
					}).start();
				} else {
					editInPlaceQuerying = false;
					editInPlaceFilling = false;
					
					new AlertDialog.Builder(m).setTitle(R.string.edit_in_place_error).setMessage(message).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
						}
					}).create().show();
				}
			}
		}
	}
}