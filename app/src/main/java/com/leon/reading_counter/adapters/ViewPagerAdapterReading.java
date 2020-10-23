package com.leon.reading_counter.adapters;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.leon.reading_counter.fragments.ReadingFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViewPagerAdapterReading extends FragmentStatePagerAdapter {
    static int position;
    List<Integer> spinnerItemSelected;

    public ViewPagerAdapterReading(FragmentManager fm, List<Integer> spinnerItemSelected) {
        super(fm);
        this.spinnerItemSelected = spinnerItemSelected;
    }

    public static int getPosition() {
        return position;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return new ReadingFragment();
    }

    @Override
    public int getCount() {
        return spinnerItemSelected.size();
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
