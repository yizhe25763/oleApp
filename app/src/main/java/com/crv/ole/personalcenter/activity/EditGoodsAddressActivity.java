package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.model.RegionsBean;
import com.crv.ole.home.model.RegionsModel;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.personalcenter.fragment.AddressBottomDialogFragment;
import com.crv.ole.personalcenter.model.AddressListData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.StringUtils;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditGoodsAddressActivity extends BaseActivity {

    private AddressListData.Address address;

    @BindView(R.id.name_et)
    public EditText name_et;

    @BindView(R.id.mobile_et)
    public EditText mobile_et;

    @BindView(R.id.area_tv)
    public TextView area_tv;

    @BindView(R.id.detail_et)
    public EditText detail_et;

    @BindView(R.id.select_cb)
    public CheckBox select_cb;

    @BindView(R.id.save_btn)
    Button save_btn;

    @BindView(R.id.address_tip_tv)
    TextView address_tip_tv;

    public final int EDIT_AREA = 0x10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goods_address);
        initView();
        initLister();
    }

    private void initView() {
        initTitleViews();
        ButterKnife.bind(this);
        address = (AddressListData.Address) getIntent().getSerializableExtra("address");
        if (address != null) {
            Log.i("地址是:" + address.toString());
            setBarTitle("编辑地址");
            name_et.setText(address.getUserName());
            if (!TextUtil.isEmpty(address.getUserName())) {
                name_et.setSelection(address.getUserName().length());
            }
            if (!StringUtils.isNullOrEmpty(address.getAddress())){
                address_tip_tv.setVisibility(View.GONE);
            }else{
                address_tip_tv.setVisibility(View.VISIBLE);
            }
            mobile_et.setText(address.getMobile());
            Map<String, RegionsBean> map = RegionsModel.getProviceAndCityAndDistrictbyId(address.getRegionId());
            RegionsBean province = map.get("province");
            RegionsBean city = map.get("city");
            RegionsBean district = map.get("district");
            StringBuilder addRess = new StringBuilder()
                    .append(province != null ? province.getName() : "")
                    .append(city != null ? city.getName() : "")
                    .append(district != null ? district.getName() : "");
            area_tv.setText(addRess);
            detail_et.setText(address.getAddress());
            select_cb.setChecked(address.isDefault());
        } else {
            setBarTitle("新建地址");
            address = new AddressListData().new Address();
        }
    }

    private void initLister() {

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = name_et.getText().toString().trim();
                String mobile = mobile_et.getText().toString().trim();
                String address = detail_et.getText().toString().trim();
                String area = area_tv.getText().toString().trim();
                if (TextUtil.isEmpty(address)){
                    address_tip_tv.setVisibility(View.VISIBLE);
                }else{
                    address_tip_tv.setVisibility(View.GONE);
                }
                if (StringUtils.isMobile(mobile) && !TextUtil.isEmpty(name) && !TextUtil.isEmpty(address) && !TextUtil.isEmpty(area)) {
                    save_btn.setEnabled(true);
                } else {
                    save_btn.setEnabled(false);
                }

            }
        };
        name_et.addTextChangedListener(watcher);
        mobile_et.addTextChangedListener(watcher);
        area_tv.addTextChangedListener(watcher);
        detail_et.addTextChangedListener(watcher);

        String area = area_tv.getText().toString();

        if (StringUtils.isMobile(address.getMobile()) && !TextUtil.isEmpty(address.getUserName()) && !TextUtil.isEmpty(address.getAddress()) && !TextUtil.isEmpty(area)) {
            save_btn.setEnabled(true);
        } else {
            save_btn.setEnabled(false);
        }

    }

    @OnClick({R.id.title_back_layout, R.id.save_btn, R.id.area_tv})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.title_back_layout) {
            finish();
        } else if (id == R.id.save_btn) {
            saveAddress();
        } else if (id == R.id.area_tv) {
            showRegionDialog();
        }
    }
    private void saveAddress() {
        address.setDefault(select_cb.isChecked());
        address.setUserName(StringUtils.getStringNoBlank(name_et.getText().toString().trim()));
        address.setPhone(StringUtils.getStringNoBlank(mobile_et.getText().toString().trim()));
        address.setMobile(StringUtils.getStringNoBlank(mobile_et.getText().toString().trim()));
        address.setAddress(StringUtils.getStringNoBlank(detail_et.getText().toString().trim()));
        address.setAddressId(address.getId());
        submitAddress();
    }

    private void submitAddress() {
        ServiceManger.getInstance().editAddress(address, new BaseRequestCallback<BaseResponseData>() {
            @Override
            public void onStart() {
                showProgressDialog("更新中...", null);
            }
            @Override
            public void onSuccess(BaseResponseData response) {
                dismissProgressDialog();
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    String json = new Gson().toJson(response);
                    Log.i("修改地址结果:" + json);
                    setResult(RESULT_OK);
                    EditGoodsAddressActivity.this.finish();
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
                ToastUtil.showToast(msg);
            }
        });
    }
    /**
     * 区域选择
     */
    private void showRegionDialog() {
        AddressBottomDialogFragment.showDialog(this, address.getRegionId(), new AddressBottomDialogFragment.OnConfirmClickListener() {
            @Override
            public void onClick(Map<String, String> map) {
                String area = map.get("area");
                String code = map.get("code");
                area_tv.setText(area);
                Log.i("code:" + code + "area:" + area);
                if (!TextUtils.isEmpty(code)) {
                    String[] result = code.split("/");
                    int index = result.length - 1;
                    if (index >= 0) {
                        address.setRegionId(result[index]);
                    }
                }
            }
        });
    }

}
