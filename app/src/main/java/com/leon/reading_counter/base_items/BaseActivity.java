package com.leon.reading_counter.base_items;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.leon.reading_counter.MyApplication;
import com.leon.reading_counter.R;
import com.leon.reading_counter.activities.HomeActivity;
import com.leon.reading_counter.adapters.NavigationDrawerAdapter;
import com.leon.reading_counter.databinding.BaseActivityBinding;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    RecyclerView recyclerView;
    NavigationDrawerAdapter adapter;
    List<NavigationDrawerAdapter.DrawerItem> dataList;
    BaseActivityBinding binding;

    protected abstract void initialize();

    @SuppressLint({"NewApi", "RtlHardcoded", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
        binding = BaseActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initializeBase();
        setSupportActionBar(toolbar);//TODO
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawer, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(view1 -> drawer.openDrawer(Gravity.START));
        initialize();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("RtlHardcoded")
    void setOnDrawerItemClick() {
        binding.imageViewHeader.setOnClickListener(v -> {
            MyApplication.position = -1;
            Intent intent = new Intent(MyApplication.getContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        });
        recyclerView.addOnItemTouchListener(
                new NavigationDrawerAdapter.RecyclerItemClickListener(MyApplication.getContext(),
                        recyclerView, new NavigationDrawerAdapter.RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        drawer.closeDrawer(GravityCompat.START);
                        MyApplication.position = position;
                        if (position == 0) {
                        } else if (position == 8) {
                            MyApplication.position = -1;
                            finishAffinity();
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    private void initializeBase() {
        toolbar = findViewById(R.id.toolbar);
        drawer = binding.drawerLayout;
        recyclerView = binding.recyclerView;
        dataList = new ArrayList<>();
        fillDrawerListView();
        setOnDrawerItemClick();
    }

    void fillDrawerListView() {
        dataList = NavigationDrawerAdapter.DrawerItem.createItemList(
                getResources().getStringArray(R.array.menu), getResources().obtainTypedArray(
                        R.array.icons));
        adapter = new NavigationDrawerAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        recyclerView.setNestedScrollingEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
