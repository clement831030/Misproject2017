package clement.project_main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import clement.project_main.Data.Post;
import clement.project_main.Data.User;

public class AddFindPassenger extends AppCompatActivity {

    //宣告元件
    private Toolbar toolBar;
    private ImageView background_iv;
    private ImageButton send_ib, delete_ib;
    private EditText from_et, to_et, note_et,price_et;
    private TextView date_tv, time_tv;
    private Spinner people_sp;
    private RadioButton car_rb,motor_rb;
    private RadioGroup vehicle_rg;
    private ArrayAdapter<String> people_adapter;
    String people,price,vehicle;
    private int people_spinnerCount = 1;
    private static int mYear, mMonth, mDay;
    private static int now_year,now_month,now_day;
    private static int mHour,mMinute;
    private static int now_hour,now_minute;
    String Uri;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfindpassenger);
        initView();
        setDate();
        setTime();
        setToolBar();

        people_adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,new String[]{"1","2","3"});
        //建立一個選項為String的ArrayAdapter物件
        //三個參數依序代表(這個activity元件,自訂Spinner介面未展開時的View,選項項目資料)
        people_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item); //自訂spinner展開後選單的樣式
        people_sp.setAdapter(people_adapter);               //將people_adapter選單內容給people_sp這個元件
        people_sp.setOnItemSelectedListener(peopleListener);

        //按下「清除」跳出對話框
        final AlertDialog alertDialog = onDeleteClicked("確定要清除嗎？");
        delete_ib.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(final View view) {
                alertDialog.show();             //顯示對話框
            }
        });

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();

        String userUID = u.getUid();


        mDatabase.child("User").child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                Uri = user.getUri();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //交通工具
        vehicle_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.car_rb) {
                    vehicle = "汽車";
                } else if (checkedId == R.id.motor_rb) {
                    vehicle = "機車";
                }
            }
        });
    }

    private void initView() {
        background_iv = (ImageView) findViewById(R.id.background_iv);
        from_et = (EditText) findViewById(R.id.from_et);
        to_et = (EditText) findViewById(R.id.to_et);
        date_tv = (TextView) findViewById(R.id.date_tv);
        time_tv = (TextView) findViewById(R.id.time_tv);
        people_sp = (Spinner) findViewById(R.id.people_sp);
        price_et = (EditText) findViewById(R.id.price_et);
        note_et = (EditText) findViewById(R.id.note_et);
        send_ib = (ImageButton) findViewById(R.id.send_ib);
        delete_ib = (ImageButton) findViewById(R.id.delete_ib);
        car_rb = (RadioButton)findViewById(R.id.car_rb);
        motor_rb = (RadioButton)findViewById(R.id.motor_rb);
        vehicle_rg = (RadioGroup)findViewById(R.id.vehicle_rg);
    }

    //設定toolbar
    private void setToolBar(){
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);   //顯示返回上一頁的按鈕
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //From驗證
    private boolean FromIsValid(EditText et) {
        String From_value = et.getText().toString();
        if(From_value == null || From_value.trim().equals("")) {
            et.setError("請輸入出發地！");
            return false;
        } else {
            return true;
        }
    }

    //To驗證
    private boolean ToIsValid(EditText et) {
        String To_value = et.getText().toString();
        if(To_value == null || To_value.trim().equals("")) {
            et.setError("請輸入目的地！");
            return false;
        } else {
            return true;
        }
    }

    //Price驗證
    private boolean PriceIsValid(EditText et) {
        String Price_value = et.getText().toString();
        if(Price_value == null || Price_value.trim().equals("")) {
            et.setError("請輸入價錢！");
            return false;
        } else {
            return true;
        }
    }

    //Date設定
    private void setDate(){
        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                now_year = c.get(Calendar.YEAR);
                now_month = c.get(Calendar.MONTH);
                now_day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddFindPassenger.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        TextView date_tv = (TextView) AddFindPassenger.this.findViewById(R.id.date_tv);
                        date_tv.setText(year + "-" + (month + 1) + "-" + day);    //月份要+1
                        mYear = year;
                        mMonth = month;
                        mDay = day;
                    }
                },now_year, now_month, now_day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);  //能夠選擇的最小時間是現在的前一秒
                dialog.show();
            }
        });
    }


    //Time設定
    private void setTime(){
        time_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                now_hour = c.get(Calendar.HOUR_OF_DAY);
                now_minute = c.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(AddFindPassenger.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute){
                        TextView time_tv = (TextView) AddFindPassenger.this.findViewById(R.id.time_tv);

                        if (mYear == now_year && mMonth == now_month && mDay==now_day) {
                            if(mHour < now_hour){
                                Toast.makeText(AddFindPassenger.this, "請輸入未來時間", Toast.LENGTH_SHORT).show();
                            }
                            else if((mHour ==  now_hour) && (mMinute < now_minute)){
                                Toast.makeText(AddFindPassenger.this, "請輸入未來時間", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                time_tv.setText(String.format("%02d:%02d", hour, minute));
                            }
                        }
                        else {
                            time_tv.setText(String.format("%02d:%02d", hour, minute));
                        }
                        mHour=hour;
                        mMinute=minute;
                    }
                },now_hour, now_minute, false);
                dialog.show();
            }
        });
    }


    //Date驗證
    private boolean dateIsValid(TextView tv) {
        String Date_pattern = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";
        String Date_value = tv.getText().toString();
        if(!Date_value.matches(Date_pattern)){
            tv.setError("請輸入日期！");
            return false;
        } else {
            return true;
        }
    }

    //Time驗證
    private boolean timeIsValid(TextView tv) {
        String Time_pattern = "([01]?[0-9]|2[0-3]):[0-9]{2}";
        String Time_value = tv.getText().toString();
        if(!Time_value.matches(Time_pattern)) {
            tv.setError("請輸入時間！");
            return false;
        } else {
            return true;
        }
    }

    //People監聽
    private Spinner.OnItemSelectedListener peopleListener= new Spinner.OnItemSelectedListener()
    {
        //如果被選擇
        public void onItemSelected(AdapterView<?>adapterView, View v, int position, long id)  //參數依序代表：(使用者操作的spinner元件,使用者選擇的項目,使用者選擇的項目編號,無用途)
        {
            people = (String)adapterView.getItemAtPosition(position);
        }
        //如果沒有選擇任何項目
        public void onNothingSelected(AdapterView<?>adapterView)
        {
            Toast.makeText(AddFindPassenger.this, "請選擇人數", Toast.LENGTH_SHORT).show();
        }
    };

    //交通工具驗證
    private boolean VehicleIsValid() {
        if(vehicle_rg.getCheckedRadioButtonId()<=0){
            motor_rb.setError("請選擇");
            return false;
        }else
            return true;
    }
    //按下send_ib先檢查是否都通過驗證再傳遞資料
    public void onSendClick(View view) {
        if(people == null){
            Toast.makeText(AddFindPassenger.this, "請選擇人數", Toast.LENGTH_SHORT).show();
        }
        boolean allValid = FromIsValid(from_et) & ToIsValid(to_et) & timeIsValid(time_tv) & dateIsValid(date_tv) & people!=null & PriceIsValid(price_et) & VehicleIsValid();;
        if (!allValid) {return;}                                     //如果沒有全部通過驗證就返回

        addData(from_et.getText().toString(), to_et.getText().toString(), date_tv.getText().toString(), time_tv.getText().toString(), people,note_et.getText().toString(),price_et.getText().toString(),vehicle);
        /*
        Intent intent = new Intent(this,MyFindPassenger.class);          //new一個intent物件，指定Activity切換到MyFindPassenger
        Bundle bundle = new Bundle();                                //new一個Bundle物件，將要傳遞的資料傳入
        bundle.putString("from_key", from_et.getText().toString());  //(KEY,value)
        bundle.putString("to_key", to_et.getText().toString());
        bundle.putString("date_key", date_tv.getText().toString());
        bundle.putString("time_key", time_tv.getText().toString());
        bundle.putString("people_key", people);
        bundle.putString("price_key", price_et.getText().toString());
        bundle.putString("note_key", note_et.getText().toString());
        intent.putExtras(bundle);                                    //將Bundle物件assign給intent
        startActivity(intent);                                       //切換Activity
        */
        Intent intent = new Intent(AddFindPassenger.this,MainActivity.class);
        startActivity(intent);
    }

    // 按下delete_ib跳出AlertDialog詢問
    private AlertDialog onDeleteClicked(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFindPassenger.this);    //產生一個Builder物件
        builder.setMessage(message);                                    //設定Dialog的訊息，在onCreate裡
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  Intent intent = getIntent();
                finish();
                startActivity(getIntent());                 //直接重新載入activity，解決無法消除驚嘆號的問題
                //  intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);    //不要換頁動畫 （無用）
             /*
                //按下確定時，清空所有欄位
                from_et.setText("");
                to_et.setText("");
                date_tv.setText("選擇日期");
                time_tv.setText("選擇時間");
                price_sp.setSelection(0);
                people_sp.setSelection(0);
                note_et.setText("");
            */
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //按下取消時，關閉對話框
                dialog.cancel();
            }
        });
        return builder.create();             //利用Builder物件建立AlertDialog
    }

    private void addData(String from,String to,String date,String time,String people,String note,String price,String vehicle) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        String userUID= u.getUid().toString();
        Post Post=new Post();
        String key = mDatabase.child("FindPassenger").push().getKey();

        Post.setFrom(from);
        Post.setTo(to);
        Post.setDate(date);
        Post.setTime(time);
        Post.setPeople(people);
        Post.setNote(note);
        Post.setPrice(price);
        Post.setUID(userUID);
        Post.setVehicle(vehicle);
        Post.setUri(user.getUri());
        Post.setName(user.getName());
        Post.setKey(key);


        Map<String, Object> Map = new HashMap<>();
        Map.put("from", Post.getFrom());
        Map.put("to", Post.getTo());
        Map.put("date", Post.getDate());
        Map.put("time", Post.getTime());
        Map.put("people", Post.getPeople());
        Map.put("note",Post.getNote());
        Map.put("price",Post.getPrice());
        Map.put("UID",userUID);
        Map.put("vehicle",vehicle);
        Map.put("Uri",Post.getUri());
        Map.put("name",Post.getName());
        Map.put("key",Post.getKey());
        mDatabase.child("FindPassenger").child(key).updateChildren(Map);
        mDatabase.child("History").child(userUID).child(key).updateChildren(Map);
    }
}
