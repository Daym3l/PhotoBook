package daym3l.photobook.com.photobook.album;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import daym3l.photobook.com.photobook.R;
import daym3l.photobook.com.photobook.utils._ImagesConst;

/**
 * Created by Daymel on 16/9/2018.
 */

public class AndroidImageAdapter extends PagerAdapter {
    Context mContext;
    public int[] sliderImagesId = _ImagesConst.IMAGES_SLIDER;

    AndroidImageAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }


    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((ImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setBackgroundColor(Color.parseColor("#282828"));
        mImageView.setImageResource(sliderImagesId[i]);
        ((ViewPager) container).addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }
}
