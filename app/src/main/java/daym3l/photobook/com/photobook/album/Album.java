package daym3l.photobook.com.photobook.album;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.PopupMenu;
import android.transition.TransitionManager;
import android.util.Log;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import daym3l.photobook.com.photobook.BuildConfig;
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
        final int[] sliderImagesId = _ImagesConst.IMAGES_SLIDER;

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_container);
        fThumbs = (FrameLayout) findViewById(R.id.frame_thumb);

        relativeLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int orientacion = getResources().getConfiguration().orientation;
                if (orientacion == Configuration.ORIENTATION_LANDSCAPE) {
                    fThumbs.setVisibility(View.GONE);
                }
            }
        });

        cubeInRotationTransformation = new CubeInRotationTransformation();
        cubeInScalingTransformation = new CubeInScalingTransformation();
        cubeInDepthTransformation = new CubeInDepthTransformation();
        zoomOutTransformation = new ZoomOutTransformation();
        verticalFlipTransformation = new VerticalFlipTransformation();
        gateTransformation = new GateTransformation();
        fadeOutTransformation = new FadeOutTransformation();


        ImageView hideFrame = (ImageView) findViewById(R.id.iv_openlist);
        hideFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fThumbs.getVisibility() == View.VISIBLE) {
                    fThumbs.setVisibility(View.GONE);
                } else {
                    fThumbs.setVisibility(View.VISIBLE);
                }
            }
        });

        images = new ArrayList<>();
        ImageView share = (ImageView) findViewById(R.id.iv_share);
        ImageView menuOption = (ImageView) findViewById(R.id.iv_options);
        final ImageView rotate = (ImageView) findViewById(R.id.iv_scren);
        fThumbs = (FrameLayout) findViewById(R.id.frame_thumb);
        mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        RelativeLayout contenedor = (RelativeLayout) findViewById(R.id.rl_container);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        contenedor.startAnimation(animation);
        thumbnailsContainer = (LinearLayout) findViewById(R.id.container);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orientacion = getResources().getConfiguration().orientation;
                if (orientacion == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });

        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        mViewPager.setPageTransformer(true, cubeInScalingTransformation);
        setImagesData();
        inflateThumbnails();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BitmapDrawable bd = (BitmapDrawable) Album.this.getResources().getDrawable(sliderImagesId[position]);
                double imageHeight = bd.getBitmap().getHeight();
                double imageWidth = bd.getBitmap().getWidth();
                int orientacion = getResources().getConfiguration().orientation;
                if (orientacion == Configuration.ORIENTATION_LANDSCAPE) {
                    if (imageHeight > imageWidth) {
                        rotate.setVisibility(View.VISIBLE);
                    } else {
                        rotate.setVisibility(View.GONE);
                    }

                } else {
                    if (imageWidth > imageHeight) {
                        rotate.setVisibility(View.VISIBLE);
                    } else {
                        rotate.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


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
        FileOutputStream fos = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT <= 25) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
        } else {
            Uri contentUri = FileProvider.getUriForFile(Album.this, "daym3l.photobook.com.nexus_promo.fileProvider", f);
//            Uri uri = FileProvider.getUriForFile(Album.this, BuildConfig.APPLICATION_ID + ".provider", f);
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        }

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
