package com.example.administrator.ccoupons.Connections;

import com.example.administrator.ccoupons.Events.CouponListEvent;
import com.example.administrator.ccoupons.Main.Coupon;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class UniversalPresenter extends BasePresenter{


    //首页推荐
    public void getRecommendByRxRetrofit() {

        ApiManager.getInstance().
                getRetrofitService()
                .getRecList()
                .map(new Function<CouponBean, ArrayList<Coupon>>() {
                    @Override
                    public ArrayList<Coupon> apply(CouponBean bean) {
                        return bean.getResult();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Coupon>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArrayList<Coupon> couponList) {
                        EventBus.getDefault().post(new CouponListEvent("Recommend", couponList));
                    }
                });
    }


    //我使用过的
    public void getUserUsedByRxRetrofit() {

        ApiManager.getInstance().
                getRetrofitService()
                .getRecList()//暂时使用这个替代
                .map(new Function<CouponBean, ArrayList<Coupon>>() {
                    @Override
                    public ArrayList<Coupon> apply(CouponBean bean) {
                        return bean.getResult();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Coupon>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArrayList<Coupon> couponList) {
                        EventBus.getDefault().post(new CouponListEvent("UserUsed", couponList));
                    }
                });
    }


    //我未使用的
    public void getUserUnsoldByRxRetrofit() {

        ApiManager.getInstance().
                getRetrofitService()
                .getRecList()//暂时使用这个替代
                .map(new Function<CouponBean, ArrayList<Coupon>>() {
                    @Override
                    public ArrayList<Coupon> apply(CouponBean bean) {
                        return bean.getResult();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Coupon>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArrayList<Coupon> couponList) {
                        EventBus.getDefault().post(new CouponListEvent("UserUnsold", couponList));
                    }
                });
    }


    //我正在出售的
    public void getUserOnsaleByRxRetrofit() {

        ApiManager.getInstance().
                getRetrofitService()
                .getRecList()//暂时使用这个替代
                .map(new Function<CouponBean, ArrayList<Coupon>>() {
                    @Override
                    public ArrayList<Coupon> apply(CouponBean bean) {
                        return bean.getResult();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Coupon>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArrayList<Coupon> couponList) {
                        EventBus.getDefault().post(new CouponListEvent("UserOnsale", couponList));
                    }
                });
    }


    /*
    //我的
    public void getOwnByRxRetrofit(String userid) {

        ApiManager.getInstance().
                getRetrofitService()
                .getRecList()
                .map(new Function<CouponBean, ArrayList<Coupon>>() {
                    @Override
                    public ArrayList<Coupon> apply(CouponBean bean) {
                        return bean.getResult();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Coupon>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArrayList<Coupon> couponList) {
                        EventBus.getDefault().post(new CouponListEvent("Recommend", couponList));
                    }
                });
    }*/
}
