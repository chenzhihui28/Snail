package com.czh.snail.base;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter
        <T> extends RecyclerView.Adapter<BaseRecyclerHolder> {

    protected final Context mContext;
    protected final int mItemLayoutId;
    protected List<T> mDatas;
    private OnItemClickListener mOnItemClickListener;
    private OnLongItemClickListener mOnLongItemClickListener;


    /**
     * @param context      context
     * @param itemLayoutId 布局的layout的Id
     */
    public BaseRecyclerAdapter(Context context, int itemLayoutId) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mDatas = new ArrayList<>();

    }

    @Override
    public void onBindViewHolder(final BaseRecyclerHolder holder, int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mOnItemClickListener.onClick(v, holder.getLayoutPosition());

                }
            });
        }


        if (mOnLongItemClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnLongItemClickListener.onLongClick(v, holder.getLayoutPosition());
                    return false;
                }
            });
        }


        convert(holder, mDatas.get(position),position);
    }

    /**
     * @param helper 自定义的ViewHolder对象，可以getView获取控件
     * @param item   对应的数据
     * @param position
     */
    public abstract void convert(BaseRecyclerHolder helper, T item, int position);

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerHolder(LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false));
    }

    @Override
    public int getItemCount() {
        return isEmpty() ? 0 : mDatas.size();
    }

    public boolean isEmpty() {
        return mDatas == null || mDatas.size() == 0;
    }

    /**
     * 设置列表中的数据
     */
    public void setDatas(List<T> datas) {
        if (datas == null) {
            return;
        }
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 将单个数据添加到列表中
     */
    public void addSingleData(T t) {
        if (t == null) {
            return;
        }
        this.mDatas.add(t);
        notifyItemInserted(mDatas.size() - 1);
    }

    public void addSingleData(T t, int position) {
        mDatas.add(position, t);
        notifyItemInserted(position);
//        notifyItemRangeChanged(position, mDatas.size());
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, mDatas.size());
    }

    public void removeData(T t) {
        int index = mDatas.indexOf(t);
        notifyItemRemoved(index);
//        notifyItemRangeChanged(index, mDatas.size());
    }

    /**
     * 将一个List添加到列表中
     */
    public void addDatas(List<T> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearDatas() {
        if (!isEmpty()) {
            this.mDatas.clear();
        }

    }

    public interface OnItemClickListener {
        void onClick(View v, int position);

    }

    public interface OnLongItemClickListener {
        void onLongClick(View v, int position);
    }

    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按点击事件
     *
     * @param onLongItemClickListener
     */
    public void setonLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        this.mOnLongItemClickListener = onLongItemClickListener;
    }
}

