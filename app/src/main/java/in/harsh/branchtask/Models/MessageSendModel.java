package in.harsh.branchtask.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageSendModel implements Serializable, Parcelable {
    @SerializedName("thread_id")
    @Expose
    Integer thread_id;
    @SerializedName("body")
    @Expose
    String body;

    public MessageSendModel(Integer thread_id, String body) {
        this.thread_id = thread_id;
        this.body = body;
    }

    public Integer getThread_id() {
        return thread_id;
    }

    public void setThread_id(Integer thread_id) {
        this.thread_id = thread_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static Creator<MessageSendModel> getCREATOR() {
        return CREATOR;
    }

    protected MessageSendModel(Parcel in) {
        if (in.readByte() == 0) {
            thread_id = null;
        } else {
            thread_id = in.readInt();
        }
        body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (thread_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(thread_id);
        }
        dest.writeString(body);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageSendModel> CREATOR = new Creator<MessageSendModel>() {
        @Override
        public MessageSendModel createFromParcel(Parcel in) {
            return new MessageSendModel(in);
        }

        @Override
        public MessageSendModel[] newArray(int size) {
            return new MessageSendModel[size];
        }
    };
}
