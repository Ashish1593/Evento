    package com.examples.android.evento.activity;

    import android.content.Context;
    import android.os.Bundle;
    import android.support.design.widget.TabLayout;
    import android.support.v4.view.PagerAdapter;
    import android.support.v4.view.ViewPager;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.Window;
    import android.view.WindowManager;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;
    import com.examples.android.evento.model.FoodCourtVendor;
    import com.examples.android.evento.model.FoodCourtVendorItem;
    import com.examples.android.evento.adapters.FoodCourtVendorRecyclerViewAdapter;
    import com.examples.android.evento.model.FoodCourtVendorSection;
    import com.examples.android.evento.interfacelistener.ItemInteractionListener;
    import com.examples.android.evento.model.Metadata;
    import com.examples.android.evento.R;
    import com.google.gson.Gson;
    import com.google.gson.GsonBuilder;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    /**
 * Created by ankit on 27/12/16.
 */


     public class FoodCourtActivity extends AppCompatActivity {

        public ViewPager viewPager;
public TabLayout tabLayout;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);

            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_foodcourt);

            viewPager = (ViewPager) findViewById(R.id.activity_foodcourt_viewpager);
            tabLayout = (TabLayout) findViewById(R.id.activity_foodcourt_tablayout);

//            FoodCourtVendorPagerAdapter foodCourtVendorPagerAdapter = new FoodCourtVendorPagerAdapter(FoodCourtActivity.this, metadata_Cold.getFoodCourtVendors());
//
//            viewPager.setAdapter(foodCourtVendorPagerAdapter);


//            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//                @Override
//                public void  onPageSelected(int position) {
//
//                    super.onPageSelected(position);
//                }
//            });


           // tabLayout.setupWithViewPager(viewPager);
          //  initViews(savedInstanceState);












            requestJsonObject();






    }

        private void requestJsonObject(){
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://hasgeek.github.io/api/space/84/metadata";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                  //  Log.d(TAG, "Response " + response);
                    GsonBuilder builder = new GsonBuilder();
                    Gson mGson = builder.create();
                    //List<Metadata> posts = new ArrayList<Metadata>();
                   // posts = Arrays.asList(mGson.fromJson(response, Metadata.class));
                    Metadata metadata = mGson.fromJson(response, Metadata.class);

                    FoodCourtVendorPagerAdapter foodCourtVendorPagerAdapter = new FoodCourtVendorPagerAdapter(FoodCourtActivity.this, metadata.getFoodCourtVendors());

                    viewPager.setAdapter(foodCourtVendorPagerAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
               //     Log.d(TAG, "Error " + error.getMessage());
                }
            });
            queue.add(stringRequest);




        }




        public class FoodCourtVendorPagerAdapter extends PagerAdapter {

            private Context context;

            public List<FoodCourtVendor> foodCourtVendors;

            public FoodCourtVendorPagerAdapter(Context context, List<FoodCourtVendor> foodCourtVendors){
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

                for (FoodCourtVendorSection s: foodCourtVendorSections) {
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




      }
