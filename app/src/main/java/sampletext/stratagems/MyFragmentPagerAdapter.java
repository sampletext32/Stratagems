package sampletext.stratagems;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position + 1) + " / " + getCount();
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return DataUnitsHolder.getCount();
    }

}