package com.crv.ole.personalcenter.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.model.RegionsBean;
import com.crv.ole.home.model.RegionsModel;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.view.CustomDiaglog;
import com.crv.sdk.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectAddressActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.rgArea)
    public RadioGroup rgArea;

    @BindView(R.id.rbProvince)
    public RadioButton rbProvince;

    @BindView(R.id.rbCity)
    public RadioButton rbCity;

    @BindView(R.id.rbArea)
    public RadioButton rbArea;

    @BindView(R.id.listView)
    public ListView listView;

    private View cartTabDom;

    private CityListAdapter mCityListAdapter;

    private List<RegionsBean> list = new ArrayList<>();

    private RegionsBean province, city, district;

    public int mCurrentTabIndex = 0;
    public int screenWidth;
    public int domWidth;
    public float domSpaceWidth;
    public float domOldx;

    public final static int EDIT_AREA = 0x10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        initView();
    }

    private void initView() {
        initTitleViews();
        ButterKnife.bind(this);
        setBarTitle("选择地址");

        cartTabDom = findViewById(R.id.cart_tab_dom);
        rbCity.setVisibility(View.INVISIBLE);
        rbArea.setVisibility(View.INVISIBLE);

        rbProvince.setOnCheckedChangeListener(this);
        rbCity.setOnCheckedChangeListener(this);
        rbArea.setOnCheckedChangeListener(this);

        screenWidth = BaseApplication.getDeviceWidth();
        domWidth = DisplayUtil.dip2px(this, 100);
        domSpaceWidth = (screenWidth / 3 - domWidth) / 2;
        domOldx = domSpaceWidth;
        cartTabDom.setX(getTabDomToX(mCurrentTabIndex));

        list.addAll(RegionsModel.getAllProvince());
        mCityListAdapter = new CityListAdapter(this, list);
        listView.setAdapter(mCityListAdapter);

    }

    /**
     * 显示同步省市数据对话框
     */
    private void showSynaRgionDataDialog() {

        showAlertDialog("是否重新同步数据?", "确定", "取消", new CustomDiaglog.OnConfimClickListerner() {
            @Override
            public void onCanleClick() {
                finish();
            }
            @Override
            public void OnConfimClick() {
                RegionsModel.syncData(mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY), new RegionsModel.RegionSynaCallBack() {

                    @Override
                    public void onSynaSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mCityListAdapter.dataChange(RegionsModel.getAllProvince());
                            }
                        });
                    }

                    @Override
                    public void onSynaFail(String error) {
                        ToastUtil.showToast("数据同步机制出错了,请稍候再试!");
                    }
                });
            }
        });
    }


    /**
     * 获取tabdom目标x坐标
     *
     * @param currIndex
     * @return
     */
    private float getTabDomToX(int currIndex) {
        return (currIndex + 1) * 2 * domSpaceWidth - domSpaceWidth + currIndex
                * domWidth;
    }

    private void lineScrollAnim(int position) {
        mCurrentTabIndex = position;
        float toX = getTabDomToX(mCurrentTabIndex);
        float curTranslationX = cartTabDom.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(cartTabDom,
                "translationX", curTranslationX, domOldx, toX);
        animator.setDuration(300);
        animator.start();
        domOldx = toX;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            int id = buttonView.getId();
            if (id == R.id.rbProvince) {
                lineScrollAnim(0);
                List<RegionsBean> temp = new ArrayList<>();
                temp = RegionsModel.getAllProvince();
                mCityListAdapter.dataChange(temp);
                listView.setSelection(0);
            } else if (id == R.id.rbCity) {
                lineScrollAnim(1);
                List temp = RegionsModel.getCities(province.getId());
                mCityListAdapter.dataChange(temp);
                rbCity.setVisibility(View.VISIBLE);
                listView.setSelection(0);
            } else if (id == R.id.rbArea) {
                lineScrollAnim(2);
                List<RegionsBean> temp = RegionsModel.getDistrictes(city.getId());
                mCityListAdapter.dataChange(temp);
                rbArea.setVisibility(View.VISIBLE);
                listView.setSelection(0);
            }
        }
    }

    @OnClick({R.id.title_back_layout})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.title_back_layout) {
            finish();
        }
    }


    class CityListAdapter extends BaseAdapter {
        private Context context;
        private List<RegionsBean> list = new ArrayList<>();

        public CityListAdapter(Context context, List<RegionsBean> areasList) {
            this.context = context;
            this.list = areasList;
        }

        public void dataChange(List<RegionsBean> list) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return list != null ? list.size() : 0;
        }

        @Override
        public RegionsBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.ole_select_address_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final RegionsBean cityInfoBean = list.get(position);
            holder.tvTitle.setText(cityInfoBean.getName());
            holder.root.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   //选择省份
                                                   if ("province".equals(cityInfoBean.getType())) {
                                                       province = cityInfoBean;
                                                       List<RegionsBean> list = RegionsModel.getCities(province.getId());
                                                       if (list != null && list.size() > 0) {
                                                           rbCity.setChecked(true);
                                                           rbCity.setText("请选择");
                                                           rbArea.setText("");
                                                           rbProvince.setText(cityInfoBean.getName());
                                                       } else {
                                                           Intent intent = getIntent();
                                                           intent.putExtra("area", province.getName());
                                                           intent.putExtra("code", province.getId());
                                                           setResult(EDIT_AREA, intent);
                                                           finish();
                                                       }

                                                   }
                                                   //选择城市
                                                   if ("city".equals(cityInfoBean.getType())) {
                                                       city = cityInfoBean;

                                                       List<RegionsBean> list = RegionsModel.getDistrictes(city.getId());
                                                       if (list != null && list.size() > 0) {
                                                           rbCity.setText(cityInfoBean.getName());
                                                           city = cityInfoBean;
                                                           rbArea.setChecked(true);
                                                           rbArea.setText("请选择");
                                                       } else {
                                                           Intent intent = getIntent();
                                                           intent.putExtra("area", province.getName() + city.getName());
                                                           intent.putExtra("code", province.getId() + "/" + city.getId());
                                                           setResult(EDIT_AREA, intent);
                                                           finish();
                                                       }

                                                   }
                                                   //选择街道
                                                   if ("district".equals(cityInfoBean.getType())) {
                                                       district = cityInfoBean;
                                                       rbArea.setText(cityInfoBean.getName());
                                                       Intent intent = getIntent();
                                                       intent.putExtra("area", province.getName() + city.getName() + district.getName());
                                                       intent.putExtra("code", province.getId() + "/" + city.getId() + "/" + district.getId());
                                                       setResult(EDIT_AREA, intent);
                                                       finish();
                                                   }
                                               }
                                           }

            );
            return convertView;
        }


        public class ViewHolder {
            public final TextView tvTitle;
            public final View root;

            public ViewHolder(View root) {
                tvTitle = (TextView) root.findViewById(R.id.tvTitle);
                this.root = root;
            }
        }
    }

}



