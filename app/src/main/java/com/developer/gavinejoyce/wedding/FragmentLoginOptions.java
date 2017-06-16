package com.developer.gavinejoyce.wedding;

/**
 * Created by harik on 13-06-2017.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class FragmentLoginOptions extends Fragment{
    ImageButton cancel;
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_options_fragment,container,false);
        return view;
    }

}
