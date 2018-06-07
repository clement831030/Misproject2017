package clement.project_main;
import android.content.Intent;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

public class Login_MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private LinearLayout mLoginForm;
    private View mProgressSpinner;
    private boolean mLoggingIn;

    private FirebaseAuth auth;

    FirebaseAuth.AuthStateListener authListener;
    private String userUID;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        auth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        mLoginForm = (LinearLayout) findViewById(R.id.mLoginForm);
        mProgressSpinner = (ProgressBar)findViewById(R.id.login_progress);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(
                    @NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null && user.isEmailVerified())/*&& user.isEmailVerified()*/ {
                    Log.d("onAuthStateChanged", "登入:" +
                            user.getUid());
                    userUID = user.getUid();
                    Intent intent = new Intent();
                    intent.setClass(Login_MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    Login_MainActivity.this.finish();

                } else {
                    Log.d("onAuthStateChanged", "已登出");
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (authListener!=null) {
            auth.removeAuthStateListener(authListener);
        }
    }
    public void sign_up(View v){
        Intent intent = new Intent();
        intent.setClass(Login_MainActivity.this, SignIn.class);
        startActivity(intent);
        Login_MainActivity.this.finish();
    }

    public void login(View v) {
        if(isVailed(email) & isVailed(password)){
        final String Stringemail = email.getText().toString();
        final String Stringpassword = password.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Log.d("AUTH", email+"/"+password);
        auth.signInWithEmailAndPassword(Stringemail, Stringpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (user.isEmailVerified()&&task.isSuccessful()) /*user.isEmailVerified()&&*/{
                            Log.d("onComplete", "登入成功");
                            showProgress(true);
                            mLoggingIn = true;
                            //hideKeyboard();
                            Intent intent = new Intent();
                            intent.setClass(Login_MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            Login_MainActivity.this.finish();
                        }
                        if (!task.isSuccessful()) {
                            Log.d("onComplete", "登入失敗");
                            Toast.makeText(Login_MainActivity.this, "帳號密碼錯誤", Toast.LENGTH_LONG).show();
                        }
                        // here
                        if (!user.isEmailVerified()) {
                            Toast.makeText(Login_MainActivity.this,"帳號尚未認證",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }else{
            Toast.makeText(this, "請輸入帳號密碼", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isVailed(EditText editText){
        String content = editText.getText().toString().trim();
        if(!content.equals("")){
            editText.setError(null);

            return true;
        }else {
            editText.setError("此欄位不可為空");
            return false;
        }
    }


    private void showProgress(boolean show) {
        mProgressSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
    }
    /*private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
    }*/

}

