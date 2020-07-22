package my.fallacy.retrofitmoshiexample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Endpoint {
    @GET("demo")
    Call<String> retrieveWaypoints();

    @POST("requestUserData")
    Call<String> retrieveUserData();

    @POST("requestUserDataEncode")
    Call<String> retrieveUserDataEncode();

    @GET("https://airamapv093.azurewebsites.net/Oryctes/Retrieve?token=x3TKgxyb0uX5c4kwmwbdLAv96pbxXr/JCHfBV53Fc2Y=")
    Call<String> retrieveWaypointsAiramap();
}