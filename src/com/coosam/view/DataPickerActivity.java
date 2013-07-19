package com.coosam.view;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;

public class DataPickerActivity extends Activity {

	private EditText dateEt=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datepicker);

		dateEt=(EditText)findViewById(R.id.editText1);
		DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		datePicker.init(year, monthOfYear, dayOfMonth,
				new OnDateChangedListener() {

					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						dateEt.setText("��ѡ��������ǣ�" + year + "��"
								+ (monthOfYear + 1) + "��" + dayOfMonth + "�ա�");
					}

				});
	}

}
