package com.leon.counter_reading.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.leon.counter_reading.fragments.ReadingFragment;
import com.leon.counter_reading.tables.KarbariDto;
import com.leon.counter_reading.tables.OnOffLoadDto;
import com.leon.counter_reading.tables.QotrDictionary;
import com.leon.counter_reading.tables.ReadingConfigDefaultDto;
import com.leon.counter_reading.tables.ReadingData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPagerAdapterReading extends FragmentStatePagerAdapter {
    ReadingData readingData;
    ReadingData readingDataTemp;
    ArrayList<String> items = new ArrayList<>();

    public ViewPagerAdapterReading(@NonNull FragmentManager fm, int behavior,
                                   ReadingData readingData) {
        super(fm, behavior);
        this.readingData = readingData;
        readingDataTemp = readingData;
        for (int i = 0; i < readingData.counterStateDtos.size(); i++) {
            items.add(readingData.counterStateDtos.get(i).title);
        }
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        ReadingConfigDefaultDto readingConfigDefaultDtoTemp = null;
        QotrDictionary qotrDictionaryTemp = null;
        KarbariDto karbariDtoTemp = null;
        for (ReadingConfigDefaultDto readingConfigDefaultDto :
                readingDataTemp.readingConfigDefaultDtos
        ) {
            if (readingConfigDefaultDto.zoneId == readingDataTemp.onOffLoadDtos.get(position).zoneId)
                readingConfigDefaultDtoTemp = readingConfigDefaultDto;
        }
        for (QotrDictionary qotrDictionary : readingDataTemp.qotrDictionary) {
            if (qotrDictionary.id == readingDataTemp.onOffLoadDtos.get(position).qotrCode)
                qotrDictionaryTemp = qotrDictionary;

        }
        for (KarbariDto karbariDto : readingDataTemp.karbariDtos) {
            if (karbariDto.id == readingDataTemp.onOffLoadDtos.get(position).karbariCode)
                karbariDtoTemp = karbariDto;

        }
        return ReadingFragment.newInstance(readingDataTemp.onOffLoadDtos.get(position),
                readingConfigDefaultDtoTemp, karbariDtoTemp, qotrDictionaryTemp,
                readingDataTemp.counterStateDtos, items, position);
    }

    @Override
    public int getCount() {
        return readingDataTemp.onOffLoadDtos.size();
    }

    @Override
    public int getItemPosition(@NotNull Object object) {
//        notifyDataSetChanged();
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

    public void filter(int type, String key) {
        switch (type) {
            case 0:
                readingDataTemp.onOffLoadDtos.clear();
                for (OnOffLoadDto onOffLoadDto : readingData.onOffLoadDtos) {
                    if (onOffLoadDto.eshterak.toLowerCase().contains(key))
                        readingDataTemp.onOffLoadDtos.add(onOffLoadDto);
                }
                break;
            case 1:
                readingDataTemp.onOffLoadDtos.clear();
                for (OnOffLoadDto onOffLoadDto : readingData.onOffLoadDtos) {
                    if (onOffLoadDto.radif == Integer.parseInt(key))
                        readingDataTemp.onOffLoadDtos.add(onOffLoadDto);
                }
                break;
            case 2:
                readingDataTemp.onOffLoadDtos.clear();
                for (OnOffLoadDto onOffLoadDto : readingData.onOffLoadDtos) {
                    if (onOffLoadDto.counterSerial.toLowerCase().contains(key))
                        readingDataTemp.onOffLoadDtos.add(onOffLoadDto);
                }
                break;
            case 3:
                readingDataTemp.onOffLoadDtos.clear();
                for (OnOffLoadDto onOffLoadDto : readingData.onOffLoadDtos) {
                    if (onOffLoadDto.firstName.toLowerCase().contains(key))
                        readingDataTemp.onOffLoadDtos.add(onOffLoadDto);
                    if (onOffLoadDto.sureName.toLowerCase().contains(key))
                        readingDataTemp.onOffLoadDtos.add(onOffLoadDto);
                }
                break;
            case 5:
                readingDataTemp.onOffLoadDtos.clear();
                readingDataTemp.onOffLoadDtos = readingData.onOffLoadDtos;
                break;
        }
        notifyDataSetChanged();
    }
}
