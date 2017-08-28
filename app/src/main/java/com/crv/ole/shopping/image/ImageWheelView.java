package com.crv.ole.shopping.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.utils.LoadImageUtil;

import java.util.ArrayList;

/**
 * @Title: ImageWheelView.java
 */
public class ImageWheelView extends FrameLayout implements OnPageChangeListener,
    OnClickListener
{
    private final static String TAG = "ImageWheelView";
    
    protected Context mContext;
    
    protected LayoutInflater mInflater;
    protected ViewPager mViewPager; //展示区域
    protected boolean isNeedPoint; //是否需要圆点
    protected Drawable mPointDrawable; //圆点的图片
    protected Drawable mPointedDrawable; //选中状态的圆点图片
    protected LinearLayout mPointLayout; //广告圆点区域
    protected ImageView mTmpImage;
    protected ArrayList<WheelInfo> mWheelInfos =
        new ArrayList<WheelInfo>(); //存放轮播信息
    
    protected ArrayList<View> mImageViews = new ArrayList<View>();
    
    protected WheelPagerAdapter mPagerAdapter;
    
    protected OnWheelClickListener mListener;
    protected float startX; 
    

    public ImageWheelView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
        mInflater =
            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.wheel_layout, this, true);
        mViewPager = (ViewPager) findViewById(R.id.wheel_container);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setAdapter(mPagerAdapter = new WheelPagerAdapter());
       
        mPointLayout = (LinearLayout) findViewById(R.id.wheel_point);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelView, defStyle, 0);
        isNeedPoint = a.getBoolean(R.styleable.WheelView_isNeedPoint, true);
        if (isNeedPoint)
        {//如果需要圆点，则需要传一个圆点图片
            mPointDrawable = a.getDrawable(R.styleable.WheelView_pointImage);
            mPointedDrawable =
                a.getDrawable(R.styleable.WheelView_pointedImage);
            if (mPointDrawable == null || mPointedDrawable == null)
                throw new IllegalArgumentException(
                    "adview must be has a pointImage and pointedImage property");
        }
        a.recycle();
    }
    
    public ImageWheelView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
  //这里是关键 
    public boolean onInterceptTouchEvent(MotionEvent event) 
    { 
	    int action = event.getAction(); 
	    switch (action){ 
		case MotionEvent.ACTION_DOWN://按下 
		    startX = event.getX(); 
		    getParent().requestDisallowInterceptTouchEvent(true); 
		    break; 
		    //滑动，在此对里层viewpager的第一页和最后一页滑动做处理 
		case MotionEvent.ACTION_MOVE: 
		    if (startX == event.getX()){ 
			    if (0 == mViewPager.getCurrentItem() || mViewPager.getCurrentItem() == mViewPager 
			    			.getAdapter().getCount() - 1){ 
			    	getParent().requestDisallowInterceptTouchEvent(false); 
			    } 
		    } 
		    //里层viewpager已经是最后一页，此时继续向右滑（手指从右往左滑） 
		    else if (startX > event.getX()) { 
			    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) 
			    { 
			    	getParent().requestDisallowInterceptTouchEvent(false); 
			    } 
		    } 
		    //里层viewpager已经是第一页，此时继续向左滑（手指从左往右滑） 
		    else if (startX < event.getX()) 
		    { 
			    if (mViewPager.getCurrentItem() == 0) 
			    { 
			    getParent().requestDisallowInterceptTouchEvent(false); 
			    } 
		    } else 
		    { 
		    	getParent().requestDisallowInterceptTouchEvent(true); 
		    } 
		    break; 
		case MotionEvent.ACTION_UP://抬起 
		case MotionEvent.ACTION_CANCEL: 
		    	getParent().requestDisallowInterceptTouchEvent(false); 
		    break; 
	    } 
	    return false; 
    } 
    
    /**
     * 添加轮播
     * @param wheels
     */
    public void addWheel(ArrayList<WheelInfo> wheels)
    {
        if (wheels != null && wheels.size() > 0)
        {
            for (WheelInfo info : wheels)
            {
                if (!mWheelInfos.contains(info)
                    && !TextUtils.isEmpty(info.path))
                {
                    mWheelInfos.add(info);
                    mImageViews.add(advertisementView(info));
                }
            }
            if (isNeedPoint)
                changePoint();
            mPagerAdapter.notifyDataSetChanged();
        }
    }
    
    /**
     * 添加轮播
     * @param wheel
     */
    public void addWheel(WheelInfo wheel)
    {
        if (null != wheel)
        {
            if (!mWheelInfos.contains(wheel) && !TextUtils.isEmpty(wheel.path))
            {
                mWheelInfos.add(wheel);
                mImageViews.add(advertisementView(wheel));
                if (isNeedPoint)
                    changePoint();
                mPagerAdapter.notifyDataSetChanged();
            }
        }
    }
    
    /**
     * 删除轮播
     * @param info
     */
    public void removeWheel(WheelInfo info)
    {
        if (null != info && mWheelInfos.contains(info))
        {
            int index = mWheelInfos.indexOf(info);
            removeWheel(index);
        }
    }
    
    /**
     * 删除轮播
     * @param index
     */
    public void removeWheel(int index)
    {
        if (index >= 0 && index < mWheelInfos.size())
        {
            mWheelInfos.remove(index);
            mImageViews.remove(index);
            if (isNeedPoint)
                changePoint();
            mPagerAdapter.notifyDataSetChanged();
        }
    }
    
    public void clear()
    {
        mWheelInfos.clear();
        mImageViews.clear();
        if (isNeedPoint)
            changePoint();
        mPagerAdapter.notifyDataSetChanged();
    }
    
    /**
     * 获取当前项
     * @return 
     * @return int   
     * @throws
     */
    public int getCurrentItem()
    {
        return mViewPager.getCurrentItem();
    }
    
    /**
     * 获取总项数
     * @return 
     * @return int   
     * @throws
     */
    public int getCount()
    {
        return mWheelInfos.size();
    }
    
    
    /**
     * 设置轮播点击事件
     * @param l
     */
    public void setOnWheelClickListener(OnWheelClickListener l)
    {
        mListener = l;
    }
    
    public void setContext(Context context)
    {
        this.mContext = context;
    }
    
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {
    }
    
    @Override
    public void onPageScrollStateChanged(int arg0)
    {
        
    }
    
    @Override
    public void onPageSelected(int arg0)
    {
        if (isNeedPoint)
            changePoint(arg0);
       /* if(arg0 == getCount()-1){
        	isCanScroll = false;
        }else{
        	isCanScroll = true;
        }*/
    }
    
    private void changePoint()
    {
        mPointLayout.removeAllViews();
        ImageView iv = null;
        for (int i = 0; i < mWheelInfos.size(); i++)
        {
            iv = (ImageView) mInflater.inflate(R.layout.wheel_point, null);
            iv.setImageDrawable(mViewPager.getCurrentItem() == i ? mPointedDrawable
                : mPointDrawable);
            iv.setPadding(20, 0, 9, 9);
            mPointLayout.addView(iv);
        }
        if(mPointLayout.getChildCount() > 0){
        	mTmpImage = (ImageView) mPointLayout.getChildAt(0);
        }
    }
    
    private void changePoint(int current)
    {
        if (mWheelInfos != null && mWheelInfos.size() > 0)
        {
            for (int i = 0; i < mWheelInfos.size(); i ++)
            {
                if (mTmpImage != null)
                {
                    mTmpImage = (ImageView) mPointLayout.getChildAt(i);
                    mTmpImage.setImageDrawable(mPointDrawable);
                }
            }
            
            mTmpImage = (ImageView) mPointLayout.getChildAt(current);
            if (mTmpImage != null)
            {
                mTmpImage.setImageDrawable(mPointedDrawable);
            }
        }
    }
    
    /*
     * 获取轮播显示布局
     */
    public View advertisementView(WheelInfo wheel)
    {
        View view = null;
        view = mInflater.inflate(R.layout.wheel_content, null);
        ImageView image = (ImageView) view.findViewById(R.id.iv_image);
        TextView content = (TextView) view.findViewById(R.id.tv_content);
        LoadImageUtil.getInstance().loadImage(image, wheel.path, R.drawable.home_banner_1, null);
        content.setText(wheel.content);
        view.setClickable(true);
        view.setOnClickListener(this);
        return view;
    }
    
    @Override
    public void onClick(View v)
    {
        if (mListener != null && mWheelInfos != null && mWheelInfos.size() > 0)
            mListener.onWheelClick(mViewPager.getCurrentItem(),
                mWheelInfos.get(mViewPager.getCurrentItem()));
    }
    
    public class WheelPagerAdapter extends PagerAdapter
    {
        
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2)
        {
            Log.v(TAG, "destroyItem");
            ((ViewGroup) arg0).removeView((View) arg2);
        }
        
        @Override
        public void finishUpdate(View arg0)
        {
//            EwjLogUtils.v(TAG, "finishUpdate");
        }
        
        @Override
        public int getCount()
        {
//            EwjLogUtils.v(TAG, "getCount:"+mImageViews.size());
            return mImageViews.size();
        }
        
        @Override
        public Object instantiateItem(View arg0, int arg1)
        {
            Log.v(TAG, "instantiateItem");
            View iv = mImageViews.get(arg1);
            ((ViewPager) arg0).addView(iv);
            return iv;
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
//            EwjLogUtils.v(TAG, "isViewFromObject");
            return arg0 == arg1;
        }
        
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1)
        {
            Log.v(TAG, "restoreState");
        }
        
        @Override
        public Parcelable saveState()
        {
            Log.v(TAG, "saveState");
            return null;
        }
        
        @Override
        public void startUpdate(View arg0)
        {
            Log.v(TAG, "startUpdate");
        }
        
        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }
    }
    
    /**
     * 触摸处理
     *
     */
    public static class TouchHandler
    {
        protected final static int DEFAULT_REACH_TIME = 3000;
        
        protected final static int WHAT_CHECK = 10001;
        
        protected int mReachTime = DEFAULT_REACH_TIME;
        
        protected Handler mHandler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                isTouching = false;
                if (mTouch != null)
                    mTouch.onReachTime();
            }
        };
        
        protected ITouch mTouch;
        
        public boolean isTouching = false;
        
        public TouchHandler(ITouch touch)
        {
            mTouch = touch;
        }
        
        public void onTouchDown()
        {
            mHandler.removeMessages(WHAT_CHECK);
            isTouching = true;
            if (mTouch != null)
                mTouch.onTouchDown();
        }
        
        public void onTouchUp()
        {
            mHandler.sendMessageDelayed(mHandler.obtainMessage(WHAT_CHECK),
                mReachTime);
        }
        
        public interface ITouch
        {
            /**
             * 触摸前的操作
             */
            void onTouchDown();
            
            /**
             * 未触摸的时间达到后操作
             */
            void onReachTime();
        }
        
        /**
         * 设置达到的未触摸的时间(以毫秒为单位)
         * @param time
         */
        public void setReachTime(int time)
        {
            mReachTime = time;
        }
    }
    
    
    /**
     * 轮播点击事件
     *
     */
    public interface OnWheelClickListener
    {
        void onWheelClick(int position, WheelInfo info);
    }
    
    
    /**
     * 轮播信息
     *
     */
    public static class WheelInfo
    {
        public String path;
        
        public String content;
        
        public String url;
        
        public int intervalTime;
        
        public String beginTime;
        
        public String endTime;
        
        public WheelInfo(String path, String content, int intervalTime,
            String beginTime, String endTime)
        {
            this.path = path;
            this.content = content;
            this.intervalTime = intervalTime;
            this.beginTime = beginTime;
            this.endTime = endTime;
        }
        
        public WheelInfo(String path, String content, int intervalTime)
        {
            this.path = path;
            this.content = content;
            this.intervalTime = intervalTime;
        }
        
        public WheelInfo(String path, int intervalTime)
        {
            this.path = path;
            this.intervalTime = intervalTime;
        }
        
        public WheelInfo(String path, String content, String url)
        {
            this.path = path;
            this.content = content;
            this.url = url;
        }
        
        public WheelInfo(String path)
        {
            this.path = path;
        }
        
        @Override
        public boolean equals(Object o)
        {
            WheelInfo info = (WheelInfo) o;
            if (!TextUtils.isEmpty(path))
            {
                return path.equals(info.path);
            }
            return super.equals(o);
        }
        
        @Override
        public int hashCode()
        {
            if (!TextUtils.isEmpty(path))
            {
                return path.hashCode();
            }
            return super.hashCode();
        }
    }
}
