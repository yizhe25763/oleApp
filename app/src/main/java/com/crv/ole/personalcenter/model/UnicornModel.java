package com.crv.ole.personalcenter.model;

import android.content.Context;

import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

/**
 * Created by lihongshi on 2017/7/24.
 *
 */

public class UnicornModel {
    /**
     * 在线客服
     */
    public static void openChat(Context context) {
        if (Unicorn.isServiceAvailable() == false) {
            return;
        }
        /**
         * 设置访客来源，标识访客是从哪个页面发起咨询的，
         * 用于客服了解用户是从什么页面进入三个参数分别为
         * 来源页面的url，来源页面标题，来源页面额外信息（可自由定义）。
         * 设置来源后，在客服会话界面的"用户资料"栏的页面项，可以看到这里设置的值。
         */
        ConsultSource source = new ConsultSource("android", "你好", "欢迎咨询");
        /**
         * 请注意： 调用该接口前，应先检查Unicorn.isServiceAvailable()，
         * 如果返回为false，该接口不会有任何动作
         *
         * @param context 上下文
         * @param title   聊天窗口的标题
         * @param source  咨询的发起来源，包括发起咨询的url，title，描述信息等
         */
        Unicorn.openServiceActivity(context, "小e", source);
    }
}
