package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public class DialogPicker extends DialogFragment {
	private Crime mCrime;
	private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    
    public static DialogPicker newInstance(UUID crime) {
		Bundle args = new Bundle();
		args.putSerializable(CrimeFragment.EXTRA_CRIME_ID, crime);
		
		DialogPicker fragment = new DialogPicker();
		fragment.setArguments(args);
		
		return fragment;
	}
    
	@Override
	public Dialog onCreateDialog(Bundle onSavedInstanceState) {
		UUID crimeId = (UUID)getArguments().getSerializable(CrimeFragment.EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
		return new AlertDialog.Builder(getActivity())
			.setTitle(R.string.choose)
			.setPositiveButton(R.string.date, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					FragmentManager fm = getActivity().getSupportFragmentManager();
					DatePickerFragment mDialog = DatePickerFragment.newInstance(mCrime.getDate());
	                mDialog.setTargetFragment(DialogPicker.this, REQUEST_DATE);
	                mDialog.show(fm, DIALOG_DATE);					
				}
			})
			.setNegativeButton(R.string.time, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					FragmentManager fm = getActivity().getSupportFragmentManager();
					TimePickerFragment mDialog = TimePickerFragment.newInstance(mCrime.getDate());
					mDialog.setTargetFragment(DialogPicker.this, REQUEST_TIME);
					mDialog.show(fm, DIALOG_DATE);
				}
			})
			.create();
	}
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data
                .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, null);
        }
        if (requestCode == REQUEST_TIME) {
        	Date date = (Date)data
        		.getSerializableExtra(TimePickerFragment.EXTRA_DATE);
        	mCrime.setDate(date);
        	getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, null);
        }
    }
}
