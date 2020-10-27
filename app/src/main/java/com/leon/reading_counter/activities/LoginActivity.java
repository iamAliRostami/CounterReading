package com.leon.reading_counter.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.InputType;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.reading_counter.BuildConfig;
import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.ActivityLoginBinding;
import com.leon.reading_counter.enums.SharedReferenceKeys;
import com.leon.reading_counter.enums.SharedReferenceNames;
import com.leon.reading_counter.utils.SharedPreferenceManager;

public class LoginActivity extends AppCompatActivity {
    SharedPreferenceManager sharedPreferenceManager;
    ActivityLoginBinding binding;
    Context context;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialize();
    }

    void initialize() {
        context = this;
        binding.textViewVersion.setText(getString(R.string.version).concat(" ")
                .concat(BuildConfig.VERSION_NAME));
        sharedPreferenceManager = new SharedPreferenceManager(context, SharedReferenceNames.ACCOUNT.getValue());
        loadPreference();
        binding.imageViewPassword.setImageResource(R.drawable.img_password);
        binding.imageViewLogo.setImageResource(R.drawable.img_login_logo);
        binding.imageViewPerson.setImageResource(R.drawable.img_profile);
        binding.imageViewUsername.setImageResource(R.drawable.img_user);
        setOnLoginClickListener();
        setOnImageViewPasswordClickListener();
        setEditTextUsernameOnFocusChangeListener();
        setEditTextPasswordOnFocusChangeListener();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void setEditTextUsernameOnFocusChangeListener() {
        binding.editTextUsername.setOnFocusChangeListener((view, b) -> {
            binding.editTextUsername.setHint("");
            if (b) {
                binding.linearLayoutUsername.setBackground(getResources().getDrawable(R.drawable.border_black_2));
                binding.editTextPassword.setTextColor(getResources().getColor(R.color.black));
            } else {
                binding.linearLayoutUsername.setBackground(getResources().getDrawable(R.drawable.border_gray_2));
                binding.editTextPassword.setTextColor(getResources().getColor(R.color.gray));
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void setEditTextPasswordOnFocusChangeListener() {
        binding.editTextPassword.setOnFocusChangeListener((view, b) -> {
            binding.editTextPassword.setHint("");
            if (b) {
                binding.linearLayoutPassword.setBackground(getResources().getDrawable(R.drawable.border_black_2));
                binding.editTextPassword.setTextColor(getResources().getColor(R.color.black));
            } else {
                binding.linearLayoutPassword.setBackground(getResources().getDrawable(R.drawable.border_gray_2));
                binding.editTextPassword.setTextColor(getResources().getColor(R.color.gray));
            }
        });
    }

    void setOnImageViewPasswordClickListener() {
        binding.imageViewPassword.setOnClickListener(v -> binding.imageViewPassword.setOnClickListener(view -> {
            if (binding.editTextPassword.getInputType() != InputType.TYPE_CLASS_TEXT) {
                binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            } else
                binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }));
    }

    void setOnLoginClickListener() {
        binding.buttonLogin.setOnClickListener(v -> {
            View view;
            boolean cancel = false;
            if (binding.editTextUsername.getText().length() < 1) {
                binding.editTextUsername.setError(getString(R.string.error_empty));
                view = binding.editTextUsername;
                view.requestFocus();
                cancel = true;
            }
            if (!cancel && binding.editTextPassword.getText().length() < 1) {
                binding.editTextPassword.setError(getString(R.string.error_empty));
                view = binding.editTextPassword;
                view.requestFocus();
                cancel = true;
            }
            if (!cancel) {
                username = binding.editTextUsername.getText().toString();
                password = binding.editTextPassword.getText().toString();
                savePreference();
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void savePreference() {
        if (binding.checkBoxSave.isChecked()) {
            sharedPreferenceManager.putData(SharedReferenceKeys.USERNAME.getValue(), username);
            sharedPreferenceManager.putData(SharedReferenceKeys.PASSWORD.getValue(), password);
        }
    }

    void loadPreference() {
        if (sharedPreferenceManager.checkIsNotEmpty(SharedReferenceKeys.USERNAME.getValue()) &&
                sharedPreferenceManager.checkIsNotEmpty(SharedReferenceKeys.PASSWORD.getValue())) {
            binding.editTextUsername.setText(sharedPreferenceManager.getStringData(
                    SharedReferenceKeys.USERNAME.getValue()));
            binding.editTextPassword.setText(sharedPreferenceManager.getStringData(
                    SharedReferenceKeys.PASSWORD.getValue()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        context = null;
        binding.imageViewPerson.setImageDrawable(null);
        binding.imageViewPassword.setImageDrawable(null);
        binding.imageViewLogo.setImageDrawable(null);
        binding.imageViewUsername.setImageDrawable(null);
        System.gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }

    @Override
    protected void onStop() {
        super.onStop();
        context = null;
        binding.imageViewPerson.setImageDrawable(null);
        binding.imageViewPassword.setImageDrawable(null);
        binding.imageViewLogo.setImageDrawable(null);
        binding.imageViewUsername.setImageDrawable(null);
        System.gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().totalMemory();
        Runtime.getRuntime().freeMemory();
        Runtime.getRuntime().maxMemory();
        Debug.getNativeHeapAllocatedSize();
    }
}