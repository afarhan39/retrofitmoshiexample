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

import my.fallacy.okhttpmoshiexample.R;
import my.fallacy.retrofitmoshiexample.userdata.Res;
import my.fallacy.retrofitmoshiexample.userdata.UserDataAdapter;
import retrofit2.Call;

public class UserDataFragment extends Fragment {
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

        efabCallApi.setOnClickListener(v -> onCallApiUserDataEncoded());
        tvRequest.setMovementMethod(new ScrollingMovementMethod());
        tvRawResponse.setMovementMethod(new ScrollingMovementMethod());
        tvParsedResponse.setMovementMethod(new ScrollingMovementMethod());
        tvSpecific.setMovementMethod(new ScrollingMovementMethod());
    }

    private void onCallApiUserDataEncoded() {
        onLoad();
        Endpoint endpoint = ApiBuilder.getRetrofitJsonInstance().create(Endpoint.class);
        Call<String> call = endpoint.retrieveUserDataEncode();

        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                hideFab();
                String resString = response.body();
                tvRawResponse.setText(resString);

                parseUserDataEncoded(resString);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                resetFab();
            }
        });
    }

    private void parseUserDataEncoded(String resString) {
        Moshi moshi = new Moshi.Builder().add(new UserDataAdapter()).build();
        JsonAdapter<Res> jsonAdapter = moshi.adapter(Res.class);

        try {
            Res res = jsonAdapter.nonNull().fromJson(resString);
            tvParsedResponse.setText(res.toString());
            tvSpecific.setText(res.getUserData().getProfession());
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