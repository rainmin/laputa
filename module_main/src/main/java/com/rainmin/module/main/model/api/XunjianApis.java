package com.rainmin.module.main.model.api;

import com.rainmin.module.main.model.bean.HttpResult;
import com.rainmin.module.main.model.bean.Xunjian;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface XunjianApis {

    @GET("xunjian/query")
    Observable<HttpResult<Xunjian>> queryXunjianHistory(@Query("pageStart") String pageStart,
                                                        @Query("pageSize") String pageSize);
}
