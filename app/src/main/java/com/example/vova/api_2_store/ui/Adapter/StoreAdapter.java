package com.example.vova.api_2_store.ui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.vova.api_2_store.R;
import com.example.vova.api_2_store.model.Store;
import com.example.vova.api_2_store.ui.fragments.ToolsFragment;

import java.util.ArrayList;

import static com.example.vova.api_2_store.ui.Adapter.StoreAdapter.StoreHolder.getActivityFromContext;

/**
 * Created by Dmyrty on 23-Sep-16.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreHolder> {


    private Context mContext;
    private ArrayList mStoreData;

    public StoreAdapter(Context context, ArrayList storeData) {
        this.mContext = context;
        this.mStoreData = storeData;
    }

    @Override
    public StoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        StoreHolder storeViewHolder = new StoreHolder(view);
        return storeViewHolder;
    }

    @Override
    public void onBindViewHolder(StoreHolder holder, int position) {
        final Store store = (Store) mStoreData.get(position);
        holder.textNameStore.setText(store.getName());
        holder.textAdressStore.setText(store.getAddress());
        holder.textPhoneStore.setText(store.getPhone());
        holder.cardStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolsFragment toolsFragment = new ToolsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("key", store.getId());
                toolsFragment.setArguments(bundle);
                getActivityFromContext(v.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, toolsFragment)
                        .commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mStoreData.size();
    }

    static class StoreHolder extends RecyclerView.ViewHolder {
        public CardView cardStore;
        public TextView textNameStore;
        public TextView textAdressStore;
        public TextView textPhoneStore;
        public TextView textLocarionStore;

        public StoreHolder(View itemView) {
            super(itemView);
            this.cardStore = (CardView) itemView.findViewById(R.id.card_view_store);
            this.textNameStore = (TextView) itemView.findViewById(R.id.txt_name_store);
            this.textAdressStore = (TextView) itemView.findViewById(R.id.txt_address_store);
            this.textPhoneStore = (TextView) itemView.findViewById(R.id.txt_phone_store);
            this.textLocarionStore = (TextView) itemView.findViewById(R.id.txt_location);

        }


        public static AppCompatActivity getActivityFromContext(Context context) {
            if (context instanceof Activity) {
                return (AppCompatActivity) context;
            }
            if (context instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext() instanceof Activity) {
                return (AppCompatActivity) ((ContextWrapper) context).getBaseContext();
            }
            return null;
        }

    }
}
