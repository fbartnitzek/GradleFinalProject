package com.fbartnitzek.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by frank on 30.11.15.
 */
public class JokeAsyncTaskTest extends AndroidTestCase{

    private String mJoke;
    private final CountDownLatch signal = new CountDownLatch(1);

    public void testVerifyValidJokeResponse() {
        // needs inline callback-class and wait for the result
        // src: http://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework

        JokeAsyncTask task = new JokeAsyncTask();
        task.execute(new JokeAsyncTask.JokeCallback() {
            @Override
            public void onSuccess(String joke) {
                mJoke = joke;
                signal.countDown();
            }

            @Override
            public void onError(IOException e) {
                fail(e.getMessage());
            }
        });

        try {
            signal.await(20, TimeUnit.SECONDS);
//            assert (mJoke != null && !mJoke.isEmpty());
            if (BuildConfig.DEBUG && (mJoke == null || mJoke.isEmpty())) {
                throw new AssertionError("no joke found");
            }
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }

}
