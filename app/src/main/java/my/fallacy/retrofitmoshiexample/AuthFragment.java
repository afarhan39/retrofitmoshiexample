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
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Map;

import my.fallacy.okhttpmoshiexample.R;
import my.fallacy.retrofitmoshiexample.auth.ResAuth;
import my.fallacy.retrofitmoshiexample.retrievewp.ResMission;
import my.fallacy.retrofitmoshiexample.retrievewp.ResRetrieveWps;
import my.fallacy.retrofitmoshiexample.retrievewp.RetrieveWpsAdapter;
import retrofit2.Call;

public class AuthFragment extends Fragment {
    private ExtendedFloatingActionButton efabCallApi;
    private TextView tvRequest;
    private TextView tvRawResponse;
    private TextView tvParsedResponse;
    private TextView tvSpecific;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_base, container, false);
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
        efabCallApi.setOnClickListener(v -> onCallAuthAiramap());
        tvRequest.setMovementMethod(new ScrollingMovementMethod());
        tvRawResponse.setMovementMethod(new ScrollingMovementMethod());
        tvParsedResponse.setMovementMethod(new ScrollingMovementMethod());
        tvSpecific.setMovementMethod(new ScrollingMovementMethod());
        tvRequest.setTextIsSelectable(true);
        tvRawResponse.setTextIsSelectable(true);
        tvParsedResponse.setTextIsSelectable(true);
        tvSpecific.setTextIsSelectable(true);

        tvRequest.setText(R.string.api_authAiramap);
    }

    private void onCallAuthAiramap() {
        onLoad();
        Endpoint endpoint = ApiBuilder.getRetrofitJsonInstance().create(Endpoint.class);
        Call<String> call = endpoint.get(getString(R.string.api_authAiramap));

        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                hideFab();
                String resString = response.body();
                tvRawResponse.setText(resString);

                parseAuthAiramap(resString);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                resetFab();
            }
        });
    }

    private void parseAuthAiramap(String resString) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<ResAuth> jsonAdapter = moshi.adapter(ResAuth.class);

        try {
            ResAuth resAuth = jsonAdapter.fromJson(resString);
            tvParsedResponse.setText(resAuth.toString());
            tvSpecific.setText(resAuth.getAuth());
        } catch (IOException e) {
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