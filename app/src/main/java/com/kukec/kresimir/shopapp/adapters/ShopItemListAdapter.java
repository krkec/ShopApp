package com.kukec.kresimir.shopapp.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kukec.kresimir.shopapp.R;
import com.kukec.kresimir.shopapp.model.ShopItem;
import com.kukec.kresimir.shopapp.model.ShopList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopItemListAdapter extends RecyclerView.Adapter<ShopItemListAdapter.ViewHolder>{

    public ShopItemListAdapter.SILAdapterListener onClickListener;
    private List<ShopItem> lis;

    public ShopItemListAdapter(List<ShopItem> lis, SILAdapterListener onClickListener) {
        this.onClickListener = onClickListener;
        this.lis = lis;
    }

    @NonNull
    @Override
    public ShopItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.ui_item_element_layout,viewGroup,false);
        ShopItemListAdapter.ViewHolder viewHolder = new ShopItemListAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemListAdapter.ViewHolder viewHolder, int i) {
        if (lis!=null){
            ShopItem shopItem = lis.get(i);
            CheckBox cb = viewHolder.cbBuyed;
            if (shopItem.getBuyed()==1){
                cb.setChecked(true);
            }
            else {
                cb.setChecked(false);
            }
            TextView textView1 = viewHolder.tvItemName;
            textView1.setText(shopItem.getItemName());
            TextView textView2 = viewHolder.tvItemQty;
            textView2.setText(shopItem.getItemQty());
        }

    }

    @Override
    public int getItemCount() {
        if (lis != null) {
            return lis.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        @BindView(R.id.cbBuyed)
        CheckBox cbBuyed;
        @BindView(R.id.tvItemName)
        TextView tvItemName;
        @BindView(R.id.tvItemQty)
        TextView tvItemQty;
        @BindView(R.id.btnElDelete)
        ImageButton btnElDelete;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ButterKnife.bind(this, itemView);

            cbBuyed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onClickListener.isBuyed(buttonView, getAdapterPosition());
                }
            });
            btnElDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.deleteItem(v, getAdapterPosition());
                }
            });

        }
    }
    public interface SILAdapterListener{
        void  isBuyed(View v, int position);
        void  deleteItem(View v, int position);
    }
}

/*if (!isChecked){
                        tvItemName.setPaintFlags(tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }else {
                        tvItemName.setPaintFlags(tvItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }*/