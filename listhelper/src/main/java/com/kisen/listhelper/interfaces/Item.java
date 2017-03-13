package com.kisen.listhelper.interfaces;

import android.content.Context;

import com.kisen.recyclerviewhelper.adapter.BaseViewHolder;

public interface Item {

    /**
     * 需要实现，返回对应Item的布局文件Id 如果返回0，则使用适配器默认布局
     *
     * @return 返回当前数据类对应布局
     */
    int getViewResId();

    /**
     * 必须实现，在数据类中直接将数据适配到通过BaseViewHolder获取到的视图中
     *
     * @param helper  用来获取Item的控件
     * @param adapterPosition  该Item在Adapter中的位置
     * @like BaseAdapter's getView()
     */
    void setViewData(Context context,BaseViewHolder helper, int adapterPosition);

    /**
     * 需要实现，默认返回0，同一列表中出现多种不同的布局时，必须返回不同的类型，
     * 如果返回相同的值，会因BaseViewHolder复用出现布局错乱，处理数据时异常
     * 在{@like getViewResId()}中已经把对应的布局返回给适配器
     *
     * @return 返回当前自定义Item类型
     * @like BaseAdapter's getItemViewType()
     */
    int getType();
}
