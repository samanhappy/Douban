package com.coosam.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class TimePickerActivity extends Activity {

	private EditText timeEt = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timepicker);

		timeEt = (EditText) findViewById(R.id.editText1);
		TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker1);

		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				timeEt.setText("��ѡ���ʱ���ǣ�" + hourOfDay + "ʱ" + minute + "�֡�");
			}

		});
	}

}
