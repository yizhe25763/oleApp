package com.mingle.pulltonextlayout.anim;

import android.view.View;

import com.nineoldandroids.animation.Animator;

/**
 * Created by zzz40500 on 15/5/3.
 */
public interface PullToNextAnimationI {


    Animator getPullDownAnim(View showView, View dismissView);

    Animator getPullUpAnim(View showView, View dismissView);
    Animator getDeleteItemAnim(View showView, View dismissView);


}
