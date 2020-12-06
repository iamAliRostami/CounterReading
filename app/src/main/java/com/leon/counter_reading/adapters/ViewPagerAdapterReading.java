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

import java.util.ArrayList;

public class ViewPagerAdapterReading extends FragmentStatePagerAdapter {
    ReadingData readingData;
    ArrayList<String> items = new ArrayList<>();

    public ViewPagerAdapterReading(@NonNull FragmentManager fm, int behavior,
                                   ReadingData readingData) {
        super(fm, behavior);
        this.readingData = readingData;
        for (int i = 0; i < readingData.counterStateDtos.size(); i++) {
            items.add(readingData.counterStateDtos.get(i).title);
        }
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        ReadingData.ReadingConfigDefaultDto readingConfigDefaultDtoTemp = null;
        ReadingData.QotrDictionary qotrDictionaryTemp = null;
        ReadingData.KarbariDto karbariDtoTemp = null;
        for (ReadingData.ReadingConfigDefaultDto readingConfigDefaultDto :
                readingData.readingConfigDefaultDtos
        ) {
            if (readingConfigDefaultDto.zoneId == readingData.onOffLoadDtos.get(position).zoneId)
                readingConfigDefaultDtoTemp = readingConfigDefaultDto;
        }
        for (ReadingData.QotrDictionary qotrDictionary : readingData.qotrDictionary) {
            if (qotrDictionary.id == readingData.onOffLoadDtos.get(position).qotrCode)
                qotrDictionaryTemp = qotrDictionary;

        }
        for (ReadingData.KarbariDto karbariDto : readingData.karbariDtos) {
            if (karbariDto.id == readingData.onOffLoadDtos.get(position).karbariCode)
                karbariDtoTemp = karbariDto;

        }
        return ReadingFragment.newInstance(readingData.onOffLoadDtos.get(position),
                readingConfigDefaultDtoTemp, karbariDtoTemp, qotrDictionaryTemp,
                readingData.counterStateDtos, items, position);
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
