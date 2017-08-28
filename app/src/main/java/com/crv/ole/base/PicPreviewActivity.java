package com.crv.ole.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.crv.ole.R;
import com.crv.ole.databinding.ActivityPicPreviewBinding;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.adapter.ImagePageAdapter;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.SystemBarTintManager;

import java.util.ArrayList;

/**
 * 图片预览(支持本地和网络图片)
 * create by lihongshi 2017/08/21
 */
public class PicPreviewActivity extends BaseAppCompatActivity {
    private ArrayList<ImageItem> mImageItems;      //跳转进ImagePreviewFragment的图片文件夹
    private int mCurrentPosition = 0;              //跳转进ImagePreviewFragment时的序号，第几个图片
    private ActivityPicPreviewBinding mDataBind;
    private ImagePageAdapter mAdapter;
    private boolean isEditMode = false;
    private SystemBarTintManager tintManager;

    private boolean isFromNet = false;
    public static final String IS_FROM_NET = "is_from_net";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageItems = (ArrayList<ImageItem>) getIntent().getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        mCurrentPosition = getIntent().getIntExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        isEditMode = getIntent().getBooleanExtra(ImagePreviewActivity.IS_EDIT_MODE, false);
        isFromNet = getIntent().getBooleanExtra(IS_FROM_NET, false);

        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(com.lzy.imagepicker.R.color.status_bar);  //设置上方状态栏的颜色

        initView();
        updateToobTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_preview;
    }


    private void initView() {
        mDataBind = (ActivityPicPreviewBinding) getViewDataBinding();
        mDataBind.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDataBind.delete.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        mDataBind.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentPosition = mDataBind.viewPagerFixed.getCurrentItem();
                mImageItems.remove(mCurrentPosition);
                mAdapter.notifyDataSetChanged();
                updateToobTitle();
                callBack();
                if (mImageItems.size() <= 0 && isEditMode) {
                    finish();
                }
            }
        });

        mAdapter = new ImagePageAdapter(this, mImageItems);
        mAdapter.setIsFromNet(isFromNet);
        mAdapter.setPhotoViewClickListener(new ImagePageAdapter.PhotoViewClickListener() {
            @Override
            public void OnPhotoTapListener(View view, float v, float v1) {
                onImageSingleTap();
            }
        });
        mDataBind.viewPagerFixed.setAdapter(mAdapter);
        mDataBind.viewPagerFixed.setCurrentItem(mCurrentPosition, false);
        mDataBind.viewPagerFixed.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                updateToobTitle();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateToobTitle() {
        String title = (1 + mCurrentPosition) + "/" + mImageItems.size();
        mDataBind.title.setText(title);
    }

    private void callBack() {
        Intent intent = new Intent();
        intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, mImageItems);
        setResult(ImagePicker.RESULT_CODE_ITEMS, intent);
    }

    private void onImageSingleTap() {
        if (mDataBind.nav.getVisibility() == View.VISIBLE) {
            mDataBind.nav.setAnimation(AnimationUtils.loadAnimation(this, com.lzy.imagepicker.R.anim.top_out));
            mDataBind.nav.setVisibility(View.GONE);
            tintManager.setStatusBarTintResource(com.lzy.imagepicker.R.color.transparent);//通知栏所需颜色
            //给最外层布局加上这个属性表示，Activity全屏显示，且状态栏被隐藏覆盖掉。
//            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            mDataBind.nav.setAnimation(AnimationUtils.loadAnimation(this, com.lzy.imagepicker.R.anim.top_in));
            mDataBind.nav.setVisibility(View.VISIBLE);
            tintManager.setStatusBarTintResource(com.lzy.imagepicker.R.color.status_bar);//通知栏所需颜色
            //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
//            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
}
