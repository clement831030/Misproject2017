package clement.project_main;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
//import android.app.Fragment;


public  class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton profile,showposts,history,post,notification;
    private int currentIndex = 0;
    private ArrayList<Fragment> fragmentArrayList;
    private Fragment mCurrentFragment;


    final static String DB_URL="https://misproject-d5b35.firebaseio.com/";
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    private String userUID;
    private GoogleApiClient client;
    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
       // mRef = new Firebase(DB_URL);
        initView();
        initFragment();
    }

    private void initView() {
        profile = (ImageButton) findViewById(R.id.profile);
        profile.setOnClickListener(this);
        profile.setTag(0);

        showposts = (ImageButton) findViewById(R.id.showposts);
        showposts.setOnClickListener(this);
        showposts.setTag(1);

        history = (ImageButton) findViewById(R.id.history);
        history.setOnClickListener(this);
        history.setTag(2);


        post = (ImageButton) findViewById(R.id.post);
        post.setOnClickListener(this);
        post.setTag(3);

        notification = (ImageButton) findViewById(R.id.notification);
        notification.setOnClickListener(this);
        notification.setTag(4);
    }

    private void initFragment() {
        fragmentArrayList = new ArrayList<Fragment>(5);
        fragmentArrayList.add(new Profile());
        fragmentArrayList.add(new ShowPosts());
        fragmentArrayList.add(new History());
        fragmentArrayList.add(new Post_MainActivity());
        fragmentArrayList.add(new Notification());

        showposts.setSelected(true);
        changeTab(1);
        changeIcon(1);
    }

    @Override
    public void onClick(View v) {
        changeTab((Integer) v.getTag());
        changeIcon((Integer) v.getTag());
    }
    private void changeIcon(int index){
        if (index==0){
            profile.setImageResource(R.drawable.profile2);
            showposts.setImageResource(R.drawable.showposts);
            history.setImageResource(R.drawable.history);
            post.setImageResource(R.drawable.post);
            notification.setImageResource(R.drawable.notification);
        }
        if (index==1){
            profile.setImageResource(R.drawable.profile);
            showposts.setImageResource(R.drawable.showposts2);
            history.setImageResource(R.drawable.history);
            post.setImageResource(R.drawable.post);
            notification.setImageResource(R.drawable.notification);
        }
        if (index==2){
            profile.setImageResource(R.drawable.profile);
            showposts.setImageResource(R.drawable.showposts);
            history.setImageResource(R.drawable.history2);
            post.setImageResource(R.drawable.post);
            notification.setImageResource(R.drawable.notification);
        }
        if (index==3){
            profile.setImageResource(R.drawable.profile);
            showposts.setImageResource(R.drawable.showposts);
            history.setImageResource(R.drawable.history);
            post.setImageResource(R.drawable.post2);
            notification.setImageResource(R.drawable.notification);
        }
        if (index==4){
            profile.setImageResource(R.drawable.profile);
            showposts.setImageResource(R.drawable.showposts);
            history.setImageResource(R.drawable.history);
            post.setImageResource(R.drawable.post);
            notification.setImageResource(R.drawable.notification2);
        }
    }


    private void changeTab(int index) {
        currentIndex = index;
        profile.setSelected(index == 0);
        showposts.setSelected(index == 1);
        history.setSelected(index == 2);
        post.setSelected(index == 3);
        notification.setSelected(index == 4);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFragment) {
            ft.hide(mCurrentFragment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentArrayList.get(currentIndex).getClass().getName());

        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = fragmentArrayList.get(index);
        }
        mCurrentFragment = fragment;

        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(R.id.fragment, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }
    /*private void switchToLogin_MainActivity() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment,new Login_MainActivity(), "Login");
        ft.commit();
    }*/
    private void switchToShowPostsFragment(String repoUrl) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment Showposts = new ShowPosts();
        Bundle args = new Bundle();
        args.putString("FIREBASE", repoUrl);
        Showposts.setArguments(args);
        ft.replace(R.id.fragment, Showposts);
        ft.commit();
    }
}