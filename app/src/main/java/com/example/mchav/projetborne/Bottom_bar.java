package com.example.mchav.projetborne;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class Bottom_bar extends AppCompatActivity
{

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.contentLayout,homeFragment,homeFragment.getTag()).commit();
                    return true;

                case R.id.navigation_uv:
                    UVFragment UVFragment = new UVFragment();
                    FragmentManager manager1 = getFragmentManager();
                    manager1.beginTransaction().replace(R.id.contentLayout,UVFragment,UVFragment.getTag()).commit();
                    return true;

                case R.id.navigation_radiation:
                    RadiationFragment RadiationFragment = new RadiationFragment();
                    FragmentManager manager2 = getFragmentManager();
                    manager2.beginTransaction().replace(R.id.contentLayout,RadiationFragment,RadiationFragment.getTag()).commit();
                    return true;

                case R.id.navigation_info:
                    InfoFragment InfoFragment = new InfoFragment();
                    FragmentManager manager3 = getFragmentManager();
                    manager3.beginTransaction().replace(R.id.contentLayout,InfoFragment,InfoFragment.getTag()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar); // <-- A la création de l'activity, on pointe vers le XML "activity_bottom_bar".

        // Instantiation de la Bottom Navigation Bar.
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Lors du lancement de l'application, on force la redirection vers l'activity "Home_Activity". Sinon page blanche.
        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.contentLayout,homeFragment,homeFragment.getTag()).commit();

    }

}
