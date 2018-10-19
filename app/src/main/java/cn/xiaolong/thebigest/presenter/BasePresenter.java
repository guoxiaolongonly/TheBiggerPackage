package cn.xiaolong.thebigest.presenter;

import cn.xiaolong.thebigest.view.ILoadingView;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/20 16:57
 */
public abstract class BasePresenter<T extends ILoadingView> {
    protected T mView;
    protected CompositeDisposable mCompositeSubscription;


    public BasePresenter(T view) {
        mView =view;
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.dispose();
        }
    }

    /**
     * 因为在请求过程中用户可能会进行其他操作销毁当前页面，
     * 这边必须要通过addSubscribe 来做请求，防止页面销毁后订阅者
     * 的生命周期没结束抛异常
     * 在RX2 中可以通过disposable来取消
     *
     * @param disposable
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        mCompositeSubscription.add(disposable);
    }

    @SuppressWarnings("TypeParameterHidesVisibleType")
    protected <T> Observer<T> getSubscriber(OnSubscribeSuccess<T> onSubscribeSuccess) {
        mView.showLoading();
        return new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onNext(T t) {
                mView.hideLoading();
                onSubscribeSuccess.onSuccess(t);
            }

            @Override
            public void onError(Throwable e) {
                mView.hideLoading();
                mView.showError(e);
            }

            @Override
            public void onComplete() {

            }


        };
    }

    protected <T> Observer<T> getSubscriberNoProgress(OnSubscribeSuccess<T> onSubscribeSuccess) {
        return new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onNext(T t) {
                onSubscribeSuccess.onSuccess(t);
            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
