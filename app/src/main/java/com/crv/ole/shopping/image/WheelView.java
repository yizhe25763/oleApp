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
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.utils.LoadImageUtil;
import com.daimajia.slider.library.Tricks.FixedSpeedScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @Title: WheelView.java
 * @Package com.crc.cre.crv.ewj.ui.libui
 * @Description: 轮播显示图片控件
 * Copyright:ewj.com All Rights Reserved
 * @author Rocky
 * @version V1.0
 * Create Date: 2015-2-10 上午9:46:08
 */
public class WheelView extends FrameLayout implements OnPageChangeListener,
    OnClickListener
{
    private final static String TAG = "WheelView";
    
    protected Context mContext;
    
    protected LayoutInflater mInflater;
    
    private FixedSpeedScroller mScroller;
    
    protected ViewPager mViewPager; //展示轮播区域
    
    protected int mIntervalTime; //轮播播的间隔时间
    
    protected boolean isNeedPoint; //是否需要圆点
    
    protected boolean isAutoWheel; //是否自动轮播
    
    protected Drawable mPointDrawable; //圆点的图片
    
    protected Drawable mPointedDrawable; //选中状态的圆点图片
    
    protected LinearLayout mPointLayout; //广告圆点区域
    
    protected ImageView mTmpImage;
    
    protected ArrayList<WheelInfo> mWheelInfos =
        new ArrayList<WheelInfo>(); //存放轮播信息
    
    protected ArrayList<View> mImageViews = new ArrayList<View>();
    
    protected WheelPagerAdapter mPagerAdapter;
    
    protected OnWheelClickListener mListener;
    
    protected OnWheelTouchListener mTouchListener;
    
    protected OnWheelChangedListener mChangedListener;
    
    protected TimeHandler mTimeHandler;
    
    protected TouchHandler mTouchHandler;
    
    protected float X;
    
    protected float Y;
    
    protected float distanceX;
    
    protected float distanceY;
    
    // 当前实际显示的广告位的下标
    private int currentRealPosition = 0;
    
    public WheelView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
        mInflater =
            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.wheel_layout, this, true);
        mViewPager = (ViewPager) findViewById(R.id.wheel_container);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setAdapter(mPagerAdapter = new WheelPagerAdapter());
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new FixedSpeedScroller(mViewPager.getContext(),new AccelerateInterpolator());
            mField.set(mViewPager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mScroller.setmDuration(400);
        mPointLayout = (LinearLayout) findViewById(R.id.wheel_point);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelView, defStyle, 0);
        mIntervalTime = a.getInt(R.styleable.WheelView_intervalTime, 3); //轮播播放间隔时间，默认为3
        isNeedPoint = a.getBoolean(R.styleable.WheelView_isNeedPoint, true);
        isAutoWheel = a.getBoolean(R.styleable.WheelView_isAutoWheel, true);
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
        mTimeHandler = new TimeHandler(new ITimeHandlerCallback()
        {
            @Override
            public void callback()
            {
                int next = mTimeHandler.getNextWheel();
                if (isNeedPoint)
                    changePoint(mTimeHandler.mCurrentWheel, next);
                mViewPager.setCurrentItem(next);
            }
        });
        mTouchHandler = new TouchHandler(new TouchHandler.ITouch()
        {
            @Override
            public void onTouchDown()
            {
                mTimeHandler.forceStop();
                if (mTouchListener != null && mWheelInfos != null
                    && mWheelInfos.size() > 0)
                    mTouchListener.onWheelTouch(mTimeHandler.mCurrentWheel,
                        mWheelInfos.get(mTimeHandler.mCurrentWheel));
            }
            
            @Override
            public void onReachTime()
            {
                mTimeHandler.forceStart();
                //                if(mListener!=null && mWheelInfos!=null && mWheelInfos.size()>0) mListener.onWheelClick(mTimeHandler.mCurrentWheel,mWheelInfos.get(mTimeHandler.mCurrentWheel));
            }
        });
        
        mViewPager.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        X = event.getX();
                        Y = event.getY();
                    case MotionEvent.ACTION_MOVE:
                        distanceX = Math.abs(event.getX() - X);
                        distanceY = Math.abs(event.getY() - Y);
                        if (distanceX > distanceY)
                        {
                            getParent().requestDisallowInterceptTouchEvent(true);
                            mTouchHandler.onTouchDown();
                        }
                        else
                        {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        
                        break;
                    case MotionEvent.ACTION_UP:
                        if (distanceX > distanceY)
                        {
                            getParent().requestDisallowInterceptTouchEvent(true);
                            mTouchHandler.onTouchUp();
                        }
                        else
                        {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                }
                return false;
            }
        });
    }
    
    public WheelView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
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
            mTimeHandler.notiftyChange();
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
//                mViewPager.setCurrentItem(mTimeHandler.mCurrentWheel);
                mTimeHandler.notiftyChange();
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
        if (index >= 0 && index < mTimeHandler.mTotalWheel)
        {
            mWheelInfos.remove(index);
            mImageViews.remove(index);
            if (mTimeHandler.mCurrentWheel == index)
            {
                if (index >= mTimeHandler.mTotalWheel)
                {
                    mTimeHandler.mCurrentWheel = 0;
                }
                else
                {
                    mTimeHandler.increase();
                }
            }
            mTimeHandler.notiftyChange();
            if (isNeedPoint)
                changePoint();
            mPagerAdapter.notifyDataSetChanged();
        }
    }
    
    public void clear()
    {
        mTimeHandler.notiftyChange();
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
        return mTimeHandler.mCurrentWheel;
    }
    
    /**
     * 获取总项数
     * @return 
     * @return int   
     * @throws
     */
    public int getCount()
    {
        return mTimeHandler.mTotalWheel;
    }
    
    /**
     * 开始/恢复轮播
     */
    public void start()
    {
        mTimeHandler.forceStart();
    }
    
    /**
     * 停止轮播
     */
    public void stop()
    {
        mTimeHandler.forceStop();
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
    
    /**
     * 设置轮播触摸事件
     * @param l
     */
    public void setOnWheelTouchListener(OnWheelTouchListener l)
    {
        mTouchListener = l;
    }
    
    /**
     * 设置轮播改变事件
     * @param l 
     * @return void   
     * @throws
     */
    public void setOnWheelChangedListener(OnWheelChangedListener l)
    {
        mChangedListener = l;
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
        currentRealPosition = arg0;
        if (isNeedPoint)
            changePoint(mTimeHandler.mCurrentWheel, arg0);
        if (mTouchHandler.isTouching && mTimeHandler.mCurrentWheel != arg0)
        {
            mTimeHandler.mCurrentWheel = arg0;
        }
        if (mChangedListener != null)
            mChangedListener.onWheelChanged(arg0, mWheelInfos.get(arg0));
    }
    
    private void changePoint()
    {
        mPointLayout.removeAllViews();
        ImageView iv = null;
        for (int i = 0; i < mTimeHandler.mTotalWheel; i++)
        {
            iv = (ImageView) mInflater.inflate(R.layout.wheel_point, null);
            iv.setImageDrawable(mTimeHandler.mCurrentWheel == i ? mPointedDrawable
                : mPointDrawable);
            iv.setPadding(20, 0, 9, 9);
            mPointLayout.addView(iv);
        }
    }
    
    private void changePoint(int current, int next)
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
            
            mTmpImage = (ImageView) mPointLayout.getChildAt(next);
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
//        if (mListener != null && mWheelInfos != null && mWheelInfos.size() > 0)
//            mListener.onWheelClick(mTimeHandler.mCurrentWheel,
//                mWheelInfos.get(mTimeHandler.mCurrentWheel));

        if (mListener != null && mWheelInfos != null && mWheelInfos.size() > 0)
            mListener.onWheelClick(currentRealPosition,
                    mWheelInfos.get(currentRealPosition));
    }
    
    public class WheelPagerAdapter extends PagerAdapter
    {
        
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2)
        {
            ((ViewGroup) arg0).removeView((View) arg2);
        }
        
        @Override
        public void finishUpdate(View arg0)
        {
//            Logutil.v(TAG+ "finishUpdate");
        }
        
        @Override
        public int getCount()
        {
//            Logutil.v(TAG+ "getCount:"+mImageViews.size());
            return mImageViews.size();
        }
        
        @Override
        public Object instantiateItem(View arg0, int arg1)
        {
            View iv = mImageViews.get(arg1);
            ((ViewPager) arg0).addView(iv);
            return iv;
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
//            Logutil.v(TAG+ "isViewFromObject");
            return arg0 == arg1;
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
//            Logutil.v(TAG+ "startUpdate");
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
     * 定时操作
     *
     */
    public class TimeHandler
    {
        private final static int WHAT_TIME = 1000;
        
        private Handler mHandler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                switch (msg.what)
                {
                    case WHAT_TIME:
                        if (callback != null)
                            callback.callback();
                        if (isRunning)
                            mHandler.sendMessageDelayed(mHandler.obtainMessage(WHAT_TIME),
                                getNext());
                        break;
                }
            }
        };
        
        private boolean isRunning; //是否正在运行
        
        private ITimeHandlerCallback callback;
        
        public volatile int mCurrentWheel; //当前轮播的位置
        
        public volatile int mTotalWheel; //轮播的总数
        
        public TimeHandler(ITimeHandlerCallback callback)
        {
            this.callback = callback;
        }
        
        /**
         * 是否在运行
         * @return
         */
        public synchronized boolean isRunning()
        {
            return isRunning;
        }
        
        /**
         * 强制停止
         * @return
         */
        public boolean forceStop()
        {
            isRunning = false;
            mHandler.removeMessages(WHAT_TIME);
            return true;
        }
        
        public boolean forceStart()
        {
            if (!isRunning && isAutoWheel)
            {
                if (null != mWheelInfos && mWheelInfos.size() > 0)
                {
                    isRunning = true;
                    getCurrent();
                    mHandler.sendMessage(mHandler.obtainMessage(WHAT_TIME));
                    return true;
                }
            }
            return false;
        }
        
        private int getCurrent()
        {
            if (mCurrentWheel >= mTotalWheel || mCurrentWheel < 0)
                mCurrentWheel = 0;
            int time = mWheelInfos.get(mCurrentWheel).intervalTime;
            return time > 0 ? time * 1000 : mIntervalTime * 1000;
        }
        
        private int getNext()
        {
            mCurrentWheel++;
            if (mCurrentWheel >= mTotalWheel)
                mCurrentWheel = 0;
            int time = mWheelInfos.get(mCurrentWheel).intervalTime;
            return time > 0 ? time * 1000 : mIntervalTime * 1000;
        }
        
        public int getNextWheel()
        {
            int next = mCurrentWheel + 1;
            return (next >= mTotalWheel) ? 0 : next;
        }
        
        public synchronized void increase()
        {
            mCurrentWheel++;
        }
        
        public void notiftyChange()
        {
            Log.e("-----", "notiftyChange");
            mTotalWheel = mWheelInfos.size();
        }
    }
    
    public interface ITimeHandlerCallback
    {
        void callback();
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
     * 轮播触摸事件
     *
     */
    public interface OnWheelTouchListener
    {
        void onWheelTouch(int position, WheelInfo info);
    }
    
    /**
     * 轮播改变事件
     */
    public interface OnWheelChangedListener
    {
        void onWheelChanged(int position, WheelInfo info);
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
