package com.leon.reading_counter.infrastructure;


import android.view.View;

public interface ViewConfigurator<T extends View> {
    void configureView(T v);
}
