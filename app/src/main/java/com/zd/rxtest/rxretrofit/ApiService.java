package com.zd.rxtest.rxretrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * use to
 * <p>
 * Created by zhangdong on 2017/10/10.
 *
 * @version 2.1
 */

public interface ApiService {

    @GET("joke/content/list.from")
    Observable<CodeMsgModel> getJoke(@Query("sort") String sort,
                                     @Query("page") int page,
                                     @Query("pagesize") int pagesize,
                                     @Query("time") String time,
                                     @Query("key") String key);
}
