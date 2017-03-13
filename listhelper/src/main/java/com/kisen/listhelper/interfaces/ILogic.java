package com.kisen.listhelper.interfaces;

import android.support.v7.widget.RecyclerView;

import com.kisen.listhelper.impl.AbsItem;

/**
 * 列表试图逻辑处理类
 * Created by huang on 2017/3/13.
 */

public interface ILogic {

    /**
     * 是否准备就绪
     */
    boolean isReady();

    /**
     * Item 的点击事件，如果isReady() 返回false，不会执行该方法
     *
     * @param adapter 适配器
     * @param item    点击的item
     */
    void onItemClick(RecyclerView.Adapter adapter, AbsItem item);

    /**
     * 返回该Item是否被选中
     *
     * @param item 判断的item
     */
    boolean isSelect(AbsItem item);

    /**
     * 设置Item选中
     */
    void setSelect(AbsItem item);

    /**
     * 清空保存的所有数据
     */
    void clear();
}
