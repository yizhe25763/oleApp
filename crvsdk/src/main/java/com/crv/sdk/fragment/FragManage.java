package com.crv.sdk.fragment;

import com.crv.sdk.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * fragment管理
 *
 * @author ljf
 */
public class FragManage {

    public static void turnToFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass, String tag, Bundle args, boolean addToBackStack) {
        turnToFragment(fm,fragmentClass, R.id.layout_frag,tag,args,addToBackStack);
    }

    public static void turnToFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass, int layoutId, String tag, Bundle args, boolean addToBackStack){
        Fragment fragment = fm.findFragmentByTag(tag);
        boolean isFragmentExist = true;
        if (fragment == null) {
            try {
                isFragmentExist = false;
                fragment = fragmentClass.newInstance();
                fragment.setArguments(new Bundle());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (fragment.isAdded()) {
            return;
        }
        if (args != null && !args.isEmpty()) {
            fragment.getArguments().putAll(args);
        }
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.animation_right_in, R.anim.animation_right_in, R.anim.animation_right_in, R.anim.animation_right_in);
        if (isFragmentExist) {
            ft.replace(layoutId, fragment);
        } else {
            ft.replace(layoutId, fragment, tag);
        }

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
    }

}
