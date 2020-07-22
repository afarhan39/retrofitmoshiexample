package my.fallacy.retrofitmoshiexample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Endpoint {
    @GET
    Call<String> get(@Url String url);

    @POST
    Call<String> post(@Url String url);
}