package com.leon.counter_reading.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

    public ViewPagerAdapterReading(@NonNull FragmentManager fm, int behavior, ReadingData readingData) {
        super(fm, behavior);
        this.readingData = readingData;
    }

    public static int getPosition() {
        return position;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
//        for (ReadingData.ReadingConfigDefaultDto readingConfigDefaultDto :
//                readingData.readingConfigDefaultDtos
//        ) {
//            if (readingConfigDefaultDto.zoneId==readingData.onOffLoadDtos.get(position).)
//        }
        return ReadingFragment.newInstance(readingData.onOffLoadDtos.get(position),
                readingData.readingConfigDefaultDtos.get(position));
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
