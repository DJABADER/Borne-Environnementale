package com.example.mchav.projetborne;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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

public class UVFragment extends Fragment {

    Context context;
    SwipeRefreshLayout mSwipeRefreshLayout;
    WebView webView;
    private int contentView;
    Handler mHandler;

    JSONObject json1 = null;
    String str1 = "";
    HttpResponse response1;

    JSONObject json2 = null;
    String str2 = "";
    HttpResponse response2;

    JSONObject json3 = null;
    String str3 = "";
    HttpResponse response3;

    JSONObject json4 = null;
    String str4 = "";
    HttpResponse response4;

    JSONObject json5 = null;
    String str5 = "";
    HttpResponse response5;

    JSONObject json6 = null;
    String str6 = "";
    HttpResponse response6;

    TextView UV1;
    TextView UV2;
    TextView UV3;
    TextView UV4;
    TextView UV5;
    TextView UV6;

    TextView Date1;
    TextView Date2;
    TextView Date3;
    TextView Date4;
    TextView Date5;
    TextView Date6;

    TextView Heure1;
    TextView Heure2;
    TextView Heure3;
    TextView Heure4;
    TextView Heure5;
    TextView Heure6;

    public UVFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_uv);

        this.mHandler = new Handler();
        m_Runnable.run();

        View view = inflater.inflate(R.layout.fragment_uv,container,false);

        // Find By ID
        // Webview
        webView = (WebView) view.findViewById(R.id.web);

        // Valeur UV
        UV1 = (TextView) view.findViewById(R.id.RAD1);
        UV2 = (TextView) view.findViewById(R.id.RAD2);
        UV3 = (TextView) view.findViewById(R.id.RAD3);
        UV4 = (TextView) view.findViewById(R.id.RAD4);
        UV5 = (TextView) view.findViewById(R.id.RAD5);
        UV6 = (TextView) view.findViewById(R.id.RAD6);

        // Valeur Date
        Date1 = (TextView) view.findViewById(R.id.Date1);
        Date2 = (TextView) view.findViewById(R.id.Date2);
        Date3 = (TextView) view.findViewById(R.id.Date3);
        Date4 = (TextView) view.findViewById(R.id.Date4);
        Date5 = (TextView) view.findViewById(R.id.Date5);
        Date6 = (TextView) view.findViewById(R.id.Date6);

        // Valeur Heure
        Heure1 = (TextView) view.findViewById(R.id.Heure1);
        Heure2 = (TextView) view.findViewById(R.id.Heure2);
        Heure3 = (TextView) view.findViewById(R.id.Heure3);
        Heure4 = (TextView) view.findViewById(R.id.Heure4);
        Heure5 = (TextView) view.findViewById(R.id.Heure5);
        Heure6 = (TextView) view.findViewById(R.id.Heure6);

        // Execute
        new GetTextViewData(context).execute();
        new graph(context).execute();

        // Swipe refresh
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

                new GetTextViewData(context).execute();
                new graph(context).execute();
            }
        });

        return view;
    }

    public void setContentView(int contentView) {
        this.contentView = contentView;
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            UVFragment.this.mHandler.postDelayed(m_Runnable,30000);
            new UVFragment.GetTextViewData(context).execute();
            new UVFragment.graph(context).execute();
        }

    };

    private class graph extends AsyncTask<Void, Void, Void>
    {
        public Context context;


        public graph(Context context)
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
            webView.loadUrl("http://192.168.0.10/~admin/android/androidUVgraph.php");
            /*webView.loadUrl("http://192.168.137.1/android/androidUVgraph.php");*/
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
            HttpClient myClient3 = new DefaultHttpClient();
            HttpClient myClient4 = new DefaultHttpClient();
            HttpClient myClient5 = new DefaultHttpClient();
            HttpClient myClient6 = new DefaultHttpClient();

            HttpPost myConnection1 = new HttpPost("http://192.168.0.10/~admin/android/recupUV1.php");
            HttpPost myConnection2 = new HttpPost("http://192.168.0.10/~admin/android/recupUV2.php");
            HttpPost myConnection3 = new HttpPost("http://192.168.0.10/~admin/android/recupUV3.php");
            HttpPost myConnection4 = new HttpPost("http://192.168.0.10/~admin/android/recupUV4.php");
            HttpPost myConnection5 = new HttpPost("http://192.168.0.10/~admin/android/recupUV5.php");
            HttpPost myConnection6 = new HttpPost("http://192.168.0.10/~admin/android/recupUV6.php");

            /*HttpPost myConnection1 = new HttpPost("http://192.168.137.1/android/recupUV1.php");
            HttpPost myConnection2 = new HttpPost("http://192.168.137.1/android/recupUV2.php");
            HttpPost myConnection3 = new HttpPost("http://192.168.137.1/android/recupUV3.php");
            HttpPost myConnection4 = new HttpPost("http://192.168.137.1/android/recupUV4.php");
            HttpPost myConnection5 = new HttpPost("http://192.168.137.1/android/recupUV5.php");
            HttpPost myConnection6 = new HttpPost("http://192.168.137.1/android/recupUV6.php");*/

            try {
                response1 = myClient.execute(myConnection1);
                str1 = EntityUtils.toString(response1.getEntity(), "UTF-8");

                response2 = myClient2.execute(myConnection2);
                str2 = EntityUtils.toString(response2.getEntity(), "UTF-8");

                response3 = myClient3.execute(myConnection3);
                str3 = EntityUtils.toString(response3.getEntity(), "UTF-8");

                response4 = myClient4.execute(myConnection4);
                str4 = EntityUtils.toString(response4.getEntity(), "UTF-8");

                response5 = myClient5.execute(myConnection5);
                str5 = EntityUtils.toString(response5.getEntity(), "UTF-8");

                response6 = myClient6.execute(myConnection6);
                str6 = EntityUtils.toString(response6.getEntity(), "UTF-8");

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try{
                JSONArray jArray1 = new JSONArray(str1);
                json1 = jArray1.getJSONObject(0);

                JSONArray jArray2 = new JSONArray(str2);
                json2 = jArray2.getJSONObject(0);

                JSONArray jArray3 = new JSONArray(str3);
                json3 = jArray3.getJSONObject(0);

                JSONArray jArray4 = new JSONArray(str4);
                json4 = jArray4.getJSONObject(0);

                JSONArray jArray5 = new JSONArray(str5);
                json5 = jArray5.getJSONObject(0);

                JSONArray jArray6 = new JSONArray(str6);
                json6 = jArray6.getJSONObject(0);

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
                UV1.setText(json1.getString("UV"));
                Date1.setText(json1.getString("Date"));
                Heure1.setText(json1.getString("Heure"));

                UV2.setText(json2.getString("UV"));
                Date2.setText(json2.getString("Date"));
                Heure2.setText(json2.getString("Heure"));

                UV3.setText(json3.getString("UV"));
                Date3.setText(json3.getString("Date"));
                Heure3.setText(json3.getString("Heure"));

                UV4.setText(json4.getString("UV"));
                Date4.setText(json4.getString("Date"));
                Heure4.setText(json4.getString("Heure"));

                UV5.setText(json5.getString("UV"));
                Date5.setText(json5.getString("Date"));
                Heure5.setText(json5.getString("Heure"));

                UV6.setText(json6.getString("UV"));
                Date6.setText(json6.getString("Date"));
                Heure6.setText(json6.getString("Heure"));
            }
            catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
