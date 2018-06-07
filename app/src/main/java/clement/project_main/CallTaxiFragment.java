package clement.project_main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;

import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clement.project_main.Data.Post;

/**
 * Created by clement on 9/9/16.
 */
public class CallTaxiFragment extends Fragment {
    ArrayList<Post> posts = new ArrayList<Post>();
    ListView lvCallTaxi;
    private MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calltaxifragment, container, false);
        adapter = new MyAdapter(getActivity(), posts);
        lvCallTaxi = (ListView) view.findViewById(R.id.lvcalltaxi);
        adapter =new MyAdapter(getActivity(),posts);
        lvCallTaxi.setAdapter(adapter);

        lvCallTaxi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent myIntent = new Intent(getActivity(), MyCallTaxi.class);

                Post p = (Post)parent.getItemAtPosition(position);

                Bundle bundle=new Bundle();

                bundle.putString("from",p.getFrom());
                bundle.putString("to",p.getTo());
                bundle.putString("date",p.getDate());
                bundle.putString("note",p.getNote());
                bundle.putString("time",p.getTime());
                bundle.putString("people",p.getPeople());
                bundle.putString("price",p.getPrice());
                bundle.putString("Uri",p.getUri());
                bundle.putString("name",p.getName());
                bundle.putString("UID",p.getUID());
                bundle.putString("key",p.getKey());
                myIntent.putExtras(bundle);

                startActivity(myIntent);
            }
        });

        setupFirebase();

        return view;
    }


    private void setupFirebase() {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("CallTaxi").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //posts.clear();
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                getUpdates(dataSnapshot);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public class MyAdapter extends BaseAdapter{
        private LayoutInflater myInflater;
        private List<Post> posts;

        public MyAdapter(Context context,List<Post> post){
            myInflater = LayoutInflater.from(context);
            this.posts = post;
        }
        /*private view holder class*/
        private class ViewHolder {
            TextView txtfrom;
            TextView txtto;
            TextView txtdate;
            TextView txttime;
            TextView txtpeople;
            ImageView ivpic;
            public ViewHolder(TextView txtfrom, TextView txtto,TextView txtdate,TextView txttime,TextView txtpeople,ImageView ivpic){
                this.txtfrom = txtfrom;
                this.txtto = txtto;
                this.txtdate = txtdate;
                this.txttime =txttime;
                this.txtpeople =txtpeople;
                this.ivpic=ivpic;
            }
        }

        @Override
        public int getCount() {
            return posts.size();
        }

        @Override
        public Object getItem(int arg0) {
            return posts.get(arg0);
        }

        @Override
        public long getItemId(int position) {
            return posts.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = myInflater.inflate(R.layout.listview, null);
                holder = new ViewHolder(
                        (TextView) convertView.findViewById(R.id.tvfrom),
                        (TextView) convertView.findViewById(R.id.tvto),
                        (TextView) convertView.findViewById(R.id.tvdate),
                        (TextView) convertView.findViewById(R.id.tvtime),
                        (TextView) convertView.findViewById(R.id.tvpeople),
                        (ImageView)convertView.findViewById(R.id.UserPic)


                );
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            Post post = (Post)getItem(position);
            holder.txtfrom.setText(post.getFrom());
            holder.txtto.setText(post.getTo());
            holder.txtdate.setText(post.getDate());
            holder.txttime.setText(" 時間:"+post.getTime()+" ");
            holder.txtpeople.setText("徵客人數:" + post.getPeople());

            Picasso.with(getActivity()).load(post.getUri()).into(holder.ivpic);
            return convertView;
        }
    }
    private void getUpdates(DataSnapshot dataSnapshot){

        Post p = new Post();

        p.setFrom(dataSnapshot.getValue(Post.class).getFrom());
        p.setTo(dataSnapshot.getValue(Post.class).getTo());
        p.setDate(dataSnapshot.getValue(Post.class).getDate());
        p.setTime(dataSnapshot.getValue(Post.class).getTime());
        p.setPeople(dataSnapshot.getValue(Post.class).getPeople());
        p.setUri(dataSnapshot.getValue(Post.class).getUri());
        p.setNote(dataSnapshot.getValue(Post.class).getNote());
        p.setName(dataSnapshot.getValue(Post.class).getName());
        p.setUID(dataSnapshot.getValue(Post.class).getUID());
        p.setKey(dataSnapshot.getValue(Post.class).getKey());


        posts.add(0, p);
        adapter.notifyDataSetChanged();

    }
}