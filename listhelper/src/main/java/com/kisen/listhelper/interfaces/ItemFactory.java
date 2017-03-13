package com.kisen.listhelper.interfaces;

import com.kisen.listhelper.impl.AbsItem;

import java.util.List;

/**
 * Item 工厂类
 * Created by huang on 2017/3/3.
 */

public interface ItemFactory<D extends IModel> {

    /**
     * 设置model数据，在该方法中将model转换成Item数据
     * <p>
     * {@link com.kisen.listhelper.impl.ItemFactoryImpl#makeItem(D)}
     * {@link AbsItem#newSelf()}
     * </p>
     *
     * @param models 数据源
     */
    void setList(List<D> models);

    /**
     * ItemFactory生产的AbsItem，会全部提交到Adapter中，
     * 每次只会生产对应{@link ItemFactory#setList(List)} 中List对应的Item
     *
     * @return
     */
    List<AbsItem> getItems();

    void setPageSize(int pageSize);

    void notifyAfterLoad();

    void clearAdapter();
}
