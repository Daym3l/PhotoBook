package daym3l.photobook.com.photobook;

import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;

import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.BounceAnimation;
import com.easyandroidanimations.library.PuffInAnimation;
import com.easyandroidanimations.library.PuffOutAnimation;
import com.easyandroidanimations.library.SlideInAnimation;

import daym3l.photobook.com.photobook.about.AboutUsActivity;
import daym3l.photobook.com.photobook.album.Album;


public class PortadaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView portada = (ImageView) findViewById(R.id.iv_backGround);
        final ImageView logo = (ImageView) findViewById(R.id.frameLayout);
        logo.setVisibility(View.INVISIBLE);
        final FloatingActionButton initAlbum = (FloatingActionButton) findViewById(R.id.fab_gotoalbum);
        portada.setScaleX((float) 1.1);
        portada.setScaleY((float) 1.1);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        final SlideInAnimation slideInAnimation = new SlideInAnimation(logo);

        PuffInAnimation puffInAnimation = new PuffInAnimation(initAlbum).setListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(com.easyandroidanimations.library.Animation animation) {
                logo.setVisibility(View.VISIBLE);
                slideInAnimation.animate();
            }
        });
        puffInAnimation.animate();



        portada.startAnimation(myAnim);


        initAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PuffOutAnimation(initAlbum)
                        .setListener(new AnimationListener() {
                            @Override
                            public void onAnimationEnd(com.easyandroidanimations.library.Animation animation) {
                                gotoAlbum();
                            }
                        })
                        .animate();


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
