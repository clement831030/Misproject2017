package clement.project_main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class Post_MainActivity extends Fragment {
    private ImageButton findPassenger_ib,findDriver_ib,callTaxi_ib,filter_ib;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // 介面元件
        super.onActivityCreated(savedInstanceState);
        findPassenger_ib = (ImageButton) getView().findViewById(R.id.findPassenger_ib);
        findDriver_ib = (ImageButton) getView().findViewById(R.id.findDriver_ib);
        callTaxi_ib = (ImageButton) getView().findViewById(R.id.callTaxi_ib);
        filter_ib = (ImageButton) getView().findViewById(R.id.filter_ib);
        searchView = (SearchView) getView().findViewById(R.id.searchView);

        findPassenger_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddFindPassenger.class);
                startActivity(intent);
            }
        });

        findDriver_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddFindDriver.class);
                startActivity(intent);
            }
        });

        callTaxi_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCallTaxi.class);
                startActivity(intent);
            }
        });
        /*
        filter_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteAlert = new AlertDialog.Builder(v.getContext());
                deleteAlert.setMessage("篩選的對話框");
                deleteAlert.show();
            }
        });
        */
    }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_post__main, container, false);
        }

    }
