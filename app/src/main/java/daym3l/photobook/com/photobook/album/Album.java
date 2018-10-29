package daym3l.photobook.com.photobook.album;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.PopupMenu;
import android.transition.TransitionManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import daym3l.photobook.com.photobook.R;

import daym3l.photobook.com.photobook.about.AboutUsActivity;
import daym3l.photobook.com.photobook.transformations.CubeInDepthTransformation;
import daym3l.photobook.com.photobook.transformations.CubeInRotationTransformation;
import daym3l.photobook.com.photobook.transformations.CubeInScalingTransformation;
import daym3l.photobook.com.photobook.transformations.FadeOutTransformation;
import daym3l.photobook.com.photobook.transformations.GateTransformation;
import daym3l.photobook.com.photobook.transformations.VerticalFlipTransformation;
import daym3l.photobook.com.photobook.transformations.ZoomOutTransformation;
import daym3l.photobook.com.photobook.utils._ImagesConst;


public class Album extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ArrayList<Integer> images;
    private BitmapFactory.Options options;
    private LinearLayout thumbnailsContainer;
    private ViewPager mViewPager;
    private FrameLayout fThumbs;
    private ImageView share, menuOption;
    CubeInRotationTransformation cubeInRotationTransformation;
    CubeInScalingTransformation cubeInScalingTransformation;
    CubeInDepthTransformation cubeInDepthTransformation;
    ZoomOutTransformation zoomOutTransformation;
    VerticalFlipTransformation verticalFlipTransformation;
    GateTransformation gateTransformation;
    FadeOutTransformation fadeOutTransformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        cubeInRotationTransformation = new CubeInRotationTransformation();
        cubeInScalingTransformation = new CubeInScalingTransformation();
        cubeInDepthTransformation = new CubeInDepthTransformation();
        zoomOutTransformation = new ZoomOutTransformation();
        verticalFlipTransformation = new VerticalFlipTransformation();
        gateTransformation = new GateTransformation();
        fadeOutTransformation = new FadeOutTransformation();

        images = new ArrayList<>();
        share = (ImageView) findViewById(R.id.iv_share);
        menuOption = (ImageView) findViewById(R.id.iv_options);
        fThumbs = (FrameLayout) findViewById(R.id.frame_thumb);
        mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        RelativeLayout contenedor = (RelativeLayout) findViewById(R.id.rl_container);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        contenedor.startAnimation(animation);
        thumbnailsContainer = (LinearLayout) findViewById(R.id.container);

        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        mViewPager.setPageTransformer(true, cubeInScalingTransformation);
        setImagesData();
        inflateThumbnails();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });
        menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Album.this, v);
                popupMenu.setOnMenuItemClickListener(Album.this);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.show();
            }
        });


    }

    private void shareImage() {
        Bitmap image = BitmapFactory.decodeResource(getResources(), images.get(mViewPager.getCurrentItem()), options);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
        startActivity(Intent.createChooser(shareIntent, "Compartir con:"));
    }


    private void setImagesData() {
        int[] resourceIDs = _ImagesConst.IMAGES_SLIDER;
        for (int i = 0; i < resourceIDs.length; i++) {
            images.add(resourceIDs[i]);
        }
    }

    private void inflateThumbnails() {
        for (int i = 0; i < images.size(); i++) {
            View imageLayout = getLayoutInflater().inflate(R.layout.item_image, null);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.img_thumb);

            imageView.setOnClickListener(onChagePageClickListener(i));
            options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            options.inDither = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), images.get(i), options);
            imageView.setImageBitmap(bitmap);
            //set to image view
            imageView.setImageBitmap(bitmap);
            //add imageview

            thumbnailsContainer.addView(imageLayout);
        }
    }

    private View.OnClickListener onChagePageClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(i);

            }
        };
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Album.this, AboutUsActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.acerca:
                Intent intent = new Intent(Album.this, AboutUsActivity.class);
                startActivity(intent);
                return true;

            default:
                return false;
        }

    }


}
