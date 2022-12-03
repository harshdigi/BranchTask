package in.harsh.branchtask.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import in.harsh.branchtask.MessagesActivity;
import in.harsh.branchtask.Models.MessageThreadModel;
import in.harsh.branchtask.R;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    private Context context;
    private HashMap<Integer, ArrayList<MessageThreadModel>> messagesThreads;


    public MessagesAdapter(Context context, HashMap<Integer, ArrayList<MessageThreadModel>> messagesThreads, ArrayList<Integer> threadIdByIndex) {
        this.context = context;
        this.messagesThreads = messagesThreads;
        this.threadIdByIndex = threadIdByIndex;
    }

    private ArrayList<Integer> threadIdByIndex;

    @NonNull
    @Override
    public MessagesAdapter.MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.message_thread_card, parent, false);
        return new MessagesViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.MessagesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MessageThreadModel messageThreadModel = messagesThreads.get(threadIdByIndex.get(position)).get(0);
        String role_txt = (messageThreadModel.getAgentId() == null)?"User" : "Agent";
        holder.role.setText(role_txt);
        holder.userId.setText(messageThreadModel.getUserId());
        holder.message.setText(messageThreadModel.getBody());
        OffsetDateTime odt = OffsetDateTime.parse(messageThreadModel.getTimestamp());
        Instant instant = odt.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.of("Asia/Kolkata"));
        holder.timestamp.setText("Date: "+ zdt.getDayOfMonth() + "-"+ zdt.getMonth()+"-" +zdt.getYear()+"\n" + "Time: " + zdt.getHour() + ":" + zdt.getMinute());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessagesActivity.class);
                ArrayList<MessageThreadModel> arrayList =  messagesThreads.get(threadIdByIndex.get(position));
                intent.putParcelableArrayListExtra("messages_list", arrayList);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return threadIdByIndex.size();
    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder {
        TextView userId, message, timestamp,role;
        ConstraintLayout parent;
        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.user_id_txt);
            message = itemView.findViewById(R.id.message_txt);
            timestamp = itemView.findViewById(R.id.timestamp_txt);
            parent = itemView.findViewById(R.id.message_card);
            role = itemView.findViewById(R.id.role_txt);

        }
    }
}
