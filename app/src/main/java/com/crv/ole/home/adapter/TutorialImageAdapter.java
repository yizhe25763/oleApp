package com.crv.ole.home.adapter;


import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.crv.ole.R;

/**
 * @FileName: TutorialImageAdapter.java
 * @Package com.crc.sdk.adapter
 * @Description: 引导页Adapter
 * Copyright:com.crc.hrt All Rights Reserved
 */
public class TutorialImageAdapter extends PagerAdapter
{
    private LayoutInflater mInflater;
    private int[] mImages;
    private View[] mViews;


    public TutorialImageAdapter(LayoutInflater inflater, int[] images)
    {
        mInflater = inflater;
        mImages = images;
        mViews = new ImageView[images.length];
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2)
    {
        ((ViewPager) arg0).removeView(mViews[arg1]);
        mViews[arg1] = null;
    }

    @Override
    public void finishUpdate(View arg0)
    {
    }

    @Override
    public int getCount()
    {
        return mImages == null ? 0 : mImages.length;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1)
    {
        View view = mViews[arg1];
        if (view == null)
        {
            view = mInflater.inflate(R.layout.tutorial_item, null);
            ((ImageView) view.findViewById(R.id.iv_pic)).setImageResource(mImages[arg1]);
            if (arg1 == mImages.length - 1)
            {
                view.findViewById(R.id.btn_to_go).setVisibility(View.VISIBLE);
            }
            else
            {
                view.findViewById(R.id.btn_to_go).setVisibility(View.GONE);
            }
        }
        ((ViewPager) arg0).addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1)
    {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1)
    {
    }

    @Override
    public Parcelable saveState()
    {
        return null;
    }

    @Override
    public void startUpdate(View arg0)
    {
    }
}
