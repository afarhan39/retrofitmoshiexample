package my.fallacy.retrofitmoshiexample;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Map;

import my.fallacy.okhttpmoshiexample.R;
import my.fallacy.retrofitmoshiexample.retrievewp.ResMission;
import my.fallacy.retrofitmoshiexample.retrievewp.ResRetrieveWps;
import my.fallacy.retrofitmoshiexample.retrievewp.RetrieveWpsAdapter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class UpdateHandshakeFragment extends Fragment {
    private ExtendedFloatingActionButton efabCallApi;
    private TextView tvRequest;
    private TextView tvRawResponse;
    private TextView tvParsedResponse;
    private TextView tvSpecific;
    private String body;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_base, container, false);
        if (getArguments().getBoolean("body"))
            body = "{\n" +
                    "    \"token\": \"x3TKgxyb0uX5c4kwmwbdLAv96pbxXr/JCHfBV53Fc2Y=\",\n" +
                    "    \"totalMissions\": \"2\",\n" +
                    "    \"Act\": \"reset\"\n" +
                    "}";
        else
            body = "{\n" +
                    "    \"token\": \"x3TKgxyb0uX5c4kwmwbdLAv96pbxXr/JCHfBV53Fc2Y=\",\n" +
                    "    \"totalMissions\": \"2\",\n" +
                    "    \"Act\": \"\"\n" +
                    "}";
        bindId(root);
        return root;
    }

    private void bindId(View root) {
        efabCallApi = root.findViewById(R.id.efabCallApi);
        tvRequest = root.findViewById(R.id.tvRequest);
        tvRawResponse = root.findViewById(R.id.tvRawResponse);
        tvParsedResponse = root.findViewById(R.id.tvParsedResponse);
        tvSpecific = root.findViewById(R.id.tvSpecific);

        setDefaultAttr();
    }

    private void setDefaultAttr() {
        efabCallApi.setOnClickListener(v -> onCallApiUpdateHandshake());
        tvRequest.setMovementMethod(new ScrollingMovementMethod());
        tvRawResponse.setMovementMethod(new ScrollingMovementMethod());
        tvParsedResponse.setMovementMethod(new ScrollingMovementMethod());
        tvSpecific.setMovementMethod(new ScrollingMovementMethod());
        tvRequest.setTextIsSelectable(true);
        tvRawResponse.setTextIsSelectable(true);
        tvParsedResponse.setTextIsSelectable(true);
        tvSpecific.setTextIsSelectable(true);

        tvRequest.setText(body);
    }

    private void onCallApiUpdateHandshake() {
        onLoad();
        String x = "{\n" +
                "    \"token\": \"x3TKgxyb0uX5c4kwmwbdLAv96pbxXr/JCHfBV53Fc2Y=\",\n" +
                "    \"totalMissions\": \"2\",\n" +
                "    \"Act\": \"reset\"\n" +
                "}";
        Endpoint endpoint = ApiBuilder.getRetrofitJsonInstance().create(Endpoint.class);
        Call<String> call = endpoint.post(getString(R.string.api_updateHandshake), RequestBody.create(MediaType.parse("application/json"), body));

        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                hideFab();
                String resString = response.body();
                tvRawResponse.setText(resString);

//                parseAiramapEndoindEncoded(resString);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                resetFab();
            }
        });
    }

    private void parseAiramapEndoindEncoded(String resString) {
        Moshi moshi = new Moshi.Builder().add(new RetrieveWpsAdapter()).build();
        JsonAdapter<ResRetrieveWps> jsonAdapter = moshi.adapter(ResRetrieveWps.class);

        try {
            ResRetrieveWps res = jsonAdapter.fromJson(resString);
            tvParsedResponse.setText(res.toString());

            if (res.getStatus() == 200) {
                for (Map.Entry<String, ResMission> entry: res.getResMissionSortedMap().entrySet()) {
                    if (entry.getKey().equals("0"))
                        tvSpecific.setText(entry.getValue().getWaypointMap().toString());
                }
            } else
                tvSpecific.setText("some error");

        } catch (IOException | JsonDataException e) {
            resetFab();
            tvParsedResponse.setText(e.getMessage());
            tvSpecific.setText(e.getMessage());
        }
    }

    private void onLoad() {
        efabCallApi.setText(R.string.btn_loading);
    }

    private void hideFab() {
        efabCallApi.hide();
    }

    private void resetFab() {
        efabCallApi.show();
        efabCallApi.setText(R.string.btn_callapitryagain);
    }
}