package es.udc.psi.cleanarchv2.app.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Runs a task in a background thread and delivers its result in the main thread
 * (replacement of the deprecated AsyncTask).
 */
public class TaskRunner {

    public interface Callback<T> {

        void onSuccess(T result);

        void onError(Exception exception);
    }

    private static final ExecutorService BACKGROUND_EXECUTOR = Executors.newFixedThreadPool(4);

    private final Executor mBackgroundExecutor;
    private final Executor mMainExecutor;

    public TaskRunner() {

        this(BACKGROUND_EXECUTOR, new MainThreadExecutor());
    }

    public TaskRunner(Executor backgroundExecutor,
                      Executor mainExecutor) {

        mBackgroundExecutor = backgroundExecutor;
        mMainExecutor = mainExecutor;
    }

    public <T> void execute(Callable<T> task,
                            Callback<T> callback) {

        mBackgroundExecutor.execute(() -> {
            try {
                T result = task.call();
                mMainExecutor.execute(() -> callback.onSuccess(result));
            } catch (Exception e) {
                mMainExecutor.execute(() -> callback.onError(e));
            }
        });
    }

    private static class MainThreadExecutor implements Executor {

        private final Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {

            mHandler.post(command);
        }
    }
}
