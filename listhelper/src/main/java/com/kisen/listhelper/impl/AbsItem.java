package com.kisen.listhelper.impl;

import android.content.Context;
import android.view.View;

import com.kisen.listhelper.adapter.QuickAdapter;
import com.kisen.listhelper.interfaces.Item;
import com.kisen.listhelper.interfaces.ILogic;
import com.kisen.listhelper.interfaces.IModel;
import com.kisen.listhelper.interfaces.ItemFactory;
import com.kisen.recyclerviewhelper.adapter.BaseViewHolder;

/**
 * 自定义Item控制类
 */
public abstract class AbsItem<D extends IModel> implements Item, View.OnClickListener {

    protected D model;
    protected ILogic logic;
    protected QuickAdapter adapter;
    protected int position;
    protected Context context;

    /**
     * 创建该Item类型的工厂
     * @param logic 对应逻辑处理类
     * @return 创建的工厂
     */
    public ItemFactory<D> createFactory(QuickAdapter adapter, ILogic logic) {
        this.adapter = adapter;
        this.logic = logic;
        return new ItemFactoryImpl<>(this, adapter, logic);
    }

    @Override
    public int getViewResId() {
        return 0;
    }

    @Override
    public void setViewData(Context context,BaseViewHolder helper, int adapterPosition) {
        this.context = context;
        position = adapterPosition;
        helper.getConvertView().setOnClickListener(this);
        setViewData(helper);
    }

    /**
     * @see Item setViewData(Context context,BaseViewHolder helper, int adapterPosition)
     * @param helper item UI持有对象
     */
    protected abstract void setViewData(BaseViewHolder helper);

    @Override
    public int getType() {
        //默认返回 0，可重写
        return 0;
    }

    /**
     * 返回该Item在Adapter中的位置
     */
    public int getItemPosition() {
        return position;
    }

    /**
     * 返回的AbsItem必须通过new AbsItem()创建一个新对象。
     * @return 返回一个新对象
     */
    public abstract AbsItem<D> newSelf();

    @Override
    public void onClick(View v) {
        if (logic.isReady()) {
            logic.onItemClick(adapter, adapter.getItem(position));
        }
    }

    public void setModel(D model) {
        this.model = model;
    }

    public void setAdapter(QuickAdapter adapter) {
        this.adapter = adapter;
    }

    public void setLogic(ILogic logic) {
        this.logic = logic;
    }
}
