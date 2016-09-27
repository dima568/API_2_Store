package com.example.vova.api_2_store.ui.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.example.vova.api_2_store.R;
import com.example.vova.api_2_store.Realm.StoreRealm;
import com.example.vova.api_2_store.app.ApiClient;
import com.example.vova.api_2_store.model.Store;
import com.example.vova.api_2_store.ui.Adapter.StoreAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vova on 21.09.2016.
 */

public class StoreFragment extends Fragment {
    private Context context = getActivity();
    private RecyclerView recycler;
    private ArrayList<Store> stores;
    private StoreAdapter adapter;
    final String TAG = "myLogs";
    private ArrayList<StoreRealm> realmStoreData;
    private Realm realm;
    private StoreRealm storeRealm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_store, container, false);
        stores = new ArrayList<>();
        getRequestStore(stores);
        recycler = (RecyclerView) v.findViewById(R.id.recycler_view_store);
        recycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new StoreAdapter(context, stores);
        recycler.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }


    private void getRequestStore(final ArrayList stores) {
        realmStoreData = new ArrayList<>();
        Call<List<Store>> call = ApiClient.getStoreService().getStores("", 100);
        call.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Response<List<Store>> response) {

                    realmStoreData.add(storeRealm);
                    realm.commitTransaction();

                    realm.beginTransaction();
                    RealmResults<StoreRealm> result = realm.where(StoreRealm.class).findAll();
                    StoreRealm storeRealm = result.last();
                    Log.v(TAG, "realmDBStoreLast" + " " + storeRealm);
                    realm.commitTransaction();

                    recycler.setAdapter(adapter);
                    stores.clear();
                    stores.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    System.out.println(response.body().toString());
                }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

