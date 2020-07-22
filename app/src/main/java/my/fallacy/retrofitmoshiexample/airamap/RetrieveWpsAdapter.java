package my.fallacy.retrofitmoshiexample.airamap;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Map;

public class RetrieveWpsAdapter {
    @FromJson
    Map<String, ResMission> fromJson(String resMissionEncoded) {
        try {
            Type type = Types.newParameterizedType(Map.class, String.class, ResMission.class);

            Moshi moshi = new Moshi.Builder().build();
            //url decode
            String resMissionDecoded = URLDecoder.decode(resMissionEncoded,"utf-8");
            return (Map<String, ResMission>) moshi.adapter(type).fromJson(resMissionDecoded);
//            return moshi.adapter(ResMission.class).fromJson(resMissionDecoded);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}