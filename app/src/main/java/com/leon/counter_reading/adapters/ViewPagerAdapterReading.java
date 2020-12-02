package com.leon.counter_reading.adapters;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.leon.counter_reading.fragments.ReadingFragment;
import com.leon.counter_reading.tables.ReadingData;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapterReading extends FragmentStatePagerAdapter {
    static int position;
    ReadingData readingData;

    public ViewPagerAdapterReading(FragmentManager fm, ReadingData readingData) {
        super(fm);
        this.readingData = readingData;
    }

    public static int getPosition() {
        return position;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return ReadingFragment.newInstance(readingData.onOffLoadDtos.get(position));
    }

    @Override
    public int getCount() {
        return readingData.onOffLoadDtos.size();
    }

    @Override
    public int getItemPosition(@NotNull Object object) {
        notifyDataSetChanged();
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
        FragmentManager manager = ((Fragment) object).getFragmentManager();
        FragmentTransaction trans;
        if (manager != null) {
            trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
        }
        super.destroyItem(container, position, object);
    }
}
