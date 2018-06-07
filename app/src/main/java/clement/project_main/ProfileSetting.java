package clement.project_main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import clement.project_main.Data.User;


public class ProfileSetting extends AppCompatActivity implements View.OnClickListener  {

    //宣告元件參數
    private static final int RESULT_Load_PIC = 1;
    int RESULI_OK;
    ImageView UserPic;
    ImageView profilebox;
    TextView FansNum_tv;
    TextView followNum_tv;
    EditText Edname,Eddip,Edgender,Edphone,Edcarnum;
    ImageButton finishEdit;
    final static String DB_URL="https://misproject-d5b35.firebaseio.com/";
    Firebase mRef;



    public ProfileSetting()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesetting);
        initView();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();


            final String userUID = user.getUid();
            mDatabase.child("User").child(userUID).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);

                            Edname = (EditText) findViewById(R.id.Edname);
                            Eddip = (EditText) findViewById(R.id.Eddip);
                            Edgender = (EditText) findViewById(R.id.Edgender);

                            Edcarnum = (EditText) findViewById(R.id.Edcarnum);

                            Edname.setText(user.getName());
                            Eddip.setText(user.getDip());
                            Edgender.setText(user.getGender());

                            Edcarnum.setText(user.getCarnum());
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                        });
        }
    }


    public void initView() {

        UserPic  = (ImageView) findViewById(R.id.UserPi);
        //Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
        //UserPic.setImageBitmap(icon);
        //UserPic.setOnClickListener(this);
        finishEdit = (ImageButton) findViewById(R.id.finishEdit);


    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.UserPi:
                Intent galleryIntend = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntend ,RESULT_Load_PIC);
                break;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==RESULT_Load_PIC && resultCode ==RESULI_OK && data!=null)
        {
            Uri selectedImage = data.getData();
            UserPic.setImageURI(selectedImage);
        }
    }

    public void onClickFinish(View view) {
        addData(Edname.getText().toString(), Eddip.getText().toString(), Edgender.getText().toString(),  Edcarnum.getText().toString());
        Toast.makeText(ProfileSetting.this,"修改成功",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ProfileSetting.this,MainActivity.class);
        startActivity(intent);
    }

    private void addData(String Edname,String Eddip,String Edgender,String Edcarnum){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        String userUID= u.getUid().toString();
        User user=new User();

        user.setName(Edname);
        user.setDip(Eddip);
        user.setGender(Edgender);
        user.setCarnum(Edcarnum);


        Map<String, Object> Map = new HashMap<>();
        Map.put("name", user.getName());
        Map.put("dip", user.getDip());
        Map.put("gender", user.getGender());
        Map.put("carnum", user.getCarnum());

        mDatabase.child("User").child(userUID).updateChildren(Map);
    }
    //修改個人資料
    /*
    private boolean NameIsValid(EditText et) {
        String Name_value = et.getText().toString();
        if(Name_value == null || Name_value.trim().equals("")) {
            et.setError("請輸入姓名！");
            return false;
        } else {
            return true;
        }
    }
    private boolean IdentifyIsValid(EditText et) {
        String  Identify_value = et.getText().toString();
        if( Identify_value == null ||  Identify_value.trim().equals("")) {
            et.setError("請輸入學號ID！");
            return false;
        } else {
            return true;
        }
    }

    private boolean PhonenumIsValid(EditText et) {
        String Phonenum_value = et.getText().toString();
        if(Phonenum_value == null || Phonenum_value.trim().equals("")) {
            et.setError("請輸入電話號碼！");
            return false;
        } else {
            return true;
        }
    }
    private boolean GradeIsValid(EditText et) {
        String Grade_value = et.getText().toString();
        if(Grade_value == null || Grade_value.trim().equals("")) {
            et.setError("請輸入系級！");
            return false;
        } else {
            return true;
        }
    }
    //按下send_ib先檢查是否都通過驗證再傳遞資料
    public void onClickFinish(View view) {

        boolean allValid = NameIsValid(name) & IdentifyIsValid(identify) & PhonenumIsValid(phonenum) & GradeIsValid(grade) ;
        if (!allValid) {return;}                                     //如果沒有全部通過驗證就返回
        Intent intent = new Intent(this, ShowProfileDetail.class);           //new一個intent物件，指定Activity切換到Show
        Bundle bundle = new Bundle();                                       //new一個Bundle物件，將要傳遞的資料傳入
        bundle.putString("name_key", name.getText().toString());          //(KEY,value)
        bundle.putString("identify_key", identify.getText().toString());
        bundle.putString("phonenum_key", phonenum.getText().toString());
        bundle.putString("grade_key", grade.getText().toString());
        bundle.putString("carnumber_key", grade.getText().toString());

        intent.putExtras(bundle);                                    //將Bundle物件assign給intent
        startActivity(intent);                                       //切換Activity
    }
    */

}

