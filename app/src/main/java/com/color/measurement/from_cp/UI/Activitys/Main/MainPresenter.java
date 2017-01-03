package com.color.measurement.from_cp.UI.Activitys.Main;

/**
 * Created by wpc on 2016/11/30.
 */

public class MainPresenter {
    private MainBiz mMainBiz;
    private IMainView mIMainView;

    public MainPresenter(IMainView iMainView) {
        mIMainView = iMainView;
        mMainBiz = new MainBiz();
    }
}
