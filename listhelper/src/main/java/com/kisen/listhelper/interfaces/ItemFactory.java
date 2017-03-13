package com.kisen.listhelper.interfaces;

import com.kisen.listhelper.impl.AbsItem;

import java.util.List;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/3/3.
 */

public interface ItemFactory<D extends IModel> {

    void setList(List<D> models);

    List<AbsItem> getItems();

    void setPageSize(int pageSize);

    void notifyAfterLoad();

    void clear();

    void clearAdapter();

    ILogic getLogic();
}
