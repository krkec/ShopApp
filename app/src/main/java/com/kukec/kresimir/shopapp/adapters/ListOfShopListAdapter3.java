package com.kukec.kresimir.shopapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kukec.kresimir.shopapp.R;
import com.kukec.kresimir.shopapp.model.ShopList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfShopListAdapter3 extends RecyclerView.Adapter<ListOfShopListAdapter3.ViewHolder> {

    //region Global variables
    private List<ShopList> lis;
    public ListOfShopListAdapter3.LSLAdapterListener onClickListener;
    //endregion

    public ListOfShopListAdapter3(List<ShopList> _lis, LSLAdapterListener listener) {
        this.lis = _lis;
        this.onClickListener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //shopLIsts = new ShopLIsts();

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.losl,viewGroup,false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ShopList shopList = lis.get(i);
        TextView textView1 = viewHolder.tv_Listname;
        textView1.setText(shopList.getListName());
        TextView textView2 = viewHolder.tv_NumberOfItems;
        textView2.setText(shopList.getNumberOfItems()+" items");
    }

    @Override
    public int getItemCount() {
        return lis.size();
    }

    public void swap(List<ShopList> datas)
    {
        lis.clear();
        lis.addAll(datas);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        @BindView(R.id.btnEditList)
        ImageButton btnEditList;
        @BindView(R.id.btnShareList)
        ImageButton btnShareList;
        @BindView(R.id.btnDeleteLIst)
        ImageButton btnDeleteLIst;
        @BindView(R.id.tv_Listname)
        TextView tv_Listname;
        @BindView(R.id.tv_NumberOfItems)
        TextView tv_NumberOfItems;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            ButterKnife.bind(this, itemView);

            btnEditList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.editList(v, getAdapterPosition());
                }
            });
            btnShareList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.shareList(v, getAdapterPosition());
                }
            });
            btnDeleteLIst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.removeList(v, getAdapterPosition());
                }
            });
        }
    }
    public interface LSLAdapterListener{
        void  editList(View v, int position);
        void  shareList(View v, int position);
        void removeList(View v, int position);
    }
}
