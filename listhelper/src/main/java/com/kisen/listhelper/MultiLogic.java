package com.kisen.listhelper;

import android.support.v7.widget.RecyclerView;

import com.kisen.listhelper.impl.AbsItem;
import com.kisen.listhelper.interfaces.ILogic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/3/13.
 */

public class MultiLogic implements ILogic {

    private List<AbsItem> selectedItems = new ArrayList<>();

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, AbsItem item) {
        if (isSelect(item)) {
            selectedItems.remove(item);
        } else {
            setSelect(item);
        }
        adapter.notifyItemChanged(item.getItemPosition());
    }

    @Override
    public boolean isSelect(AbsItem item) {
        boolean select = false;
        for (AbsItem absItem : selectedItems) {
            if (absItem == item) {
                select = true;
                break;
            }
        }
        return select;
    }

    @Override
    public void setSelect(AbsItem item) {
        selectedItems.add(item);
    }

    @Override
    public void clear() {
        selectedItems.clear();
    }

    public List<AbsItem> getSelectedItems(){
        return selectedItems;
    }
}
