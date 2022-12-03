package in.harsh.branchtask.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.HarmonizedColorsOptions;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import in.harsh.branchtask.Models.MessageThreadModel;
import in.harsh.branchtask.R;

public class MessageItemAdapter extends RecyclerView.Adapter<MessageItemAdapter.MessageItemViewHolder> {
    private Context context;
    private ArrayList<MessageThreadModel> messageThreadModels;

    public MessageItemAdapter(Context context, ArrayList<MessageThreadModel> messageThreadModels) {
        this.context = context;
        this.messageThreadModels = messageThreadModels;
    }

    @NonNull
    @Override
    public MessageItemAdapter.MessageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        v = layoutInflater.inflate(R.layout.message_item_card, parent, false);
        return new MessageItemViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MessageItemAdapter.MessageItemViewHolder holder, int position) {
            if(messageThreadModels.get(position).getAgentId() == null){
                holder.agentMessageCard.setVisibility(View.GONE);
                holder.agentIdCard.setVisibility(View.GONE);
                holder.userMessage.setText(messageThreadModels.get(position).getBody());
                OffsetDateTime odt = OffsetDateTime.parse(messageThreadModels.get(position).getTimestamp());
                Instant instant = odt.toInstant();
                ZonedDateTime zdt = instant.atZone(ZoneId.of("Asia/Kolkata"));
                holder.userMessageDate.setText("Date: "+ zdt.getDayOfMonth() + "-"+ zdt.getMonth()+"-" +zdt.getYear()+ " Time: " + zdt.getHour() + ":" + zdt.getMinute());
                holder.userId.setText(String.valueOf(messageThreadModels.get(position).getUserId()));
            }
            else{

                holder.userMessageCard.setVisibility(View.GONE);
                holder.userIdCard.setVisibility(View.GONE);
                holder.agentMessage.setText(messageThreadModels.get(position).getBody());
                OffsetDateTime odt = OffsetDateTime.parse(messageThreadModels.get(position).getTimestamp());
                Instant instant = odt.toInstant();
                ZonedDateTime zdt = instant.atZone(ZoneId.of("Asia/Kolkata"));
                holder.agentMessageDate.setText("Date: "+ zdt.getDayOfMonth() + "-"+ zdt.getMonth()+"-" +zdt.getYear()+ " Time: " + zdt.getHour() + ":" + zdt.getMinute());
                holder.agentId.setText(String.valueOf(messageThreadModels.get(position).getAgentId()));

            }
    }

    @Override
    public int getItemCount() {
        return messageThreadModels.size();
    }

    public static class MessageItemViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView userIdCard, userMessageCard, agentIdCard, agentMessageCard;
        TextView userId,userMessage, agentId, agentMessage, userMessageDate, agentMessageDate;
        public MessageItemViewHolder(@NonNull View itemView) {
            super(itemView);
            userIdCard = itemView.findViewById(R.id.id_user);
            agentIdCard = itemView.findViewById(R.id.id_agent);
            userMessageCard = itemView.findViewById(R.id.user_message_card);
            agentMessageCard = itemView.findViewById(R.id.agent_message_card);
            userId = itemView.findViewById(R.id.user_id_txt);
            agentId =itemView.findViewById(R.id.agent_id_txt);
            userMessage = itemView.findViewById(R.id.user_message);
            agentMessage = itemView.findViewById(R.id.agent_message);
            userMessageDate = itemView.findViewById(R.id.user_message_date);
            agentMessageDate = itemView.findViewById(R.id.agent_message_date);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterlist(ArrayList<MessageThreadModel> filterList){
        messageThreadModels = filterList;
        notifyDataSetChanged();
    }
}
