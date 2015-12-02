package com.fbartnitzek.gradle.displayjokeandroidlibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.frank.displayjokeandroidlibrary.R;


/**
 * A simple {@link Fragment} subclass.
 * shows the joke passed by intent-extra 'JOKE'
 */
public class DisplayJokeFragment extends Fragment {

    public static final String ARG_JOKE = "JOKE";

    public DisplayJokeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_display_joke, container, false);

        if (getActivity().getIntent() != null
                && getActivity().getIntent().hasExtra(DisplayJokeFragment.ARG_JOKE)) {
            TextView jokeView = (TextView) rootView.findViewById(R.id.joke_text);
            jokeView.setText(getActivity().getIntent().getStringExtra(DisplayJokeFragment.ARG_JOKE));
        }

        return rootView;
    }


}
