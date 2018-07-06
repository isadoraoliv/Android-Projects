package com.example.overwatch.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dl = (DrawerLayout) findViewById(R.id.dl);
        mToggle = new ActionBarDrawerToggle(this,dl,R.string.open,R.string.close);
        dl.addDrawerListener(mToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectIterDrawer(MenuItem menuItem){
        Fragment myFragment = null;
        Class fragmentClass;

        switch (menuItem.getItemId()){
            case R.id.profile:
                fragmentClass = Profile.class;
                break;

            case R.id.friends:
                fragmentClass = Friends.class;
                break;

            case R.id.map:
                fragmentClass = Map.class;
                break;

            case R.id.events:
                fragmentClass = Events.class;
                break;

            case R.id.schedule:
                fragmentClass = Schedule.class;
                break;

            case R.id.search:
                fragmentClass = Search.class;
                break;

            case R.id.settings:
                fragmentClass = Settings.class;
                break;

            case R.id.logout:
                fragmentClass = Logout.class;
                break;

                default:
                    fragmentClass = Profile.class;
        }
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        dl.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectIterDrawer(item);
                return false;
            }
        });
    }
}








