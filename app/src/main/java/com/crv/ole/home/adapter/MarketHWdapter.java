package com.crv.ole.home.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crv.ole.R;
import com.crv.ole.home.model.DataServer;
import com.crv.ole.home.model.HWBean;
import com.crv.ole.home.model.Status;
import com.crv.ole.utils.ImageUtils;
import com.crv.ole.utils.LoadImageUtil;

import java.util.List;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class MarketHWdapter extends BaseQuickAdapter<HWBean.RETURNDATABean, BaseViewHolder> {
    private Context mContext;

    public MarketHWdapter(Context instance, List<HWBean.RETURNDATABean> RETURN_DATA) {
        super(R.layout.adapter_market_hw, RETURN_DATA);
        this.mContext = instance;

    }

    @Override
    protected void convert(BaseViewHolder helper, HWBean.RETURNDATABean item) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_market_hw, null);        //                        helper.setImageResource(R.id.img, R.drawable.animation_img3);
        ImageView imag = (ImageView) itemView.findViewById(R.id.image);
        LoadImageUtil.getInstance().loadImage(imag, item.getImgUrl(), R.drawable.capture01, null);
        helper.setText(R.id.title, item.getPara());
        helper.setText(R.id.content, item.getTime());
        helper.setText(R.id.zk, item.getDiscount());
    }


}
