package com.kukec.kresimir.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kukec.kresimir.shopapp.api.IUser;
import com.kukec.kresimir.shopapp.api.RetrofitInstance;
import com.kukec.kresimir.shopapp.model.User;
import com.kukec.kresimir.shopapp.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kukec.kresimir.shopapp.utils.AppConstants.KTAG;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etLgUname)
    EditText etLgUname;
    @BindView(R.id.etLgPass)
    EditText etLgPass;
    @BindView(R.id.btnLogin)
    ImageButton btnLogin;
    IUser service;
    private static User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void btnLoginM() {
        service = RetrofitInstance.getRetrofitInstance().create(IUser.class);
        if (etLgUname.getText().toString().length() > 0 & etLgPass.getText().toString().length() > 0) {
            String u = etLgUname.getText().toString();
            String p = etLgPass.getText().toString();
            Call<User> call = service.GetUid(u, p);
            Log.wtf(KTAG, call.request().url() + "");
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Log.wtf(KTAG, response.toString());
                        Log.wtf(KTAG, response.body().toString());
                        if (response.body() != null) {
                            User u = response.body();
                            SPUtils.writeToSP(u, getApplicationContext());
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);

                        } else {
                            Log.d(KTAG, "Greska: User not found. 2");
                        }
                    } else {
                        Log.d(KTAG, "Greska: User not found.");
                        Toast.makeText(LoginActivity.this, "Greska: User not found.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d(KTAG, "Greska: " + t.getMessage());
                }
            });
        }
    }


}
