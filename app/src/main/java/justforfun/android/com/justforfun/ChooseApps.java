package justforfun.android.com.justforfun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;


public class ChooseApps extends AppCompatActivity {

    Set<String> appsCheckedSet = new HashSet<>();
    static final String fbapp = "facebook";
    static final String instaapp = "instagram";
    static final String twitterapp = "twitter";
    static final String quoraapp = "quora";
    static final String linkedapp = "linkedin";
    public static final String SharedPrefName = "sharedPrefFile";
    public static final String isLaunched = "is_Launched";
    public static final String appList = "appList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_apps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = new Intent();

        CheckBox fb = (CheckBox) findViewById(R.id.facebook);
        CheckBox is = (CheckBox) findViewById(R.id.instagram);
        CheckBox tw = (CheckBox) findViewById(R.id.twitter);
        CheckBox qu = (CheckBox) findViewById(R.id.quora);
        CheckBox li = (CheckBox) findViewById(R.id.linkedin);
        Button con = findViewById(R.id.cont);

        LinearLayout linearLayout = findViewById(R.id.layout1);
        int count = linearLayout.getChildCount();

        SharedPreferences sharedPref = getSharedPreferences(SharedPrefName, MODE_PRIVATE);
        if(sharedPref.getStringSet(appList, null)!=null) {
            appsCheckedSet = sharedPref.getStringSet(appList, null);
        }


            if (appsCheckedSet.contains(fbapp)) {
                fb.setChecked(true);
            }
            if (appsCheckedSet.contains(instaapp)) {
                is.setChecked(true);
            }
            if (appsCheckedSet.contains(twitterapp)) {
                tw.setChecked(true);
            }
            if (appsCheckedSet.contains(quoraapp)) {
                qu.setChecked(true);
            }
            if (appsCheckedSet.contains(linkedapp)) {
                li.setChecked(true);
            }


        fb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appsCheckedSet.add(fbapp);
                } else {
                    appsCheckedSet.remove(fbapp);
                }
            }
        });
        is.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appsCheckedSet.add(instaapp);
                } else {
                    appsCheckedSet.remove(instaapp);
                }
            }
        });
        tw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    appsCheckedSet.add(twitterapp);
                } else {
                    appsCheckedSet.remove(twitterapp);
                }
            }
        });
        qu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appsCheckedSet.add(quoraapp);
                } else {
                    appsCheckedSet.remove(quoraapp);
                }
            }
        });

        li.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    appsCheckedSet.add(linkedapp);
                } else {
                    appsCheckedSet.remove(linkedapp);
                }
            }
        });

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("GetSocial", "Clicked on Continue Button");
                openNewActivity();
            }
        });
    }


    void openNewActivity() {

            if (!appsCheckedSet.isEmpty()) {
                Log.i("GetSocial", "App List: " + appsCheckedSet);
                SharedPreferences sharedPref = getSharedPreferences(SharedPrefName, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.putStringSet(appList, appsCheckedSet);
                Log.i("GetSocial", "Added App List");
                editor.apply();

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                Log.i("GetSocial", "Launching Main Activity from Choose Apps.");
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please Select Atleast one App.", Toast.LENGTH_LONG).show();
            }

    }

}
