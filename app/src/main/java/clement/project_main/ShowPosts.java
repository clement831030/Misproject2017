

package clement.project_main;

        import android.app.AlertDialog;
        import android.app.DatePickerDialog;
        import android.app.TimePickerDialog;
        import android.content.DialogInterface;
        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.support.v4.app.FragmentTransaction;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.DatePicker;
        import android.widget.ImageButton;
        import android.widget.Filter;
        import android.widget.Filterable;
//import android.support.v7.widget.SearchView;
        import android.widget.SearchView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.lang.String;

public class ShowPosts extends Fragment {

    private int currentIndex = 0;
    private ImageButton calltaxi, finddriver, findpassenger, filter_ib;
    private ArrayList<Fragment> fragmentArrayList;
    private Fragment mCurrentFragment;
    private SearchView searchView;
    // private TextView TVdate,TVtime;
    // private Spinner spinner;
    String people;
    private static int cYear, cMonth, cDay;
    private static int now_year, now_month, now_day;
    private static int cHour, cMinute;
    private static int now_hour, now_minute;

    public ShowPosts() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.post, container, false);
        searchView = (SearchView)view.findViewById(R.id.searchView);


        filter_ib = (ImageButton) view.findViewById(R.id.filter_ib);
        filter_ib.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (currentIndex) {
                    //當index=0: call taxi 的篩選框
                    case 0:
                        //Toast.makeText(v.getContext(), "calltaxi(0)的篩選", Toast.LENGTH_SHORT).show();

                        LayoutInflater taxi_inflater = getActivity().getLayoutInflater();
                        final View taxi_view = taxi_inflater.inflate(R.layout.filter_calltaxi, null);
                        final Spinner taxi_spinner = (Spinner) taxi_view.findViewById(R.id.spinner);
                        final TextView taxi_TVdate = (TextView) taxi_view.findViewById(R.id.TVdate);
                        final TextView taxi_TVtime = (TextView) taxi_view.findViewById(R.id.TVtime);

                        //人數
                        ArrayAdapter<String> taxi_adt = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.option));
                        taxi_adt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        taxi_spinner.setAdapter(taxi_adt);

                        //日期
                        taxi_TVdate.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Calendar c = Calendar.getInstance();
                                now_year = c.get(Calendar.YEAR);
                                now_month = c.get(Calendar.MONTH);
                                now_day = c.get(Calendar.DAY_OF_MONTH);

                                final DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int day) {

                                        taxi_TVdate.setText(year + "-" + (month + 1) + "-" + day);    //月份要+1
                                        cYear = year;
                                        cMonth = month;
                                        cDay = day;
                                    }
                                }, now_year, now_month, now_day);

                                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);  //能夠選擇的最小時間是現在的前一秒
                                dialog.show();
                            }
                        });

                        //時間
                        taxi_TVtime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar c = Calendar.getInstance();
                                now_hour = c.get(Calendar.HOUR_OF_DAY);
                                now_minute = c.get(Calendar.MINUTE);
                                TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hour, int minute) {
                                        if (cYear == now_year && cMonth == now_month && cDay == now_day) {
                                            if (cHour < now_hour) {
                                                Toast.makeText(getActivity(), "請輸入未來時間", Toast.LENGTH_SHORT).show();
                                            } else if ((cHour == now_hour) && (cMinute < now_minute)) {
                                                Toast.makeText(getActivity(), "請輸入未來時間", Toast.LENGTH_SHORT).show();
                                            } else {
                                                taxi_TVtime.setText(String.format("%02d:%02d", hour, minute));
                                            }
                                        } else {
                                            taxi_TVtime.setText(String.format("%02d:%02d", hour, minute));
                                        }
                                        cHour = hour;
                                        cMinute = minute;
                                    }
                                }, now_hour, now_minute, false);
                                dialog.show();
                            }
                        });

                        //對話框
                        AlertDialog.Builder taxi_dialog = new AlertDialog.Builder(v.getContext());
                        taxi_dialog.setTitle("篩選條件");
                        taxi_dialog.setView(taxi_view);
                        taxi_dialog.setPositiveButton("篩選", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(getActivity(), "篩選條件已送出", Toast.LENGTH_SHORT).show();
                            }
                        });
                        taxi_dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });
                        taxi_dialog.show();
                        break;

                    //當index=1:find passenger 的篩選框
                    case 1:
                        //Toast.makeText(v.getContext(), "findpassenger(1)的篩選", Toast.LENGTH_SHORT).show();

                        LayoutInflater passenger_inflater = getActivity().getLayoutInflater();
                        final View passenger_view = passenger_inflater.inflate(R.layout.filter_findpassenger, null);
                        final Spinner passenger_spinner = (Spinner) passenger_view.findViewById(R.id.spinner);
                        final TextView passenger_TVdate = (TextView) passenger_view.findViewById(R.id.TVdate);
                        final TextView passenger_TVtime = (TextView) passenger_view.findViewById(R.id.TVtime);

                        //人數
                        ArrayAdapter<String> passenger_adt = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.option));
                        passenger_adt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        passenger_spinner.setAdapter(passenger_adt);

                        //日期
                        passenger_TVdate.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Calendar c = Calendar.getInstance();
                                now_year = c.get(Calendar.YEAR);
                                now_month = c.get(Calendar.MONTH);
                                now_day = c.get(Calendar.DAY_OF_MONTH);

                                final DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int day) {
                                        passenger_TVdate.setText(year + "-" + (month + 1) + "-" + day);    //月份要+1
                                        cYear = year;
                                        cMonth = month;
                                        cDay = day;
                                    }
                                }, now_year, now_month, now_day);
                                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);  //能夠選擇的最小時間是現在的前一秒
                                dialog.show();
                            }
                        });

                        //時間
                        passenger_TVtime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar c = Calendar.getInstance();
                                now_hour = c.get(Calendar.HOUR_OF_DAY);
                                now_minute = c.get(Calendar.MINUTE);
                                TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hour, int minute) {
                                        if (cYear == now_year && cMonth == now_month && cDay == now_day) {
                                            if (cHour < now_hour) {
                                                Toast.makeText(getActivity(), "請輸入未來時間", Toast.LENGTH_SHORT).show();
                                            } else if ((cHour == now_hour) && (cMinute < now_minute)) {
                                                Toast.makeText(getActivity(), "請輸入未來時間", Toast.LENGTH_SHORT).show();
                                            } else {
                                                passenger_TVtime.setText(String.format("%02d:%02d", hour, minute));
                                            }
                                        } else {
                                            passenger_TVtime.setText(String.format("%02d:%02d", hour, minute));
                                        }
                                        cHour = hour;
                                        cMinute = minute;
                                    }
                                }, now_hour, now_minute, false);
                                dialog.show();
                            }
                        });

                        //對話框
                        AlertDialog.Builder passenger_dialog = new AlertDialog.Builder(v.getContext());
                        passenger_dialog.setTitle("篩選條件");
                        passenger_dialog.setView(passenger_view);
                        passenger_dialog.setPositiveButton("篩選", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(getActivity(), "篩選條件已送出", Toast.LENGTH_SHORT).show();
                            }
                        });
                        passenger_dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });
                        passenger_dialog.show();
                        break;

                    //當index=2:find driver 的篩選框
                    case 2:
                        //Toast.makeText(v.getContext(), "finddriver(2)的篩選", Toast.LENGTH_SHORT).show();

                        LayoutInflater driver_inflater = getActivity().getLayoutInflater();
                        final View driver_view = driver_inflater.inflate(R.layout.filter_finddriver, null);
                        final Spinner driver_spinner = (Spinner) driver_view.findViewById(R.id.spinner);
                        final TextView driver_TVdate = (TextView) driver_view.findViewById(R.id.TVdate);
                        final TextView driver_TVtime = (TextView) driver_view.findViewById(R.id.TVtime);

                        //人數
                        ArrayAdapter<String> driver_adt = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.option));
                        driver_adt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        driver_spinner.setAdapter(driver_adt);

                        //日期
                        driver_TVdate.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Calendar c = Calendar.getInstance();
                                now_year = c.get(Calendar.YEAR);
                                now_month = c.get(Calendar.MONTH);
                                now_day = c.get(Calendar.DAY_OF_MONTH);

                                final DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int day) {

                                        driver_TVdate.setText(year + "-" + (month + 1) + "-" + day);    //月份要+1
                                        cYear = year;
                                        cMonth = month;
                                        cDay = day;
                                    }
                                }, now_year, now_month, now_day);

                                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);  //能夠選擇的最小時間是現在的前一秒
                                dialog.show();
                            }
                        });

                        //時間
                        driver_TVtime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar c = Calendar.getInstance();
                                now_hour = c.get(Calendar.HOUR_OF_DAY);
                                now_minute = c.get(Calendar.MINUTE);

                                TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hour, int minute) {


                                        if (cYear == now_year && cMonth == now_month && cDay == now_day) {
                                            if (cHour < now_hour) {
                                                Toast.makeText(getActivity(), "請輸入未來時間", Toast.LENGTH_SHORT).show();
                                            } else if ((cHour == now_hour) && (cMinute < now_minute)) {
                                                Toast.makeText(getActivity(), "請輸入未來時間", Toast.LENGTH_SHORT).show();
                                            } else {
                                                driver_TVtime.setText(String.format("%02d:%02d", hour, minute));
                                            }
                                        } else {
                                            driver_TVtime.setText(String.format("%02d:%02d", hour, minute));
                                        }
                                        cHour = hour;
                                        cMinute = minute;
                                    }
                                }, now_hour, now_minute, false);
                                dialog.show();
                            }
                        });

                        //對話框
                        AlertDialog.Builder driver_dialog = new AlertDialog.Builder(v.getContext());
                        driver_dialog.setTitle("篩選條件");
                        driver_dialog.setView(driver_view);
                        driver_dialog.setPositiveButton("篩選", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Toast.makeText(getActivity(), "篩選條件已送出", Toast.LENGTH_SHORT).show();
                            }
                        });
                        driver_dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        });
                        driver_dialog.show();
                        break;
                }
            }
        });


        finddriver=(ImageButton)view.findViewById(R.id.finddriver);
        finddriver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                calltaxi.setImageResource(R.drawable.calltaxi);
                finddriver.setImageResource(R.drawable.finddriver2);
                findpassenger.setImageResource(R.drawable.findpassenger);
                changeTab(2);
            }});
        finddriver.setTag(2);

        findpassenger=(ImageButton)view.findViewById(R.id.findpassenger);
        findpassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                calltaxi.setImageResource(R.drawable.calltaxi);
                finddriver.setImageResource(R.drawable.finddriver);
                findpassenger.setImageResource(R.drawable.findpassenger2);
                changeTab(1);
            }
        });
        findpassenger.setTag(1);

        calltaxi=(ImageButton)view.findViewById(R.id.calltaxi);
        calltaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                calltaxi.setImageResource(R.drawable.calltaxi2);
                finddriver.setImageResource(R.drawable.finddriver);
                findpassenger.setImageResource(R.drawable.findpassenger);
                changeTab(0);
            }
        });
        calltaxi.setTag(0);

        initFragment();

    /*取得日期*/
        Calendar c = Calendar.getInstance();//建立抓日期物件
        mYear=c.get(Calendar.YEAR);//年
        mMonth=c.get(Calendar.MONTH);//月
        mDay=c.get(Calendar.DAY_OF_MONTH);//日

        showyear=(TextView)view.findViewById(R.id.showyear);
        /*指定tv到xml中的textview元件*/
        showdate=(TextView)view.findViewById(R.id.showdate);

    /*指定tv到xml中的textview元件*/
        updateDisplay();
        //呼叫自定方法，用來把日期丟到textview裡面
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //insertNestedFragment();
    }
    /*
    private void insertNestedFragment() {
        Fragment FindDriverFragment = new ChildFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, FindDriverFragment).commit();
    }
    */


    /*宣告三個日期變數，用來抓年月日*/
    private int mYear;
    private int mMonth;
    private int mDay;
    /*宣告物件變數tv，在程式碼裡指定tv到textview用的*/
    TextView showyear;
    TextView showdate;

    private void updateDisplay() {//設定tv的字，用append()方法加入字串，數字用自定方法轉成二個字元

        showyear.setText(
                new StringBuilder().append(mYear).append(""));
        showdate.setText(
                new StringBuilder().append(format(mMonth + 1)).append("/").append(format(mDay)).append(""));
    }

    //自定方法，如果只有一位數的時候就加入一個0
    private String format(int i) {
        String s = " " + i;
        if (s.length() == 1) s = "0" + s;
        return s;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initFragment() {
        fragmentArrayList = new ArrayList<Fragment>(3);
        fragmentArrayList.add(new CallTaxiFragment());
        fragmentArrayList.add(new FindPassengerFragment());
        fragmentArrayList.add(new FindDriverFragment());

        calltaxi.setSelected(true);
        changeTab(0);

    }
    /*
    @Override
    public void onClick(View v) {
        changeTab((Integer) v.getTag());
        //changeIcon((Integer) v.getTag());
    }
    */

    private void changeTab(int index) {
        currentIndex = index;
        calltaxi.setSelected(index == 0);
        findpassenger.setSelected(index == 1);
        findpassenger.setSelected(index == 2);


        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFragment) {
            ft.hide(mCurrentFragment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = getChildFragmentManager().findFragmentByTag(fragmentArrayList.get(currentIndex).getClass().getName());

        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = fragmentArrayList.get(index);
        }
        mCurrentFragment = fragment;

        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(R.id.frameLayout, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }
}