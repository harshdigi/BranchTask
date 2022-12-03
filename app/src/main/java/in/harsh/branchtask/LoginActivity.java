package in.harsh.branchtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.regex.Pattern;

import in.harsh.branchtask.API.ApiClient;
import in.harsh.branchtask.API.ApiServices;
import in.harsh.branchtask.Models.LoginModel;
import in.harsh.branchtask.databinding.ActivityLoginBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LoginActivity.this,R.layout.activity_login);


        Retrofit retrofit = ApiClient.getRetrofit();
        ApiServices apiService = retrofit.create(ApiServices.class);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validatePassword()) {
                    Toast.makeText(LoginActivity.this, "Enter valid details", Toast.LENGTH_SHORT).show();
                }
                else {
                    String email = binding.emailInput.getText().toString();
                    String password = binding.passwordInput.getText().toString();
                    LoginModel loginModel = new LoginModel(email, password);
                    Call<JsonObject> authToken = apiService.getAuthLogin(loginModel);
                    authToken.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            try {

                                if (response.code() == 200) {
                                    mSharedPref = getSharedPreferences("SharedPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = mSharedPref.edit();
                                    editor.putString("auth_token", response.body().get("auth_token").getAsString());
                                    editor.apply();
                                    Log.e("Auth Token is: ", response.body().toString());
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (response.code() == 401) {
                                    Toast.makeText(LoginActivity.this, "Username or password is invalid.", Toast.LENGTH_SHORT).show();
                                } else if (response.code() == 400) {
                                    Toast.makeText(LoginActivity.this, " Error in getting user details", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("Error in AuthToken", e.getMessage());
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.e("Auth Token Error:", t.getMessage());
                        }
                    });
                }
            }
        });

    }
    private Boolean validateEmail() {
        String val = binding.emailInput.getText().toString();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (val.isEmpty()) {
            binding.emailInput.setError("Field cannot be empty");
            return false;
        } else if (!pattern.matcher(val).matches()) {
            binding.emailInput.setError("Enter Valid Email");
            return false;
        } else {
            binding.emailInput.setError(null);
            return true;
        }
    }


    private Boolean validatePassword() {
        String val = binding.passwordInput.getText().toString();
        if (val.isEmpty()) {
            binding.passwordInput.setError("Field cannot be empty");
            return false;
        } else {
            binding.passwordInput.setError(null);
            return true;
        }
    }
}