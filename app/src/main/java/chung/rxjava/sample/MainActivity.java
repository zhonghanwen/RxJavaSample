package chung.rxjava.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;


/**
 * Created by zhonghanwen on 2017/6/12.
 */

public class MainActivity extends AppCompatActivity {

    public static final String tag = MainActivity.class.getSimpleName();

    private Observer<String> mObserver;
    private Observable<String> mObservable;
    private Subscriber<String> mSubscriber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObserver();
        initObservable();
        onSubscribe();

    }




    private void initObserver() {
        mObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(tag, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }

            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
            }
        };
    }

    private void initSubscriber(){
        mSubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d(tag, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }
        };
    }

    private void initObservable() {
        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
    }

    private void onSubscribe() {
        mObservable.subscribe(mObserver);
    }
}
