package justforfun.android.com.justforfun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private static final String TAG= "MainActivity";
    public static final String SharedPrefName="sharedPrefFile";
    Set<String> Apps=new HashSet<>();
    public static final String appList="appList";

    private SectionsPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar mToolbar= findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        SharedPreferences sharedPref = getSharedPreferences(SharedPrefName, MODE_PRIVATE);
        Apps = sharedPref.getStringSet(appList,null);
        if(Apps==null) {

                Intent intent = new Intent(getBaseContext(), ChooseApps.class);
                startActivity(intent);
                Log.i("GetSocial", "Called Choose Apps Activity");
                finish();

        }
            if(Apps!=null) {
                if (!Apps.isEmpty()) {
                    Log.i("GetSocial", "App list is not null: " + Apps);
                    mSectionPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
                    mViewPager = findViewById(R.id.container);
                    //  mViewPager.setPagingEnabled(false);
                    setupViewPager(mViewPager);

                    TabLayout tabLayout = findViewById(R.id.tabs);

                    tabLayout.setupWithViewPager(mViewPager);
                    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                    mViewPager.setOffscreenPageLimit(6);
                }
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.modify:
                Intent intent = new Intent(getBaseContext(), ChooseApps.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.close:
                finish();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    private void setupViewPager(ViewPager viewPager){

        SectionsPageAdapter adapter=new SectionsPageAdapter(getSupportFragmentManager());
        if(Apps.contains("facebook")) {
            adapter.addFragment(new FacebookTab(), "facebook");
            Log.i("GetSocial", "Addded Facebook");
        }
        if(Apps.contains("instagram")) {
            adapter.addFragment(new InstagramTab(), "Instagram");
            Log.i("GetSocial", "Added Instagram");
        }
        if(Apps.contains("twitter")) {
            adapter.addFragment(new TwitterTab(), "Twitter");
            Log.i("GetSocial", "Added Twitter");
        }
        if(Apps.contains("quora")) {
            adapter.addFragment(new QuoraTab(), "Quora");
            Log.i("GetSocial", "Added Quora");
        }
        if(Apps.contains("linkedin")) {
            adapter.addFragment(new LinkedTab(), "linkedin");
            Log.i("GetSocial", "Added LinkedIN");
        }
        viewPager.setAdapter(adapter);

    }

}




