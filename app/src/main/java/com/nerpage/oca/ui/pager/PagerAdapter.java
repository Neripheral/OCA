package com.nerpage.oca.ui.pager;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nerpage.oca.fragments.SkillsFragment;
import com.nerpage.oca.fragments.AttributesFragment;
import com.nerpage.oca.fragments.BasicInfoFragment;
import com.nerpage.oca.R;

public class PagerAdapter extends FragmentPagerAdapter {
    public static final int ITEMS_NUMBER = 3;

    private static final int[] TAB_TITLES = new int[]{R.string.tab_string_basicInfo, R.string.tab_string_attributes, R.string.tab_string_skills};
    private final Context mContext;

    public PagerAdapter(Context context, FragmentManager fm){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public int getCount(){
        return ITEMS_NUMBER;
    }

    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                return new BasicInfoFragment();
            case 1:
                return new AttributesFragment();
            case 2:
                return new SkillsFragment();
        }
        return new Fragment();
    }

    public CharSequence getPageTitle(int position){
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
