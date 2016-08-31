package com.czh.snail.views.knowledge;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.czh.snail.R;
import com.czh.snail.adapters.KnowledgeListAdapter;
import com.czh.snail.base.LazyLoadFragment;
import com.czh.snail.databinding.FragmentKnowledgeBinding;
import com.czh.snail.model.beans.GankBeauty;
import com.czh.snail.model.beans.Knowledge;
import com.czh.snail.utils.TransitionHelper;
import com.czh.snail.views.welfare.welfaredetail.WelFareDetailActivity;
import com.czh.snail.views.welfare.welfarelist.WelfareContract;
import com.czh.snail.views.welfare.welfarelist.WelfarePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhihui on 16/8/23.
 */

public class KnowledgeFragment extends LazyLoadFragment<FragmentKnowledgeBinding, KnowledgePresenter> implements KnowledgeContract.View {
    private KnowledgeListAdapter mKnowledgeListAdapter;
    private View loadAllCompleteView, mEmptyView, mNoMoreView;

    public static KnowledgeFragment newInstance() {
        return new KnowledgeFragment();
    }

    @Override
    protected void initView() {
        mKnowledgeListAdapter = new KnowledgeListAdapter(new ArrayList<Knowledge>());
        mKnowledgeListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mBinding.recyclerView.setAdapter(mKnowledgeListAdapter);
        mBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getWelfareList(true);
            }
        });
        mBinding.recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (mKnowledgeListAdapter.getItem(i) != null) {
//                    transitionToActivity(WelFareDetailActivity.class,view.findViewById(R.id.imgWelfareItem)
//                            ,mKnowledgeListAdapter.getItem(i));
                }
            }
        });
    }


    private void transitionToActivity(Class target, View v, GankBeauty sample) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(mParentActivity, false,
                new Pair<>(v, getString(R.string.transition_welfare_item_img)));
        startActivity(target, pairs, sample);
    }

    private void startActivity(Class target, Pair<View, String>[] pairs, GankBeauty sample) {
        Intent i = new Intent(mParentActivity, target);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat
                .makeSceneTransitionAnimation(mParentActivity, pairs);
        i.putExtra(WelFareDetailActivity.INTENT_ENTITY, sample);
        mParentActivity.startActivity(i, transitionActivityOptions.toBundle());
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_knowledge;
    }


    @Override
    protected KnowledgePresenter setPresenter() {
        return new KnowledgePresenter(this);
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
            mKnowledgeListAdapter.loadComplete();
        }
    }


    @Override
    public void refreshOrLoadMoreSucceed(List<Knowledge> gankBeautyList, boolean isFirstPage) {
        if (gankBeautyList.size() > 0) {
            if (isFirstPage) {
                mKnowledgeListAdapter.getData().clear();
            }
            mKnowledgeListAdapter.addData(gankBeautyList);
            if (gankBeautyList.size() < WelfarePresenter.PAGE_SIZE) {
                mKnowledgeListAdapter.loadComplete();
                if (loadAllCompleteView == null) {
                    loadAllCompleteView = mParentActivity.getLayoutInflater()
                            .inflate(R.layout.footer_no_more_data
                                    , (ViewGroup) mBinding.recyclerView.getParent(), false);
                }
                mKnowledgeListAdapter.addFooterView(loadAllCompleteView);
            } else {
                mKnowledgeListAdapter.openLoadMore(WelfarePresenter.PAGE_SIZE);
            }
            mKnowledgeListAdapter.notifyDataSetChanged();
            mKnowledgeListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
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
                mKnowledgeListAdapter.setEmptyView(mEmptyView);
            } else {
                mKnowledgeListAdapter.loadComplete();
                if (mNoMoreView == null) {
                    mNoMoreView = mParentActivity.getLayoutInflater().inflate(R.layout.footer_no_more_data
                            , (ViewGroup) mBinding.recyclerView.getParent(), false);
                }
                mKnowledgeListAdapter.addFooterView(mNoMoreView);
            }
            mKnowledgeListAdapter.notifyDataSetChanged();
        }


    }


    @Override
    public void refreshOrLoadMoreError(String err, boolean isFirstPage) {
        if (isFirstPage) {
            mKnowledgeListAdapter.removeAllFooterView();
        } else {
            mKnowledgeListAdapter.showLoadMoreFailedView();
        }
        if (TextUtils.isEmpty(err)) {
            err = getString(R.string.network_err);
        }
        Snackbar.make(mBinding.getRoot(), err, Snackbar.LENGTH_LONG).show();
    }


}
