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
import uk.co.senab.photoview.PhotoView;

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
        return v == ((PhotoView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        PhotoView photoView = new PhotoView(mContext);
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photoView.setBackgroundColor(Color.parseColor("#282828"));
        photoView.setImageResource(sliderImagesId[i]);
        ((ViewPager) container).addView(photoView, 0);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }
}
