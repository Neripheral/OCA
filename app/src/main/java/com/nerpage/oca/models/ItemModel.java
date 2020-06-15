package com.nerpage.oca.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.classes.ItemDatabase;
import com.nerpage.oca.fragments.ItemListFragment;
import com.nerpage.oca.interfaces.Inventory;

import java.lang.ref.WeakReference;

public class ItemModel implements Comparable<ItemModel> {
    //================================================================================
    // Inner classes
    //================================================================================

    public class LayoutHelper{
        private ItemModel model;
        private View v;
        private View.OnClickListener listener;
        private FragmentManager fm;

        public LayoutHelper setView(View newView){
            this.v = newView;
            return this;
        }
        public LayoutHelper setModel(ItemModel newModel){
            this.model = newModel;
            return this;
        }
        public LayoutHelper setListener(View.OnClickListener listener){
            this.listener = listener;
            return this;
        }
        public LayoutHelper setFragmentManager(FragmentManager fm){
            this.fm = fm;
            return this;
        }

        private void setupInitState(){
            v.findViewById(R.id.inventory_item_quantity_container).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_nested_inv_container).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_nested_inv_menu_container).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_side_menu).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_remove_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_nested_inv_menu_less_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_nested_inv_menu_more_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_move_from_hand_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_move_to_hand_button).setVisibility(View.GONE);

            ((TextView)v.findViewById(R.id.inventory_item_title)).setText(model.getTitle());
            ((ImageView)v.findViewById(R.id.inventory_item_mainTag_image)).setImageResource(model.getMainTagImageId());
        }

        private void prepareGroupableElements(){
            // Prepare visibility
            v.findViewById(R.id.inventory_item_quantity_container).setVisibility(View.VISIBLE);

            // Prepare shown data
            ((TextView)v.findViewById(R.id.inventory_item_quantity)).setText(model.getQuantity());
        }

        private void prepareNestedInventoryElements(){
            // Prepare visibility
            v.findViewById(R.id.inventory_item_nested_inv_menu_container).setVisibility(View.VISIBLE);

            //fm.executePendingTransactions();

            FragmentTransaction transaction = fm.beginTransaction();
            View holder = v.findViewWithTag(ItemModel.HOLDER_TAG);
            if(holder == null){
                holder = v.findViewById(R.id.inventory_nested_inv_fragment_holder);
                holder.setId(View.generateViewId());
                holder.setTag(ItemModel.HOLDER_TAG);
                transaction.add(holder.getId(), model.getNestedInventoryFragment());//(new ItemListFragment()).setCorrespondingInventory(((Inventory)model.getItemRef().get()).getInventory()));
                transaction.commit();
            }

            if(model.isNestedInvVisible()) {
                v.findViewById(R.id.inventory_nested_inv_container).setVisibility(View.VISIBLE);
                v.findViewById(R.id.inventory_item_nested_inv_menu_less_button).setVisibility(View.VISIBLE);
            }else
                v.findViewById(R.id.inventory_item_nested_inv_menu_more_button).setVisibility(View.VISIBLE);

            // Prepare shown data
            ((TextView)v.findViewById(R.id.inventory_item_nested_inv_fullness)).setText(model.getFullnessString());
            ((TextView)v.findViewById(R.id.inventory_nested_inv_contentweight)).setText(model.getWeight());
            ((TextView)v.findViewById(R.id.inventory_nested_inv_capacity)).setText(model.getCapacity());

            // Bind listener
            v.findViewById(R.id.inventory_item_nested_inv_menu_less_button).setOnClickListener(listener);
            v.findViewById(R.id.inventory_item_nested_inv_menu_more_button).setOnClickListener(listener);
        }

        public LayoutHelper prepareHolder(){
            setupInitState();

            if(!model.getQuantity().isEmpty())
                prepareGroupableElements();

            if(model.getNestedInventoryFragment() != null)
                prepareNestedInventoryElements();

            return this;
        }

        public LayoutHelper showRemoveButton(){
            v.findViewById(R.id.inventory_item_remove_button).setVisibility(View.VISIBLE);
            v.findViewById(R.id.inventory_item_remove_button).setOnClickListener(listener);
            return this;
        }

        public LayoutHelper showSideMenu(){
            v.findViewById(R.id.inventory_item_side_menu).setVisibility(View.VISIBLE);
            return this;
        }

        public LayoutHelper showOperationSpaceTransferButtons(){
            v.findViewById(R.id.inventory_item_move_from_hand_button).setVisibility(View.VISIBLE);
            v.findViewById(R.id.inventory_item_move_from_hand_button).setOnClickListener(listener);
            v.findViewById(R.id.inventory_item_move_to_hand_button).setVisibility(View.VISIBLE);
            v.findViewById(R.id.inventory_item_move_to_hand_button).setOnClickListener(listener);

            return this;
        }

        public LayoutHelper setOverallListener(){
            v.setOnClickListener(listener);
            return this;
        }

        public LayoutHelper(ItemModel model, View v, FragmentManager fm){
            this.setModel(model);
            this.setView(v);
            this.setFragmentManager(fm);
        }

        public LayoutHelper(ItemModel model, View v){
            this(model, v, null);
        }
    }

    //================================================================================
    // Fields
    //================================================================================

    private WeakReference<Item> itemRef;
    private String id = "";
    private String title = "";
    private String quantity = "";
    private int mainTagImageId = 0;
    private Fragment nestedInventoryFragment = null;
    private boolean nestedInvVisible;
    private double fullness = 0.0;
    private String weight = "";
    private String capacity = "";
    private int nestedInventoryHolderId = -1;
    public static final String HOLDER_TAG = "nestedInventory";

    //================================================================================
    // Accessors
    //================================================================================

    public String getId(){
        return id;
    }
    public ItemModel setId(String id){
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }
    public ItemModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }
    public ItemModel setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getMainTagImageId() {
        return mainTagImageId;
    }
    public ItemModel setMainTagImageId(int mainTagImageId) {
        this.mainTagImageId = mainTagImageId;
        return this;
    }

    public Fragment getNestedInventoryFragment() {
        return nestedInventoryFragment;
    }
    public ItemModel setNestedInventoryFragment(Fragment nestedInventoryFragment) {
        this.nestedInventoryFragment = nestedInventoryFragment;
        return this;
    }

    public boolean isNestedInvVisible() {
        return nestedInvVisible;
    }
    public ItemModel setNestedInvVisible(boolean nestedInvVisible) {
        this.nestedInvVisible = nestedInvVisible;
        return this;
    }

    public double getFullness() {
        return fullness;
    }
    public ItemModel setFullness(double fullness) {
        this.fullness = fullness;
        return this;
    }

    public WeakReference<Item> getItemRef(){
        return this.itemRef;
    }

    public String getWeight() {
        return weight;
    }
    public ItemModel setWeight(String weight) {
        this.weight = weight;
        return this;
    }

    public String getCapacity() {
        return capacity;
    }
    public ItemModel setCapacity(String capacity) {
        this.capacity = capacity;
        return this;
    }

    public int getNestedInventoryHolderId() {
        return nestedInventoryHolderId;
    }
    public ItemModel setNestedInventoryHolderId(int nestedInventoryHolderId) {
        this.nestedInventoryHolderId = nestedInventoryHolderId;
        return this;
    }

    //================================================================================
    // Constructors
    //================================================================================

    public ItemModel(Item item, Context context){
        this.itemRef = new WeakReference<>(item);
        this.setId(item.getId());
        this.setTitle(item.getName(context));
        this.setMainTagImageId(item.getMainTag().getImageId());
        if(item instanceof Item.Groupable){
            this.setQuantity(((Item.Groupable)item).getShownQuantity());
        }
        if(item instanceof Inventory){
            this.setFullness(countFullnessFor((Inventory)item));
            this.setWeight(((Inventory) item).getContentWeightToDisplay());
            this.setCapacity(((Inventory) item).getCapacityToDisplay());
            this.setNestedInventoryFragment((new ItemListFragment()).setCorrespondingInventory(((Inventory) item).getInventory()));
            this.setNestedInvVisible(((Inventory) item).isOpen());
        }
    }

    //================================================================================
    // Methods
    //================================================================================

    public static double countFullnessFor(Inventory inv){
        return (double)inv.getInventory().getContentWeight()/inv.getCapacity();
    }

    public String getFullnessString(){
        return String.valueOf((int)(getFullness()*100)) + "%";
    }

    public LayoutHelper initLayoutHelperFor(View v, FragmentManager fm){
        LayoutHelper lh = new LayoutHelper(this, v, fm);
        return lh;
    }

    public void refreshData(){
        if(getItemRef().get() instanceof Inventory){
            this.setFullness(countFullnessFor((Inventory)getItemRef().get()));
            this.setNestedInvVisible(((Inventory) getItemRef().get()).isOpen());
            this.setWeight(((Inventory) getItemRef().get()).getContentWeightToDisplay());
        }
    }

    public void freeNestedInventory(View root){
        //root.findViewById(this.getNestedInventoryHolderId()).setId(R.id.inventory_nested_inv_fragment_holder);
        //this.setNestedInventoryHolderId(-1);
    }

    //================================================================================
    // Overrides
    //================================================================================

    @Override
    public int compareTo(ItemModel other){
        String myStr = this.getTitle();
        String otherStr = other.getTitle();
        return myStr.compareTo(otherStr);
    }
}
