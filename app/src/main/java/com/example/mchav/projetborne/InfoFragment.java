package com.example.mchav.projetborne;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends android.app.Fragment {

    Button Bouton;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info);

        View view = inflater.inflate(R.layout.fragment_info, container,false);

        Bouton = (Button) view.findViewById(R.id.deco);

        Bouton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent decoIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(decoIntent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void setContentView(int fragment_info) {
    }

}



