package com.rdc.test;


import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.rdc.test.recyclerviewHelper.IItemTouchHelperAdapter;
import com.rdc.test.recyclerviewHelper.IItemTouchHelperViewHolder;
import com.rdc.test.recyclerviewHelper.OnStartDragListener;

import java.util.Collections;
import java.util.List;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> implements IItemTouchHelperAdapter {

    private List<ItemEntity> mList;
    private OnClickSwitchListener mOnClickSwitchListener;
    private final OnStartDragListener mDragListener;

    public RecyclerViewAdapter(List<ItemEntity> list, OnStartDragListener mDragListener) {
        mList = list;
        this.mDragListener = mDragListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.text.setText(mList.get(position).getText());
        holder.switchCompat.setChecked(mList.get(position).isChecked());
        if (mOnClickSwitchListener != null) {
            holder.switchCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean b = mList.get(position).isChecked();
                    mList.get(position).setChecked(!b);
                    mOnClickSwitchListener.onClick(position, !b);
                }
            });
        }
        holder.menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()
                        == MotionEvent.ACTION_DOWN) {
                    mDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements IItemTouchHelperViewHolder {
        private TextView text;
        private ImageView menu;
        private SwitchCompat switchCompat;

        ItemViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.item_list_text_textView);
            menu = itemView.findViewById(R.id.item_list_menu_imageView);
            switchCompat = itemView.findViewById(R.id.item_list_switchCompat);
        }

        @Override
        public void onItemSelected() {
            itemView.setTranslationZ(10);
        }

        @Override
        public void onItemClear() {
            itemView.setTranslationZ(0);
        }
    }

    public void setOnClickSwitchListener(OnClickSwitchListener onClickSwitchListener) {
        mOnClickSwitchListener = onClickSwitchListener;
    }

    public interface OnClickSwitchListener {
        void onClick(int position, boolean isChecked);
    }
}
