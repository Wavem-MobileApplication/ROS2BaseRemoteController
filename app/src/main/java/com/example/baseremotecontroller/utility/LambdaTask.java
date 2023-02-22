package com.example.baseremotecontroller.utility;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LambdaTask implements Disposable {
    TaskRunnable taskRunnable;

    public LambdaTask(TaskRunnable taskRunnable) {
        this.taskRunnable = taskRunnable;
    }

    @Override
    public void dispose() {
        Observable.fromCallable(() -> {
                    taskRunnable.run();
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {

                });
    }

    @Override
    public boolean isDisposed() {
        return false;
    }

    public interface TaskRunnable {
        void run();
    }
}
