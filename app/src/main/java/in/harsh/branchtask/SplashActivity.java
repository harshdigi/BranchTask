package in.harsh.branchtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.Map;

import in.harsh.branchtask.Utils.InternetConnection;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME = 3500;
    private SharedPreferences mSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSharedPref = getSharedPreferences("SharedPref", MODE_PRIVATE);
        String userAuthToken = mSharedPref.getString("auth_token","");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    // Its Available...
                    if(userAuthToken.length() != 0 ){
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent1 = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                } else {
                    // Not Available...
                    Intent intent = new Intent(SplashActivity.this,NoInternetActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIME);
    }
}