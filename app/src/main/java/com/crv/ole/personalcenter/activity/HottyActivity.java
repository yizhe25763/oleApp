package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.personalcenter.model.Hotty;
import com.crv.sdk.utils.TextUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HottyActivity extends BaseActivity {

    private final static String HOTTYS = "hotty";
    private ArrayList<Hotty> hottys;

    @BindView(R.id.confirm_bt)
    Button confirm_bt;

    @BindView(R.id.hotty_gv)
    GridView hotty_gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotty);
        initView();
    }

    private void initView() {
        initTitleViews();
        ButterKnife.bind(this);
        setBarTitle("兴趣爱好");
        initData();
    }

    public void initData() {
        hottys = new ArrayList<>();
        Hotty hotty1 = new Hotty("健康养生", false, "001");
        Hotty hotty2 = new Hotty("美容护理", false, "002");
        Hotty hotty3 = new Hotty("户外运动", false, "003");
        Hotty hotty4 = new Hotty("品酒", false, "004");
        Hotty hotty5 = new Hotty("插花", false, "005");
        Hotty hotty6 = new Hotty("音乐", false, "006");
        Hotty hotty7 = new Hotty("公益", false, "007");
        Hotty hotty8 = new Hotty("美食", false, "008");
        Hotty hotty9 = new Hotty("艺术", false, "009");
        Hotty hotty10 = new Hotty("亲子", false, "010");
        Hotty hotty11 = new Hotty("理财", false, "011");
        Hotty hotty12 = new Hotty("摄影", false, "012");
        hottys.add(hotty1);
        hottys.add(hotty2);
        hottys.add(hotty3);
        hottys.add(hotty4);
        hottys.add(hotty5);
        hottys.add(hotty6);
        hottys.add(hotty7);
        hottys.add(hotty8);
        hottys.add(hotty9);
        hottys.add(hotty10);
        hottys.add(hotty11);
        hottys.add(hotty12);
        String[] love = {};

        String loveStr = getIntent().getStringExtra(HOTTYS);
        if (!TextUtil.isEmpty(loveStr)) {
            love = loveStr.split(",");
        }

        for (Hotty hotty : hottys) {
            for (int i = 0; i < love.length; i++) {
                if (love[i].equals(hotty.getNo())) {
                    hotty.setLove(true);
                    break;
                }
            }
        }

        boolean isSelected = false;
        for (Hotty t : hottys) {
            if (t.isLove()) {
                isSelected = true;
                break;
            }
        }
        confirm_bt.setEnabled(isSelected);

        hotty_gv.setAdapter(new HottyGridViewAdapter(hottys));
    }

    @OnClick({R.id.title_back_layout, R.id.confirm_bt})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.title_back_layout) {
            finish();
        } else if (id == R.id.confirm_bt) {
            Intent intent = getIntent();
            String loveStr = "";
            for (Hotty hotty : hottys) {
                if (hotty.isLove()) {
                    loveStr += hotty.getNo() + ",";
                }
            }
            intent.putExtra(HOTTYS, loveStr);
            setResult(0, intent);
            finish();
        }
    }

    class HottyGridViewAdapter extends BaseAdapter {

        private ArrayList<Hotty> hottys;

        HottyGridViewAdapter(ArrayList<Hotty> hottys) {

            this.hottys = hottys;
        }

        @Override
        public int getCount() {
            return hottys == null ? 0 : hottys.size();
        }

        @Override
        public Object getItem(int position) {
            return hottys.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CheckBox cb;
            Hotty hotty = hottys.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotty_gridview_item, null);
                cb = (CheckBox) convertView.findViewById(R.id.hotty_cb);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        hotty.setLove(isChecked);
                        boolean isSelected = false;
                        for (Hotty t : hottys) {
                            if (t.isLove()) {
                                isSelected = true;
                                break;
                            }
                        }
                        confirm_bt.setEnabled(isSelected);
                    }
                });
                convertView.setTag(cb);
            } else {
                cb = (CheckBox) convertView.getTag();
            }
            cb.setText(hotty.getName());
            cb.setChecked(hotty.isLove());
            return convertView;
        }
    }
}
