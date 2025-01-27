package com.example.signify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Dashboard extends AppCompatActivity {
    public static MeowBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        TellTailFragment tellTailFragment = new TellTailFragment();
//        tellTailFragment.sign_data_list = null;
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_warning_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_format_list_bulleted_24));
//        if(tellTailFragment.sign_data_list != null)
//        {
//            bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_format_list_bulleted_24));
//        }
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_location_on_24));



        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //initilize fragment
                Fragment fragment = null;

                switch (item.getId()) {
                    case 1:
                        fragment = new HomeFragment();
                        break;
                    case 2:
                        fragment = new TellTailFragment();
                        break;
                    case 3:
                        /** Testing Something New **/
                        if(tellTailFragment.sign_data_list != null)
                        {
                            fragment = new ListFragment();
                            break;
                        }
                        else{
                            fragment = new NotFound();
                            break;
                        }
                        /**End Of Testing **/
//                        fragment = new ListFragment();
//                        break;
                    case 4:
                        fragment = new MapsFragment();
                        break;
                }
                loadFragment(fragment);

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(),"You are already in selected screen",Toast.LENGTH_SHORT).show();
            }
        });
        bottomNavigation.show(1, true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                //Toast.makeText(getApplicationContext(),"you clicked"+item.getId(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

}