package com.leon.reading_counter.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.InputType;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.auth0.android.jwt.JWT;
import com.leon.reading_counter.BuildConfig;
import com.leon.reading_counter.R;
import com.leon.reading_counter.databinding.ActivityLoginBinding;
import com.leon.reading_counter.enums.DialogType;
import com.leon.reading_counter.enums.ProgressType;
import com.leon.reading_counter.enums.SharedReferenceKeys;
import com.leon.reading_counter.enums.SharedReferenceNames;
import com.leon.reading_counter.infrastructure.IAbfaService;
import com.leon.reading_counter.infrastructure.ICallback;
import com.leon.reading_counter.infrastructure.ICallbackError;
import com.leon.reading_counter.infrastructure.ICallbackIncomplete;
import com.leon.reading_counter.infrastructure.ISharedPreferenceManager;
import com.leon.reading_counter.tables.LoginFeedBack;
import com.leon.reading_counter.tables.LoginInfo;
import com.leon.reading_counter.utils.Crypto;
import com.leon.reading_counter.utils.CustomDialog;
import com.leon.reading_counter.utils.CustomErrorHandling;
import com.leon.reading_counter.utils.CustomToast;
import com.leon.reading_counter.utils.HttpClientWrapper;
import com.leon.reading_counter.utils.NetworkHelper;
import com.leon.reading_counter.utils.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    ISharedPreferenceManager sharedPreferenceManager;
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
                attemptLogin();
            }
        });
    }

    void attemptLogin() {
        Retrofit retrofit = NetworkHelper.getInstance();
        final IAbfaService loginInfo = retrofit.create(IAbfaService.class);
        Call<LoginFeedBack> call = loginInfo.login(new LoginInfo(username, password));
        HttpClientWrapper.callHttpAsync(call, ProgressType.SHOW.getValue(), this,
                new Login(), new GetErrorIncomplete(), new GetError());
    }

    void savePreference() {
        if (binding.checkBoxSave.isChecked()) {
            sharedPreferenceManager.putData(SharedReferenceKeys.USERNAME.getValue(), username);
            sharedPreferenceManager.putData(SharedReferenceKeys.PASSWORD.getValue(), Crypto.encrypt(password));
        }
    }

    void loadPreference() {
        if (sharedPreferenceManager.checkIsNotEmpty(SharedReferenceKeys.USERNAME.getValue()) &&
                sharedPreferenceManager.checkIsNotEmpty(SharedReferenceKeys.PASSWORD.getValue())) {
            binding.editTextUsername.setText(sharedPreferenceManager.getStringData(
                    SharedReferenceKeys.USERNAME.getValue()));
            binding.editTextPassword.setText(Crypto.decrypt(sharedPreferenceManager.getStringData(
                    SharedReferenceKeys.PASSWORD.getValue())));
        }
    }

    class Login implements ICallback<LoginFeedBack> {
        @Override
        public void execute(Response<LoginFeedBack> response) {
            LoginFeedBack loginFeedBack = response.body();
            if (loginFeedBack.access_token == null ||
                    loginFeedBack.refresh_token == null ||
                    loginFeedBack.access_token.length() < 1 ||
                    loginFeedBack.refresh_token.length() < 1) {
                CustomToast customToast = new CustomToast();
                customToast.warning(getString(R.string.error_is_not_match));
            } else {
                List<String> cookieList = response.headers().values("Set-Cookie");
                loginFeedBack.XSRFToken = (cookieList.get(1).split(";"))[0];
                JWT jwt = new JWT(loginFeedBack.access_token);
                loginFeedBack.displayName = jwt.getClaim("DisplayName").asString();
                loginFeedBack.userCode = jwt.getClaim("UserCode").asString();
                sharedPreferenceManager.putData(SharedReferenceKeys.DISPLAY_NAME.getValue(), loginFeedBack.displayName);
                sharedPreferenceManager.putData(SharedReferenceKeys.USER_CODE.getValue(), loginFeedBack.userCode);
                sharedPreferenceManager.putData(SharedReferenceKeys.TOKEN.getValue(), loginFeedBack.access_token);
                sharedPreferenceManager.putData(SharedReferenceKeys.REFRESH_TOKEN.getValue(), loginFeedBack.refresh_token);
                sharedPreferenceManager.putData(SharedReferenceKeys.XSRF.getValue(), loginFeedBack.XSRFToken);

                savePreference();
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    class GetErrorIncomplete implements ICallbackIncomplete<LoginFeedBack> {
        @Override
        public void executeIncomplete(Response<LoginFeedBack> response) {
            CustomErrorHandling customErrorHandlingNew = new CustomErrorHandling(context);
            String error = customErrorHandlingNew.getErrorMessageDefault(response);
            if (response.code() == 401) {
                error = LoginActivity.this.getString(R.string.error_is_not_match);
                CustomToast customToast = new CustomToast();
                customToast.warning(error);
            } else
                new CustomDialog(DialogType.Yellow, LoginActivity.this, error,
                        LoginActivity.this.getString(R.string.dear_user),
                        LoginActivity.this.getString(R.string.login),
                        LoginActivity.this.getString(R.string.accepted));
        }
    }

    class GetError implements ICallbackError {
        @Override
        public void executeError(Throwable t) {
            CustomErrorHandling customErrorHandlingNew = new CustomErrorHandling(context);
            String error = customErrorHandlingNew.getErrorMessageTotal(t);
            new CustomDialog(DialogType.YellowRedirect, LoginActivity.this, error,
                    LoginActivity.this.getString(R.string.dear_user),
                    LoginActivity.this.getString(R.string.login),
                    LoginActivity.this.getString(R.string.accepted));
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