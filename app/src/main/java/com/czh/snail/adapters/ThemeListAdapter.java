package com.czh.snail.adapters;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.czh.snail.R;
import com.czh.snail.base.BaseRecyclerAdapter;
import com.czh.snail.base.BaseRecyclerHolder;
import com.czh.snail.utils.MaterialTheme;
import com.czh.snail.model.SingData;

/**
 * 主题选择页面的adapter
 */
public class ThemeListAdapter extends BaseRecyclerAdapter<MaterialTheme> {
    /**
     * @param context      context
     * @param itemLayoutId 布局的layout的Id
     */
    public ThemeListAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void convert(BaseRecyclerHolder helper, MaterialTheme item, int position) {
        TextView mTextView =  helper.getView(R.id.tvThemeText);
        RelativeLayout mRelativeLayout = helper.getView(R.id.rlRoot);
        StringBuilder stringBuilder = new StringBuilder();
        if (MaterialTheme.getThemeList().get(position).getThemeResId() == SingData.getInstance().getCurrentTheme().getThemeResId()) {
            stringBuilder.append("Current:");
        }
        stringBuilder.append(mContext.getString(MaterialTheme.getThemeList().get(position).getNameResId()));
        mTextView.setText(stringBuilder.toString());
        mRelativeLayout.setBackgroundResource(MaterialTheme.getThemeList().get(position).getColorResId());
    }





    @Override
    public int getItemCount() {
        return MaterialTheme.getThemeList().size();
    }






}
