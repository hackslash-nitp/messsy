package in.hackslash.messsy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import in.hackslash.messsy.home.HomeActivity;
import in.hackslash.messsy.onboarding.LoginActivity;

public class Splash_screen extends AppCompatActivity {

    SharedPreferences onBoardingScreen;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iv=findViewById(R.id.logo);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.transition);
        iv.startAnimation(anim);


        Thread timer = new Thread(){
            public void run() {
                try{
                    sleep(3000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                    boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);

                    if(isFirstTime){

                        SharedPreferences.Editor editor = onBoardingScreen.edit();
                        editor.putBoolean("firstTime", false);
                        editor.commit();

                        Intent i=new Intent(getApplicationContext(), OnBoardingActivity.class);
                        startActivity(i);
                        finish();

                    }

                    else{
                        Intent i=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
        };
        timer.start();
    }
}