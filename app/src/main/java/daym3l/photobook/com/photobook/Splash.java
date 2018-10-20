package daym3l.photobook.com.photobook;


import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import daym3l.photobook.com.photobook.animations.MyBounceInterpolator;
import daym3l.photobook.com.photobook.utils._Permision;


public class Splash extends AppCompatActivity {
    SharedPreferences preferences;
    int time_delay = 5000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_layout);
        final Animation myAnimBtn = AnimationUtils.loadAnimation(this, R.anim.button);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 50);
        ImageView imageView = (ImageView) findViewById(R.id.iv_logo);
        myAnimBtn.setInterpolator(interpolator);
        imageView.startAnimation(myAnimBtn);
        preferences = getSharedPreferences("photobook.data", MODE_PRIVATE);
        if (_Permision.checkPermiso(this)) {
            time_delay = 5000;
        }
        initActivity();
    }

    /**
     * Cambiar de actividad
     */
    private void initActivity() {

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(time_delay);
                    Intent intent = new Intent(getApplicationContext(), PortadaActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }


}
