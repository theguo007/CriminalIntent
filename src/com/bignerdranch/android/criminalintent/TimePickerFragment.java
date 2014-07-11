package com.bignerdranch.android.criminalintent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;


public class TimePickerFragment extends DialogFragment {
	public static final String EXTRA_DATE =
	        "com.bignerdranch.android.criminalintent.time";
	private int year;
	private int month;
	private int date;
	private Date mDate;
	private Calendar calendar;
	
	public static TimePickerFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
	        return;
	    calendar.setTime(mDate);
	    mDate = new GregorianCalendar(year, month, date, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 0).getTime();
	    
	    Intent i = new Intent();
	    i.putExtra(EXTRA_DATE, mDate);

	    getTargetFragment()
	        .onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle onSavedInstance) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
		mDate = (Date) getArguments().getSerializable(EXTRA_DATE);
		
		calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		year = calendar.get(Calendar.YEAR);
	    month = calendar.get(Calendar.MONTH);
	    date = calendar.get(Calendar.DAY_OF_MONTH);
	    
		TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_timePicker);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				mDate = new GregorianCalendar(0, 0, 0, hourOfDay, minute).getTime();
				getArguments().putSerializable(EXTRA_DATE, mDate);
			}
		});
		
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.time_picker_title)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    sendResult(Activity.RESULT_OK);
                }
			})
			.create();
	}
}
