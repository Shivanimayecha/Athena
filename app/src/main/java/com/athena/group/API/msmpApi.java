package com.athena.group.API;


import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.Model.OrderDetailsTruckModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface msmpApi {
    @FormUrlEncoded
    @POST("UserData/all_assign_order")
    public Call<OrderDetailsTruckModel> all_assign_order(@Field("ao_trusr_id") String ao_trusr_id);

    @FormUrlEncoded
    @POST("UserData/get_open_order")
    public Call<OrderDetailsModel> get_openOrders(@Field("ord_site_id") String site);

}
