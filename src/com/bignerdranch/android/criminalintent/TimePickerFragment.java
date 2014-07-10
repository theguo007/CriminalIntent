package com.bignerdranch.android.criminalintent;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;


public class TimePickerFragment extends DialogFragment {
	
	View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
	
	@Override
	public Dialog onCreateDialog(Bundle onSavedInstance) {
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.time_picker_title)
			.setPositiveButton(android.R.string.ok, null)
			.create();
	}
}
