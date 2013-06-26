package com.teamuniverse.drtmobile.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.teamuniverse.drtmobile.LogonActivity;
import com.teamuniverse.drtmobile.R;

public class LayoutSetterUpper {
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
}
