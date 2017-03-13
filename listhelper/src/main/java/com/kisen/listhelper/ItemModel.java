package com.kisen.listhelper;

import com.kisen.listhelper.interfaces.IModel;

/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2017/3/3.
 */

public class ItemModel implements IModel {
    private String text;

    public ItemModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
