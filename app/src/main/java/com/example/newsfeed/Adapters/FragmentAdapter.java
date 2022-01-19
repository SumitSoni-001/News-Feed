package com.example.newsfeed.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.newsfeed.Fragments.AllFragment;
import com.example.newsfeed.Fragments.BitcoinFragment;
import com.example.newsfeed.Fragments.BusinessFragment;
import com.example.newsfeed.Fragments.EntertainmentFragment;
import com.example.newsfeed.Fragments.HealthFragment;
import com.example.newsfeed.Fragments.HollywoodFragment;
import com.example.newsfeed.Fragments.ScienceFragment;
import com.example.newsfeed.Fragments.SportsFragment;
import com.example.newsfeed.Fragments.TechnologyFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    int TabCount;

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        TabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllFragment();

            case 1:
                return new SportsFragment();

            case 2:
                return new TechnologyFragment();

            case 3:
                return new BusinessFragment();

            case 4:
                return new HealthFragment();

            case 5:
                return new EntertainmentFragment();

            case 6:
                return new ScienceFragment();

            case 7:
                return new BitcoinFragment();

            case 8:
                return new HollywoodFragment();

            default:
                return new AllFragment();
        }
    }

    @Override
    public int getCount() {
        return TabCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        switch (position) {
            case 0:
                title = "All";
                break;

            case 1:
                title = "Sports";
                break;

            case 2:
                title = "Technology";
                break;

            case 3:
                title = "Business";
                break;

            case 4:
                title = "Health";
                break;

            case 5:
                title = "Entertainment";
                break;

            case 6:
                title = "Science";
                break;

            case 7:
                title = "Bitcoin";
                break;

            case 8:
                title = "Hollywood";
                break;

        }
        return title;
    }
}
