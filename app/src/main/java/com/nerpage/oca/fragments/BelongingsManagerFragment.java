package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.nerpage.oca.R;
import com.nerpage.oca.adapters.BelongingsManagerViewPagerAdapter;

public class BelongingsManagerFragment extends Fragment {
    public static class Layout{
        public static ViewPager getViewPager(View rootView){
            return rootView.findViewById(R.id.belongings_viewpager);
        }
    }

    //================================================================================
    // Fields
    //================================================================================

    public View rootView;

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_belongings_manager, container, false);
        Layout.getViewPager(this.rootView).setAdapter(new BelongingsManagerViewPagerAdapter(this.getContext(), getChildFragmentManager()));
        return this.rootView;
    }
}
