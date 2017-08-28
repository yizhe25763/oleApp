package com.crv.ole.home.adapter;

import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crv.ole.R;
import com.crv.ole.home.model.DataServer;
import com.crv.ole.home.model.Status;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class NarketTrualAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    public NarketTrualAdapter() {
        super(R.layout.adapter_market_trial, DataServer.getSampleData(10));
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        //        switch (helper.getLayoutPosition() %
        //                3) {
        //            case 0:
        //                helper.setImageResource(R.id.img, R.drawable.animation_img1);
        //                break;
        //            case 1:
        //                helper.setImageResource(R.id.img, R.drawable.animation_img2);
        //                break;
        //            case 2:
        //                helper.setImageResource(R.id.img, R.drawable.animation_img3);
        //                break;
        //        }
        helper.setText(R.id.numbers, "60");
        helper.setText(R.id.content, "#content");
        helper.setText(R.id.title, "title");

        //        String msg = "\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";
        //        ((TextView) helper.getView(R.id.tweetText)).setText("title");
        //        ((TextView) helper.getView(R.id.tweetText)).setMovementMethod(LinkMovementMethod.getInstance());
    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            //            ToastUtils.showShortToast("事件触发了 landscapes and nedes");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            //            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
            //            ds.setUnderlineText(true);
        }
    };


}
