package clement.project_main;

import android.content.Intent;
import android.location.GpsStatus;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import clement.project_main.Data.User;


public class SignIn extends AppCompatActivity {

    final static String DB_URL="https://misproject-d5b35.firebaseio.com/";
    private EditText stuid;
    private EditText password;
    private EditText username;
    private EditText userdip;
    private RadioButton male;
    private RadioButton female;
    private RadioGroup rgroup;
    private FirebaseAuth auth;
    private String gender;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.signin);
        stuid = (EditText) findViewById(R.id.EDstuid);
        password = (EditText) findViewById(R.id.EDpassword);
        username = (EditText) findViewById(R.id.EDusername);
        userdip = (EditText) findViewById(R.id.EDuserdip);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        rgroup = (RadioGroup) findViewById(R.id.RadioGroup);
        rgroup.setOnCheckedChangeListener(listener);

        mDatabase = FirebaseDatabase.getInstance().getReference();


    }
    public void signUp(View v) {
        boolean allValid = IsValid(stuid) & IsValid(password) & IsValid(username) & IsValid(userdip) & GenderIsValid();
        if (!allValid) {return;}
        final String email = stuid.getText().toString()+"@cc.ncu.edu.tw";
        final String mpassword = password.getText().toString();

        auth.createUserWithEmailAndPassword(email, mpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("AD", "createUserWithEmail: " + task.isSuccessful());
                        if (task.isSuccessful()) {
                            addData(email,mpassword,username.getText().toString(),userdip.getText().toString(),gender,auth.getCurrentUser().getUid());
                            Toast.makeText(SignIn.this, "註冊成功", Toast.LENGTH_SHORT).show();
                            sendConfirmEmail();
                            FirebaseAuth.getInstance().signOut();

                            /*Intent intent = new Intent();
                            intent.setClass(SignIn.this, Login_MainActivity.class);
                            startActivity(intent);
                            SignIn.this.finish();*/


                        } else  {

                            Toast.makeText(SignIn.this, "註冊失敗", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    private  void sendConfirmEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignIn.this,"請前往信箱驗證身份",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void Back(View view) {
        Intent intent = new Intent(SignIn.this,Login_MainActivity.class);
        startActivity(intent);
        finish();
    }

    //ADD DATA
    private void addData(String email,String password,String name,String dip,String gender,String UserUID){
        User user=new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setDip(dip);
        user.setGender(gender);
        user.setUserUID(UserUID);

        mDatabase.child("User").child(UserUID).setValue(user);
    }

    //欄位驗證
    private boolean IsValid(EditText et) {
        String value = et.getText().toString();
        if(value == null || value.trim().equals("")) {
            et.setError("此欄為必填！");
            return false;
        } else {
            return true;
        }
    }

    //性別驗證
    private boolean GenderIsValid() {
        if(rgroup.getCheckedRadioButtonId()<=0){
            male.setError("請選擇");
            return false;
        }else
            return true;
    }

    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (male.getId() == checkedId) {
                gender = male.getText().toString();
            }
            else if (female.getId() == checkedId) {
                gender = female.getText().toString();
            }
        }
    };
}
