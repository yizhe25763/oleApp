package com.crv.ole.personalcenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.crv.ole.R;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.activity.CollectionAddFolderActivity;
import com.crv.ole.personalcenter.activity.CollectionGoodsListActivity;
import com.crv.ole.personalcenter.activity.CollectionInformationListActivity;
import com.crv.ole.personalcenter.adapter.CollectionGoodsFolderListAdapter;
import com.crv.ole.personalcenter.adapter.CollectionInformationFolderListAdapter;
import com.crv.ole.personalcenter.model.CollectionFolderListData;
import com.crv.ole.personalcenter.model.CollectionGoodsFolderListData;
import com.crv.ole.personalcenter.requestmodel.RequestCollectionInformationData;
import com.crv.ole.personalcenter.tools.CollectionEvent;
import com.crv.ole.personalcenter.tools.CollectionUtils;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.fragment.BaseFragment;
import com.crv.sdk.utils.PreferencesHelper;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人中心 - 我的收藏  - 资讯和商品公用fragment
 */

public class CollectionItemFragment extends BaseFragment {
    private Unbinder unbinder;
    private CollectionUtils.CollectionTypeEnum collectionTypeEnum;

    @BindView(R.id.collection_information_layout)
    PullToRefreshLayout collection_information_layout;
    @BindView(R.id.collection_information_list)
    GridView collection_information_list;

    private CollectionInformationFolderListAdapter collectionListAdapter;
    private List<CollectionFolderListData.FolderData> informationDataList;

    private CollectionGoodsFolderListAdapter goodsListAdapter;
    private List<CollectionGoodsFolderListData.GoodsFolderData> goodsDataList;

    private boolean isEdit = false, isRefresh = false;
    private int pageNum = 1, pageTotal, pageLimit = 10;

    public static CollectionItemFragment getInstance() {

        CollectionItemFragment collectionInformationFragment = new CollectionItemFragment();
        return collectionInformationFragment;
    }

    public void setCollectionTypeEnum(CollectionUtils.CollectionTypeEnum collectionTypeEnum) {
        this.collectionTypeEnum = collectionTypeEnum;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_collection_tab_item, container, false);
            unbinder = ButterKnife.bind(this, view);

            initView();
            initListener();
        }

        if (this.collectionTypeEnum == CollectionUtils.CollectionTypeEnum.INFORMATION_COLLECTION)
            getData();
        return view;
    }

    private void initView() {
//        collectionListAdapter = new CollectionInformationFolderListAdapter(mContext, dataList);
//        collection_information_list.setAdapter(collectionListAdapter);

        collection_information_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == informationDataList.size() - 1) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, CollectionAddFolderActivity.class);
                    intent.putExtra("edit_tag", "new");
                    mContext.startActivity(intent);
                } else {
                    if (collectionTypeEnum == CollectionUtils.CollectionTypeEnum.GOODS_COLLECTION) {
                        startActivity(new Intent(mContext, CollectionGoodsListActivity.class)
                                .putExtra("folder_name", goodsDataList.get(i).getFavoriteClassName())
                                .putExtra("folder_id", goodsDataList.get(i).getId()));
                    } else
                        startActivity(new Intent(mContext, CollectionInformationListActivity.class)
                                .putExtra("folder_name", informationDataList.get(i).getArticleFileName()));
                }
            }
        });
    }

    /**
     * 显示资讯收藏夹列表
     *
     * @param lists
     */
    private void showInformationData(List<CollectionFolderListData.FolderData> lists) {
        if (lists == null)
            lists = new ArrayList<>();

        if (collectionListAdapter == null || informationDataList == null) {
            informationDataList = new ArrayList<>();
            informationDataList.addAll(lists);

            CollectionFolderListData.FolderData folderData = new CollectionFolderListData.FolderData();
            folderData.setArticleFileName("");
            folderData.setArticleFileImage(R.drawable.collection_add_folder_bg + "");
            informationDataList.add(folderData);

            collectionListAdapter = new CollectionInformationFolderListAdapter(mContext, informationDataList);
            collection_information_list.setAdapter(collectionListAdapter);

            isLoad = true;
        } else {
            if (isRefresh)
                informationDataList.clear();
            informationDataList.addAll(lists);

            CollectionFolderListData.FolderData folderData = new CollectionFolderListData.FolderData();
            folderData.setArticleFileName("");
            folderData.setArticleFileImage(R.drawable.collection_add_folder_bg + "");
            informationDataList.add(folderData);

            collectionListAdapter.notifyDataSetChanged();
        }

        if (isRefresh) {
            collection_information_layout.finishRefresh();
            isRefresh = false;
        }

    }

    /**
     * 显示商品收藏夹列表
     *
     * @param lists
     */
    private void showGoodsData(List<CollectionGoodsFolderListData.GoodsFolderData> lists) {
        if (lists == null)
            lists = new ArrayList<>();

        if (goodsListAdapter == null || goodsDataList == null) {
            goodsDataList = new ArrayList<>();
            goodsDataList.addAll(lists);

            CollectionGoodsFolderListData.GoodsFolderData folderData = new CollectionGoodsFolderListData.GoodsFolderData();
            folderData.setFavoriteClassName("");
            folderData.setImgUrl(R.drawable.collection_add_folder_bg + "");
            goodsDataList.add(folderData);

            goodsListAdapter = new CollectionGoodsFolderListAdapter(mContext, goodsDataList);
            collection_information_list.setAdapter(goodsListAdapter);

            isLoad = true;
        } else {
            goodsDataList.clear();
            goodsDataList.addAll(lists);

            CollectionGoodsFolderListData.GoodsFolderData folderData = new CollectionGoodsFolderListData.GoodsFolderData();
            folderData.setFavoriteClassName("");
            folderData.setImgUrl(R.drawable.collection_add_folder_bg + "");
            goodsDataList.add(folderData);

            goodsListAdapter.notifyDataSetChanged();
        }

        if (isRefresh) {
            collection_information_layout.finishRefresh();
            isRefresh = false;
        }

    }

    private void getData() {
        RequestData requestData = new RequestData();
        if (this.collectionTypeEnum == CollectionUtils.CollectionTypeEnum.INFORMATION_COLLECTION) {
            requestData.getRequestAttrsInstance().setApi_ID(OleConstants.INFORMATION_COLLECTION_LIST_URL_ID);
            requestData.setREQUEST_DATA(new RequestCollectionInformationData("", pageNum, pageLimit));

            ServerApi.request(false, requestData, CollectionFolderListData.class, new PreferencesHelper(getActivity()).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            Log.i("获取收藏列表开始请求");
                            mDialog.showProgressDialog("加载中……", null);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CollectionFolderListData>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onNext(CollectionFolderListData response) {
                            mDialog.dissmissDialog();
                            if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                                showInformationData(response.getRETURN_DATA().getFileList());

                            } else {
                                ToastUtil.showToast(response.getRETURN_DESC());
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mDialog.dissmissDialog();
                            ToastUtil.showToast("获取收藏列表失败");
                        }

                        @Override
                        public void onComplete() {
                            Log.i("获取收藏列表完成");
                            mDialog.dissmissDialog();
                        }
                    });
        } else {
            requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GOODS_COLLECTION_FOLDER_LIST_URL_ID);
            requestData.setREQUEST_DATA(new RequestCollectionInformationData("", pageNum, pageLimit));

            ServerApi.request(false, requestData, CollectionGoodsFolderListData.class, new PreferencesHelper(getActivity()).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            Log.i("获取收藏列表开始请求");
                            mDialog.showProgressDialog("加载中……", null);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CollectionGoodsFolderListData>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onNext(CollectionGoodsFolderListData response) {
                            mDialog.dissmissDialog();
                            if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                                showGoodsData(response.getRETURN_DATA().getFavorTypeList());

                            } else {
                                ToastUtil.showToast(response.getRETURN_DESC());
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mDialog.dissmissDialog();
                            ToastUtil.showToast("获取收藏列表失败");
                        }

                        @Override
                        public void onComplete() {
                            Log.i("获取收藏列表完成");
                            mDialog.dissmissDialog();
                        }
                    });
        }


    }

    public void edit() {
        if (isEdit) {
            isEdit = false;
            collectionListAdapter.setEditStatus(0);
            collectionListAdapter.notifyDataSetChanged();
        } else {
            isEdit = true;
            collectionListAdapter.setEditStatus(1);
            collectionListAdapter.notifyDataSetChanged();
        }
    }

    public boolean getIsEdit() {
        return isEdit;
    }

    private void initListener() {
        collection_information_layout.setCanLoadMore(false);
        collection_information_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                isRefresh = true;
                getData();
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        collection_information_layout.finishLoadMore();
                    }
                }, 3000);
            }
        });
    }

    public void showThisPage() {
        if (!isLoad) {
            getData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100) //在ui线程执行 优先级100
    public void onDataSynEvent(CollectionEvent event) {
        Log.d("event---->" + event);

        if (collectionTypeEnum == CollectionUtils.CollectionTypeEnum.INFORMATION_COLLECTION &&
                (event == CollectionEvent.INFORMATION_CREATE_FOLDER ||
                        event == CollectionEvent.INFORMATION_DELETE_FOLDER ||
                        event == CollectionEvent.INFORMATION_UPDATE_FOLDER)) {
            pageNum = 1;
            isRefresh = true;
            getData();
        } else if (collectionTypeEnum == CollectionUtils.CollectionTypeEnum.GOODS_COLLECTION &&
                (event == CollectionEvent.GOODS_CREATE_FOLDER ||
                        event == CollectionEvent.GOODS_DELETE_FOLDER ||
                        event == CollectionEvent.GOODS_UPDATE_FOLDER)) {
            pageNum = 1;
            isRefresh = true;
            getData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dispose();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }
}
