package com.fbartnitzek.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.example.frank.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by frank on 29.11.15.
 */
public class JokeAsyncTask extends
        AsyncTask<JokeAsyncTask.JokeCallback, Void, Pair<String, IOException>> {

    private static MyApi mApiService = null;
    private JokeCallback mJokeCallback;
    private static final String LOG_TAG = JokeAsyncTask.class.getName();

    @Override
    protected Pair<String, IOException> doInBackground(JokeCallback... params) {
        if (mApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            //really needed?
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            mApiService = builder.build();
        }

        if (params != null && params.length > 0) {
            mJokeCallback = params[0];
        }

        try {
            Log.v(LOG_TAG, "doInBackground - before request");
            // seems to be better that custom object
            return new Pair<>(mApiService.getJoke().execute().getData(), null);
        } catch (IOException e) {
            Log.v(LOG_TAG, "doInBackground - request failed");
            return new Pair<>(null,e);
        }
    }

    @Override
    protected void onPostExecute(Pair<String, IOException> result) {
        Log.v(LOG_TAG, "onPostExecute, " + "result = [" + result + "]");
        if (mJokeCallback == null) {
            Log.w(LOG_TAG, "no Callback-Activity found for task!");
            return;
        }

        if (result.first != null) {
            mJokeCallback.onSuccess(result.first);
            Log.v(LOG_TAG, "onPostExecute - joke found: " + result.first);
        } else if (result.second != null) {
            mJokeCallback.onError(result.second);
            Log.v(LOG_TAG, "onPostExecute, IOException occured ...");
        }
    }

    public interface JokeCallback {
        void onSuccess(String joke);

        void onError(IOException e);
    }
}
