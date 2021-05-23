package in.hackslash.messsy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import in.hackslash.messsy.home.HomeActivity;

public class Splash_screen extends AppCompatActivity {


    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iv=findViewById(R.id.logo);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.transition);
        iv.startAnimation(anim);

        final Intent i = new Intent(this, HomeActivity.class);

        Thread timer = new Thread(){
            public void run() {
                try{
                    sleep(3000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}