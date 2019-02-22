package com.kukec.kresimir.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.kukec.kresimir.shopapp.api.IShopItem;
import com.kukec.kresimir.shopapp.api.IUser;
import com.kukec.kresimir.shopapp.api.RetrofitInstance;
import com.kukec.kresimir.shopapp.model.ShopItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kukec.kresimir.shopapp.utils.AppConstants.KEY_EL_INPUT_ID;
import static com.kukec.kresimir.shopapp.utils.AppConstants.KEY_SA_INPUT_ID;
import static com.kukec.kresimir.shopapp.utils.AppConstants.KTAG;

public class ShareActivity extends AppCompatActivity {
    List<String> l;
    public List<ShopItem> lisI;
    public int listID;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.shareMode)
    RadioGroup shareMode;
    @BindView(R.id.rbtnShareByEmail)
    RadioButton rbtnShareByEmail;
    @BindView(R.id.rbtnShareByUserName)
    RadioButton rbtnShareByUserName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.btnShare)
    Button btnShare;


    private static final String TYPE = "plain/text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        IUser service = RetrofitInstance.getRetrofitInstance().create(IUser.class);
        Intent i = getIntent();
        if (i.hasExtra(KEY_SA_INPUT_ID)) {
            listID = i.getIntExtra(KEY_SA_INPUT_ID, 0);
        }
        Call<List<String>> call = service.GetAllUsers();
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                l = response.body();
                listToSpinner(l);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
        shareMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbtnShareByEmail.getId()) {
                    spinner.setEnabled(false);
                    etEmail.setEnabled(true);
                } else if (checkedId == rbtnShareByUserName.getId()) {
                    etEmail.setEnabled(false);
                    spinner.setEnabled(true);
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbtnShareByEmail.isChecked()) {
                    if (inputOk()) {
                        lisI = new ArrayList<>();
                        IShopItem service = RetrofitInstance.getRetrofitInstance().create(IShopItem.class);
                        Call<List<ShopItem>> call = service.GetAllShopItems(listID);
                        Log.wtf("URL Called", call.request().url() + "");
                        call.enqueue(new Callback<List<ShopItem>>() {
                            @Override
                            public void onResponse(Call<List<ShopItem>> call, Response<List<ShopItem>> response) {
                                lisI = response.body();


                                sendEmail(etEmail.getText().toString(), "Yout got ShopApp share request", lisI);
                            }

                            @Override
                            public void onFailure(Call<List<ShopItem>> call, Throwable t) {
                                Toast.makeText(ShareActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } else if (rbtnShareByUserName.isChecked()) {
                    IUser service = RetrofitInstance.getRetrofitInstance().create(IUser.class);
                    String ss = spinner.getSelectedItem().toString();
                    Log.d(KTAG, ss);
                    Call<ResponseBody> call = service.ShareListByUsername(listID, ss);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Log.d(KTAG, response.toString());
                                finish();
                            } else {
                                Log.d(KTAG, "Cant insert to database. Response: " + response.toString());
                            }
                            Log.d(KTAG, "Zapisano" + response.toString());
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e(KTAG, "Nije zapisano" + t.toString());
                        }
                    });
                }
            }
        });
    }

    public void listToSpinner(List<String> lista) {
        if (lista != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
            spinner.setAdapter(adapter);
        }
    }

    private void sendEmail(String address, String subject, List<ShopItem> listica) {
        String s = "ShopApp user send you to buy this:\r\n";
        for(int i =0; i<listica.size();i++){
            s=s+listica.get(i).getItemName()+" ; "+listica.get(i).getItemQty()+" ; "+"\r\n";
        }
        Log.d(KTAG,s);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType(TYPE);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, s);
        startActivity(Intent.createChooser(emailIntent, "Please select application"));
    }

    private boolean inputOk() {
        if (etEmail.getText().length() == 0) {
            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
