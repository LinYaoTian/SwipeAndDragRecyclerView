package com.rdc.test.recyclerviewHelper;


import android.support.v7.widget.RecyclerView;

public interface OnStartDragListener {
    /**
     * 当View需要拖拽时回调
     *
     * @param viewHolder The holder of view to drag
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
