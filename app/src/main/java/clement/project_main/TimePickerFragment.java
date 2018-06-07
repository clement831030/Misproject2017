package clement.project_main;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private static int mHour,mMinute;
    private static int now_hour,now_minute;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar c = Calendar.getInstance();
        now_hour = c.get(Calendar.HOUR_OF_DAY);
        now_minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, now_hour, now_minute, false);
    }

    public void onTimeSet(TimePicker view, int hour, int minute){
        TextView time_tv = (TextView) getActivity().findViewById(R.id.time_tv);

        if (hour < now_hour) {
            Toast.makeText(getActivity(), "請輸入未來時間", Toast.LENGTH_SHORT).show();
        }
        else if (hour ==  now_hour && minute < now_minute ) {
            Toast.makeText(getActivity(), "請輸入未來時間", Toast.LENGTH_SHORT).show();
        }
        else {
            time_tv.setText(String.format("%02d:%02d", hour, minute));
        }
        mHour=hour;
        mMinute=minute;
    }
}