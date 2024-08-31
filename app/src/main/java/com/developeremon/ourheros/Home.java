package com.developeremon.ourheros;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Home extends Fragment {

    private ViewPager2 viewPager2;
    private Handler sliderhander = new Handler();
    TextView timetv,viewall;
    TabLayout tableLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ViewHome = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager2 = ViewHome.findViewById(R.id.viewpager2);
        //timetv = ViewHome.findViewById(R.id.timetv);
        tableLayout = ViewHome.findViewById(R.id.tabLayout);
        viewall = ViewHome.findViewById(R.id.viewall);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list_frag, new List_Fargment() );
        fragmentTransaction.commit();


        //----------------Method Called-------------------------
        //timetv.setText(getGreetingMessage());
        ImageSliderMethod();
        TabLayoutMethod();
        //----------------------------------------------------------


        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              startActivity(new Intent(getContext(), View_All_List.class));
              TabLayoutMethod();



            }
        });



        return ViewHome;


    }//---- OnCrate Finish

    //------------------------------------------------------------------------------------

    private void TabLayoutMethod(){

        // Add OnTabSelectedListener to TabLayout
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {

                    List_Fargment.api_url = "http://192.168.1.100/Shohid/data.json";
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.list_frag, new List_Fargment() );
                    fragmentTransaction.commit();

                } else   if (tab.getPosition() == 1) {

                    List_Fargment.api_url = "http://192.168.1.100/Shohid/data2.json";
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.list_frag, new List_Fargment() );
                    fragmentTransaction.commit();

                }  else   if (tab.getPosition() == 2) {

                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.list_frag, new List_Fargment() );
                    fragmentTransaction.commit();

                } else {

                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.list_frag, new List_Fargment() );
                    fragmentTransaction.commit();

                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselected
                Toast.makeText(getContext(), tab.getText() + " Unselected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselected
                Toast.makeText(getContext(), tab.getText() + " Reselected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //===================================================================================

    private void ImageSliderMethod(){

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.mir_mugdho));
        sliderItems.add(new SliderItem(R.drawable.cover_pic1));
        sliderItems.add(new SliderItem(R.drawable.pic));
        sliderItems.add(new SliderItem(R.drawable.cover_pic2));
        sliderItems.add(new SliderItem(R.drawable.abu_sayed));
        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderhander.removeCallbacks(sliderRunnable);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    sliderhander.postDelayed(sliderRunnable, 3000 );
                }
            }
        });

    }

    //-------------------  Time  Decler -----------------------------------------
    private String getGreetingMessage() {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 5 && hour < 12) {
            return "Good Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon";
        } else if (hour >= 17 && hour < 21) {
            return "Good Evening";
        } else {
            return "Good Night";
        }
    }
    //=============================================================================


    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
           viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderhander.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderhander.postDelayed(sliderRunnable,3000);
    }

    //=============================================================================





}