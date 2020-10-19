package com.leon.reading_counter.utils.custom_dialogue;

import android.view.View;

public interface ViewConfigurator<T extends View> {
    void configureView(T v);
}
