package com.leon.reading_counter.base_items;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public abstract class BaseFragmentDialog extends DialogFragment {
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        view = FragmentDialogView(inflater, parent, savedInstanseState);
        initialize();
        return view;
    }

    public abstract View FragmentDialogView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    public abstract void initialize();

    @Override
    public void onResume() {
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(params);
        super.onResume();
    }
}
