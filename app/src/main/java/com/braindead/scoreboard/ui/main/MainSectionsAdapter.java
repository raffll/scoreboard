package com.braindead.scoreboard.ui.main;

import com.braindead.scoreboard.ui.scoreboard.ScoreboardFragment;
import com.braindead.scoreboard.ui.sessions.SessionsFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
        switch (position) {
            case 0:
                return "Scoreboard";
            case 1:
                return "Sessions";
            default:
                return null;
        }
    }
}

