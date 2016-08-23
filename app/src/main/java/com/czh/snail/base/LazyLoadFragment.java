package com.czh.snail.base;

/**
 * 懒加载Fragment，综合是否设置contentView和是否可见两种情况来考虑是否加载数据
 * Created by chenzhihui on 2015/8/31 0031.
 */
public abstract class LazyLoadFragment extends BaseFragment {
    protected boolean isVisible;
    protected boolean isLoaded;

    /**
     * 注意：setUserVisibleHint方法会比onCreateView方法更早地调用，所以不能单纯在onVisiable的时候
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 界面准备好的时候触发,综合是否设置contentView和是否可见两种情况来考虑是否加载数据
     */
    @Override
    protected final void initData() {
        if (!isPrepared || !isVisible) {
            return;
        }
        isLoaded = true;
        onLazyLoad();
    }

    /**
     * 出现在ViewPager当前页面的时候会触发触发，综合是否设置contentView和是否可见两种情况来考虑是否加载数据
     */
    protected final void lazyLoad() {
        if (!isPrepared || isLoaded) {
            return;
        }
        isLoaded = true;
        onLazyLoad();
    }

    /**
     * 首次显示的时候调用，用于懒加载数据
     */
    protected abstract void onLazyLoad();

    /**
     *
     */
    protected void onInvisible() {

    }

}
