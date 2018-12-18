package com.braindead.scoreboard.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.braindead.scoreboard.ui.scoreboard.ScoreboardFragment;
import com.braindead.scoreboard.ui.sessions.SessionsFragment;

public class MainSectionsAdapter extends FragmentPagerAdapter
{
    private static int NUM_ITEMS = 2;

    public MainSectionsAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ScoreboardFragment.newInstance();
            case 1:
                return SessionsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}

