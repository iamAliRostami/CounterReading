package com.leon.reading_counter.base_items;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.leon.reading_counter.BuildConfig;
import com.leon.reading_counter.MyApplication;
import com.leon.reading_counter.R;
import com.leon.reading_counter.activities.DownloadActivity;
import com.leon.reading_counter.activities.HelpActivity;
import com.leon.reading_counter.activities.HomeActivity;
import com.leon.reading_counter.activities.LocationActivity;
import com.leon.reading_counter.activities.ReadingActivity;
import com.leon.reading_counter.activities.ReadingSettingActivity;
import com.leon.reading_counter.activities.ReportActivity;
import com.leon.reading_counter.activities.SettingActivity;
import com.leon.reading_counter.activities.UploadActivity;
import com.leon.reading_counter.adapters.NavigationDrawerAdapter;
import com.leon.reading_counter.databinding.BaseActivityBinding;
import com.leon.reading_counter.enums.BundleEnum;
import com.leon.reading_counter.enums.SharedReferenceKeys;
import com.leon.reading_counter.enums.SharedReferenceNames;
import com.leon.reading_counter.infrastructure.ISharedPreferenceManager;
import com.leon.reading_counter.utils.CustomToast;
import com.leon.reading_counter.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    RecyclerView recyclerView;
    RelativeLayout linearLayoutReadingHeader;
    NavigationDrawerAdapter adapter;
    List<NavigationDrawerAdapter.DrawerItem> dataList;
    BaseActivityBinding binding;
    ISharedPreferenceManager sharedPreferenceManager;

    protected abstract void initialize();

    @SuppressLint({"NewApi", "RtlHardcoded", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext(),
                SharedReferenceNames.ACCOUNT.getValue());
        int theme;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            theme = extras.getInt(BundleEnum.THEME.getValue());
        } else {
            theme = sharedPreferenceManager.getIntData(SharedReferenceKeys.THEME_STABLE.getValue());
        }
        MyApplication.onActivitySetTheme(this, theme, false);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
        binding = BaseActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeBase();
        initialize();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new CustomToast().info(getString(R.string.how_to_exit));
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
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            if (MyApplication.position != -1) {
                MyApplication.position = -1;
                Intent intent = new Intent(MyApplication.getContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            } else
                drawer.closeDrawer(GravityCompat.START);
        });
        recyclerView.addOnItemTouchListener(
                new NavigationDrawerAdapter.RecyclerItemClickListener(MyApplication.getContext(),
                        recyclerView, new NavigationDrawerAdapter.RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        drawer.closeDrawer(GravityCompat.START);
                        if (position == 8) {
                            MyApplication.position = -1;
                            finishAffinity();
                        } else if (MyApplication.position != position) {
                            MyApplication.position = position;
                            Intent intent = new Intent();
                            if (position == 0) {
                                intent = new Intent(getApplicationContext(), DownloadActivity.class);
                            } else if (position == 1) {
                                intent = new Intent(getApplicationContext(), ReadingActivity.class);
                            } else if (position == 2) {
                                intent = new Intent(getApplicationContext(), UploadActivity.class);
                            } else if (position == 3) {
                                intent = new Intent(getApplicationContext(), ReportActivity.class);
                            } else if (position == 4) {
                                intent = new Intent(getApplicationContext(), LocationActivity.class);
                            } else if (position == 5) {
                                intent = new Intent(getApplicationContext(), ReadingSettingActivity.class);
                            } else if (position == 6) {
                                intent = new Intent(getApplicationContext(), SettingActivity.class);
                            } else if (position == 7) {
                                intent = new Intent(getApplicationContext(), HelpActivity.class);
                            }
                            startActivity(intent);
                            finish();
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    @SuppressLint("WrongConstant")
    private void initializeBase() {
        TextView textView = findViewById(R.id.text_view_title);
        textView.setText(sharedPreferenceManager.getStringData(
                SharedReferenceKeys.DISPLAY_NAME.getValue()).concat(" (").concat(
                sharedPreferenceManager.getStringData(
                        SharedReferenceKeys.USER_CODE.getValue())).concat(")"));
        binding.textViewVersion.setText(getString(R.string.version).concat(" ")
                .concat(BuildConfig.VERSION_NAME));
        linearLayoutReadingHeader = findViewById(R.id.relative_layout_reading_header);
        if (MyApplication.position == 1) {
            linearLayoutReadingHeader.setVisibility(View.VISIBLE);
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//TODO
        drawer = binding.drawerLayout;
        recyclerView = binding.recyclerView;
        dataList = new ArrayList<>();
        fillDrawerListView();
        setOnDrawerItemClick();
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
