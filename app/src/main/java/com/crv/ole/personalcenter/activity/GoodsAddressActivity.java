package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.personalcenter.adapter.SlideAdapter;
import com.crv.ole.personalcenter.model.AddressListData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.slidelistview.SlideListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收货地址
 */
public class GoodsAddressActivity extends BaseActivity {

    @BindView(R.id.list_view)
    public SlideListView mSlideListView;

    @BindView(R.id.non_tv)
    public TextView non_tv;

    private SlideAdapter mAdapter;
    private List<AddressListData.Address> addressList;
    public static final int REQUESETCODE_ADD = 0X11;

    private String fromPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_address);
        fromPage = getIntent().getStringExtra("from_page");
        initView();
        fetchAddress();
    }

    private void initView() {
        initTitleViews();
        ButterKnife.bind(this);
        setBarTitle("收货地址");
        addressList = new ArrayList<>();
        mAdapter = new SlideAdapter(this, addressList);
        mSlideListView.setAdapter(mAdapter);

        mSlideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (TextUtils.equals(fromPage, "confirm_order")) {
//                    setResult(100, new Intent()
//                            .putExtra("address_data", addressList.get(i)));
//                    finish();
                    addressList.get(i).setDefault(true);
                    addressList.get(i).setAddressId(addressList.get(i).getId());
                    setDefaultAddress(addressList.get(i));
                }
            }
        });
    }

    private void setDefaultAddress(AddressListData.Address address) {
        ServiceManger.getInstance().editAddress(address, new BaseRequestCallback<BaseResponseData>() {
            @Override
            public void onStart() {
                showProgressDialog("加载中...", null);
            }
            @Override
            public void onSuccess(BaseResponseData response) {
                dismissProgressDialog();
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    String json = new Gson().toJson(response);
                    Log.i("修改地址结果:" + json);
                    setResult(100, new Intent()
                            .putExtra("address_data", address));
                    GoodsAddressActivity.this.finish();
                } else {
                    ToastUtil.showToast(response.getRETURN_DESC());
                }
            }
            @Override
            public void onEnd() {
                dismissProgressDialog();
            }
            @Override
            public void onFailed(String msg) {
                dismissProgressDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESETCODE_ADD && resultCode == RESULT_OK) {
            fetchAddress();
        }
    }

    @OnClick({R.id.title_back_layout, R.id.add_btn})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.title_back_layout) {
            finish();
        } else if (id == R.id.add_btn) {
            Intent intent = new Intent(this, EditGoodsAddressActivity.class);
            intent.putExtra("add", true);
            startActivityForResultWithAnim(intent, REQUESETCODE_ADD);
        }
    }

    /**
     * 获取地址列表
     */
    private void fetchAddress() {
        ServiceManger.getInstance().getUserInfoAddress(new Object(), new BaseRequestCallback<AddressListData>() {
            @Override
            public void onStart() {
                showProgressDialog("请求中...", null);
            }

            @Override
            public void onSuccess(AddressListData response) {
                Log.i("地址列表结果是:" + new Gson().toJson(response));
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    addressList.clear();
                    addressList.addAll(response.getRETURN_DATA());
                    if (addressList.size()==0){
                        non_tv.setVisibility(View.VISIBLE);
                        mSlideListView.setVisibility(View.INVISIBLE);
                        return;
                    }else{
                        non_tv.setVisibility(View.GONE);
                        mSlideListView.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                    String json = new Gson().toJson(response);
                    Log.i("获取到的地址信息是:" + json);
                } else {
                    ToastUtil.showToast(response.getRETURN_DESC());
                }
            }

            @Override
            public void onEnd() {
                Log.i("请求结束");
                dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                Log.i("请求出错:" + msg);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 删除地址
     *
     * @param address
     */
    public void deleteAddress(AddressListData.Address address) {

        if (address != null) {
            address.setAddressId(address.getId());
        } else {
            return;
        }

        ServiceManger.getInstance().deleteAddress(address, new BaseRequestCallback<BaseResponseData>() {

            @Override
            public void onStart() {
                showProgressDialog("删除中...", null);
            }
            @Override
            public void onSuccess(BaseResponseData response) {
                dismissProgressDialog();
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    addressList.remove(address);
                    if (addressList.size()==0){
                        non_tv.setVisibility(View.VISIBLE);
                        mSlideListView.setVisibility(View.INVISIBLE);
                        return;
                    }else{
                        non_tv.setVisibility(View.GONE);
                        mSlideListView.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showToast(response.getRETURN_DESC());
                }

            }
            @Override
            public void onFailed(String msg) {
                dismissProgressDialog();
            }
        });
    }
}
