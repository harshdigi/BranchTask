package in.harsh.branchtask.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageThreadModel implements Serializable, Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("thread_id")
    @Expose
    private Integer threadId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    @SerializedName("agent_id")
    @Expose
    private String agentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public static Creator<MessageThreadModel> getCREATOR() {
        return CREATOR;
    }

    protected MessageThreadModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            threadId = null;
        } else {
            threadId = in.readInt();
        }
        userId = in.readString();
        body = in.readString();
        timestamp = in.readString();
        agentId = in.readString();
    }

    public static final Creator<MessageThreadModel> CREATOR = new Creator<MessageThreadModel>() {
        @Override
        public MessageThreadModel createFromParcel(Parcel in) {
            return new MessageThreadModel(in);
        }

        @Override
        public MessageThreadModel[] newArray(int size) {
            return new MessageThreadModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (threadId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(threadId);
        }
        dest.writeString(userId);
        dest.writeString(body);
        dest.writeString(timestamp);
        dest.writeString(agentId);
    }
}
