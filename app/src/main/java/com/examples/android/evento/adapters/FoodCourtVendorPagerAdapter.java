package com.examples.android.evento.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examples.android.evento.R;
import com.examples.android.evento.interfacelistener.ItemInteractionListener;
import com.examples.android.evento.model.FoodCourtVendor;
import com.examples.android.evento.model.FoodCourtVendorItem;
import com.examples.android.evento.model.FoodCourtVendorSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ankit on 19/2/17.
 */

public class FoodCourtVendorPagerAdapter extends PagerAdapter {

    public List<FoodCourtVendor> foodCourtVendors;
    private Context context;

    public FoodCourtVendorPagerAdapter(Context context, List<FoodCourtVendor> foodCourtVendors) {
        this.context = context;
        this.foodCourtVendors = foodCourtVendors;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_foodcourt_foodcourt_vendor_page, container, false);
        RecyclerView recyclerView = (RecyclerView) viewGroup.findViewById(R.id.activity_foodcourt_foodcourt_vendor_page_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        List<String> sections = new ArrayList<>();

        List<FoodCourtVendorSection> foodCourtVendorSections = foodCourtVendors.get(position).getSections();
        HashMap<String, List<FoodCourtVendorItem>> hashMap = new HashMap<>();

        for (FoodCourtVendorSection s : foodCourtVendorSections) {
            sections.add(s.getTitle());
            hashMap.put(s.getTitle(), s.getItems());
        }
        recyclerView.setAdapter(new FoodCourtVendorRecyclerViewAdapter(hashMap, sections, new ItemInteractionListener<FoodCourtVendorItem>() {
            @Override
            public void onItemClick(View v, FoodCourtVendorItem item) {

            }

            @Override
            public void onItemLongClick(View v, FoodCourtVendorItem item) {

            }
        }));

        container.addView(viewGroup);
        return viewGroup;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return foodCourtVendors.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return foodCourtVendors.get(position).getTitle();
    }
}
