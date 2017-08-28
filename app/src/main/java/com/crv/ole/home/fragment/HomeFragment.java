package com.crv.ole.home.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.crv.ole.R;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.activity.LiveActivity;
import com.crv.ole.home.activity.OleCaptureActivity;
import com.crv.ole.home.adapter.HWAdapter;
import com.crv.ole.home.adapter.SYAdapter;
import com.crv.ole.home.adapter.SpecialListAdapter;
import com.crv.ole.home.model.DataBean;
import com.crv.ole.home.model.HWBean;
import com.crv.ole.information.activity.ArticleListActivity;
import com.crv.ole.information.activity.SpecialDeatail1Activity2;
import com.crv.ole.information.activity.ThematicDetailsActivity;
import com.crv.ole.information.activity.ZzActivity;
import com.crv.ole.information.model.ContentID;
import com.crv.ole.information.model.ListResult;
import com.crv.ole.shopping.activity.GoodsTrialReportActivity;
import com.crv.ole.shopping.activity.HwDetailActivity;
import com.crv.ole.shopping.activity.HwThemeListActivity;
import com.crv.ole.shopping.activity.ProductDetailActivity;
import com.crv.ole.shopping.activity.TrialActiveListActivity;
import com.crv.ole.shopping.ui.ProductListView;
import com.crv.ole.trial.activity.TrialInfoActivity;
import com.crv.ole.trial.activity.TrialReportGoodsListActivity;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.fragment.BaseFragment;
import com.crv.sdk.utils.DisplayUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * //┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //┃　　　┃  神兽保佑
 * //┃　　　┃  代码无BUG！
 * //┃　　　┗━━━┓
 * //┃　　　　　　　┣┓
 * //┃　　　　　　　┏┛
 * //┗┓┓┏━┳┓┏┛
 * // ┃┫┫　┃┫┫
 * // ┗┻┛　┗┻┛
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.fragment_home_scroll)
    NestedScrollView fragment_home_scroll;
    @BindView(R.id.fragment_home_content_layout)
    LinearLayout fragment_home_content_layout;
    @BindView(R.id.fragment_home_live_more_layout)
    RelativeLayout fragment_home_live_more_layout;
    @BindView(R.id.fragment_home_banner_content_layout)
    ConvenientBanner fragmentHomeBannerContentLayout;
    @BindView(R.id.fragment_home_banner_content_layout2)
    ConvenientBanner fragmentHomeBannerContentLayout2;
    @BindView(R.id.fragment_home_scan_iv)
    ImageView fragmentHomeScanIv;
    @BindView(R.id.fragment_home_special_layout)
    LinearLayout fragmentHomeSpecialLayout;
    @BindView(R.id.fragment_home_special_list)
    ProductListView fragmentHomeSpecialList;
    @BindView(R.id.fragment_home_special_more_layout)
    RelativeLayout fragmentHomeSpecialMoreLayout;
    @BindView(R.id.fragment_home_hw_layout)
    LinearLayout fragmentHomeHwLayout;
    @BindView(R.id.fragment_home_hw_list)
    ProductListView fragmentHomeHwList;
    @BindView(R.id.fragment_home_hw_more_layout)
    RelativeLayout fragmentHomeHwMoreLayout;
    @BindView(R.id.fragment_home_sybg_layout)
    LinearLayout fragmentHomeSybgLayout;
    @BindView(R.id.fragment_home_sybg_banner)
    ImageView fragmentHomeSybgBanner;
    @BindView(R.id.fragment_home_sybg_content_layout)
    LinearLayout fragmentHomeSybgContentLayout;
    @BindView(R.id.fragment_home_trial_layout)
    LinearLayout fragmentHomeTrialLayout;
    @BindView(R.id.fragment_home_trial_list)
    ProductListView fragmentHomeTrialList;
    @BindView(R.id.fragment_home_sy_more_layout)
    RelativeLayout fragmentHomeSyMoreLayout;


    Unbinder unbinder;

    //专题列表数据
    List<ListResult.RETURNDATABean.InformationBean> datas = new ArrayList<>();

    //好物列表数据
    private HWAdapter hwAdapter;
    private List<HWBean.RETURNDATABean> RETURN_DATA;

    //试用列表数据
    private List<DataBean> titleList = new ArrayList<>();
    private List<DataBean> bannerList = new ArrayList<>();
    private List<DataBean> trialGoodsList = new ArrayList<>();
    private SYAdapter syAdapter;

    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
        }

        fragmentHomeScanIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RxPermissions(getActivity())
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) { // 在android 6.0之前会默认返回true
                                // 已经获取权限
                                startActivity(new Intent(mContext, OleCaptureActivity.class));
                            } else {
                                // 未获取权限
                            }
                        });
                //startActivity(new Intent(mContext, BarCodeDetailActivity.class));
            }
        });

        fragment_home_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > 0) {
                    fragmentHomeBannerContentLayout.setVisibility(View.INVISIBLE);
                } else {
                    fragmentHomeBannerContentLayout.setVisibility(View.VISIBLE);
                }
            }
        });

//        fragment_home_content_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                int i = fragmentHomeBannerContentLayout.getCurrentItem();
//                if (bannerList.get(i).getLinkTo().contains("杂")) {
//                    intent.setClass(mContext, ZzActivity.class);
//                    intent.putExtra("id", bannerList.get(i).getLinkTo().substring(0, bannerList.get(i).getLinkTo().length() - 1));
//                } else if (bannerList.get(i).getLinkTo().contains("文")) {
//                    intent.setClass(mContext, SpecialDeatail1Activity2.class);
//                    //intent.setClass(mContext, SpecialDetailActivity.class);
//                    // intent.putExtra("id", bannerList.get(i).getLinkTo().substring(0, bannerList.get(i).getLinkTo().length() - 1));
//                } else if (bannerList.get(i).getLinkTo().contains("试")) {
//                    intent.setClass(mContext, TrialInfoActivity.class);
//                    intent.putExtra("activeId", bannerList.get(i).getLinkTo().substring(0, bannerList.get(i).getLinkTo().length() - 1));
//                    intent.putExtra("url", bannerList.get(i).getImageUrl());
//                    intent.putExtra("title", "");
//                } else if (bannerList.get(i).getLinkTo().contains("好")) {
//                    intent.setClass(mContext, HwDetailActivity.class);
//                    intent.putExtra("imgLinkTo", bannerList.get(i).getLinkTo().substring(0, bannerList.get(i).getLinkTo().length() - 1));
//                } else if (bannerList.get(i).getLinkTo().contains("报")) {
//                    //                            intent.setClass(mContext, HwDetailActivity.class);
//                    //                            intent.putExtra("imgLinkTo", data.getLinkTo().substring(0, data.getLinkTo().length() - 1));
//                    intent.setClass(getActivity(), ProductDetailActivity.class);
//                    intent.putExtra("productId", "p_380002");
//                } else if (bannerList.get(i).getLinkTo().contains("直")) {
//                    //                            intent.setClass(mContext, HwDetailActivity.class);
//                    //                            intent.putExtra("imgLinkTo", data.getLinkTo().substring(0, data.getLinkTo().length() - 1));
//                }
//
//                startActivity(intent);
//            }
//        });

        fragmentHomeTrialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TrialInfoActivity.class);
                intent.putExtra("url", titleList.get(position).getImageUrl());
                intent.putExtra("title", titleList.get(position).getName());
                intent.putExtra("activeId", titleList.get(position).getId());
                startActivity(intent);
            }
        });

        fragmentHomeHwList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("好物详细信息是:" + titleList.get(position).toString());
                Intent intent = new Intent(getContext(), HwDetailActivity.class);
                intent.putExtra("imgLinkTo", titleList.get(position).getLinkTo());
                startActivity(intent);
            }
        });

        fragment_home_live_more_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, LiveActivity.class));
            }
        });

        fragmentHomeSpecialMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                EventBus.getDefault().post(HomeClickEnum.HOME_CLICK_MORE_SPECIAL);
                startActivity(new Intent(mContext, ArticleListActivity.class));
            }
        });

        fragmentHomeHwMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, HwThemeListActivity.class));
            }
        });

        fragmentHomeSybgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, TrialReportGoodsListActivity.class));
            }
        });

        fragmentHomeSyMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                EventBus.getDefault().post(HomeClickEnum.HOME_CLICK_MORE_TRIAL);
                startActivity(new Intent(mContext, TrialActiveListActivity.class));
            }
        });

        getData();
        return view;
    }

    private void getData() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("pageId", "oleHome");
        stringMap.put("rappId", "oleMarketTemplate");
        ServiceManger.<String>getInstance().getOleMarketHome(stringMap,
                new BaseRequestCallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("大首页数据：" + data);
                        if (!TextUtils.isEmpty(data)) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (TextUtils.equals(OleConstants.REQUES_SUCCESS,
                                        jsonObject.optString("RETURN_CODE"))) {
                                    JSONObject dataObject = jsonObject.getJSONObject("RETURN_DATA");

                                    //banner数据
                                    bannerList.clear();
                                    for (int i = 0; i < dataObject.optJSONArray("rotationList").length(); i++) {
                                        DataBean dataBean = new DataBean();
                                        JSONObject bannerObject = dataObject.optJSONArray("rotationList").optJSONObject(i);
                                        dataBean.setId(bannerObject.optString("id"));
                                        dataBean.setImageUrl(bannerObject.optString("imgUrl"));
                                        dataBean.setLinkTo(bannerObject.optString("linkTo"));
                                        dataBean.setOpenInNewPage(bannerObject.optString("openInNewPage"));
                                        bannerList.add(dataBean);
                                    }
                                    initBanner();

                                    if (dataObject.optBoolean("isShowArticle")) {
                                        //是否显示文章模块
                                        datas.clear();
                                        for (int i = 0; i < dataObject.optJSONArray("articleList").length(); i++) {
                                            ListResult.RETURNDATABean.InformationBean beans = new ListResult.RETURNDATABean.InformationBean();
                                            JSONObject articleObject = dataObject.optJSONArray("articleList").optJSONObject(i);
                                            //                                            beans.setAuthor(articleObject.optString(""));
                                            beans.setTitle(articleObject.optString("title"));
                                            beans.setFavoriteCount(articleObject.optInt("browse"));
                                            beans.setLikeCount(articleObject.optInt("likes"));
                                            beans.setCoverImg(articleObject.optString("imageUrl"));
                                            beans.setId(articleObject.optString("imgLinkTo"));
                                            beans.setAuthor(articleObject.optString("descriptions"));
                                            beans.setIconUrl(ContentID.SP_TYPE_IMAGES.get(0).getUrl());
                                            datas.add(beans);
                                        }
                                        if (datas != null) {
                                            fragmentHomeSpecialList.setAdapter(new SpecialListAdapter(mContext, datas));
                                            fragmentHomeSpecialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    //                                                    Intent intent = new Intent();
                                                    //                                                    intent.setClass(getActivity(), SpecialDetailActivity.class);
                                                    //                                                    intent.putExtra("id", datas.get(i).getId());
                                                    //                                                    startActivity(intent);
                                                    ArrayList<CharSequence> list = new ArrayList<>();
                                                    for (ListResult.RETURNDATABean.InformationBean informationBean : datas) {
                                                        list.add(informationBean.getId());
                                                    }
                                                    Intent intent = new Intent(mContext, ThematicDetailsActivity.class);
                                                    intent.putCharSequenceArrayListExtra(OleConstants.BundleConstant.ARG_PARAMS_0, list);
                                                    intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_1, i);
                                                    startActivity(intent);
                                                }
                                            });

                                            fragmentHomeSpecialLayout.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    if (dataObject.optBoolean("isShowTryOut")) {
                                        //是否显示试用模块
                                        //显示使用banner图
                                        fragmentHomeSybgLayout.setVisibility(View.VISIBLE);
                                        fragmentHomeTrialLayout.setVisibility(View.VISIBLE);
                                        LoadImageUtil.getInstance().loadImage(fragmentHomeSybgBanner,
                                                dataObject.optJSONObject("tryOutHeadImg").optString("imgUrl"));

                                        //获取试用商品数据
                                        trialGoodsList.clear();
                                        for (int i = 0; i < dataObject.optJSONArray("tryOutProduct").length(); i++) {
                                            DataBean dataBean = new DataBean();
                                            JSONObject trialObject = dataObject.optJSONArray("tryOutProduct").optJSONObject(i);
                                            dataBean.setId(trialObject.optString("id"));
                                            dataBean.setImageUrl(trialObject.optString("imgUrl"));
                                            dataBean.setLinkTo(trialObject.optString("linkTo"));
                                            dataBean.setOpenInNewPage(trialObject.optString("openInNewPage"));
                                            trialGoodsList.add(dataBean);
                                        }
                                        showTrialGoods();

                                        titleList.clear();
                                        for (int i = 0; i < dataObject.optJSONArray("tryOutList").length(); i++) {
                                            if (i == 0) {
                                                DataBean beans = new DataBean();
                                                beans.setUnitName("");
                                                beans.setName("");
                                                titleList.add(beans);
                                            }

                                            DataBean dataBean = new DataBean();
                                            JSONObject tryObject = dataObject.optJSONArray("tryOutList").optJSONObject(i);
                                            dataBean.setUnitName("");
                                            dataBean.setName(tryObject.optString("parh"));
                                            dataBean.setImageUrl(tryObject.optString("imgUrl"));
                                            dataBean.setParp(tryObject.optString("parp"));
                                            dataBean.setId(tryObject.optString("imgLinkTo"));
                                            titleList.add(dataBean);
                                        }
                                        syAdapter = new SYAdapter(getActivity(), titleList);
                                        fragmentHomeTrialList.setAdapter(syAdapter);

                                    }
                                    if (dataObject.optBoolean("isShowGoodProduct")) {
                                        //是否显示好物模块
                                        Gson hwGson = new Gson();
                                        Type hwType = new TypeToken<List<HWBean.RETURNDATABean>>() {
                                        }.getType();
                                        RETURN_DATA = hwGson.fromJson(dataObject.optString("goodProductList"), hwType);
                                        hwAdapter = new HWAdapter(getActivity(), RETURN_DATA);
                                        fragmentHomeHwList.setAdapter(hwAdapter);
                                        fragmentHomeHwLayout.setVisibility(View.VISIBLE);
                                    }

                                    isLoad = true;
                                } else {

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });
    }

    /**
     * 显示试用商品
     */
    private void showTrialGoods() {
        if (trialGoodsList.size() > 0) {
            for (DataBean data : trialGoodsList) {
                ImageView imageView = new ImageView(mContext);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, GoodsTrialReportActivity.class)
                                .putExtra("productId", data.getLinkTo())
                                .putExtra("content", data.getDescription()));
                    }
                });
                LoadImageUtil.getInstance().loadImage(imageView, data.getImageUrl());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        DisplayUtil.dip2px(mContext, 150),
                        DisplayUtil.dip2px(mContext, 150));
                layoutParams.setMargins(5, 0, 5, 0);
                fragmentHomeSybgContentLayout.addView(imageView, layoutParams);
            }
        }
    }

    /**
     * 初始化轮播控件
     */
    private void initBanner() {
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        fragmentHomeBannerContentLayout.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, bannerList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_sylb_normal, R.drawable.ic_sylb_pressed})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
        fragmentHomeBannerContentLayout.setManualPageable(true);//设置不能手动影响  默认是手指触摸 轮播图不能翻页
        fragmentHomeBannerContentLayout.setCanLoop(true);  //默认true,设置轮播图是否轮播

        fragmentHomeBannerContentLayout2.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, bannerList)
                .setPageIndicator(new int[]{R.drawable.ic_sylb_normal, R.drawable.ic_sylb_pressed})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        fragmentHomeBannerContentLayout2.setManualPageable(true);
        fragmentHomeBannerContentLayout2.setCanLoop(true);

        fragmentHomeBannerContentLayout.setOnItemClickListener(onBannerClickListener);
        fragmentHomeBannerContentLayout2.setOnItemClickListener(onBannerClickListener);
    }

    /**
     * banner的点击事件监听
     */
    private OnItemClickListener onBannerClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int i) {
            //                if (i == 0) {
            //                    startActivity(new Intent(getActivity(), PreSaleActivity.class));
            //                } else
            //                    startActivity(new Intent(getActivity(), ProductDetailActivity.class)
            //                            .putExtra("productId", "p_380002"));

            Intent intent = new Intent();
            if (bannerList.get(i).getLinkTo().contains("杂")) {
                intent.setClass(mContext, ZzActivity.class);
                intent.putExtra("id", bannerList.get(i).getLinkTo().substring(0, bannerList.get(i).getLinkTo().length() - 1));
            } else if (bannerList.get(i).getLinkTo().contains("文")) {
                intent.setClass(mContext, SpecialDeatail1Activity2.class);
                //intent.setClass(mContext, SpecialDetailActivity.class);
               // intent.putExtra("id", bannerList.get(i).getLinkTo().substring(0, bannerList.get(i).getLinkTo().length() - 1));
            } else if (bannerList.get(i).getLinkTo().contains("试")) {
                intent.setClass(mContext, TrialInfoActivity.class);
                intent.putExtra("activeId", bannerList.get(i).getLinkTo().substring(0, bannerList.get(i).getLinkTo().length() - 1));
                intent.putExtra("url", bannerList.get(i).getImageUrl());
                intent.putExtra("title", "");
            } else if (bannerList.get(i).getLinkTo().contains("好")) {
                intent.setClass(mContext, HwDetailActivity.class);
                intent.putExtra("imgLinkTo", bannerList.get(i).getLinkTo().substring(0, bannerList.get(i).getLinkTo().length() - 1));
            } else if (bannerList.get(i).getLinkTo().contains("报")) {
                //                            intent.setClass(mContext, HwDetailActivity.class);
                //                            intent.putExtra("imgLinkTo", data.getLinkTo().substring(0, data.getLinkTo().length() - 1));
                intent.setClass(getActivity(), ProductDetailActivity.class);
                intent.putExtra("productId", "p_380002");
            } else if (bannerList.get(i).getLinkTo().contains("直")) {
                //                            intent.setClass(mContext, HwDetailActivity.class);
                //                            intent.putExtra("imgLinkTo", data.getLinkTo().substring(0, data.getLinkTo().length() - 1));
            }

            startActivity(intent);
        }
    };

    public class LocalImageHolderView implements Holder<DataBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, DataBean data) {
            LoadImageUtil.getInstance().loadImage(imageView, data.getImageUrl());
        }
    }

    @Override
    public void showThisPage() {
        super.showThisPage();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("onResume");
        //设置开始轮播以及轮播时间  建议在onResume方法中设置
        fragmentHomeBannerContentLayout.startTurning(3000);
        fragmentHomeBannerContentLayout2.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("onPause");
        //停止轮播  建议在onPause方法中设置
        fragmentHomeBannerContentLayout.stopTurning();
        fragmentHomeBannerContentLayout2.stopTurning();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
