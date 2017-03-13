package com.kisen.recyclerviewhelper.adapter.animation;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/2/24.
 */

import android.animation.Animator;
import android.view.View;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public interface  BaseAnimation {

    Animator[] getAnimators(View view);

}