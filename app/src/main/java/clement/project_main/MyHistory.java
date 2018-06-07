package clement.project_main;

import android.content.Intent;
import android.os.Bundle;
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

public class MyHistory extends AppCompatActivity {
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
    private User user;
    private String user1, user2, user3;
    private String key, msg_key;
    private String uri,name;
    private String UID1, UID2, UID3;
    private String Image1, Image2, Image3;
    int cunt = 0;
    private DatabaseReference chat;
    private TextView chat_conversation;
    private String totalpeople,type;
    private int inttotalpeople;
    private int[] count= new int[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myhistory);
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
                    Picasso.with(MyHistory.this).load(Image1).into(imUser1);
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
/*
                if (UID1 != null && u.getUid().equals(UID1) && u.getUid().equals(UID2) && u.getUid().equals(UID3)) {
                    Join_ib.setBackgroundResource(R.drawable.already);
                    Join_ib.setEnabled(false);
                }*/
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
                    Picasso.with(MyHistory.this).load(Image2).into(imUser2);
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
                /*
                if (UID2 != null && u.getUid().equals(UID1) && u.getUid().equals(UID2) && u.getUid().equals(UID3)) {
                    Join_ib.setBackgroundResource(R.drawable.already);
                    Join_ib.setEnabled(false);
                }*/
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
                    Picasso.with(MyHistory.this).load(Image3).into(imUser3);
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
                /*
                if (UID3 != null && u.getUid().equals(UID1) && u.getUid().equals(UID2) && u.getUid().equals(UID3)) {
                    Join_ib.setBackgroundResource(R.drawable.already);
                    Join_ib.setEnabled(false);
                }*/
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




        ref.child("User").child(u.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                uri = user.getUri();
                name = user.getName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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



    public void onCloseClick(View view) {

        Intent intent = new Intent(MyHistory.this, MainActivity.class);
        startActivity(intent);
        finish();
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


        Picasso.with(MyHistory.this).load(Uri).into(UserPic);




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








}
