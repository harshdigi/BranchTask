package in.harsh.branchtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import in.harsh.branchtask.API.ApiClient;
import in.harsh.branchtask.API.ApiServices;
import in.harsh.branchtask.Adapter.MessageItemAdapter;
import in.harsh.branchtask.Adapter.MessagesAdapter;
import in.harsh.branchtask.Models.MessageSendModel;
import in.harsh.branchtask.Models.MessageThreadModel;
import in.harsh.branchtask.databinding.ActivityMessagesBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MessagesActivity extends AppCompatActivity {
    ActivityMessagesBinding binding;
    private SharedPreferences mSharedPref;
    private RecyclerView recyclerView;
    MessageItemAdapter messageItemAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MessagesActivity.this,R.layout.activity_messages);
        ArrayList<MessageThreadModel> messageThreadModels = getIntent().getParcelableArrayListExtra("messages_list");
        Retrofit retrofit = ApiClient.getRetrofit();
        ApiServices apiService = retrofit.create(ApiServices.class);
        shimmerFrameLayout = binding.shimmerLayout;
        shimmerFrameLayout.startShimmer();
        mSharedPref = getSharedPreferences("SharedPref", MODE_PRIVATE);
        String userAuthToken = mSharedPref.getString("auth_token","");
        Map<String,String> headers= new HashMap<>();
        headers.put("X-Branch-Auth-Token",userAuthToken);
        Collections.reverse(messageThreadModels);
        putDataIntoRecyclerView(messageThreadModels);

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer thread_id = messageThreadModels.get(0).getThreadId();
                String body = binding.sendMessage.getText().toString();
                MessageSendModel  messageSendModel = new MessageSendModel(thread_id,body);
                if (!validateMesssage()) {
                    Toast.makeText(MessagesActivity.this, "Enter valid message", Toast.LENGTH_SHORT).show();
                }
                else{
                    Call<MessageThreadModel> call = apiService.postMessages(headers,messageSendModel);

                call.enqueue(new Callback<MessageThreadModel>() {
                    @Override
                    public void onResponse(Call<MessageThreadModel> call, Response<MessageThreadModel> response) {
                        messageThreadModels.add(response.body());
                        messageItemAdapter.filterlist(messageThreadModels);
                        binding.sendMessage.setText("");
                    }

                    @Override
                    public void onFailure(Call<MessageThreadModel> call, Throwable t) {

                    }
                });
                }
            }
        });

    }
    private Boolean validateMesssage() {
        String val = binding.sendMessage.getText().toString();
        if (val.isEmpty()) {

            return false;
        } else {
            return true;
        }
    }
    private void putDataIntoRecyclerView(ArrayList<MessageThreadModel> messagesThreads) {
        messageItemAdapter = new MessageItemAdapter(MessagesActivity.this,messagesThreads);
        LinearLayoutManager manager = new LinearLayoutManager(MessagesActivity.this) {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        };
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(messageItemAdapter);


    }
}