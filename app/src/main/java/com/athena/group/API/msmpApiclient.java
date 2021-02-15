package com.athena.group.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class msmpApiclient {



    //public static final String Base_Url="http://api.goldhana.com/public/";

   //public static final String Base_Url="http://192.168.0.104:8000/";
   public static final String Base_Url="http://diestechnology.com.au/projects/athena/app_controller/";

    public static Retrofit retrofit=null;

    public msmpApiclient() {
    }

    public static Retrofit getApiClient()
    {



      /*  Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/
        if (retrofit==null)
        {
            retrofit=new Retrofit.Builder().
                    baseUrl(Base_Url).
                    addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;

    }


}

