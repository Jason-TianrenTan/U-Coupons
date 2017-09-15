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
//Universal Presenter for special list requests
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
                        System.out.println(" on rx, recommend list size = " + couponList);
                        EventBus.getDefault().post(new CouponListEvent("Recommend", couponList));
                    }
                });
    }


    //我使用过的
    public void getUserUsedByRxRetrofit(String userid) {

        ApiManager.getInstance().
                getRetrofitService()
                .getUsedList(userid)
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
                        System.out.println("user used list size = " + couponList.size());
                        EventBus.getDefault().post(new CouponListEvent("UserUsed", couponList));
                    }
                });
    }


    //我未使用的
    public void getUserUnsoldByRxRetrofit(String userid) {

        ApiManager.getInstance().
                getRetrofitService()
                .getUnusedList(userid)
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
                        System.out.println("user store list size = " + couponList.size());
                        EventBus.getDefault().post(new CouponListEvent("UserUnsold", couponList));
                    }
                });
    }


    //我正在出售的
    public void getUserOnsaleByRxRetrofit(String userid) {

        ApiManager.getInstance().
                getRetrofitService()
                .getOnSaleList(userid)
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
                        System.out.println("user onsale list size = " + couponList.size());
                        EventBus.getDefault().post(new CouponListEvent("UserOnsale", couponList));
                    }
                });
    }


    //我已经卖出的
    public void getUserSoldByRxRetrofit(String userid) {

        ApiManager.getInstance().
                getRetrofitService()
                .getSoldList(userid)
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
                        System.out.println("user already sold list size = " + couponList.size());
                        EventBus.getDefault().post(new CouponListEvent("UserSold", couponList));
                    }
                });
    }


    //我已经买到的
    public void getUserBoughtByRxRetrofit(String userid) {

        ApiManager.getInstance().
                getRetrofitService()
                .getBoughtList(userid)
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
                        System.out.println("user bought list size = " + couponList.size());
                        EventBus.getDefault().post(new CouponListEvent("UserBought", couponList));
                    }
                });
    }


    //我关注的
    public void getUserFollowByRxRetrofit(String userid) {

        ApiManager.getInstance().
                getRetrofitService()
                .getFollowList(userid)
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
                        System.out.println("user follow list size = " + couponList.size());
                        EventBus.getDefault().post(new CouponListEvent("UserFollow", couponList));
                    }
                });
    }


    //搜索

    /**
     *
     * @param keyWord 搜索关键字
     * @param order 排序方式
     */
    public void getSearchResultByRxRetrofit(String keyWord, String order) {
        System.out.println("Search --> " + keyWord + ", " + order);
        ApiManager.getInstance().
                getRetrofitService()
                .getSearchResultList(keyWord, order)
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
                        System.out.println("search result list size = " + couponList.size());
                        EventBus.getDefault().post(new CouponListEvent("UserSearch", couponList));
                    }
                });
    }


    //分类搜索
    public void getCatSearchResultByRxRetrofit(String keyWord, String order, String catId) {
        System.out.println("Category Search --> " + keyWord + ", " + order + ", " + catId);
        ApiManager.getInstance().
                getRetrofitService()
                .getCatSearchResultList(keyWord, order, catId)
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
                        System.out.println("search result list size = " + couponList.size());
                        EventBus.getDefault().post(new CouponListEvent("UserSearch", couponList));
                    }
                });
    }


    /**
     *
     * @param sellerId
     */
    public void getSellerOnsaleByRxRetrofit(String sellerId) {
        ApiManager.getInstance().
                getRetrofitService()
                .getSellerOnsaleList(sellerId)
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
                        EventBus.getDefault().post(new CouponListEvent("SellerOnsale", couponList));
                    }
                });
    }


    /**
     *
     * @param sellerId
     */
    public void getSellerSoldByRxRetrofit(String sellerId) {
        ApiManager.getInstance().
                getRetrofitService()
                .getSellerSoldList(sellerId)
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
                        EventBus.getDefault().post(new CouponListEvent("SellerSold", couponList));
                    }
                });
    }

}
