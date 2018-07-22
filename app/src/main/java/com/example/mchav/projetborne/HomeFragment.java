package com.example.mchav.projetborne;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView textview;
    TextView textview2;
    JSONObject json = null;
    JSONObject json2 = null;
    String str = "";
    String str2 = "";
    HttpResponse response;
    HttpResponse response2;
    Context context;
    ProgressBar progressbar;
    ProgressBar progressbar2;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Handler mHandler;
    ImageView ImageUV;
    ImageView ImageRAD;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        this.mHandler = new Handler();
        m_Runnable.run();

        View view = inflater.inflate(R.layout.fragment_home, container,false);

        webView = (WebView) view.findViewById(R.id.meteo);

        progressbar = (ProgressBar) view.findViewById(R.id.progress1);
        textview = (TextView) view.findViewById(R.id.text1);
        progressbar2 = (ProgressBar) view.findViewById(R.id.progress2);
        textview2 = (TextView) view.findViewById(R.id.text2);
        ImageUV = (ImageView) view.findViewById(R.id.ImageUV);
        ImageRAD = (ImageView) view.findViewById(R.id.ImageRAD);

        progressbar.setVisibility(View.GONE);
        progressbar2.setVisibility(View.GONE);

        progressbar.setVisibility(View.VISIBLE);
        progressbar2.setVisibility(View.VISIBLE);

        new GetTextViewData(context).execute();
        new web(context).execute();

        ImageUV.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UVFragment UVFragment = new UVFragment();
                FragmentManager manager1 = getFragmentManager();
                manager1.beginTransaction().replace(R.id.contentLayout,UVFragment,UVFragment.getTag()).commit();
            }
        });

        ImageRAD.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RadiationFragment RadiationFragment = new RadiationFragment();
                FragmentManager manager2 = getFragmentManager();
                manager2.beginTransaction().replace(R.id.contentLayout,RadiationFragment,RadiationFragment.getTag()).commit();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                new Handler().postDelayed(new Runnable()
                {
                    @Override public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);

                progressbar.setVisibility(View.VISIBLE);
                progressbar2.setVisibility(View.VISIBLE);
                new GetTextViewData(context).execute();
            }
        });

        return view;
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            HomeFragment.this.mHandler.postDelayed(m_Runnable,30000);
            new GetTextViewData(context).execute();
        }

    };

    private void setContentView(int fragment_home)
    {
    }

    private class web extends AsyncTask<Void, Void, Void>
    {
        public Context context;


        public web(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            return null;
        }
        protected void onPostExecute(Void result)
        {
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.loadUrl("http://192.168.0.10/~admin/android/meteo.html");
        }
    }


    private class GetTextViewData extends AsyncTask<Void, Void, Void>
    {
        public Context context;


        public GetTextViewData(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            HttpClient myClient = new DefaultHttpClient();
            HttpClient myClient2 = new DefaultHttpClient();

            HttpPost myConnection = new HttpPost("http://192.168.0.10/~admin/android/recupUV1.php");
            HttpPost myConnection2 = new HttpPost("http://192.168.0.10/~admin/android/recupRAD1.php");

            /*HttpPost myConnection = new HttpPost("http://192.168.1.14/android/recupUV1.php");
            HttpPost myConnection2 = new HttpPost("http://192.168.1.14/android/recupRAD1.php");*/

            try
            {
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");

                response2 = myClient2.execute(myConnection2);
                str2 = EntityUtils.toString(response2.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try{
                JSONArray jArray = new JSONArray(str);
                json = jArray.getJSONObject(0);

                JSONArray jArray2 = new JSONArray(str2);
                json2 = jArray2.getJSONObject(0);

            } catch ( JSONException e) {
                e.printStackTrace();
            }

            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            try
            {
                textview.setText(json.getString("UV"));
                textview2.setText(json2.getString("Rad"));
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //Hiding progress bar after done loading TextView.
            progressbar.setVisibility(View.GONE);
            progressbar2.setVisibility(View.GONE);
        }
    }
}