package com.crv.sdk.wedgets;


import com.crv.sdk.R;
import com.crv.sdk.utils.DisplayUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 图片滑动的指示器,左边文字提示，右边显示点点
 * 
 * @author Administrator
 * 
 */
public class ImagePointer extends LinearLayout
{
	private List<ImageView> pointers = new ArrayList<ImageView>(); // 图片指示器
	private Context ctx;
	private int dotNormalId = R.drawable.bg_oval_normal;
	private int dotSelectId = R.drawable.bg_oval_selected;
	private int mDividerPadding = 0;

	public ImagePointer(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
		init();

	}

	public ImagePointer(Context context) {
		super(context);
		ctx = context;
		init();
	}

	private void init() {
		this.setOrientation(HORIZONTAL);
		this.setBackgroundColor(ctx.getResources().getColor(android.R.color.transparent));
	}

	/**
	 * 设置点点
	 */
	public void setPointers(int pointersCount) {
		this.pointers.clear();
		this.removeAllViews();
		mDividerPadding = DisplayUtil.dip2px(getContext(), 3);
		for (int i = 0; i < pointersCount; i++) {
			ImageView imageView = new ImageView(ctx);
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setImageResource(dotNormalId);
			this.addView(imageView);
			this.pointers.add(imageView);
		}
		this.setGravity(Gravity.CENTER);
	}

	/**
	 * 设置点点,自定义点点
	 */
	public void setPointers(int pointersCount, int dotnormalid, int dotselectid) {
		this.pointers.clear();
		this.removeAllViews();
		this.dotNormalId = dotnormalid;
		this.dotSelectId = dotselectid;
		mDividerPadding = DisplayUtil.dip2px(getContext(), 5);
		for (int i = 0; i < pointersCount; i++) {
			ImageView imageView = new ImageView(ctx);
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setImageResource(dotNormalId);
			this.addView(imageView);
			this.pointers.add(imageView);
		}
		this.setGravity(Gravity.CENTER);
	}

	/**
	 * 根据paramInt 选择显示
	 * 
	 * @param paramInt
	 */
	public void checkPointer(int paramInt) {
		for (int i = 0; i < this.pointers.size(); i++) {
			if (this.pointers.size() > 0) {
				if (i == paramInt) {
					this.pointers.get(paramInt)
							.setImageResource(dotSelectId);
				} else {
					this.pointers.get(i).setImageResource(dotNormalId);
				}
			}
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 将分隔元素的宽高转化为对应的margin
		setChildrenDivider(mDividerPadding);
		// ViewGroup.LayoutParams params = this.getLayoutParams();
		// params.height += mDividerPadding;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 将分隔元素的宽高转化为对应的margin
	 */
	public void setChildrenDivider(int pointersCount) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			// 遍历每个子View
			View child = getChildAt(i);
			// 拿到索引
			int index = indexOfChild(child);
			// 方向
			int orientation = getOrientation();

			final LayoutParams params = (LayoutParams) child.getLayoutParams();
			// 判断是否需要在子View左边绘制分隔
			if (hasDividerBeforeChildAt(index)) {
				params.leftMargin = mDividerPadding;
			}
		}
	}

	/**
	 * 判断是否需要在子View左边绘制分隔
	 */
	public boolean hasDividerBeforeChildAt(int childIndex) {
		if (childIndex == 0 || childIndex == getChildCount()) {
			return false;
		}
		boolean hasVisibleViewBefore = false;
		for (int i = childIndex - 1; i >= 0; i--) {
			// 当前index的前一个元素不为GONE则认为需要
			if (getChildAt(i).getVisibility() != GONE) {
				hasVisibleViewBefore = true;
				break;
			}
		}
		return hasVisibleViewBefore;
	}
}
