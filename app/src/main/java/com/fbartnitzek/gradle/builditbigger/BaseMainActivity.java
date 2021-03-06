package com.fbartnitzek.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fbartnitzek.gradle.displayjokeandroidlibrary.DisplayJokeActivity;
import com.fbartnitzek.gradle.displayjokeandroidlibrary.DisplayJokeFragment;

import java.io.IOException;


public class BaseMainActivity extends AppCompatActivity implements JokeAsyncTask.JokeCallback{

    private JokeAsyncTask mJokeTask = null;
    private ProgressBar mProgressBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void requestJoke(){
        mProgressBar.setVisibility(View.VISIBLE);
        if (mJokeTask != null) {
            mJokeTask.cancel(true);
        }

        mJokeTask = new JokeAsyncTask();
        mJokeTask.execute(this);
    }

    public void tellJoke(View view) {
        requestJoke();
//        if (mJokeTask != null) {
//            mJokeTask.cancel(true);
//        }
//
//        mJokeTask = new JokeAsyncTask();
//        mJokeTask.execute(this);
    }


    @Override
    public void onSuccess(String joke) {
        mProgressBar.setVisibility(View.GONE);
        Intent jokeIntent = new Intent(this, DisplayJokeActivity.class)
            .putExtra(DisplayJokeFragment.ARG_JOKE, joke);
        startActivity(jokeIntent);
    }

    @Override
    public void onError(IOException e) {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, "an error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
