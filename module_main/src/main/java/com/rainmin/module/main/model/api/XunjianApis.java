package com.rainmin.module.main.model.api;

import com.rainmin.module.main.model.bean.HttpResult;
import com.rainmin.module.main.model.bean.Xunjian;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface XunjianApis {

    @GET("/")
    Observable<HttpResult<Xunjian>> queryXunjianHistory(@Query("pageNo") int pageNo,
                                                        @Query("pageSize") int pageSize);
}
