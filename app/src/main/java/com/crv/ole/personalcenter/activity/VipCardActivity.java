package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.model.TabEntity;
import com.crv.ole.personalcenter.ui.ObservableScrollView;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心 - 会员卡页面
 */
public class VipCardActivity extends BaseActivity {
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.card_img)
    ImageView cardImg;
    @BindView(R.id.card_name)
    TextView cardName;
    @BindView(R.id.card_num)
    TextView cardNum;
    @BindView(R.id.card_tab_layout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.card_tabTop_layout)
    CommonTabLayout topTabLayout;
    @BindView(R.id.card_hjmk_arrow)
    ImageView card_hjmk_arrow;
    @BindView(R.id.card_hysjj_arrow)
    ImageView card_hysjj_arrow;
    @BindView(R.id.card_hyqy_arrow)
    ImageView card_hyqy_arrow;
    @BindView(R.id.card_hjmk_content)
    ImageView card_hjmk_content;
    @BindView(R.id.card_hysjj_content)
    ImageView card_hysjj_content;
    @BindView(R.id.card_hyqy_content)
    ImageView card_hyqy_content;

    private boolean isHjmkOpen = true, isHysjjOpen = true, isHyqyOpen = true;

    private int[] mTitles = {R.string.vipCard_cxVip, R.string.vipCard_yxVip, R.string.vipCard_zxVip};
    private int[] mIconSelectIds = {
            R.drawable.ic_home_pressed, R.drawable.ic_channel_pressed, R.drawable.ic_my_pressed};
    private int[] mIconUnselectIds = {R.drawable.ic_home_normal, R.drawable.ic_channel_normal, R.drawable.ic_my_normal};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int index;
    private String level;
    private int stateHeight = 0;    //  手机状态栏高度
    private ScrollChangedListener listener = new ScrollChangedListener() {
        @Override
        public void scrollChanged(int a, int b) {
            int Location[] = new int[2];
            commonTabLayout.getLocationOnScreen(Location);
            int tabHeight = commonTabLayout.getHeight();
            if(Location[1]-stateHeight <= tabHeight){
                topTabLayout.setCurrentTab(index);
                topTabLayout.setVisibility(View.VISIBLE);
                findViewById(R.id.card_tabTop_line).setVisibility(View.VISIBLE);
                commonTabLayout.setVisibility(View.INVISIBLE);
            }else{
                commonTabLayout.setCurrentTab(index);
                topTabLayout.setVisibility(View.INVISIBLE);
                findViewById(R.id.card_tabTop_line).setVisibility(View.GONE);
                commonTabLayout.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_card);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.vipCard_title);
        title_iv_1.setVisibility(View.VISIBLE);
        title_iv_1.setText(R.string.vipCard_unbind);

        initView();
        initChildViewPager();

        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            stateHeight = getResources().getDimensionPixelSize(resourceId);
        }
    }

    private void initView(){
        if (BaseApplication.getInstance().getUserCenter().getMemberlevel() != null){
            level = BaseApplication.getInstance().getUserCenter().getMemberlevel();
            if (level.equals("ole")){
                cardImg.setBackgroundResource(R.drawable.bg_olek);
                cardNum.setText("No."+BaseApplication.getInstance().getUserCenter().getMemberid());
                findViewById(R.id.card_active).setVisibility(View.VISIBLE);
            }else if(level.equals("hrtv1")){
                cardImg.setBackgroundResource(R.drawable.bg_v1k);
                cardName.setText("畅享会员");
                cardNum.setText("No."+BaseApplication.getInstance().getUserCenter().getMemberid());
            }else if(level.equals("hrtv2")){
                cardImg.setBackgroundResource(R.drawable.bg_v2k);
                cardName.setText("优享会员");
                cardNum.setText("No."+BaseApplication.getInstance().getUserCenter().getMemberid());
            }else if(level.equals("hrtv3")){
                cardImg.setBackgroundResource(R.drawable.bg_v3k);
                cardName.setText("尊享会员");
                cardNum.setText("No."+BaseApplication.getInstance().getUserCenter().getMemberid());
            }
        }
    }


    /**
     * 初始化tab和pager的滑动点击事件
     */
    private void initChildViewPager() {
        if(scrollView != null){
            scrollView.setListener(listener);
        }
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(getString(mTitles[i]), mIconSelectIds[i], mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                index = position;
                setHyInfo();
            }

            @Override
            public void onTabReselect(int position) {}
        });

        topTabLayout.setTabData(mTabEntities);
        topTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                index = position;
                setHyInfo();
            }

            @Override
            public void onTabReselect(int position) {}
        });
        commonTabLayout.setCurrentTab(0);
        index = 0;
    }


    @OnClick({R.id.card_active, R.id.title_iv_1, R.id.card_hjmk_layout, R.id.card_hysjj_layout,
            R.id.card_hyqy_layout, R.id.card_gd_layout})
    public void onClick(View v){
        super.onClick(v);
        switch (v.getId()){
            case R.id.card_active:   //  激活华润通
                startActivity(new Intent(mContext, CardIntroduceActivity.class).putExtra("source","active"));
                break;
            case R.id.title_iv_1:   //  解绑
                startActivity(new Intent(mContext, ActiveBindActivity.class).putExtra("source","unbind"));
                break;
            case R.id.card_hjmk_layout:
                if(isHjmkOpen){
                    isHjmkOpen = false;
                    card_hjmk_arrow.setBackgroundResource(R.drawable.right_arrow_selector);
                    card_hjmk_content.setVisibility(View.GONE);
                }else{
                    isHjmkOpen = true;
                    card_hjmk_arrow.setBackgroundResource(R.drawable.down_arrow_selector);
                    card_hjmk_content.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.card_hysjj_layout:
                if(isHysjjOpen){
                    isHysjjOpen = false;
                    card_hysjj_arrow.setBackgroundResource(R.drawable.right_arrow_selector);
                    card_hysjj_content.setVisibility(View.GONE);
                }else{
                    isHysjjOpen = true;
                    card_hysjj_arrow.setBackgroundResource(R.drawable.down_arrow_selector);
                    card_hysjj_content.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.card_hyqy_layout:
                if(isHyqyOpen){
                    isHyqyOpen = false;
                    card_hyqy_arrow.setBackgroundResource(R.drawable.right_arrow_selector);
                    card_hyqy_content.setVisibility(View.GONE);
                }else{
                    isHyqyOpen = true;
                    card_hyqy_arrow.setBackgroundResource(R.drawable.down_arrow_selector);
                    card_hyqy_content.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.card_gd_layout:
                break;
        }
    }


    /**
     * 点击后设置展示内容
     */
    private void setHyInfo(){
        if(index == 0){
            card_hjmk_content.setBackground(getResources().getDrawable(R.drawable.img_cxhy_hjmk));
            card_hysjj_content.setBackground(getResources().getDrawable(R.drawable.img_cxhy_hysjj));
            card_hyqy_content.setBackground(getResources().getDrawable(R.drawable.img_cxhy_hyqy));

        }else if(index == 1){
            card_hjmk_content.setBackground(getResources().getDrawable(R.drawable.img_yxhy_hjmk));
            card_hysjj_content.setBackground(getResources().getDrawable(R.drawable.img_yxhy_hysjj));
            card_hyqy_content.setBackground(getResources().getDrawable(R.drawable.img_yxhy_hyqy));

        }else if(index == 2){
            card_hjmk_content.setBackground(getResources().getDrawable(R.drawable.img_zxhy_hjmk));
            card_hysjj_content.setBackground(getResources().getDrawable(R.drawable.img_zxhy_hysjj));
            card_hyqy_content.setBackground(getResources().getDrawable(R.drawable.img_zxhy_hyqy));
        }
    }
    /**
     * scrollView的回调
     */
    public interface ScrollChangedListener {
        void scrollChanged(int a, int b);
    }


}
