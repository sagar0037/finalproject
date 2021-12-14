package com.example.resultchecker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.resultchecker.R;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.utils.Constant;
import com.khalti.widget.KhaltiButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    KhaltiButton btnPayment;
    private Config config;
    private Toolbar tbPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        tbPayment = findViewById(R.id.tbPayment);
        setSupportActionBar(tbPayment);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnPayment = findViewById(R.id.btnPayment);
        createBuildConfigKhalti();
        KhaltiCheckOut khaltiCheckOut = new KhaltiCheckOut(this, config);

        btnPayment.setOnClickListener(v->{
            khaltiCheckOut.show();
        });

    }
    private void createBuildConfigKhalti() {
        Map<String, Object> map = new HashMap<>();
        map.put("merchant_extra", "This is extra data");
        config = new Config.Builder(Constant.pub, "Product ID", "Khalti", 100L, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i("gtx", "action " + action + " error " + errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());

            }
        }).paymentPreferences(new ArrayList<PaymentPreference>() {{
            add(PaymentPreference.KHALTI);
        }}).additionalData(map).build();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}