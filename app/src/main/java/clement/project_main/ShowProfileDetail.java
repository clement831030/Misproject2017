package clement.project_main;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by clement on 8/16/16.
 */
public class ShowProfileDetail extends AppCompatActivity  {
    private TextView name_tv,identify_tv,carnumber_tv,phonenum_tv,grade_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showprofiledetail);
        name_tv = (TextView) findViewById(R.id.Eddip);
        carnumber_tv = (TextView) findViewById(R.id.Edcarnum);
        phonenum_tv = (TextView) findViewById(R.id.Edphone);
        grade_tv = (TextView) findViewById(R.id.Eddip);
        ShowProfileDetail();

    }

    private void ShowProfileDetail() {
        Bundle bundle = getIntent().getExtras();          //取的intent中的bundle物件
        String name = bundle.getString("name_key");
        String identify = bundle.getString("identify_key");
        String phonenum = bundle.getString("phonenum_key");
        String grade = bundle.getString("grade_key");
        String carnumber = bundle.getString("carnumber_key");


        name_tv.setText(name);
        identify_tv.setText(identify);
        carnumber_tv.setText(carnumber);
        phonenum_tv.setText(phonenum);
        grade_tv.setText(grade);

    }
}
