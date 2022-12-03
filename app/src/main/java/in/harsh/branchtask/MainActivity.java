package in.harsh.branchtask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import in.harsh.branchtask.API.ApiClient;
import in.harsh.branchtask.API.ApiServices;
import in.harsh.branchtask.Adapter.MessagesAdapter;
import in.harsh.branchtask.Models.MessageThreadModel;
import in.harsh.branchtask.Utils.MessageSortingComparator;
import in.harsh.branchtask.Utils.ThreadSortingComparator;
import in.harsh.branchtask.databinding.ActivityLoginBinding;
import in.harsh.branchtask.databinding.ActivityMainBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mSharedPref;
    private RecyclerView recyclerView;
    ActivityMainBinding mainBinding;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding= DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
        recyclerView = mainBinding.recyclerView;
        mSharedPref = getSharedPreferences("SharedPref", MODE_PRIVATE);
        shimmerFrameLayout = mainBinding.shimmerLayout;
        shimmerFrameLayout.startShimmer();
        mainBinding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSharedPref.edit();
                editor.putString("auth_token","");
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getAllMessageThread();

    }
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
        getAllMessageThread();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void printAllResponses(ArrayList<MessageThreadModel> allResponses) {

        HashMap<Integer, ArrayList<MessageThreadModel>> hashMap = new HashMap<>();
        ArrayList<Integer> threadByIndex = new ArrayList<>();
        for( MessageThreadModel item : allResponses){
            if(hashMap.containsKey(item.getThreadId())){
                hashMap.get(item.getThreadId()).add(item);
            }
            else{
                ArrayList<MessageThreadModel> temp = new ArrayList<>();
                temp.add(item);
                hashMap.put(item.getThreadId(),temp);
                threadByIndex.add(item.getThreadId());
            }


        }
        for (int i =0; i<threadByIndex.size();i++){
            Collections.sort(Objects.requireNonNull(hashMap.get(threadByIndex.get(i))), (new MessageSortingComparator()).reversed());
        }
        Collections.sort(threadByIndex,(new ThreadSortingComparator(hashMap)).reversed());
        putDataIntoRecyclerView(hashMap, threadByIndex);
    }
    private void putDataIntoRecyclerView(HashMap<Integer, ArrayList<MessageThreadModel>> messagesThreads,ArrayList<Integer> threadByIndex) {
        MessagesAdapter messagesAdapter = new MessagesAdapter(MainActivity.this,messagesThreads, threadByIndex);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        };
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(messagesAdapter);


    }
    private void getAllMessageThread(){
        String userAuthToken = mSharedPref.getString("auth_token","");
        Map<String,String> headers= new HashMap<>();
        headers.put("X-Branch-Auth-Token",userAuthToken);
        Retrofit retrofit = ApiClient.getRetrofit();
        ApiServices apiService = retrofit.create(ApiServices.class);
        Call<ArrayList<MessageThreadModel>> getMessages = apiService.getMessages(headers);
        getMessages.enqueue(new Callback<ArrayList<MessageThreadModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ArrayList<MessageThreadModel>> call, Response<ArrayList<MessageThreadModel>> response) {
                ArrayList<MessageThreadModel> allMessagesThread = response.body();
                printAllResponses(allMessagesThread);
            }

            @Override
            public void onFailure(Call<ArrayList<MessageThreadModel>> call, Throwable t) {
                Log.e("Message Failure", t.getMessage());
            }
        });
    }

}