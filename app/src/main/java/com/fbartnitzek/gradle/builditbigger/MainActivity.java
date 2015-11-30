package com.fbartnitzek.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.frank.displayjokeandroidlibrary.DisplayJokeActivity;
import com.example.frank.displayjokeandroidlibrary.DisplayJokeFragment;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements JokeAsyncTask.JokeCallback{

    private JokeAsyncTask mJokeTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void tellJoke(View view) {
        if (mJokeTask != null) {
            mJokeTask.cancel(true);
        }

        mJokeTask = new JokeAsyncTask();
        mJokeTask.execute(this);
    }


    @Override
    public void onSuccess(String joke) {
        Intent jokeIntent = new Intent(this, DisplayJokeActivity.class)
            .putExtra(DisplayJokeFragment.ARG_JOKE, joke);
        startActivity(jokeIntent);
    }

    @Override
    public void onError(IOException e) {
        Toast.makeText(this, "an error occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
