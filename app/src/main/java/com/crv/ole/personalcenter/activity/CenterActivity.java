package com.crv.ole.personalcenter.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.model.ImageListData;
import com.crv.ole.personalcenter.model.SubmitUserInfoResponse;
import com.crv.ole.personalcenter.model.UserInfoResultBean;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.fileupload.UploadUtils;
import com.crv.sdk.timeseletor.TimeSelector;
import com.crv.sdk.utils.StringUtils;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vondear.rxtools.view.dialog.RxDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.crv.ole.utils.fileupload.UploadUtils.TACK_PIC_PATH;

public class CenterActivity extends BaseActivity {

    private final int EDIT_NICKNAME = 0X1001;
    private final int EDIT_MOBILE = 0X1002;
    private final int EDIT_ADDRESS = 0X1003;
    private final int EDIT_HOTTY = 0X1004;
    private final int EDIT_EMAIL = 0X1005;

    private final String NICK_NAME = "nickname";
    private final String HOTTYS = "hotty";
    private final String EMAIL = "email";
    private final String TITLE = "title";
    private final String PHOTO_PATH = OleConstants.TMP_PATH + File.separator + "temp.png";


    @BindView(R.id.avatar_iv)
    ImageView avatar_iv;

    @BindView(R.id.nickname)
    TextView nickName_tv;

    @BindView(R.id.sex)
    TextView sex_tv;

    @BindView(R.id.brithday)
    TextView birthday_tv;

    @BindView(R.id.tellphone)
    TextView mobile_tv;

    @BindView(R.id.hotty_tv)
    TextView hotty_tv;

    @BindView(R.id.email_tv)
    TextView email_tv;

    UserInfoResultBean.UserInfo userInfo = new UserInfoResultBean().new UserInfo();

    //选择头像
    private RxDialog selectIconDialog;

    //选择性别
    private RxDialog selectSexdialog;

    //选择生日
    private TimeSelector birthdaySelector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        initTitleViews();
        ButterKnife.bind(this);
        setBarTitle("个人资料");
        getUserInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_NICKNAME) {
            if (data != null) {
                Log.i("修改昵称来的");
                if (!TextUtils.isEmpty(data.getStringExtra(NICK_NAME))) {
                    userInfo.setNickname(data.getStringExtra(NICK_NAME));

                    submitUserInfo();
                }
            }

        } else if (requestCode == EDIT_MOBILE) {
            getUserInfo();

        } else if (requestCode == EDIT_ADDRESS) {

            getUserInfo();
        } else if (requestCode == EDIT_HOTTY) {
            if (data != null) {
                userInfo.setHotty(data.getStringExtra(HOTTYS));
                submitUserInfo();
            }

        } else if (requestCode == UploadUtils.TAKE_PICTURE) {
            Log.i("图片路径是:" + PHOTO_PATH);
            File file = new File(PHOTO_PATH);

            if (file.exists()) {
                uploadImage(new File(PHOTO_PATH));
            }
        } else if (requestCode == UploadUtils.CHOOSE_PICTURE) {
            if (data != null) {
                Log.i("选择图库回调来了");
                Uri uri = data.getData();
                try {
                    int sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
                    if (sdkVersion >= 19) {
                        String path = UploadUtils.getPath_above19(this, uri);
                        Log.i("图片路径是:" + path);
                        File file = new File(path);
                        if (file.exists()) {
                            uploadImage(file);
                        }

                    } else {
                        String path = UploadUtils.getFilePath_below19(getContentResolver(), uri);
                        // 上传图片
                        Log.i("图片路径是:" + path);
                        File file = new File(path);
                        if (file.exists()) {
                            uploadImage(file);
                        }
                    }

                } catch (Exception e) {
                    Log.e(e.getMessage());
                }
            }

        }
        setUpUserInfo();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 监听点击
     *
     * @param view
     */
    @OnClick({R.id.title_back_layout, R.id.avatar_iv, R.id.nickname_rellayout, R.id.gender_rellayout, R.id.brithday_rellayout, R.id.tellphone_rellayout, R.id.bindemail_rellayout, R.id.address_rellayout, R.id.interest_rellayout})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.title_back_layout) {
            finish();
        } else if (id == R.id.avatar_iv) {

            showIconDialog();
        } else if (id == R.id.nickname_rellayout) {
            Intent intent = new Intent(this, EditNickNameActivity.class);
            intent.putExtra(NICK_NAME, userInfo.getNickname());
            startActivityForResultWithAnim(intent, EDIT_NICKNAME);
        } else if (id == R.id.gender_rellayout) {
            showSexDialog();
        } else if (id == R.id.brithday_rellayout) {
            showBirthdayDialog();
        } else if (id == R.id.bindemail_rellayout) {
            Intent intent = new Intent(this, BindMessageActivity.class);
            intent.putExtra(EMAIL, userInfo.getEmail());
            intent.putExtra(TITLE, "绑定邮箱");
            intent.putExtra("type", "email");
            startActivityForResultWithAnim(intent, EDIT_EMAIL);

        } else if (id == R.id.tellphone_rellayout) {
            Log.i("电话");
            if (StringUtils.isMobile(userInfo.getMobile())) {
                ToastUtil.showToast("手机已绑定");
                return;
            }
            Intent intent = new Intent(this, BindMessageActivity.class);
            intent.putExtra(TITLE, "绑定手机");
            intent.putExtra("type", "mobile");
            startActivityForResultWithAnim(intent, EDIT_MOBILE);
        } else if (id == R.id.address_rellayout) {
            Log.i("地址");
            Intent intent = new Intent(this, GoodsAddressActivity.class);
            startActivityWithAnim(intent);

        } else if (id == R.id.interest_rellayout) {
            Intent intent = new Intent(this, HottyActivity.class);
            intent.putExtra(HOTTYS, userInfo.getHotty());
            startActivityForResultWithAnim(intent, EDIT_HOTTY);
            Log.i("兴趣");
        }
    }

    /**
     * 获取个人资料
     */
    public void getUserInfo() {
        ServiceManger.getInstance().getUserInfo(new HashMap<>(), new BaseRequestCallback<UserInfoResultBean>() {
            @Override
            public void onStart() {
                showProgressDialog("加载中...", null);
            }

            @Override
            public void onSuccess(UserInfoResultBean response) {
                Log.e("用户个人资料获取：" + new Gson().toJson(response));
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    userInfo = response.getRETURN_DATA();
                    setUpUserInfo();

                } else {
                    ToastUtil.showToast("获取用户个人资料失败");
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
     * 设置个人信息
     */
    private void setUpUserInfo() {
        LoadImageUtil.getInstance().loadIconImage(avatar_iv, userInfo.getUserimage(), true);
        nickName_tv.setText(userInfo.getNickname());
        mobile_tv.setText(userInfo.getMobile());
        email_tv.setText(userInfo.getEmail());
        setSexTv();
        birthday_tv.setText(userInfo.getBirthday());
    }

    /**
     * 提交个人信息
     */

    private void submitUserInfo() {
        ServiceManger.getInstance().submitUserInfo(userInfo, new BaseRequestCallback<SubmitUserInfoResponse>() {
            @Override
            public void onStart() {
                showProgressDialog("更新中...", null);
            }

            @Override
            public void onSuccess(SubmitUserInfoResponse response) {
                dismissProgressDialog();
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    String json = new Gson().toJson(response);
                    Log.i("更新个人资料结果:" + json);
                    getUserInfo();
                    EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
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
                Log.i("更新失败:" + msg);
            }
        });
    }

    private void setSexTv() {
        if (TextUtil.isEmpty(userInfo.getSex())) {
            sex_tv.setText("保密");
            return;
        }
        if (userInfo.getSex().equals("0")) {
            sex_tv.setText("男");
        } else if (userInfo.getSex().equals("1")) {
            sex_tv.setText("女");
        } else if (userInfo.getSex().equals("2")) {
            sex_tv.setText("保密");
        }
    }


    /**
     * 选择头像
     */
    public void showIconDialog() {
        if (selectIconDialog == null) {
            View selectIcon = LayoutInflater.from(this).inflate(R.layout.select_icon_layout, null);
            selectIcon.findViewById(R.id.camera_linear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCamera();
                }
            });

            selectIcon.findViewById(R.id.photo_linear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPhoto();
                }
            });

            selectIcon.findViewById(R.id.cancle_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectIconDialog != null) {
                        selectIconDialog.dismiss();
                    }
                }
            });

            selectIconDialog = new RxDialog(mContext, 0, Gravity.BOTTOM);
            selectIconDialog.setFullScreenWidth();
            selectIconDialog.setContentView(selectIcon);
            selectIconDialog.getWindow().setWindowAnimations(R.style.dialogBottomStyle);
        }
        selectIconDialog.show();
    }

    /**
     * 选择图库
     */
    private void selectPhoto() {
        new RxPermissions(this).request(
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            CenterActivity.this.startActivityForResult(intent, UploadUtils.CHOOSE_PICTURE);
                            if (selectIconDialog != null) {
                                selectIconDialog.dismiss();
                            }

                        } else {
                            ToastUtil.showToast("获取权限失败");
                        }
                    }

                });
    }

    /**
     * 选择性别
     */
    public void showSexDialog() {
        if (selectSexdialog == null) {
            View selectSex = LayoutInflater.from(mContext).inflate(R.layout.select_sex_layout, null);
            selectSex.findViewById(R.id.boy_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userInfo.setSex("0");
                    setSexTv();
                    if (selectSexdialog != null) {
                        selectSexdialog.dismiss();
                    }
                    submitUserInfo();
                }
            });
            selectSex.findViewById(R.id.girl_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userInfo.setSex("1");
                    setSexTv();
                    if (selectSexdialog != null) {
                        selectSexdialog.dismiss();
                    }
                    submitUserInfo();
                }
            });
            selectSex.findViewById(R.id.other_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userInfo.setSex("2");
                    setSexTv();
                    if (selectSexdialog != null) {
                        selectSexdialog.dismiss();
                    }
                    submitUserInfo();
                }
            });
            selectSexdialog = new RxDialog(mContext, 0, Gravity.BOTTOM);
            selectSexdialog.setFullScreenWidth();
            selectSexdialog.setContentView(selectSex);
            selectSexdialog.getWindow().setWindowAnimations(R.style.dialogBottomStyle);
        }
        selectSexdialog.show();
    }

    /**
     * 生日dialog
     */
    public void showBirthdayDialog() {

        if (birthdaySelector == null) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date current = new Date();
            birthdaySelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
                @Override
                public void handle(String time) {
                    String birthday = time.substring(0, 10);
                    birthday_tv.setText(birthday);
                    userInfo.setBirthday(birthday);
                    submitUserInfo();
                }
            }, "1950-01-01 00:00", sf.format(current));

            birthdaySelector.setMode(TimeSelector.MODE.YMD);
            birthdaySelector.setIsLoop(true);
        }
        birthdaySelector.show();
        //自动滚动显示到指定日期(写法待优化,暂时先这个样子吧!!!!!)
        if ((userInfo.getBirthday() != null) && (userInfo.getBirthday().length() == 10)) {
            birthdaySelector.refreshComponent(userInfo.getBirthday() + " 00:00");
        }
    }

    /**
     * 上传头像
     *
     * @param file
     */
    public void uploadImage(File file) {
        List<File> files = new ArrayList<>();
        files.add(file);
        ServiceManger.getInstance().uploadImage(files, new BaseRequestCallback<ImageListData>() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog("头像上传中...", null);
            }

            @Override
            public void onSuccess(ImageListData response) {
                Log.i("图片上传结果" + response.toString());
                if (response.getCode().equals(OleConstants.REQUES_SUCCESS)) {
                    if (response.getData() != null && !response.getData().isEmpty()) {
                        userInfo.setUserimage(response.getData().get(0).getShowImageUrl());
                        submitUserInfo();
                    } else {
                        ToastUtil.showToast(response.getMsg());
                    }
                } else {
                    ToastUtil.showToast(response.getMsg());
                }
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                dismissProgressDialog();
                ToastUtil.showToast("网络错误" + msg);
            }

            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }
        });
    }


    /**
     * 调用摄像头
     */
    public void selectCamera() {
        new RxPermissions(this).request(
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File file = new File(PHOTO_PATH);
                            if (file.exists()) {
                                file.delete();
                            }
                            // 下面这句指定调用相机拍照后的照片存储的路径
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(TACK_PIC_PATH)));
                            CenterActivity.this.startActivityForResult(intent, UploadUtils.TAKE_PICTURE);
                            if (selectIconDialog != null) {
                                selectIconDialog.dismiss();
                            }

                        } else {
                            ToastUtil.showToast("没有摄像头权限");
                        }
                    }

                });
    }

}
