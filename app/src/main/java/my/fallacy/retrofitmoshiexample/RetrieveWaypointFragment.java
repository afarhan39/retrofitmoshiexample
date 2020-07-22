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
import java.util.Map;

import my.fallacy.okhttpmoshiexample.R;
import my.fallacy.retrofitmoshiexample.airamap.ResMission;
import my.fallacy.retrofitmoshiexample.airamap.ResRetrieveWps;
import my.fallacy.retrofitmoshiexample.airamap.RetrieveWpsAdapter;
import retrofit2.Call;

public class RetrieveWaypointFragment extends Fragment {
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

        bCallApi.setOnClickListener(v -> onCallApiWaypointAiramap());
        tvRaw.setMovementMethod(new ScrollingMovementMethod());
        tvProcessed.setMovementMethod(new ScrollingMovementMethod());
        tvProcessedJob.setMovementMethod(new ScrollingMovementMethod());
    }

    private void onCallApiWaypointAiramap() {
        Endpoint endpoint = ApiBuilder.getRetrofitJsonInstance().create(Endpoint.class);
        Call<String> call = endpoint.retrieveWaypointsAiramap();

        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String resString = response.body();
                tvRaw.setText(resString);

                parseAiramapEndoindEncoded(resString);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void parseAiramapEndoindEncoded(String resString) {
        Moshi moshi = new Moshi.Builder().add(new RetrieveWpsAdapter()).build();
        JsonAdapter<ResRetrieveWps> jsonAdapter = moshi.adapter(ResRetrieveWps.class);

        try {
            ResRetrieveWps res = jsonAdapter.fromJson(resString);
            tvProcessed.setText(res.toString());

            for (Map.Entry<String, ResMission> entry: res.getResMissionSortedMap().entrySet()) {
                if (entry.getKey().equals("0"))
                    tvProcessedJob.setText(entry.getValue().getWaypointMap().toString());
            }
        } catch (IOException e) {
            tvProcessed.setText("GG");
        }
    }
}