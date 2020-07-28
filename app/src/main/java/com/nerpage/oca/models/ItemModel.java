package com.nerpage.oca.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.nerpage.oca.R;
import com.nerpage.oca.classes.Item;
import com.nerpage.oca.classes.ItemStorage;
import com.nerpage.oca.interfaces.Inventory;

import java.lang.ref.WeakReference;

public class ItemModel implements Comparable<ItemModel> {
    //================================================================================
    // Inner classes
    //================================================================================

    public static class LayoutHelper{
        //================================================================================
        // Inner class
        //================================================================================

        enum ItemView{
            MainBar(R.id.inventory_item_mainBar),
                SideMenu(R.id.inventory_item_side_menu),
                NestedInvSideMenu(R.id.inventory_item_nested_inv_menu_container),
                    Fullness(R.id.inventory_item_nested_inv_fullness),
                    ExpandControls(R.id.inventory_item_nested_inv_expandControls),
                        NestedInvShowMoreBtn(R.id.inventory_item_nested_inv_menu_more_button),
                        NestedInvShowLessBtn(R.id.inventory_item_nested_inv_menu_less_button),
                QuantityContainer(R.id.inventory_item_quantity_container),
                    Quantity(R.id.inventory_item_quantity),
                Title(R.id.inventory_item_title),
                MainTagImage(R.id.inventory_item_mainTag_image),
                OperationButtons(R.id.inventory_item_operation_buttons),
                    EquipButton(R.id.inventory_item_equip_button),
                    OperationSpaceButtons(R.id.inventory_item_operation_space_controls_container),
                        MoveToHandButton(R.id.inventory_item_move_to_hand_button),
                        MoveFromHandButton(R.id.inventory_item_move_from_hand_button),
                    RemoveButton(R.id.inventory_item_remove_button),
            NestedInventoryContainer(R.id.inventory_nested_inv_container),
                NestedInventoryInfobar(R.id.inventory_nested_inv_infobar),
                    NestedInventoryWeightStats(R.id.inventory_nested_inv_weightStats),
                        NestedInventoryContentWeight(R.id.inventory_nested_inv_contentweight),
                        NestedInventoryCapacity(R.id.inventory_nested_inv_capacity),
                NestedInventoryFragmentHolder(R.id.inventory_nested_inv_fragment_holder);

            int id;

            ItemView(int id){
                this.id = id;
            }
        }

        //================================================================================
        // Fields
        //================================================================================

        private ItemModel model;
        private View v;
        private View.OnClickListener listener;

        //================================================================================
        // Accessors
        //================================================================================

        public ItemModel getModel() {
            return model;
        }

        public LayoutHelper setModel(ItemModel newModel){
            this.model = newModel;
            return this;
        }

        public View getV() {
            return v;
        }

        public LayoutHelper setView(View newView){
            this.v = newView;
            return this;
        }

        public View.OnClickListener getListener() {
            return listener;
        }

        public LayoutHelper setListener(View.OnClickListener listener){
            this.listener = listener;
            return this;
        }

        //================================================================================
        // Methods
        //================================================================================

        public View getView(int id){
            return v.findViewById(id);
        }

        public View getView(ItemView view){
            return this.getView(view.id);
        }

        public void show(int id){
            this.getView(id).setVisibility(View.VISIBLE);
        }

        public void show(ItemView view){
            this.show(view.id);
        }

        public void hide(int id){
            this.getView(id).setVisibility(View.GONE);
        }

        public void hide(ItemView view){
            this.hide(view.id);
        }

        private void setupInitState(){
            for(ItemView view : ItemView.values()){
                hide(view);
            }

            /*
            v.findViewById(R.id.inventory_item_quantity_container).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_nested_inv_container).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_nested_inv_menu_container).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_side_menu).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_remove_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_nested_inv_menu_less_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_nested_inv_menu_more_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_move_from_hand_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_move_to_hand_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_operation_space_controls_container).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_equip_button).setVisibility(View.GONE);
             */

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

            if(model.getBoundItemStorage() != null)
                prepareNestedInventoryElements();

            return this;
        }

        public LayoutHelper showRemoveButton(){
            v.findViewById(R.id.inventory_item_remove_button).setVisibility(View.VISIBLE);
            v.findViewById(R.id.inventory_item_remove_button).setOnClickListener(listener);
            return this;
        }

        public LayoutHelper hideSideMenu(){
            v.findViewById(R.id.inventory_item_side_menu).setVisibility(View.GONE);
            return this;
        }

        public LayoutHelper showSideMenu(){
            v.findViewById(R.id.inventory_item_side_menu).setVisibility(View.VISIBLE);
            return this;
        }

        public LayoutHelper showGetFromOperationSpaceButton(){
            v.findViewById(R.id.inventory_item_move_from_hand_button).setVisibility(View.VISIBLE);
            v.findViewById(R.id.inventory_item_move_from_hand_button).setOnClickListener(listener);
            return this;
        }

        public LayoutHelper showMoveToOperationSpaceButton(){
            v.findViewById(R.id.inventory_item_move_to_hand_button).setVisibility(View.VISIBLE);
            v.findViewById(R.id.inventory_item_move_to_hand_button).setOnClickListener(listener);
            return this;
        }

        public LayoutHelper showOperationSpaceTransferButtons(){
            v.findViewById(R.id.inventory_item_operation_space_controls_container).setVisibility(View.VISIBLE);
            if(!model.isHiddenTransferToHandsButton())
                this.showMoveToOperationSpaceButton();
            if(this.model.getBoundItemStorage() != null)
                this.showGetFromOperationSpaceButton();
            return this;
        }

        public LayoutHelper showEquipButton(){
            v.findViewById(R.id.inventory_item_equip_button).setVisibility(View.VISIBLE);
            v.findViewById(R.id.inventory_item_equip_button).setOnClickListener(listener);
            return this;
        }

        public LayoutHelper hideAllButtons(){
            v.findViewById(R.id.inventory_item_move_from_hand_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_move_to_hand_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_remove_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_nested_inv_menu_more_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_nested_inv_menu_less_button).setVisibility(View.GONE);
            v.findViewById(R.id.inventory_item_equip_button).setVisibility(View.GONE);
            return this;
        }

        public LayoutHelper setOverallListener(){
            v.setOnClickListener(listener);
            return this;
        }

        public LayoutHelper(ItemModel model, View v){
            this.setModel(model);
            this.setView(v);
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
    private ItemStorage boundItemStorage = null;
    private boolean nestedInvVisible = false;
    private double fullness = 0.0;
    private String weight = "";
    private String capacity = "";
    private boolean hiddenTransferToHandsButton = false;

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

    public ItemStorage getBoundItemStorage() {
        return this.boundItemStorage;
    }

    public ItemModel setBoundItemStorage(ItemStorage boundItemStorage) {
        this.boundItemStorage = boundItemStorage;
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

    public boolean isHiddenTransferToHandsButton() {
        return hiddenTransferToHandsButton;
    }
    public ItemModel setHiddenTransferToHandsButton(boolean hiddenTransferToHandsButton) {
        this.hiddenTransferToHandsButton = hiddenTransferToHandsButton;
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
            this.setBoundItemStorage(((Inventory) item).getInventory());
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

    public LayoutHelper initLayoutHelperFor(View v){
        LayoutHelper lh = new LayoutHelper(this, v);
        return lh;
    }

    public void refreshData(){
        if(getItemRef().get() instanceof Inventory){
            this.setFullness(countFullnessFor((Inventory)getItemRef().get()));
            this.setNestedInvVisible(((Inventory) getItemRef().get()).isOpen());
            this.setWeight(((Inventory) getItemRef().get()).getContentWeightToDisplay());
        }
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
