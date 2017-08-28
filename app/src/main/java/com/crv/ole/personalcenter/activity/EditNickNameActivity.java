package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.sdk.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNickNameActivity extends BaseActivity {


    @BindView(R.id.nickname_et)
    EditText nickName_et;

    @BindView(R.id.delete_bt)
    Button delete_bt;

    private final static String NICK_NAME = "nickname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nick_name);
        initView();
    }

    private void initView() {
        initTitleViews();
        ButterKnife.bind(this);
        setBarTitle("修改昵称");
        title_iv_1.setVisibility(View.VISIBLE);
        title_iv_1.setText("确定");
        String nickname = getIntent().getStringExtra("nickname");
        nickName_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = StringUtils.getStringNoBlank(s.toString());
                delete_bt.setVisibility(result.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                title_iv_1.setEnabled(ToolUtils.getWordCount(result) >= 4);
            }
        });
        if (!TextUtils.isEmpty(nickname)) {
            nickName_et.setText(nickname);
            nickName_et.setSelection(nickname.length());
        }
    }

    @OnClick({R.id.title_back_layout, R.id.title_iv_1, R.id.delete_bt})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.title_back_layout) {
            finish();
        } else if (id == R.id.title_iv_1) {
            String result = StringUtils.getStringNoBlank(nickName_et.getText().toString());
            if (ToolUtils.getWordCount(result) < 4) {
                return;
            } else if (ToolUtils.getWordCount(result) > 20) {
                ToastUtil.showToast("昵称的长度超过限制");
                return;
            }
            Intent intent = getIntent();
            intent.putExtra(NICK_NAME, result);
            setResult(0, intent);
            finish();

        } else if (id == R.id.delete_bt) {
            nickName_et.setText("");
        }
    }
}
