package my.fallacy.retrofitmoshiexample.userdata;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UserDataAdapter {
    @FromJson
    UserData fromJson(String userDataEncoded) {
        try {
            Moshi moshi = new Moshi.Builder().build();
            //url decode
            String userDataDecoded = URLDecoder.decode(userDataEncoded,"utf-8");
            return moshi.adapter(UserData.class).fromJson(userDataDecoded);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}