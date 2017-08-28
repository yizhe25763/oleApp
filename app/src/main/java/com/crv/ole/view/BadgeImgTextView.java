package com.crv.ole.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;

/**
 * Created by lihongshi on 2017/7/25.
 */
public class BadgeImgTextView extends RelativeLayout {
    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvCount;

    private Drawable drawable;
    private String badgeTitle;
    private String badgeCount;
    private float badgeCountTextSize = 12;
    private int badgeTitleColor;

    public BadgeImgTextView(Context context) {
        super(context);
    }

    public BadgeImgTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        initView(context);
    }

    public BadgeImgTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        initView(context);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BadgeImgTextView);
        drawable = typedArray.getDrawable(R.styleable.BadgeImgTextView_badgeDrawable);
        badgeTitle = typedArray.getString(R.styleable.BadgeImgTextView_badgeTitle);
        badgeCount = typedArray.getString(R.styleable.BadgeImgTextView_badgeCount);
        badgeCountTextSize = typedArray.getDimension(R.styleable.BadgeImgTextView_badgeCountTextSize, 9);
        badgeTitleColor = typedArray.getColor(R.styleable.BadgeImgTextView_badgeTitleColor, context.getResources().getColor(R.color.white));
        //获取资源后要及时回收
        typedArray.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.badge_img_text_view, this, true);
        imageView = (ImageView) findViewById(R.id.badge_imageView);
        tvTitle = (TextView) findViewById(R.id.badge_title);
        tvCount = (TextView) findViewById(R.id.badge_count);

        setBadgeDrawable(drawable);
        setBadgeTitle(badgeTitle);
        setBadgeCount(badgeCount);
        setBadgeCountTextSize(badgeCountTextSize);
        setBadgeTitleColor(badgeTitleColor);
    }

    public void setBadgeDrawable(Drawable drawable) {
        this.drawable = drawable;
        imageView.setImageDrawable(drawable);
    }

    public void setBadgeTitle(String title) {
        this.badgeTitle = title;
        tvTitle.setText(title);
    }

    public void setBadgeCount(String count) {
        this.badgeCount = count;
        if (TextUtils.isEmpty(count)) {
            tvCount.setVisibility(GONE);
        } else {
            tvCount.setVisibility(VISIBLE);
            tvCount.setText(count);
        }
    }

    public void setBadgeCountTextSize(float size) {
        this.badgeCountTextSize = size;
        tvCount.setTextSize(size);
    }

    public void setBadgeTitleColor(int color) {
        this.badgeTitleColor = color;
        this.tvTitle.setTextColor(this.badgeTitleColor);
    }

}
