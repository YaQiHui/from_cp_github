package com.color.measurement.from_cp.UI.fragments.child_fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.color.measurement.from_cp.R;
import com.color.measurement.from_cp.UI.Activitys.CheeseDetailActivity;

import java.util.ArrayList;
import java.util.List;

import com.color.measurement.from_cp.others.adapter.RecyclerAdapter;

/**
 * Created by wpc on 2016/8/27.
 */
public class Fourth_1_Fragment extends Fragment {


    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";

    public static Fourth_1_Fragment createInstance(int itemsCount) {
        Fourth_1_Fragment partThreeFragment = new Fourth_1_Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fourth_1, container, false);
        setupRecyclerView(recyclerView);

        return recyclerView;
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(createItemList());
        recyclerAdapter.setOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(getActivity(), position + " click",
//                        Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(),CheeseDetailActivity.class));
                Intent it = new Intent(getActivity(), CheeseDetailActivity.class);
                it.putExtra("flag", 0);
                View v = getActivity().findViewById(R.id.fab);
                startActivity(it, ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, "fab").toBundle());
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), position + " long click",
                        Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            int itemsCount = bundle.getInt(ITEMS_COUNT_KEY);
            for (int i = 0; i < itemsCount; i++) {
                itemList.add("Item " + i);
            }
        }
        return itemList;
    }
}
