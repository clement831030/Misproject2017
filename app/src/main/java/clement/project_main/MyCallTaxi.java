package clement.project_main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import clement.project_main.Data.Message;
import clement.project_main.Data.Post;
import clement.project_main.Data.User;

public class MyCallTaxi extends AppCompatActivity {
    private Toolbar toolBar;
    private TextView mFrom_tv, mTo_tv, mPeople_tv, mDate_tv, mTime_tv, mNote_tv, PostTime_tv, user1_name,user2_name,user3_name, MainUserName_tv,price_tv,vehicle_tv;
    private ImageButton Join_ib, close_ib;
    private ScrollView scrollView_sv;
    private int JoinFlag = 0;
    private ListView listView_lv;
    private List<Message> myMsg = new ArrayList<Message>();
    private ImageView UserPic, imUser1, imUser2, imUser3;
    private TextView UserName_tv;
    private DatabaseReference ref;
    private EditText msg;
    private FirebaseUser u;
    private String currentnum;
    private Post post;
    private User currentuser ;
    private String user1, user2, user3;
    private String key, msg_key;
    private String uri,name;
    private String UID1, UID2, UID3;
    private String Image1, Image2, Image3;
    int cunt = 0;
    private DatabaseReference chat;
    private TextView chat_conversation;
    private String totalpeople,chaturi,chatname;
    private int inttotalpeople;
    private int[] count= new int[4];
    private String myuserName0,myuserEmail0,myuserDip0,myuserGender0,myuserCarnum0;  /*這是我用來紀錄資訊的*/
    private String myuserName1,myuserEmail1,myuserDip1,myuserGender1,myuserCarnum1;
    private String myuserName2,myuserEmail2,myuserDip2,myuserGender2,myuserCarnum2;
    private String myuserName3,myuserEmail3,myuserDip3,myuserGender3,myuserCarnum3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycalltaxi);
        ref = FirebaseDatabase.getInstance().getReference();
        u = FirebaseAuth.getInstance().getCurrentUser();

        toolBar = (Toolbar) findViewById(R.id.toolBar);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);

        initView();
        showResults();

        scrollView_sv.post(new Runnable() {
            @Override
            public void run() {
                scrollView_sv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        count[0]=inttotalpeople;
        for(int n =1; n<=inttotalpeople;n++){
            count[n]= 1;
        }

        ref.child("User").child(u.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentuser = dataSnapshot.getValue(User.class);
                uri = currentuser.getUri();
                name = currentuser.getName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        chat = FirebaseDatabase.getInstance().getReference().child("CallTaxi").child(key).child("chat");
        switch (inttotalpeople){
            case 1 :
                imUser2.setVisibility(View.INVISIBLE);
                user2_name.setVisibility(View.INVISIBLE);
                imUser3.setVisibility(View.INVISIBLE);
                user3_name.setVisibility(View.INVISIBLE);
                break;
            case 2 :
                imUser3.setVisibility(View.INVISIBLE);
                user3_name.setVisibility(View.INVISIBLE);
                break;
            case 3 :
                break;

        }


        ref.child("CallTaxi").child(key).child("currentnum").child("user1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Post.class) != null) {
                    post = dataSnapshot.getValue(Post.class);
                    UID1 = post.getUID();
                    Image1 = post.getImage();
                    user1 = post.getName();

                }
                if (user1!=null) {
                    ref.child("User").child(UID1).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    myuserName1=user.getName();
                                    myuserEmail1=user.getEmail();
                                    myuserDip1=user.getDip() ;
                                    myuserGender1=user.getGender();
                                    myuserCarnum1=user.getCarnum();


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                    Picasso.with(MyCallTaxi.this).load(Image1).into(imUser1);
                    user1_name.setText(user1);
                    count[1]=0;
                    if (u.getUid().equals(UID1)){
                        Join_ib.setBackgroundResource(R.drawable.already);
                        Join_ib.setEnabled(false);
                    }
                    if (u.getUid().equals(UID2)){
                        Join_ib.setBackgroundResource(R.drawable.already);
                        Join_ib.setEnabled(false);
                    }
                    if (u.getUid().equals(UID3)){
                        Join_ib.setBackgroundResource(R.drawable.already);
                        Join_ib.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref.child("CallTaxi").child(key).child("currentnum").child("user2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Post.class) != null) {
                    post = dataSnapshot.getValue(Post.class);
                    UID2 = post.getUID();
                    Image2 = post.getImage();
                    user2 = post.getName();


                }
                if (user2!=null) {

                    ref.child("User").child(UID2).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    myuserName2=user.getName();
                                    myuserEmail2=user.getEmail();
                                    myuserDip2=user.getDip() ;
                                    myuserGender2=user.getGender();
                                    myuserCarnum2=user.getCarnum();


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                    Picasso.with(MyCallTaxi.this).load(Image2).into(imUser2);
                    user2_name.setText(user2);
                    count[2]=0;
                    if (u.getUid().equals(UID1)){
                        Join_ib.setBackgroundResource(R.drawable.already);
                        Join_ib.setEnabled(false);
                    }
                    if (u.getUid().equals(UID2)){
                        Join_ib.setBackgroundResource(R.drawable.already);
                        Join_ib.setEnabled(false);
                    }
                    if (u.getUid().equals(UID3)){
                        Join_ib.setBackgroundResource(R.drawable.already);
                        Join_ib.setEnabled(false);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ref.child("CallTaxi").child(key).child("currentnum").child("user3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Post.class) != null) {
                    post = dataSnapshot.getValue(Post.class);
                    UID3 = post.getUID();
                    Image3 = post.getImage();
                    user3 = post.getName();


                }

                if (user3!=null) {

                    ref.child("User").child(UID3).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    myuserName3=user.getName();
                                    myuserEmail3=user.getEmail();
                                    myuserDip3=user.getDip() ;
                                    myuserGender3=user.getGender();
                                    myuserCarnum3=user.getCarnum();


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                    Picasso.with(MyCallTaxi.this).load(Image3).into(imUser3);
                    user3_name.setText(user3);
                    count[3]=0;
                    if (u.getUid().equals(UID1)){
                        Join_ib.setBackgroundResource(R.drawable.already);
                        Join_ib.setEnabled(false);
                    }
                    if (u.getUid().equals(UID2)){
                        Join_ib.setBackgroundResource(R.drawable.already);
                        Join_ib.setEnabled(false);
                    }
                    if (u.getUid().equals(UID3)){
                        Join_ib.setBackgroundResource(R.drawable.already);
                        Join_ib.setEnabled(false);
                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        if(count[1]==0){
            if(count[2]==0){
                if(count[3]==0){
                    Join_ib.setVisibility(View.INVISIBLE);
                }
            }
        }


        changeJoinImage();
        Bundle bundle = getIntent().getExtras();
        String UID = bundle.getString("UID");

        ref.child("User").child(UID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        myuserName0=user.getName();
                        myuserEmail0=user.getEmail();
                        myuserDip0=user.getDip() ;
                        myuserGender0=user.getGender();
                        myuserCarnum0=user.getCarnum();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        chat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        UserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myuserName0 != null) {
                    AlertDialog.Builder UserPic = new AlertDialog.Builder(MyCallTaxi.this);
                    UserPic.setMessage("姓名：  " + myuserName0+ "\n"
                            +"信箱：  " + myuserEmail0+"\n"
                            +"科系/單位：  " + myuserDip0+"\n"
                            +"性別：  " + myuserGender0+"\n"
                            +"車牌：  " + myuserCarnum0);

                    UserPic.setTitle("個人資訊 ");

                    UserPic.setCancelable(true);
                    UserPic.show();
                }
            }
        });

        imUser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myuserName1 != null) {
                    AlertDialog.Builder imUser1 = new AlertDialog.Builder(MyCallTaxi.this);
                    imUser1.setMessage("姓名：  " + myuserName1 + "\n"
                            +"信箱：  " + myuserEmail1+"\n"
                            +"科系/單位：  " + myuserDip1+"\n"
                            +"性別：  " + myuserGender1+"\n"
                            +"車牌：  " + myuserCarnum1);


                    imUser1.setTitle("個人資訊 ");

                    imUser1.setCancelable(true);
                    imUser1.show();
                }
            }
        });
        imUser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myuserName2 != null) {
                    AlertDialog.Builder imUser2 = new AlertDialog.Builder(MyCallTaxi.this);
                    imUser2.setMessage("姓名：  " + myuserName2 + "\n"
                            +"信箱：  " + myuserEmail2+"\n"
                            +"科系/單位：  " + myuserDip2+"\n"
                            +"性別：  " + myuserGender2+"\n"
                            +"車牌：  " + myuserCarnum2);

                    imUser2.setTitle("個人資訊 ");

                    imUser2.setCancelable(true);
                    imUser2.show();
                }
            }
        });
        imUser3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myuserName3 !=null) {
                    AlertDialog.Builder imUser3 = new AlertDialog.Builder(MyCallTaxi.this);
                    imUser3.setMessage("姓名：  " + myuserName3 + "\n"
                            +"信箱：  " + myuserEmail3+"\n"
                            +"科系/單位：  " + myuserDip3+"\n"
                            +"性別：  " + myuserGender3+"\n"
                            +"車牌：  " + myuserCarnum3);

                    imUser3.setTitle("個人資訊 ");

                    imUser3.setCancelable(true);
                    imUser3.show();
                }
            }
        });


    }



    private void initView() {
        mFrom_tv = (TextView) findViewById(R.id.mFrom_tv);
        mTo_tv = (TextView) findViewById(R.id.mTo_tv);
        mPeople_tv = (TextView) findViewById(R.id.mPeople_tv);
        mDate_tv = (TextView) findViewById(R.id.mDate_tv);
        mTime_tv = (TextView) findViewById(R.id.mTime_tv);
        mNote_tv = (TextView) findViewById(R.id.mNote_tv);

        user1_name = (TextView) findViewById(R.id.user1_name);
        user2_name = (TextView) findViewById(R.id.user2_name);
        user3_name = (TextView) findViewById(R.id.user3_name);

        MainUserName_tv = (TextView) findViewById(R.id.MainUserName_tv);
        scrollView_sv = (ScrollView) findViewById(R.id.scrollView_sv);
        close_ib = (ImageButton) findViewById(R.id.close_ib);
        //listView_lv = (ListView) findViewById(R.id.listView_lv);
        UserPic = (ImageView) findViewById(R.id.userpic);
        Join_ib = (ImageButton) findViewById(R.id.Join_ib);
        imUser1 = (ImageView) findViewById(R.id.user1pic);
        imUser2 = (ImageView) findViewById(R.id.user2pic);
        imUser3 = (ImageView) findViewById(R.id.user3pic);
        msg = (EditText) findViewById(R.id.message_et);
        chat_conversation = (TextView) findViewById(R.id.tvchat);



        //listView_lv.setFocusable(false);                     //讓頁面一載入不會直接跳到討論區

    }

    // 按下「加入」會變成「等待批准中」
    private void changeJoinImage() {

        Join_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    if (count[1] == 1) {
                        addData1();
                        Join_ib.setBackgroundResource(R.drawable.already);
                        count[1]=0;
                        Join_ib.setEnabled(false);

                    }
                    else if (count[2] == 1) {
                        addData2();
                        Join_ib.setBackgroundResource(R.drawable.already);
                        count[2]=0;
                        Join_ib.setEnabled(false);

                    }
                    else if (count[3] == 1) {
                        addData3();
                        Join_ib.setBackgroundResource(R.drawable.already);
                        count[3]=0;
                        Join_ib.setEnabled(false);

                    }
                    else{
                        Join_ib.setVisibility(View.INVISIBLE);//這要放在一開始
                    }


                }

        });
    }

    public void onCloseClick(View view) {

        Intent intent = new Intent(MyCallTaxi.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void onSendClick(View view) {
        Map<String, Object> map = new HashMap<String, Object>();
        msg_key = chat.push().getKey();
        chat.updateChildren(map);

        Map<String, Object> map2 = new HashMap<String, Object>();
        Bundle bundle = getIntent().getExtras();
        map2.put("name", name);
        map2.put("msg", msg.getText().toString());
        chat.child(msg_key).updateChildren(map2);
        msg.setText("");

        scrollView_sv.post(new Runnable() {
            @Override
            public void run() {
                scrollView_sv.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(MyCallTaxi.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);


    }

    private void showResults() {
        Bundle bundle = getIntent().getExtras();          //取的intent中的bundle物件
        String from = bundle.getString("from");
        String to = bundle.getString("to");
        String note = bundle.getString("note");
        String time = bundle.getString("time");
        String date = bundle.getString("date");
        totalpeople = bundle.getString("people");
        String Uri = bundle.getString("Uri");
        String UserName = bundle.getString("name");
        String UID = bundle.getString("UID");
        key = bundle.getString("key");
        inttotalpeople = Integer.parseInt(totalpeople);

        mFrom_tv.setText("出發地：  " + from);
        mTo_tv.setText("目的地：  " + to);
        mTime_tv.setText("出發時間：  " + time);
        mDate_tv.setText("出發日期：  " + date);
        mPeople_tv.setText("徵乘客數：  " + totalpeople + "人");
        mNote_tv.setText("備註：  " + note);
        MainUserName_tv.setText(UserName);

        user1_name.setText(user1);
        user2_name.setText(user2);
        user3_name.setText(user3);


        Picasso.with(MyCallTaxi.this).load(Uri).into(UserPic);


        if (u.getUid().equals(UID)) {
            Join_ib.setVisibility(View.INVISIBLE);

        }

        //顯示貼文日期時間
        /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        Date curDate = new Date(System.currentTimeMillis()-1000) ;      // 獲取當前時間
        String str = formatter.format(curDate);
        PostTime_tv.setText(new StringBuilder().append(str));*/
    }





    //解決ListView和ScrollView之間的衝突，不然ListView會只能顯示一項
    public void setListViewHeightBasedOnChildren(ListView lv) {
        //取得ListView對應的Adapter
        ListAdapter listAdapter = lv.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {    // listAdapter.getCount()取得總共有幾項
            View listItem = listAdapter.getView(i, null, lv);
            listItem.measure(0, 0);                        // 計算每個子項View的寬高
            totalHeight += listItem.getMeasuredHeight();   // 統計所有子項的總高度
        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()取得子項間分隔符占用的高度
        // params.height最後得到整個ListView完整顯示需要的高度
        lv.setLayoutParams(params);
    }

    private void addData1() {


        Map<String, Object> Map = new HashMap<>();
        Map.put("UID", u.getUid());
        Map.put("Image", uri);
        Map.put("name",name);
        ref.child("CallTaxi").child(key).child("currentnum").child("user1").updateChildren(Map);
        user1_name.setText(name);
        Picasso.with(MyCallTaxi.this).load(uri).into(imUser1);
        //ref.child("History").child(u.getUid()).child(key).setValue(key);

        addHistory();



    }

    private void addData2() {


        Map<String, Object> Map = new HashMap<>();
        Map.put("UID", u.getUid());
        Map.put("Image", uri);
        Map.put("name",name);
        ref.child("CallTaxi").child(key).child("currentnum").child("user2").updateChildren(Map);
        user2_name.setText(name);
        Picasso.with(MyCallTaxi.this).load(uri).into(imUser2);
        //ref.child("History").child(u.getUid()).child(key).setValue(key);


    }

    private void addData3() {


        Map<String, Object> Map = new HashMap<>();
        Map.put("UID", u.getUid());
        Map.put("Image", uri);
        Map.put("name",name);
        ref.child("CallTaxi").child(key).child("currentnum").child("user3").updateChildren(Map);
        user3_name.setText(name);
        Picasso.with(MyCallTaxi.this).load(uri).into(imUser3);
        //ref.child("History").child(u.getUid()).child(key).setValue(key);

    }

    private String chat_msg, chat_username;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_username = (String) ((DataSnapshot) i.next()).getValue();

            chat_conversation.append(chat_username + ":" + chat_msg + "\n");

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(MyCallTaxi.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

    private void addHistory(){
        //ref.child("CallTaxi")
    }

}


