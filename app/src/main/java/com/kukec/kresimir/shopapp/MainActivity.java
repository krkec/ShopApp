package com.kukec.kresimir.shopapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kukec.kresimir.shopapp.adapters.ListOfShopListAdapter3;
import com.kukec.kresimir.shopapp.api.IShopList;
import com.kukec.kresimir.shopapp.api.RetrofitInstance;
import com.kukec.kresimir.shopapp.model.ShopItem;
import com.kukec.kresimir.shopapp.model.ShopList;
import com.kukec.kresimir.shopapp.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kukec.kresimir.shopapp.utils.AppConstants.KEY_EL_INPUT_ID;
import static com.kukec.kresimir.shopapp.utils.AppConstants.KEY_EL_INPUT_POS;
import static com.kukec.kresimir.shopapp.utils.AppConstants.KEY_MA_NUM_OF_ITEMS;
import static com.kukec.kresimir.shopapp.utils.AppConstants.KEY_MA_POS;
import static com.kukec.kresimir.shopapp.utils.AppConstants.KEY_SA_INPUT_ID;
import static com.kukec.kresimir.shopapp.utils.AppConstants.KTAG;

public class MainActivity extends AppCompatActivity {

    private IShopList service;
    public List<ShopList> lis;
    CustomRecyclerView recyclerView;
    ListOfShopListAdapter3 adapter;
    private final int reqCodeSecondAct = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        service = RetrofitInstance.getRetrofitInstance().create(IShopList.class);
        int id = SPUtils.getUserId(getApplicationContext());
        if (id > 0) {
            Call<List<ShopList>> call = service.GetAllShopLst(id);
            Log.wtf(KTAG, call.request().url() + "");
            lis = new ArrayList<>();
            shopListToRV(lis);
            call.enqueue(new Callback<List<ShopList>>() {
                @Override
                public void onResponse(Call<List<ShopList>> call, Response<List<ShopList>> response) {
                    if (response.body() != null) {
                        lis = response.body();
                        shopListToRV(lis);
                    }
                }

                @Override
                public void onFailure(Call<List<ShopList>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "ID is not valid!", Toast.LENGTH_SHORT).show();
            Log.d(KTAG, "ID is not valid!");
        }

    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Add a new ShopList")
                .setMessage("Please enter shopList name?")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //String task = String.valueOf(taskEditText.getText());
                        String s = taskEditText.getText().toString();
                        if (s != "") {
                            addNewShopList(s);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showAddItemDialog(MainActivity.this);
        return super.onOptionsItemSelected(item);
    }

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addList) {
            View btnaddList = findViewById(R.id.addList);
            btnaddList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddItemDialog(MainActivity.this);
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

    private void shopListToRV(final List<ShopList> listica) {
        recyclerView = findViewById(R.id.rvLosl);
        View emptyView = findViewById(R.id.tvLoslEmpty);
        recyclerView.setEmptyView(emptyView);
        adapter = null;
        adapter = new ListOfShopListAdapter3(listica, new ListOfShopListAdapter3.LSLAdapterListener() {
            @Override
            public void editList(View v, int position) {

                Intent i = new Intent(MainActivity.this, editList.class);
                Integer id = listica.get(position).getId();
                Log.d("id", id.toString());
                Toast.makeText(MainActivity.this, "ajdije " + id, Toast.LENGTH_SHORT).show();
                i.putExtra(KEY_EL_INPUT_ID, id);
                i.putExtra(KEY_EL_INPUT_POS, position);
                startActivityForResult(i, reqCodeSecondAct);
            }

            @Override
            public void shareList(View v, int position) {
                Intent i = new Intent(MainActivity.this, ShareActivity.class);
                Integer id = listica.get(position).getId();
                Log.d("id", id.toString());
                Toast.makeText(MainActivity.this, "ajdije " + id, Toast.LENGTH_SHORT).show();
                i.putExtra(KEY_SA_INPUT_ID, id);
                startActivity(i);
            }

            @Override
            public void removeList(View v, int position) {
                DeleteShopList(listica.get(position).getId());
                lis.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, lis.size());
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // requestCode => we can have multiple activities/returns, int id used to identify returning activity
        // resultCode => was work completed RESULT_OK or user left activity without completing it RESULT_CANCELED
        // Intent data => intent object holding returned values as map => String key / T value

        if (requestCode == reqCodeSecondAct && resultCode == RESULT_OK) { // && data.hasExtra(...
            int numOfItems = data.getIntExtra(KEY_MA_NUM_OF_ITEMS, 0);
            int pos = data.getIntExtra(KEY_MA_POS, 0);
            lis.get(pos).setNumberOfItems(numOfItems);
            List<ShopList> l2 = new ArrayList<>();
            l2.addAll(lis);
            adapter.swap(l2);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Operation canceled!", Toast.LENGTH_SHORT).show();
        }

    }

    public void addNewShopList(String listName) {
        List<ShopItem> li = new ArrayList<>();
        ShopList l = new ShopList(li, 0, listName, "", 1, 0);
        lis.add(l);
        adapter.notifyItemInserted(lis.size() - 1);
        int id = SPUtils.getUserId(getApplicationContext());
        if (id > 0) {
            Call<Integer> call = service.CreateList2(l.getListName(), l.getStore(), id);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        if (response.errorBody() != null) {
                            Log.d(KTAG, response.errorBody().toString());
                        } else {
                            int kid = response.body();
                            lis.get(lis.size() - 1).setId(kid);
                            Log.d(KTAG, response.toString());
                        }

                    } else {
                        Log.d(KTAG, "Cant insert to database. Response: " + response.toString());
                    }
                    Log.d(KTAG, "Zapisano" + response.toString());
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.e(KTAG, "Nije zapisano" + t.toString());
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "ID is not valid!", Toast.LENGTH_SHORT).show();
            Log.d(KTAG, "ID is not valid!");
        }

    }

    public void DeleteShopList(int id) {
        Call<ResponseBody> call = service.DeleteList(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        Log.d(KTAG, response.errorBody().toString());
                    } else {
                        Log.d(KTAG, response.toString());

                    }
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
