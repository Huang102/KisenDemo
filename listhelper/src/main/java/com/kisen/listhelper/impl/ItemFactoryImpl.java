package com.kisen.listhelper.impl;

import com.kisen.listhelper.adapter.QuickAdapter;
import com.kisen.listhelper.interfaces.ILogic;
import com.kisen.listhelper.interfaces.IModel;
import com.kisen.listhelper.interfaces.ItemFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 特定实现类
 * Created by huang on 2017/3/3.
 */
class ItemFactoryImpl<D extends IModel> implements ItemFactory<D> {

    private final AbsItem<D> item;
    private List<D> models;
    private QuickAdapter adapter;
    private int pageSize;
    private List<AbsItem> items;
    private ILogic logic;

    ItemFactoryImpl(AbsItem<D> item, QuickAdapter adapter, ILogic logic) {
        this.item = item;
        this.adapter = adapter;
        this.logic = logic;
    }

    @Override
    public void setList(List<D> models) {
        clear();
        this.models = models;
        makeItems();
        if (models == null) {
            adapter.loadMoreFail();
        } else if (models.size() > 0) {
            adapter.loadMoreComplete();
        } else {
            adapter.loadMoreEnd();
        }
    }

    @Override
    public List<AbsItem> getItems() {
        return adapter.getData();
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public void notifyAfterLoad() {
        adapter.addData(items);
        adapter.setEnableLoadMore(pageSize >= items.size());
    }

    @Override
    public void clearAdapter() {
        if (adapter != null) {
            adapter.setNewData(null);
            logic.clear();
        }
    }

    private void clear() {
        models = null;
        items = null;
    }

    private void makeItems() {
        if (models == null)
            return;
        if (items == null)
            items = new ArrayList<>();
        for (D d : models) {
            items.add(makeItem(d));
        }
    }

    private AbsItem makeItem(D d) {
        AbsItem<D> self = item.newSelf();
        self.setAdapter(adapter);
        self.setLogic(logic);
        self.setModel(d);
        return self;
    }
}
