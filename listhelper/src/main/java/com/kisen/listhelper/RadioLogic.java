package com.kisen.listhelper;

import android.support.v7.widget.RecyclerView;

import com.kisen.listhelper.impl.AbsItem;
import com.kisen.listhelper.interfaces.ILogic;

/**
 * 单选逻辑处理类
 * 不可取消选择
 * Created by huang on 2017/3/13.
 */
public class RadioLogic implements ILogic {

    private AbsItem selectedItem;

    @Override
    public boolean isReady() {
        //没有需要准备的数据，默认返回true
        return true;
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, AbsItem item) {
        if (selectedItem != null) {
            AbsItem oldSelected = selectedItem;
            setSelect(item);
            adapter.notifyItemChanged(oldSelected.getItemPosition());
        }
        setSelectAndNotify(adapter, item);
    }

    @Override
    public boolean isSelect(AbsItem item) {
        return selectedItem == item;
    }

    @Override
    public void setSelect(AbsItem item) {
        selectedItem = item;
    }

    @Override
    public void clear() {
        selectedItem = null;
    }

    private void setSelectAndNotify(RecyclerView.Adapter adapter, AbsItem item) {
        setSelect(item);
        adapter.notifyItemChanged(item.getItemPosition());
    }

}
