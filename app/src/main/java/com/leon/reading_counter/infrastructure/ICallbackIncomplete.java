package com.leon.reading_counter.infrastructure;

import retrofit2.Response;

public interface ICallbackIncomplete<T> {
    void executeIncomplete(Response<T> response);
}
