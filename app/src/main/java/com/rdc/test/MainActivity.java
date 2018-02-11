package com.rdc.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.rdc.test.recyclerviewHelper.MyItemTouchHelperCallback;
import com.rdc.test.recyclerviewHelper.OnStartDragListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnStartDragListener{

    private List<ItemEntity> mList;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData() {
        mList = new ArrayList<>();
        String [] strings = {"Android","后端","前端","iOS","人工智能","产品","工具资源","阅读","设计"};
        for (String string : strings) {
            ItemEntity item = new ItemEntity();
            item.setChecked(false);
            item.setText(string);
            mList.add(item);
        }
    }

    private void initViews() {
        RecyclerView recyclerView = findViewById(R.id.main_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mList,this);
        adapter.setOnClickSwitchListener(new RecyclerViewAdapter.OnClickSwitchListener() {
            @Override
            public void onClick(int position, boolean isChecked) {
                //实际开发中做发送请求等等一些处理
            }
        });
        recyclerView.setAdapter(adapter);
        mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(adapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        //通知ItemTouchHelper开始拖拽
        mItemTouchHelper.startDrag(viewHolder);
    }
}
