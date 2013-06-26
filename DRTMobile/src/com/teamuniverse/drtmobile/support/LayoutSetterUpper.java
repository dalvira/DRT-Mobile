package com.teamuniverse.drtmobile.support;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.teamuniverse.drtmobile.R;

public class LayoutSetterUpper {
	public static void setup(Activity m, View view) {
		// Set up the attuid and authorization displayer
		DatabaseManager db = new DatabaseManager(m);
		((TextView) view.findViewById(R.id.attuid)).setText(db.sessionGet("attuid"));
		((TextView) view.findViewById(R.id.authorization)).setText(db.sessionGet("authorization"));
		db.close();
	}
}
