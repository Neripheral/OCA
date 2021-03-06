package com.nerpage.oca.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerpage.oca.R;
import com.nerpage.oca.adapters.CharacterManagerViewPagerAdapter;

public class CharacterManagerFragment extends Fragment {
    private CharacterManagerViewPagerAdapter adapter;
    private View rootView;

    public static class Layout{
        public static ViewPager getViewPager(View rootView){
            return (ViewPager) rootView.findViewById(R.id.character_viewer_pager);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_character_manager, container, false);
        // initialize adapter
        FragmentManager fm = getChildFragmentManager();
        this.adapter = new CharacterManagerViewPagerAdapter(rootView.getContext(), fm);

        // initialize pager
        ViewPager pager = Layout.getViewPager(rootView.findViewById(R.id.content).getRootView());
        pager.setAdapter(this.adapter);
        return this.rootView;
    }
}
