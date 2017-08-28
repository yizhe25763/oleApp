package com.crv.ole.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.home.adapter.AdAdapter;
import com.crv.ole.home.model.UserCenterData;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.personalcenter.activity.CardIntroduceActivity;
import com.crv.ole.personalcenter.activity.CenterActivity;
import com.crv.ole.personalcenter.activity.CouponActivity;
import com.crv.ole.personalcenter.activity.HelpFeedbackActivity;
import com.crv.ole.personalcenter.activity.MsgCenterActivity;
import com.crv.ole.personalcenter.activity.MyCollectionActivity;
import com.crv.ole.personalcenter.activity.MyOrderActivity;
import com.crv.ole.personalcenter.activity.PointActivity;
import com.crv.ole.personalcenter.activity.RefundActivity;
import com.crv.ole.personalcenter.activity.SettingActivity;
import com.crv.ole.personalcenter.activity.VipCardActivity;
import com.crv.ole.personalcenter.model.UnicornModel;
import com.crv.ole.shopping.activity.ShoppingCartListActivity;
import com.crv.ole.shopping.model.CartCountResponseData;
import com.crv.ole.trial.activity.TrialListActivity;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.view.BadgeImgTextView;
import com.crv.sdk.fragment.BaseFragment;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心
 */
public class UserFragment extends BaseFragment {

    protected View mRootView;
    private Boolean isLogin = false;//是否登录

    //ole设置
    @BindView(R.id.ole_setting)
    ImageView oleSetting;

    //ole消息提醒tip
    @BindView(R.id.ole_message_show)
    ImageView oleMessageShow;

    //ole消息
    @BindView(R.id.ole_message)
    ImageView oleMessage;

    //ole 卡id
    @BindView(R.id.ole_card_id)
    TextView oleCardId;


    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;

    @BindView(R.id.ole_card_img)
    RelativeLayout oleCardImg;

    @BindView(R.id.ole_userinfo_relahyout)
    RelativeLayout oleUserinfoRelahyout;


    //普通用户购物车
    @BindView(R.id.badge_shoping)
    BadgeImgTextView badge_shoping;

    //ole购物车
    @BindView(R.id.ole_wallet_LinLayout)
    BadgeImgTextView oleWalletLinLayout;

    //积分
    @BindView(R.id.ole_membership_tv)
    TextView oleMembership_tv;

    //优惠卷
    @BindView(R.id.ole_probation_LinLayout)
    BadgeImgTextView oleProbationLinLayout;

    //我的收藏
    @BindView(R.id.ole_collect_LinLayout)
    BadgeImgTextView oleCollectLinLayout;

    //ole购物车栏
    @BindView(R.id.ole_frist_linlayout)
    LinearLayout oleFristLinlayout;

    //Ole精品试用栏
    @BindView(R.id.ole_second_linlayout)
    LinearLayout oleSecondLinlayout;

    //Ole精品试用
    @BindView(R.id.ole_shopping_cart_LinLayout)
    BadgeImgTextView oleShoppingCartLinLayout;

    //ole礼品卡
    @BindView(R.id.ole_lpk_LinLayout)
    BadgeImgTextView oleLpkLinLayout;

    //ole在线客服
    @BindView(R.id.ole_feed_back_online_LinLayout)
    BadgeImgTextView oleFeedBackOnlineLinLayout;

    //ole帮助和反馈
    @BindView(R.id.ole_feed_back_LinLayout)
    BadgeImgTextView oleFeedBackLinLayout;


    @BindView(R.id.ole_line_img)
    ImageView oleLineImg;


    @BindView(R.id.setting)
    ImageView setting;

    @BindView(R.id.message_show)
    ImageView messageShow;

    @BindView(R.id.message)
    ImageView message;

    @BindView(R.id.message_layout)
    RelativeLayout messageLayout;

    @BindView(R.id.ole_iv_avatar)
    ImageView OleIvAvatar;

    @BindView(R.id.card_img)
    RelativeLayout cardImg;

    @BindView(R.id.userinfo_relahyout)
    RelativeLayout userinfoRelahyout;


    @BindView(R.id.membership_card_LinLayout)
    BadgeImgTextView membershipCardLinLayout;

    @BindView(R.id.yhq_LinLayout)
    BadgeImgTextView yhqLinLayout;

    @BindView(R.id.wdsc_LinLayout)
    BadgeImgTextView wdscLinLayout;

    @BindView(R.id.frist_linlayout)
    LinearLayout fristLinlayout;

    @BindView(R.id.jpsy_LinLayout)
    BadgeImgTextView jpsyLinLayout;

    @BindView(R.id.lpk_LinLayout)
    BadgeImgTextView lpkLinLayout;

    @BindView(R.id.feed_back_online_LinLayout)
    BadgeImgTextView feedBackOnlineLinLayout;

    @BindView(R.id.feed_back_LinLayout)
    BadgeImgTextView feedBackLinLayout;

    @BindView(R.id.second_linlayout)
    LinearLayout secondLinlayout;

    @BindView(R.id.viptip_tv)
    TextView viptip_tv;

    //ole用户退出/登录提示
    @BindView(R.id.tip_tv)
    TextView tip_tv;

    // 普通用户退出/登录提示
    @BindView(R.id.normal_tip_tv)
    TextView normal_tip_tv;


    //待付款
    @BindView(R.id.badge_pay)
    BadgeImgTextView non_tip_pay;

    //待发货
    @BindView(R.id.badge_shipment)
    BadgeImgTextView non_tip_shipment;

    //待收货
    @BindView(R.id.badge_receive)
    BadgeImgTextView non_tip_receive;

    //待评价
    @BindView(R.id.badge_evaluated)
    BadgeImgTextView non_tip_evaluated;

    //退款
    @BindView(R.id.badge_sale)
    BadgeImgTextView non_tip_sale;

    MainActivity mainActivity;

    UserCenterData.UserCenter userCenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        isLogin = ToolUtils.isLoginStatus(mainActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }


    @Override
    public void onResume() {
        super.onResume();
        isLogin = ToolUtils.isLoginStatus(mainActivity);
        userCenter = BaseApplication.getInstance().getUserCenter();
        if (isLogin && (userCenter == null)) {
            getUserCenter(true);
        } else {
            switchView();
            getCartCount();
        }
    }

    @Override
    public void eventBus(String event) {
        if (event.equals(OleConstants.USER_CENTER_RELOAD)) {
            Log.i("user_center_reload来了");
            isLogin = ToolUtils.isLoginStatus(mainActivity);
            if (isLogin) {
                getUserCenter(false);
            } else {
                Log.i("未登录,事件忽略");
            }
        }
    }

    private void toIntent(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivity(intent);
    }

    public static UserFragment getInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @OnClick({R.id.ole_setting, R.id.ole_message_show, R.id.ole_message, R.id.ole_card_id,
            R.id.ole_iv_avatar, R.id.ole_card_img, R.id.ole_userinfo_relahyout, R.id.ole_wallet_LinLayout,
            R.id.ole_membership_card_LinLayout, R.id.ole_probation_LinLayout, R.id.ole_collect_LinLayout,
            R.id.ole_frist_linlayout, R.id.ole_shopping_cart_LinLayout, R.id.ole_lpk_LinLayout, R.id.ole_feed_back_online_LinLayout,
            R.id.ole_feed_back_LinLayout, R.id.ole_second_linlayout, R.id.ole_line_img, R.id.setting, R.id.message_show, R.id.message,
            R.id.message_layout, R.id.iv_avatar, R.id.card_img, R.id.userinfo_relahyout, R.id.membership_card_LinLayout,
            R.id.yhq_LinLayout, R.id.wdsc_LinLayout, R.id.frist_linlayout, R.id.jpsy_LinLayout, R.id.lpk_LinLayout,
            R.id.feed_back_online_LinLayout, R.id.feed_back_LinLayout, R.id.second_linlayout, R.id.find_all_tv,
            R.id.badge_pay, R.id.badge_shipment, R.id.badge_receive, R.id.badge_evaluated, R.id.badge_sale, R.id.badge_shoping, R.id.normal_tip_tv})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ole_setting:
                if (isLogin && (userCenter != null)) {
                    toIntent(SettingActivity.class);
                } else {
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.ole_message:
                Log.i("点击ole_message:");
                if (isLogin && (userCenter != null)) {
                    toIntent(MsgCenterActivity.class);
                } else {
                    toIntent(LoginActivity.class);
                }

                break;
            case R.id.ole_iv_avatar:
            case R.id.iv_avatar:
                Log.i("头像");
                if (isLogin && (userCenter != null)) {
                    toIntent(CenterActivity.class);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.ole_card_img:
                Log.i("会员卡页面");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, VipCardActivity.class));
                } else {//未登录
                    toIntent(LoginActivity.class);
                }

                break;
            case R.id.ole_membership_card_LinLayout:
                Log.i("积分");
                if (isLogin && (userCenter != null)) {
                    toIntent(PointActivity.class);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }

                break;
            case R.id.badge_shoping: {
                Log.i("普通购物车");
                Intent intent = new Intent(getContext(), ShoppingCartListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ole_wallet_LinLayout:
                Log.i("ole购物车");
                Intent intent = new Intent(getContext(), ShoppingCartListActivity.class);
                startActivity(intent);
                break;

            case R.id.ole_probation_LinLayout:
                Log.i("优惠卷");
                if (isLogin && (userCenter != null)) {
                    toIntent(CouponActivity.class);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.ole_collect_LinLayout:
                if (isLogin && (userCenter != null)) {
                    toIntent(MyCollectionActivity.class);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.ole_shopping_cart_LinLayout:
                Log.i("精品试用");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, TrialListActivity.class));
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.ole_lpk_LinLayout:
                Log.i("礼品卡");
                if (isLogin && (userCenter != null)) {

                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.ole_feed_back_online_LinLayout:
                Log.i("在线客服");
                if (isLogin && (userCenter != null)) {
                    UnicornModel.openChat(mContext);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.ole_feed_back_LinLayout:
                toIntent(HelpFeedbackActivity.class);
                break;
            case R.id.ole_second_linlayout:
                Log.i("精品试用");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, TrialListActivity.class));
                } else {//未登录
                    toIntent(LoginActivity.class);
                }

                break;

            case R.id.setting:
                Log.i("设置");
                if (isLogin && (userCenter != null)) {
                    toIntent(SettingActivity.class);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.message:
                if (isLogin && (userCenter != null)) {
                    toIntent(MsgCenterActivity.class);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.userinfo_relahyout:
                break;
            case R.id.membership_card_LinLayout:
                Log.i("开通会员");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, CardIntroduceActivity.class).putExtra("source", "bind"));
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.yhq_LinLayout:
                Log.i("优惠卷");
                if (isLogin && (userCenter != null)) {
                    toIntent(CouponActivity.class);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }

                break;
            case R.id.wdsc_LinLayout:
                Log.i("我的收藏");
                if (isLogin && (userCenter != null)) {
                    toIntent(MyCollectionActivity.class);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;

            case R.id.jpsy_LinLayout:
                Log.i("精品试用");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, TrialListActivity.class));
                } else {
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.lpk_LinLayout:
                Log.i("礼品卡");
                if (isLogin && (userCenter != null)) {

                } else {
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.normal_tip_tv:
                if (!isLogin || (userCenter == null)) {
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.feed_back_online_LinLayout:
                Log.i("在线客服");
                if (isLogin) {
                    UnicornModel.openChat(mContext);
                } else {
                    toIntent(LoginActivity.class);
                }

                break;
            case R.id.feed_back_LinLayout:
                Log.i("帮助与反馈");
                toIntent(HelpFeedbackActivity.class);
                break;
            case R.id.find_all_tv:  //  我的订单
                if (isLogin && (userCenter != null)) {
                    toIntent(MyOrderActivity.class);
                } else {//未登录
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.second_linlayout:
                Log.i("精品试用");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, TrialListActivity.class));
                } else {
                    toIntent(LoginActivity.class);
                }
                break;

            case R.id.badge_pay:
                Log.i("待付款");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, MyOrderActivity.class).putExtra("position", 1));
                } else {
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.badge_shipment:
                Log.i("待发货");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, MyOrderActivity.class).putExtra("position", 2));
                } else {
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.badge_receive:
                Log.i("待收货");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, MyOrderActivity.class).putExtra("position", 3));
                } else {

                }
                break;
            case R.id.badge_evaluated:
                Log.i("待评价");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext, MyOrderActivity.class).putExtra("position", 4));
                } else {
                    toIntent(LoginActivity.class);
                }
                break;
            case R.id.badge_sale:
                Log.i("退款/售后");
                if (isLogin && (userCenter != null)) {
                    startActivity(new Intent(mContext,RefundActivity.class));
                } else {
                    toIntent(LoginActivity.class);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取用户中心
     */
    public void getUserCenter(boolean reloadView) {
        Log.i("获取用户中心资料开始");
        ServiceManger.getInstance().getUserCenterInfo(new HashMap<>(), new BaseRequestCallback<UserCenterData>() {

            @Override
            public void onStart() {
                if (reloadView) {
                    mainActivity.showProgressDialog("请求中...", null);
                }
            }

            @Override
            public void onSuccess(UserCenterData response) {
                mainActivity.dismissProgressDialog();
                Log.e("用户中心：" + new Gson().toJson(response));
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    BaseApplication.getInstance().setUserCenter(response.getRETURN_DATA());
                    userCenter = response.getRETURN_DATA();
                    if (reloadView) {
                        switchView();
                    }

                } else {
                    isLogin = false;
                    mPreferencesHelper.remove(OleConstants.KEY.LOGIN_STATUS);
                    mPreferencesHelper.remove(OleConstants.KEY.LOGIN_ID);
                    if (reloadView) {
                        switchView();
                        Log.i("结果:" + response.getRETURN_DESC());
                        ToastUtil.showToast(response.getRETURN_DESC());
                    }
                }
            }

            @Override
            public void onEnd() {
                Log.e("用户中心获取结束");
                mainActivity.dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                Log.e("用户中心获取失败" + msg);
                mainActivity.dismissProgressDialog();
                mPreferencesHelper.remove(OleConstants.KEY.LOGIN_STATUS);
                mPreferencesHelper.remove(OleConstants.KEY.LOGIN_ID);
                mainActivity.dismissProgressDialog();
                isLogin = false;
                switchView();

            }
        });
    }

    /**
     * 切换未登录/普通会员/高级会员界面
     */
    private void switchView() {
        //未登录
        if (!isLogin || (userCenter == null)) {
            oleUserinfoRelahyout.setVisibility(View.GONE);
            oleFristLinlayout.setVisibility(View.GONE);
            oleSecondLinlayout.setVisibility(View.GONE);
            oleLineImg.setVisibility(View.GONE);
            userinfoRelahyout.setVisibility(View.VISIBLE);
            fristLinlayout.setVisibility(View.VISIBLE);
            secondLinlayout.setVisibility(View.VISIBLE);
            LoadImageUtil.getInstance().loadIconImage(ivAvatar, "file:///xxx", true);
            messageShow.setVisibility(View.INVISIBLE);
            tip_tv.setVisibility(View.GONE);
            normal_tip_tv.setVisibility(View.VISIBLE);
            normal_tip_tv.setText("注册/登录");

            //购物车数量
            badge_shoping.setBadgeCount("");
            non_tip_pay.setBadgeCount("");
            non_tip_shipment.setBadgeCount("");
            non_tip_receive.setBadgeCount("");
            non_tip_evaluated.setBadgeCount("");
            non_tip_sale.setBadgeCount("");
            return;
        }

        String type = userCenter.getMemberlevel();
        // type = "hrtv1";
        if ("common".equals(type)) { //普通用户
            Log.i("不是ole会员");

            //未读消息
            String countMsg = userCenter.getMessageNum();
            messageShow.setVisibility("0".equals(countMsg) ? View.INVISIBLE : View.VISIBLE);

            //普通购物车
             String count = userCenter.getCartNum();
             badge_shoping.setBadgeCount("0".equals(count) ? "" : count);

            oleUserinfoRelahyout.setVisibility(View.GONE);
            oleFristLinlayout.setVisibility(View.GONE);
            oleSecondLinlayout.setVisibility(View.GONE);
            oleLineImg.setVisibility(View.GONE);
            userinfoRelahyout.setVisibility(View.VISIBLE);
            fristLinlayout.setVisibility(View.VISIBLE);
            secondLinlayout.setVisibility(View.VISIBLE);
            LoadImageUtil.getInstance().loadIconImage(ivAvatar, userCenter.getUserimage(), true);

            tip_tv.setVisibility(View.GONE);
            normal_tip_tv.setVisibility(View.VISIBLE);

            if (!TextUtil.isEmpty(userCenter.getNickname())) {
                normal_tip_tv.setText(userCenter.getNickname());
            } else {
                normal_tip_tv.setText(userCenter.getLoginId());
            }

        } else {

            //  ole购物车
              String count = userCenter.getCartNum();
              oleWalletLinLayout.setBadgeCount("0".equals(count) ? "" : count);

            //未读消息
            String countMsg = userCenter.getMessageNum();
            oleMessageShow.setVisibility("0".equals(countMsg) ? View.INVISIBLE : View.VISIBLE);
            //会员积分
            oleMembership_tv.setText(userCenter.getTotalIntegralValue() + "");

            oleUserinfoRelahyout.setVisibility(View.VISIBLE);
            oleFristLinlayout.setVisibility(View.VISIBLE);
            oleSecondLinlayout.setVisibility(View.VISIBLE);
            oleLineImg.setVisibility(View.VISIBLE);
            LoadImageUtil.getInstance().loadIconImage(OleIvAvatar, userCenter.getUserimage(), true);
            oleCardId.setText("No." + userCenter.getMemberid());

            if ("ole".equals(type)) {
                oleCardImg.setBackgroundResource(R.drawable.olek_s);
                viptip_tv.setText("ole会员");
                viptip_tv.setTextColor(getResources().getColor(R.color.white));
            } else if ("hrtv1".equals(type)) {
                oleCardImg.setBackgroundResource(R.drawable.v1k_s);
                viptip_tv.setText("畅享会员");
                viptip_tv.setTextColor(getResources().getColor(R.color.white));
            } else if ("hrtv2".equals(type)) {
                oleCardImg.setBackgroundResource(R.drawable.v2k_s);
                viptip_tv.setText("优享会员");
                viptip_tv.setTextColor(getResources().getColor(R.color.txt_66));
            } else if ("hrtv3".equals(type)) {
                oleCardImg.setBackgroundResource(R.drawable.v3k_s);
                viptip_tv.setText("尊享会员");
                viptip_tv.setTextColor(getResources().getColor(R.color.white));
            }

            tip_tv.setText(userCenter.getNickname());
            tip_tv.setVisibility(View.VISIBLE);
            normal_tip_tv.setVisibility(View.GONE);

            userinfoRelahyout.setVisibility(View.GONE);
            fristLinlayout.setVisibility(View.GONE);
            secondLinlayout.setVisibility(View.GONE);
        }

        //待付款
        non_tip_pay.setBadgeCount(userCenter.getTotalNeedPay() > 0 ? userCenter.getTotalNeedPay() + "" : "");
        //待发货
        non_tip_shipment.setBadgeCount(userCenter.getStocking() > 0 ? userCenter.getStocking() + "" : "");
        //待收货
        non_tip_receive.setBadgeCount(userCenter.getDelivering() > 0 ? userCenter.getDelivering() + "" : "");
        //待评价
        non_tip_evaluated.setBadgeCount("");
        //售后/退款
        non_tip_sale.setBadgeCount(userCenter.getSuccess() > 0 ? userCenter.getSuccess() + "" : "");
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) this.getView().findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new AdAdapter());
    }

    private void getCartCount() {
        ServiceManger.getInstance().getCartCount("", "", new BaseRequestCallback<CartCountResponseData>() {
            @Override
            public void onSuccess(CartCountResponseData data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    String count = data.getRETURN_DATA().getCount();
                    if (userCenter != null) {
                        if ("common".equals(userCenter.getMemberlevel())) {
                            badge_shoping.setBadgeCount("0".equals(count) ? "" : count);
                        } else {
                            oleWalletLinLayout.setBadgeCount("0".equals(count) ? "" : count);
                        }

                    } else {
                        badge_shoping.setBadgeCount("0".equals(count) ? "" : count);
                    }
                }
            }

        });
    }
}