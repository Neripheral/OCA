package com.nerpage.oca.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nerpage.oca.R;
import com.nerpage.oca.activities.CharacterEditorActivity;
import com.nerpage.oca.adapters.ItemListAdapter;
import com.nerpage.oca.classes.PlayerCharacter;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.misc.RecyclerViewClickListener;
import com.nerpage.oca.misc.SpacesItemDecoration;
import com.nerpage.oca.models.ItemModel;

import java.util.List;


public abstract class ItemListFragment extends Fragment {
    public interface LayoutListener extends RecyclerViewClickListener {}

    public View rootView = null;
    public View selectedItem = null;

    public static final class Layout{
        public static RecyclerView getRecycler(View rootView){
            return (RecyclerView) rootView.findViewById(R.id.inv_browser_recycler);
        }
    }

    public abstract void clickOperator(View view, int position);

    public PlayerCharacter getPlayerCharacterData(){
        return ((CharacterEditorActivity) getActivity()).pc;
    }

    public ItemModel composeDatasetEntryFor(Item item){
        return new ItemModel(item, getContext());
    }

    public abstract List<ItemModel> getDataset();

    public ItemListAdapter getAdapter(){
        return (ItemListAdapter)Layout.getRecycler(this.rootView).getAdapter();
    }

    public void addItemToPCInventory(Item item){
        getPlayerCharacterData().getInventory().add(item);
        Toast toast = Toast.makeText(getContext(), item.composeOnAddToastMessage(getContext()), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract int getAdapterWorkMode();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        RecyclerView recyclerView = Layout.getRecycler(this.rootView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.rootView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new ItemListAdapter(this.getDataset(), new LayoutListener(){
            @Override
            public void onClick(View view, int position) {
                clickOperator(view, position);
            }
        }, this.getAdapterWorkMode());
        recyclerView.setAdapter(adapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        return this.rootView;
    }

    public ItemListFragment(){
        //Empty constructor
    }
}
