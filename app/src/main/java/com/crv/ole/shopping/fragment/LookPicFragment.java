/*
 * Copyright (C) © 2012-2014 深圳市建乔无线信息技术有限公司
 * GuosenCRM
 * java
 */
package com.crv.ole.shopping.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crv.ole.R;
import com.crv.ole.shopping.image.ImageDisplayer;
import com.crv.ole.shopping.image.PhotoView;
import com.crv.ole.shopping.image.PhotoViewAttacher;
import com.crv.ole.shopping.model.PhotoInfo;
import com.crv.ole.utils.LoadImageUtil;

/**
 * 图片查看
 */
public class LookPicFragment extends Fragment {
	private PhotoView photoView;
    private PhotoInfo bean;
    private Context mContext;
    
	public static LookPicFragment newInstance(PhotoInfo photoBean) {
	    LookPicFragment f = new LookPicFragment();
        Bundle b = new Bundle();
        b.putSerializable("photoBean", photoBean);
        f.setArguments(b);
        return f;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		mContext = getActivity();
		bean = (PhotoInfo) getArguments().getSerializable("photoBean");
	}
	
	@SuppressLint("InflateParams")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.advertising_fragment, null);
	    photoView = (PhotoView) rootView.findViewById(R.id.photo_view);
		photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View view, float x, float y) {
				((Activity)mContext).finish();
			}
		});
	    if(bean.isNetResource){
			LoadImageUtil.getInstance().loadImage(photoView, bean.sourcePath, R.drawable.home_banner_1, ImageView.ScaleType.FIT_CENTER);
	    }else{
			try {
				ImageDisplayer.getInstance(mContext).displayBmp(
						photoView, null, bean.sourcePath, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		return rootView;
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
	
}
