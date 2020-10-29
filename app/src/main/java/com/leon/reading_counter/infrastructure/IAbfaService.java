package com.leon.reading_counter.infrastructure;

import com.leon.reading_counter.tables.LoginFeedBack;
import com.leon.reading_counter.tables.LoginInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAbfaService {

    @POST("kontoriNew/V1/Account/Login")
    Call<LoginFeedBack> login(@Body LoginInfo logininfo);

}

