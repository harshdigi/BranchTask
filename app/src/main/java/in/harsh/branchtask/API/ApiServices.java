package in.harsh.branchtask.API;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.harsh.branchtask.Models.LoginModel;
import in.harsh.branchtask.Models.MessageSendModel;
import in.harsh.branchtask.Models.MessageThreadModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {


    @GET("api/messages")
    Call<ArrayList<MessageThreadModel>> getMessages(@HeaderMap Map<String,String> headers);

    @POST("api/messages")
    Call<MessageThreadModel> postMessages(@HeaderMap Map<String,String> headers,
                                          @Body MessageSendModel messageSendModel);


//    @POST("api/login")
//    Call<LoginModel> getAuthLogin(@Body LoginModel loginModel);
//
    @POST("api/login")
    Call<JsonObject> getAuthLogin(@Body LoginModel loginModel);
}
