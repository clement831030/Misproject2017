package clement.project_main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static int mYear, mMonth, mDay;
    private static int now_year,now_month,now_day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        now_year = c.get(Calendar.YEAR);
        now_month = c.get(Calendar.MONTH);
        now_day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, now_year, now_month, now_day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);  //能夠選擇的最小時間是現在的前一秒
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView date_tv = (TextView) getActivity().findViewById(R.id.date_tv);
        date_tv.setText(year + "-" + (month + 1) + "-" + day);    //月份要+1
        mYear = year;
        mMonth = month;
        mDay = day;
    }
}

