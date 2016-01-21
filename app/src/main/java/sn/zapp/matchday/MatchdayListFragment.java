package sn.zapp.matchday;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sn.zapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MatchdayListFragment extends Fragment {

    public MatchdayListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matchday1, container, false);
    }
}
