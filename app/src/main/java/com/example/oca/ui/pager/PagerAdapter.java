package com.example.oca.ui.pager;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.oca.AttributesFragment;
import com.example.oca.BasicInfoFragment;
import com.example.oca.CharacterViewerActivity;
import com.example.oca.R;

import java.text.AttributedCharacterIterator;

public class PagerAdapter extends FragmentPagerAdapter {
    public static final int ITEMS_NUMBER = 2;

    private static final int[] TAB_TITLES = new int[]{R.string.tab_string_basicInfo, R.string.tab_string_attributes};
    private final Context mContext;

    public PagerAdapter(Context context, FragmentManager fm){
        super(fm);
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
        }
        return new Fragment();
    }

    public CharSequence getPageTitle(int position){
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
