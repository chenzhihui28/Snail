package com.czh.snail.views.welfare;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.czh.snail.R;
import com.czh.snail.adapters.QuickAdapter;
import com.czh.snail.base.BaseFragment;
import com.czh.snail.databinding.FragmentWelfareBinding;
import com.czh.snail.model.beans.GankBeauty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhihui on 16/8/23.
 */

public class WelfareFragment extends BaseFragment<FragmentWelfareBinding,WelfarePresenter> implements WelfareContract.View{
    QuickAdapter mQuickAdapter;
    public static WelfareFragment newInstance() {
        WelfareFragment fragment = new WelfareFragment();
        return fragment;
    }

    @Override
    protected void initView() {
        mQuickAdapter = new QuickAdapter(new ArrayList<GankBeauty>());
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mBinding.recyclerView.setAdapter(mQuickAdapter);
        mBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getWelfarelist(true);
            }
        });
        mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getWelfarelist(false);
            }
        });
    }



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_welfare;
    }


    @Override
    protected WelfarePresenter setPresenter() {
        return new WelfarePresenter(this);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        mBinding.swipeRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void clearList() {
        mQuickAdapter.getData().clear();
        mQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public void addList(List<GankBeauty> gankBeautyList) {
        if (gankBeautyList.size() == WelfarePresenter.PAGE_SIZE) {

        }
        mQuickAdapter.addData(gankBeautyList);
        mQuickAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErr(String err) {
        if (TextUtils.isEmpty(err)) {
            err = getString(R.string.network_err);
        }
        Snackbar.make(mBinding.getRoot(),err, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmpty() {
    }


}
