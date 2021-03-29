package com.nerpage.oca.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nerpage.oca.R;
import com.nerpage.oca.fragments.InventoryManagerFragment;

public class BelongingsManagerViewPagerAdapter extends FragmentStatePagerAdapter {
    public static final int ITEMS_NUMBER = 2;

    private static final int[] TAB_TITLES = new int[]{R.string.inventory_editor_name};
    private final Context mContext;

    public BelongingsManagerViewPagerAdapter(Context context, FragmentManager fm){
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
                return new InventoryManagerFragment();
        }
        return new Fragment();
    }

    public CharSequence getPageTitle(int position){
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
