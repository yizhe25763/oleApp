package com.crv.ole.shopping.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.shopping.fragment.LookPicFragment;
import com.crv.ole.shopping.image.CustomViewPager;
import com.crv.ole.shopping.model.PhotoInfo;

import java.util.ArrayList;

/**
 * 查看照片页
 */
public class LookPicActivity extends FragmentActivity implements
		OnClickListener {

	private ArrayList<PhotoInfo> mDataList;
	private CustomViewPager pager;
	private MyPagerAdapter adapter;
	private int currentPosition;
	private TextView mNum;
//	private Map<Integer,Fragment> mFragmentCache = new HashMap<Integer,Fragment>();

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_look_pic);
		
		currentPosition = getIntent().getIntExtra("EXTRA_CURRENT_IMG_POSITION", 0);
		mDataList = (ArrayList<PhotoInfo>) getIntent().getSerializableExtra("EXTRA_IMAGE_LIST");
		mNum = (TextView)findViewById(R.id.page_num);
		mNum.setText(currentPosition + 1 + "/" + mDataList.size());
		
		pager = (CustomViewPager) findViewById(R.id.viewpager);
		pager.setScanScroll(true);
		
		
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setCurrentItem(currentPosition);
		pager.setOffscreenPageLimit(3);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				currentPosition = arg0;
				mNum.setText(currentPosition + 1 +"/" + mDataList.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
	}
	

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mDataList.get(position).sourcePath;
		}

		@Override
		public int getCount() {
			return mDataList.size();
		}

		@Override
		public Fragment getItem(int position) {
			/*if(mFragmentCache.get(position) == null){
				mFragmentCache.put(position, LookPicFragment.newInstance(mDataList.get(position)));
			}*/
			return LookPicFragment.newInstance(mDataList.get(position));
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		// 返回按钮
//		case R.id.ewj_back_layout:
//        case R.id.ewj_back:
//			finish();
//			break;
		}
	}
	

}