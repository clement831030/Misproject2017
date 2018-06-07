package clement.project_main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.utilities.Base64;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import clement.project_main.Data.User;


public class Profile extends Fragment implements View.OnClickListener {

    private static final int RESULT_Load_PIC = 1;
    private static final int RESULT_Load_PIC2 = 2;

    private int RESULI_OK;
    private ImageView UserPic;
    private ImageView CarPic;
    private EditText name;
    private EditText identify;
    private EditText carnumber;
    private EditText phonenum;
    private ImageButton followButton;
    private ImageButton searchButton;
    private Button detailButton;
    private ImageButton editButton;
    private ImageButton logout;
    private TextView follower;
    private TextView following;
    private FirebaseAuth firebaseAuth;
    private TextView TVname;
    private TextView TVmail;
    private TextView TVdip;
    private TextView TVgender;
    private TextView TVcarnum;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mDatabase;

    private StorageReference mStorage;
    User user;
    FirebaseUser u;
    String userUID;

    public Profile()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.profile, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        UserPic  = (ImageView) getView().findViewById(R.id.UserPi);
        CarPic  = (ImageView) getView().findViewById(R.id.CarPic);

        mStorage = FirebaseStorage.getInstance().getReference();

        mProgressDialog = new ProgressDialog(getActivity());

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
        Bitmap car = BitmapFactory.decodeResource(getResources(), R.drawable.car);

        UserPic.setImageBitmap(icon);
        CarPic.setImageBitmap(car);
        UserPic.setOnClickListener(this);
        CarPic.setOnClickListener(this);





        editButton = (ImageButton) getView().findViewById(R.id. editButton);
        logout = (ImageButton) getView().findViewById(R.id. logout);

        //TVname = (TextView) getView().findViewById(R.id.TVname);

        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u != null) {


            mDatabase = FirebaseDatabase.getInstance().getReference();
            userUID = u.getUid();

            mDatabase.child("User").child(userUID).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user = dataSnapshot.getValue(User.class);
                            TVname = (TextView) getView().findViewById(R.id.Eddip);
                            TVmail = (TextView) getView().findViewById(R.id.TVmail);
                            TVdip = (TextView) getView().findViewById(R.id.TVdip);
                            TVgender = (TextView) getView().findViewById(R.id.TVgender);
                            TVcarnum = (TextView) getView().findViewById(R.id.TVcarnum);


                            TVname.setText(user.getName());
                            TVmail.setText(user.getEmail());
                            TVdip.setText(user.getDip());
                            TVgender.setText(user.getGender());
                            TVcarnum.setText(user.getCarnum());



                            Picasso.with(getActivity()).load(user.getUri()).into(UserPic);
                            UserPic.getLayoutParams().width=390;
                            UserPic.getLayoutParams().height=390;

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProfileSetting.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),Login_MainActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.UserPi:
                Intent galleryIntend = new Intent(Intent.ACTION_PICK , android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntend ,RESULT_Load_PIC);
                break;
            case R.id.CarPic:
                Intent galleryIntend2 = new Intent(Intent.ACTION_PICK , android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntend2 ,RESULT_Load_PIC2);
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_Load_PIC:
                    if(data != null) {
                        try {
                            mProgressDialog.setMessage("更新大頭貼中....");
                            mProgressDialog.show();
                            Uri uri = data.getData();
                            InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
                            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                            selectedImage = getResizedBitmap(selectedImage, 500);


                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            InputStream is = new ByteArrayInputStream(baos.toByteArray());




                            StorageReference filepath = mStorage.child("Photo").child("ProfilePhoto").child(userUID);
                            filepath.putStream(is).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    mProgressDialog.dismiss();

                                    Toast.makeText(getActivity(), "上傳成功", Toast.LENGTH_SHORT).show();

                                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                                    Picasso.with(getActivity()).load(downloadUri).into(UserPic);

                                    u = FirebaseAuth.getInstance().getCurrentUser();
                                    if (u != null) {
                                        userUID = u.getUid();

                                        mDatabase.child("User").child(userUID).child("Uri").setValue(downloadUri.toString());

                                        user.setUri(downloadUri.toString());
                                    }

                                }
                            });
                            UserPic.getLayoutParams().width = 390;
                            UserPic.getLayoutParams().height = 390;




                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }



        }
        if(requestCode != RESULT_Load_PIC && data != null)
        {

            Uri selectedImage2 = data.getData();
            CarPic.setImageURI(selectedImage2);
            CarPic.getLayoutParams().width=500;
            CarPic.getLayoutParams().height=390;

        }


        /*
        if(requestCode == RESULT_Load_PIC && data != null)
        {
            mProgressDialog.setMessage("更新大頭貼中....");
            mProgressDialog.show();

            Uri uri = data.getData();


            StorageReference filepath = mStorage.child("Photo").child("ProfilePhoto").child(userUID);
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();

                    Toast.makeText(getActivity(), "上傳成功", Toast.LENGTH_SHORT).show();

                    Uri downloadUri = taskSnapshot.getDownloadUrl();

                    Picasso.with(getActivity()).load(downloadUri).into(UserPic);

                     u = FirebaseAuth.getInstance().getCurrentUser();
                    if (u!=null) {
                        userUID = u.getUid();

                        mDatabase.child("User").child(userUID).child("Uri").setValue(downloadUri.toString());

                        user.setUri(downloadUri.toString());
                    }

                }
            });


            //UserPic.setImageURI(uri);
            UserPic.getLayoutParams().width=390;
            UserPic.getLayoutParams().height=390;
        }
        else if(requestCode == RESULT_Load_PIC2 && data != null)
        {

            Uri selectedImage2 = data.getData();
            CarPic.setImageURI(selectedImage2);
            CarPic.getLayoutParams().width=500;
            CarPic.getLayoutParams().height=390;

        }*/

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Bitmap getResizedBitmap(Bitmap image,int maxSize){
        int width= image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float)width/(float)height;
        if(bitmapRatio >1){
            width=maxSize;
            height=(int)(width/bitmapRatio);
        }else {
            height=maxSize;
            width=(int) (height*bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image,width,height,true);
    }
}


