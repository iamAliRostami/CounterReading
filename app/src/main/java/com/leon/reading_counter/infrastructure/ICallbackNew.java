package com.leon.reading_counter.infrastructure;

import retrofit2.Response;

/**
 * Created by Leon on 12/12/2017.
 */

public interface ICallbackNew<T> {
    void execute(Response<T> response);
}
