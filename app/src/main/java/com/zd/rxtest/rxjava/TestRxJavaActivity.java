package com.zd.rxtest.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zd.rxtest.R;
import com.zd.rxtest.rxretrofit.ApiService;
import com.zd.rxtest.rxretrofit.CodeMsgModel;
import com.zd.rxtest.rxretrofit.RetrofitCreate;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestRxJavaActivity extends AppCompatActivity {

    private static final String TAG = "TestRxJavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rx_java);

        //打印当前线程的名字
        Log.d(TAG, "onCreate: -------------------------" + Thread.currentThread().getName());

//        firstCreate();

//        continuousCreate();

//        threadCreate();

        getInfoFromInternet();

//        flatMapCreate();

//        zipCreate();

    }

    /**
     * 模拟注册成功后登录
     */
    private void getInfoFromInternet() {
        RetrofitCreate.create().create(ApiService.class).getJoke("desc", 1, 1, "1418816972", "a7e30d19e0f568ee9aab2bec2ba69a49")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<CodeMsgModel>() {
                    @Override
                    public void accept(CodeMsgModel codeMsgModel) throws Exception {
                        Log.d(TAG, "accept: ------注册成功保存用户数据");
                        Log.d(TAG, "accept: -------" + codeMsgModel);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<CodeMsgModel, ObservableSource<CodeMsgModel>>() {
                    @Override
                    public ObservableSource<CodeMsgModel> apply(@NonNull CodeMsgModel codeMsgModel) throws Exception {
                        return RetrofitCreate.create().create(ApiService.class).getJoke("desc", 2, 1, "1418816972", "a7e30d19e0f568ee9aab2bec2ba69a49");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MObserver<>(new CallBackInter<CodeMsgModel>() {
                    @Override
                    public void onError(String msg) {
                        Log.d(TAG, "onError: ------------" + msg);
                    }

                    @Override
                    public void onNext(CodeMsgModel model) {
                        Log.d(TAG, "onNext: ------------登录成功");
                        Log.d(TAG, "onNext: ----------" + model);
                    }
                }));
    }

    /**
     * 分开写
     */
    private void firstCreate() {

        //事件的发送端
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onError(new Throwable("end"));
                e.onComplete();
            }
        });

        //事件的接收处理
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ---------------------");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, "onNext: ----------------------------" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: --------------------------" + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: -----------------------");
            }
        };

        //事件的观察联系
        observable.subscribe(observer);
    }

    /**
     * 连续写
     * Map操作符进行转换
     */
    private void continuousCreate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onError(new Throwable("end"));
                e.onComplete();
            }
        })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(@NonNull String s) throws Exception {
                        return Integer.valueOf(s);
                    }
                })
                .subscribe(/*new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: -----------------" + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "accept: ----------------" + throwable.getMessage());
            }
        }*//*new MObserver<>(new CallBackInter<String>() {
                    @Override
                    public void onError(String msg) {
                        Log.d(TAG, "onError: ----------------------------" + msg);
                    }

                    @Override
                    public void onNext(String model) {
                        Log.d(TAG, "onNext: -----------" + model);
                    }
                })*/new MObserver<Integer>(new CallBackInter<Integer>() {
                    @Override
                    public void onError(String msg) {
                        Log.d(TAG, "onError: ----------------------------" + msg);
                    }

                    @Override
                    public void onNext(Integer model) {
                        Log.d(TAG, "onNext: -----------" + model);
                    }
                }));
    }

    /**
     * flatMap操作符,不保证转换后的事件发送顺序
     */
    private void flatMapCreate() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
                e.onNext("5");
            }
        }).flatMap(new Function<String, ObservableSource<CodeMsgModel>>() {

            @Override
            public ObservableSource<CodeMsgModel> apply(@NonNull String s) throws Exception {
                ArrayList<CodeMsgModel> arrayList = new ArrayList<>();

                CodeMsgModel msgModel = new CodeMsgModel();
                for (int i = 0; i < 2; i++) {
                    msgModel.setReason(s + "this is change model " + i);
                    arrayList.add(msgModel);
                }
                return Observable.fromIterable(arrayList);
            }
        }).subscribe(new MObserver<CodeMsgModel>(new CallBackInter<CodeMsgModel>() {
            @Override
            public void onError(String msg) {
                Log.d(TAG, "onError: -----------------");
            }

            @Override
            public void onNext(CodeMsgModel model) {
                Log.d(TAG, "onNext: ---------------" + model);
            }
        }));
    }

    /**
     * zip操作符测试
     */
    private void zipCreate() {
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
                e.onNext("5");
                e.onNext("6");
                e.onComplete();
//                e.onError(new Throwable("1 end"));
            }
        }).subscribeOn(Schedulers.io());

        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(11);
                e.onNext(22);
                e.onNext(33);
                e.onComplete();
//                e.onError(new Throwable("2 end"));
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(stringObservable, integerObservable, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {
                return s + " with " + integer;
            }
        })
                .subscribe(new MObserver<>(new CallBackInter<String>() {
                    @Override
                    public void onError(String msg) {
                        Log.d(TAG, "onError: ---------" + msg);
                    }

                    @Override
                    public void onNext(String model) {
                        Log.d(TAG, "onNext: ----------" + model);
                    }
                }));
    }

    /**
     * 线程控制
     */
    private void threadCreate() {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "subscribe: ------------" + Thread.currentThread().getName());
                e.onNext("520");
                e.onNext("521");
                e.onNext("522");
//                e.onError(new Throwable("null"));
                e.onComplete();
            }
        });

        /* 线程调度后，onError事件存在，则onNext事件都不会输出 */

        //多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept: -----AndroidSchedulers.mainThread()----" + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept: -----Schedulers.io()------" + Thread.currentThread().getName());
                    }
                })
                .subscribe(/*new MObserver<String>(new CallBackInter<String>() {
                    @Override
                    public void onError(String msg) {
                        Log.d(TAG, "onError: --------" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String model) {
                        Log.d(TAG, "onNext: ----------" + Thread.currentThread().getName());
                    }
                })*/new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "onNext: -------Schedulers.io()-----" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: --------Schedulers.io()----" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: --------Schedulers.io()-----" + Thread.currentThread().getName());
                    }
                });
    }
}
