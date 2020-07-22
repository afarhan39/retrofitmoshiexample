package my.fallacy.retrofitmoshiexample;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import my.fallacy.okhttpmoshiexample.R;
import my.fallacy.retrofitmoshiexample.userdata.Res;
import my.fallacy.retrofitmoshiexample.userdata.UserDataAdapter;
import retrofit2.Call;

public class UserDataFragment extends Fragment {
    private Button bCallApi;
    private TextView tvRaw;
    private TextView tvProcessed;
    private TextView tvProcessedJob;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_base, container, false);
        bindId(root);
        return root;
    }

    private void bindId(View root) {
        bCallApi = root.findViewById(R.id.bCallApi);
        tvRaw = root.findViewById(R.id.tvRaw);
        tvProcessed = root.findViewById(R.id.tvProcessed);
        tvProcessedJob = root.findViewById(R.id.tvProcessedJob);

        bCallApi.setOnClickListener(v -> onCallApiUserDataEncoded());
        tvRaw.setMovementMethod(new ScrollingMovementMethod());
        tvProcessed.setMovementMethod(new ScrollingMovementMethod());
        tvProcessedJob.setMovementMethod(new ScrollingMovementMethod());
    }

    private void onCallApiUserDataEncoded() {
        Endpoint endpoint = ApiBuilder.getRetrofitJsonInstance().create(Endpoint.class);
        Call<String> call = endpoint.retrieveUserDataEncode();

        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String resString = response.body();
                tvRaw.setText(resString);

                parseUserDataEncoded(resString);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void parseUserDataEncoded(String resString) {
        Moshi moshi = new Moshi.Builder().add(new UserDataAdapter()).build();
        JsonAdapter<Res> jsonAdapter = moshi.adapter(Res.class);

        try {
            Res res = jsonAdapter.nonNull().fromJson(resString);
            tvProcessed.setText(res.toString());
            tvProcessedJob.setText(res.getUserData().getProfession());
        } catch (IOException e) {
            tvProcessed.setText("GG");
        }
    }
}