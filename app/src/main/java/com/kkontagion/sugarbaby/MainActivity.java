package com.kkontagion.sugarbaby;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kkontagion.sugarbaby.fragments.FoodFragment;
import com.kkontagion.sugarbaby.fragments.JournalFragment;
import com.kkontagion.sugarbaby.fragments.MedsFragment;
import com.kkontagion.sugarbaby.fragments.MonsterFragment;
import com.kkontagion.sugarbaby.fragments.ShotsFragment;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

//    private int[] fragments = new int[] {
//            R.id.navigation_home, R.id.navigation_meds,
//            R.id.navigation_shots, R.id.navigation_food,
//            R.id.navigation_journal
//    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag = null;
            String tag = "";
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    frag = MonsterFragment.newInstance();
                    tag = "monster";
                    break;
                case R.id.navigation_meds:
                    frag = MedsFragment.newInstance();
                    tag = "meds";
                    break;
                case R.id.navigation_shots:
                    frag = ShotsFragment.newInstance();
                    tag = "shots";
                    break;
                case R.id.navigation_food:
                    frag = FoodFragment.newInstance();
                    tag = "food";
                    break;
                case R.id.navigation_journal:
                    frag = JournalFragment.newInstance();
                    tag = "journal";
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.content, frag, tag).commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, MonsterFragment.newInstance());
        transaction.commit();
    }
}
