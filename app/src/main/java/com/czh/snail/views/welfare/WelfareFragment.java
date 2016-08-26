package com.czh.snail.views.welfare;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.czh.snail.R;
import com.czh.snail.adapters.WelfareListAdapter;
import com.czh.snail.base.LazyLoadFragment;
import com.czh.snail.databinding.FragmentWelfareBinding;
import com.czh.snail.model.beans.GankBeauty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhihui on 16/8/23.
 */

public class WelfareFragment extends LazyLoadFragment<FragmentWelfareBinding, WelfarePresenter> implements WelfareContract.View {
    private WelfareListAdapter mWelfareListAdapter;
    private View loadAllCompleteView, mEmptyView, mNoMoreView;

    public static WelfareFragment newInstance() {
        return new WelfareFragment();
    }

    @Override
    protected void initView() {
        mWelfareListAdapter = new WelfareListAdapter(new ArrayList<GankBeauty>());
        mWelfareListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mBinding.recyclerView.setAdapter(mWelfareListAdapter);
        mBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
//        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mParentActivity));
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getWelfareList(true);
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
    public void startRefresh() {
        mBinding.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefreshingOrLoading(boolean isFirstPage) {
        if (isFirstPage) {
            mBinding.swipeRefreshLayout.setRefreshing(false);
        } else {
            mWelfareListAdapter.loadComplete();
        }
    }


    @Override
    public void refreshOrLoadMoreSucceed(List<GankBeauty> gankBeautyList, boolean isFirstPage) {
        if (gankBeautyList.size() > 0) {
            if (isFirstPage) {
                mWelfareListAdapter.getData().clear();
            }
            mWelfareListAdapter.addData(gankBeautyList);
            if (gankBeautyList.size() < WelfarePresenter.PAGE_SIZE) {
                mWelfareListAdapter.loadComplete();
                if (loadAllCompleteView == null) {
                    loadAllCompleteView = mParentActivity.getLayoutInflater()
                            .inflate(R.layout.footer_no_more_data
                                    , (ViewGroup) mBinding.recyclerView.getParent(), false);
                }
                mWelfareListAdapter.addFooterView(loadAllCompleteView);
            } else {
                mWelfareListAdapter.openLoadMore(WelfarePresenter.PAGE_SIZE);
            }
            mWelfareListAdapter.notifyDataSetChanged();
            mWelfareListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    mPresenter.getWelfareList(false);
                }
            });
        } else {
            if (isFirstPage) {
                if (mEmptyView == null) {
                    mEmptyView = mParentActivity.getLayoutInflater().inflate(R.layout.empty_view
                            , (ViewGroup) mBinding.recyclerView.getParent(), false);
                }
                mWelfareListAdapter.setEmptyView(mEmptyView);
            } else {
                mWelfareListAdapter.loadComplete();
                if (mNoMoreView == null) {
                    mNoMoreView = mParentActivity.getLayoutInflater().inflate(R.layout.footer_no_more_data
                            , (ViewGroup) mBinding.recyclerView.getParent(), false);
                }
                mWelfareListAdapter.addFooterView(mNoMoreView);
            }
            mWelfareListAdapter.notifyDataSetChanged();
        }


    }


    @Override
    public void refreshOrLoadMoreError(String err, boolean isFirstPage) {
        if (isFirstPage) {
            mWelfareListAdapter.removeAllFooterView();
        } else {
            mWelfareListAdapter.showLoadMoreFailedView();
        }
        if (TextUtils.isEmpty(err)) {
            err = getString(R.string.network_err);
        }
        Snackbar.make(mBinding.getRoot(), err, Snackbar.LENGTH_LONG).show();
    }


}