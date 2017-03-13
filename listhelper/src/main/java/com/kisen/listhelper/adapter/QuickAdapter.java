package com.kisen.listhelper.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.kisen.listhelper.impl.AbsItem;
import com.kisen.recyclerviewhelper.adapter.BaseQuickAdapter;
import com.kisen.recyclerviewhelper.adapter.BaseViewHolder;

import java.util.List;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/3/3.
 */

public class QuickAdapter extends BaseQuickAdapter<AbsItem, BaseViewHolder> {

    protected int position;
    protected Context context;

    public QuickAdapter(Context context) {
        this(context, 0);
    }

    public QuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    public QuickAdapter(Context context, int layoutResId, List<AbsItem> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AbsItem item) {
        item.setViewData(context, helper, position);
    }

    @Override
    protected int getDefItemViewType(int position) {
        this.position = position;
        try {
            return mData.get(position).getType();
        } catch (Exception e) {
            return super.getDefItemViewType(position);
        }
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        //优先使用通过构造方法传进来的布局
        if (mData.get(position).getViewResId() != 0)
            mLayoutResId = mData.get(position).getViewResId();
        return createBaseViewHolder(parent, mLayoutResId);
    }
}
