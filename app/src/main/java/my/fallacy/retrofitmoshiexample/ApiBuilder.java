package my.fallacy.retrofitmoshiexample;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiBuilder {
    private ApiBuilder() {
    }

    private static Retrofit retrofit;

    public static Retrofit getRetrofitJsonInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://afarhan.free.beeceptor.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}