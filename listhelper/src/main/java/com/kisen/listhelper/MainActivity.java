package com.kisen.listhelper;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kisen.listhelper.adapter.QuickAdapter;
import com.kisen.listhelper.impl.AbsItem;
import com.kisen.listhelper.interfaces.ItemFactory;
import com.kisen.recyclerviewhelper.adapter.BaseQuickAdapter;
import com.kisen.recyclerviewhelper.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    private ItemFactory<ItemModel> mFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        setupData();
    }

    private void setupView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        QuickAdapter adapter = new QuickAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setDuration(150);
        adapter.setOnLoadMoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MultiLogic logic = new MultiLogic();
        mFactory = new Item().createFactory(adapter, logic);
        mFactory.setPageSize(10);
    }

    private void setupData() {
        List<ItemModel> list = createList();
        mFactory.setList(list);
        mFactory.notifyAfterLoad();
    }

    @Override
    public void onLoadMoreRequested() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupData();
            }
        },300);
    }

    @NonNull
    private List<ItemModel> createList() {
        List<ItemModel> list = new ArrayList<>();
        int base = mFactory.getItems().size();
        for (int i = 0; i < 10; i++) {
            list.add(new ItemModel("测试 Item " + (base + i)));
        }
        return list;
    }

    class Item extends AbsItem<ItemModel> {

        @Override
        public AbsItem<ItemModel> newSelf() {
            return new Item();
        }

        @Override
        public int getViewResId() {
            return R.layout.item_text_change;
        }

        @Override
        public void setViewData(BaseViewHolder helper) {
            TextView view = helper.getView(R.id.text);
            view.setText(model.getText());
            if (logic.isSelect(this)) {
                view.setTextColor(ContextCompat.getColor(context, R.color.selectedColor));
            } else {
                view.setTextColor(ContextCompat.getColor(context, R.color.selector_text_color));
            }
        }
    }
}
