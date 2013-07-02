package com.teamuniverse.drtmobile.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.att.intern.webservice.Incident;
import com.teamuniverse.drtmobile.LogonActivity;
import com.teamuniverse.drtmobile.R;

public class SetterUpper {
	public static void setup(Activity m, View view) {
		// Set up the attuid and authorization displayer
		DatabaseManager db = new DatabaseManager(m);
		((TextView) view.findViewById(R.id.attuid)).setText(db.sessionGet("attuid"));
		((TextView) view.findViewById(R.id.authorization)).setText(db.sessionGet("authorization"));
		db.close();
	}
	
	public static void timedOut(Activity activity) {
		final Activity m = activity;
		// 1. Instantiate an AlertDialog.Builder with its
		// constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(m);
		// 2. Chain together various setter methods to set the
		// dialog
		// characteristics
		builder.setMessage(R.string.token_invalid).setTitle(R.string.token_invalid_title);
		// 3. Add an okay
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(m.getApplicationContext(), LogonActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				m.startActivity(intent);
				dialog.cancel();
			}
		});
	}
	
	public static IncidentInfoList getIncidentContents(Incident i) {
		// incident.getAssessNotes();
		// incident.getBuildingAddress();
		// incident.getBuildingName();
		// incident.getBuildingStatus();
		// incident.getBuildingType();
		// incident.getCompltnDate();
		// incident.getComPowerIndicator();
		// incident.getContactPhone();
		// incident.getCreLead();
		// incident.getDamageIndicator();
		// incident.getElecIssueClsdIndicator();
		// incident.getElecIssueIndicator();
		// incident.getEnvIssueClsdIndicator();
		// incident.getEnvIssueIndicator();
		// incident.getEstCapCost(0);
		// incident.getEstExpenseCost(0);
		// incident.getEventName();
		// incident.getFenceGateIssueClsdIndicator();
		// incident.getFenceGateIssueIndicator();
		// incident.getGenIssueClsdIndicator();
		// incident.getGenIssueIndicator();
		// incident.getGeoLoc(0);
		// incident.getGroundsIssueClsdIndicator();
		// incident.getGroundsIssueIndicator();
		// incident.getIncidentCompltnDate();
		// incident.getIncidentNotes();
		// incident.getIncidentStatus();
		// incident.getIncidentYear(0);
		// incident.getInitialRptDate();
		// incident.getMechIssueClsdIndicator();
		// incident.getMechIssueIndicator();
		// incident.getMobCOIndicator();
		// incident.getOnGeneratorIndicator();
		// incident.getOtherIssueClsdIndicator();
		// incident.getOtherIssueIndicator();
		// incident.getPlumbIssueClsdIndicator();
		// incident.getPlumbIssueIndicator();
		// incident.getPMAttuid();
		// incident.getRecNumber(0);
		// incident.getReqATTUID();
		// incident.getRoofsIssueClsdIndicator();
		// incident.getRoofsIssueIndicator();
		// incident.getSafetyIssueClsdIndicator();
		// incident.getSafetyIssueIndicator();
		// incident.getState();
		// incident.getStatusNotes();
		// incident.getStructIssueClsdIndicator();
		// incident.getStructIssueIndicator();
		// incident.getUnOccupiableIndicator();
		// incident.getWaterIssueClsdIndicator();
		// incident.getWaterIssueIndicator();
		// incident.getWorkReqNumber();
		return new IncidentInfoList("first", "filler");
	}
}
