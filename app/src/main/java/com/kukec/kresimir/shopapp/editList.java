package com.kukec.kresimir.shopapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kukec.kresimir.shopapp.adapters.ShopItemListAdapter;
import com.kukec.kresimir.shopapp.api.IShopItem;
import com.kukec.kresimir.shopapp.api.IShopList;
import com.kukec.kresimir.shopapp.api.RetrofitInstance;
import com.kukec.kresimir.shopapp.model.ShopItem;

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
import static com.kukec.kresimir.shopapp.utils.AppConstants.KTAG;

public class editList extends AppCompatActivity {

    public List<ShopItem> lisI;
    CustomRecyclerView recyclerView;
    ShopItemListAdapter adapter;
    private IShopItem service;
    public int listID;
    public int position;

    /*
    @BindView(R.id.cbBuyed)
    CheckBox cbBuyed;
    @BindView(R.id.tvItemName)
    EditText tvItemName;
    @BindView(R.id.tvItemQty)
    EditText tvItemQty;
    @BindView(R.id.btnElDelete)
    ImageButton btnElDelete;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        ButterKnife.bind(this);
        lisI= new ArrayList<>();
        listToRecycleView(lisI);
        IShopItem service = RetrofitInstance.getRetrofitInstance().create(IShopItem.class);
        Intent i = getIntent();
        if (i.hasExtra(KEY_EL_INPUT_ID) && i.hasExtra(KEY_EL_INPUT_POS)) {
            listID = i.getIntExtra(KEY_EL_INPUT_ID, 0);
            position = i.getIntExtra(KEY_EL_INPUT_POS, 0);
            Integer iiii = listID;
            Log.d("id", iiii.toString());
        }
        Call<List<ShopItem>> call = service.GetAllShopItems(listID);
        Log.wtf("URL Called", call.request().url() + "");
        call.enqueue(new Callback<List<ShopItem>>() {
            @Override
            public void onResponse(Call<List<ShopItem>> call, Response<List<ShopItem>> response) {
                lisI = response.body();
                listToRecycleView(lisI);
            }

            @Override
            public void onFailure(Call<List<ShopItem>> call, Throwable t) {
                Toast.makeText(editList.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listToRecycleView(final List<ShopItem> listica) {
        recyclerView = findViewById(R.id.rvEditList);
        View emptyView = findViewById(R.id.tvELlEmpty);
        recyclerView.setEmptyView(emptyView);
        adapter = new ShopItemListAdapter(listica, new ShopItemListAdapter.SILAdapterListener() {
            @Override
            public void isBuyed(View v, int position) {
                /*if (!cbBuyed.isChecked()) {
                    tvItemName.setPaintFlags(tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    tvItemName.setPaintFlags(tvItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }*/
            }

            @Override
            public void deleteItem(View v, int position) {
                lisI.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, lisI.size());
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(editList.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showAddItemDialog(Context c) {
        LinearLayout layout = new LinearLayout(c);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText taskEditTextName = new EditText(c);
        taskEditTextName.setHint("Enter item name");
        final EditText taskEditTextQty = new EditText(c);
        taskEditTextQty.setHint("Enter item qty");
        layout.addView(taskEditTextName);
        layout.addView(taskEditTextQty);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Add a new ShopItem")
                .setMessage("Please enter shopItem name?")
                .setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //String task = String.valueOf(taskEditText.getText());
                        String s1 = taskEditTextName.getText().toString();
                        String s2 = taskEditTextQty.getText().toString();
                        if (s1 != "" && s2 != "") {
                            addNewShopList(s1, s2, 0);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void addNewShopList(String ItemName, String ItemQty, int Buyed) {
        lisI.add(new ShopItem(0, ItemName, ItemQty, listID, Buyed));
        adapter.notifyItemInserted(lisI.size() - 1);
        IShopItem service = RetrofitInstance.getRetrofitInstance().create(IShopItem.class);
        Call<ResponseBody> call = service.CreateItem(ItemName, ItemQty, listID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.errorBody()!=null){
                        Log.d(KTAG, response.errorBody().toString());
                    }
                    else {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showAddItemDialog(editList.this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent(); // create return intent which will hold the data
        returnIntent.putExtra(KEY_MA_NUM_OF_ITEMS, lisI.size()); // add data to intent => map => String key / T value
        returnIntent.putExtra(KEY_MA_POS, position); // add data to intent
        setResult(RESULT_OK, returnIntent); // set operation result and add intent holding return data => onActivityResult will be called
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*Intent returnIntent = new Intent(); // create return intent which will hold the data
        returnIntent.putExtra(KEY_MA_NUM_OF_ITEMS, lisI.size()); // add data to intent => map => String key / T value
        returnIntent.putExtra(KEY_MA_POS, position); // add data to intent
        setResult(RESULT_OK, returnIntent); // set operation result and add intent holding return data => onActivityResult will be called
        finish();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*Intent returnIntent = new Intent(); // create return intent which will hold the data
        returnIntent.putExtra(KEY_MA_NUM_OF_ITEMS, lisI.size()); // add data to intent => map => String key / T value
        returnIntent.putExtra(KEY_MA_POS, position); // add data to intent
        setResult(RESULT_OK, returnIntent); // set operation result and add intent holding return data => onActivityResult will be called
        finish();*/
    }
}
