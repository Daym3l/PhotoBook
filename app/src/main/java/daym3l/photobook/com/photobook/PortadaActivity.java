package daym3l.photobook.com.photobook;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;

import daym3l.photobook.com.photobook.about.AboutUsActivity;
import daym3l.photobook.com.photobook.album.Album;



public class PortadaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView portada = (ImageView) findViewById(R.id.iv_backGround);
        ImageView logo = (ImageView) findViewById(R.id.frameLayout);
        portada.setScaleX((float) 1.1);
        portada.setScaleY((float) 1.1);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);


        portada.startAnimation(myAnim);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAlbum();
            }
        });

        portada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAlbum();

            }
        });

    }

    private void gotoAlbum() {
        Intent intent = new Intent(PortadaActivity.this, Album.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_out, R.anim.fade);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PortadaActivity.this, AboutUsActivity.class);
        finish();
        startActivity(intent);
    }


}
