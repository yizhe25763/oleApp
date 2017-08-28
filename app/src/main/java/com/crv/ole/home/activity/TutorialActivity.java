package com.crv.ole.home.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.adapter.TutorialImageAdapter;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.wedgets.ImagePointer;


/**
 * 引导页
 */
public class TutorialActivity extends BaseActivity
{
    private ViewPager mViewPager;
    private ImagePointer mImagePointer;
    private ImageView tvGo;
    protected LayoutInflater mInflater;
    private final static int pointers=4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        mInflater = getLayoutInflater();
        initView();
    }

    protected void initView()
    {
        tvGo = (ImageView) findViewById(R.id.tvGo);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new TutorialImageAdapter(mInflater, createImages()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
               // mImagePointer.checkPointer(position % pointers);

            }

            @Override
            public void onPageSelected(int position)
            {
              //  mImagePointer.checkPointer(position % pointers);
                if(position==pointers-1){
                    tvGo.setVisibility(View.VISIBLE);
                   // mImagePointer.setVisibility(View.GONE);
                }
                else{
                    tvGo.setVisibility(View.GONE);
                   // mImagePointer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });
       // mImagePointer = (ImagePointer) findViewById(R.id.gallery_inditor);
       // mImagePointer.setPointers(pointers);
    }
    public void onClick(View view)
    {
        start();
    }
    protected int[] createImages()
    {
        return new int[]{
                R.drawable.demo_what_new_01, R.drawable.demo_what_new_02,
                R.drawable.demo_what_new_03, R.drawable.demo_what_new_04
        };
    }

    protected void start()
    {
        mPreferencesHelper.put(OleConstants.KEY_IS_SHOW_TUTORIAL, true);
        // 修复第一次启动的时候，点击消息推送，重新启动activity两次的问题
        startActivityWithAnim(MainActivity.class);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
