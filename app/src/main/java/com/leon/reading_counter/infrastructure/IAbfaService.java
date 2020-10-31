package com.leon.reading_counter.infrastructure;

import com.leon.reading_counter.tables.LoginFeedBack;
import com.leon.reading_counter.tables.LoginInfo;
import com.leon.reading_counter.tables.PasswordInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAbfaService {

    @POST("kontoriNew/V1/Account/Login")
    Call<LoginFeedBack> login(@Body LoginInfo logininfo);

    @POST("kontori/v1/api/ChangePassword")
    Call<Integer> changePassword(@Body PasswordInfo passwordInfo);

}

