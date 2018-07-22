package com.example.mchav.projetborne;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class Register extends AppCompatActivity {

    Button inscription ;
    ProgressDialog progressDialog;
    EditText name, email, password, passwordConf ;
    String NameHolder, EmailHolder, PasswordHolder, PasswordConfHolder;
    boolean CheckEditText ;
    String ServerLoginURL = "http://192.168.0.10/~admin/android/inscription.php";
    // String ServerLoginURL = "http://192.168.137.1/android/inscription.php";
    public static final String UserEmail = "";
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String FinalHttpData = "";
    BufferedWriter bufferedWriter ;
    LoginParseClass loginParseClass = new LoginParseClass();
    BufferedReader bufferedReader ;
    OutputStream outputStream ;
    StringBuilder stringBuilder = new StringBuilder();
    String Result ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inscription = (Button)findViewById(R.id.button);

        name = (EditText)findViewById(R.id.name);

        email = (EditText)findViewById(R.id.email);

        password = (EditText)findViewById(R.id.password);

        passwordConf = (EditText)findViewById(R.id.password_conf);

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetCheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    LoginFunction(NameHolder,EmailHolder,PasswordHolder,PasswordConfHolder);
                }
                else {
                    Toast.makeText(Register.this, "Merci de remplir tous les champs.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void GetCheckEditTextIsEmptyOrNot(){

        NameHolder = name.getText().toString();
        EmailHolder = email.getText().toString();
        PasswordHolder = password.getText().toString();
        PasswordConfHolder = passwordConf.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(PasswordConfHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void LoginFunction(final String name,final String email, final String password, final String passwordConf){

        class LoginFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Register.this,"Envoie des données ...",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Inscription réussie")){

                    finish();

                    Intent intent2 = new Intent(Register.this, Bottom_bar.class);

                    Intent intent = new Intent("finish");
                    sendBroadcast(intent);

                    startActivity(intent2);
                }else{

                    Toast.makeText(Register.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("name",params[0]);

                hashMap.put("password",params[1]);

                hashMap.put("passwordConf",params[2]);

                hashMap.put("email",params[3]);

                finalResult = loginParseClass.postRequest(hashMap);

                return finalResult;
            }
        }

        LoginFunctionClass loginFunctionClass = new LoginFunctionClass();
        loginFunctionClass.execute(name,password,passwordConf,email);
    }

    public class LoginParseClass {

        public String postRequest(HashMap<String, String> Data) {

            try {
                url = new URL(ServerLoginURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(12000);

                httpURLConnection.setConnectTimeout(12000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(FinalDataParse(Data));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(
                            new InputStreamReader(
                                    httpURLConnection.getInputStream()
                            )
                    );
                    FinalHttpData = bufferedReader.readLine();
                }
                else {
                    FinalHttpData = "Something Went Wrong";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return FinalHttpData;
        }

        public String FinalDataParse(HashMap<String, String> hashMap2) throws UnsupportedEncodingException {

            for(Map.Entry<String, String> map_entry : hashMap2.entrySet()){

                stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(map_entry.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(map_entry.getValue(), "UTF-8"));

            }

            Result = stringBuilder.toString();

            return Result ;
        }
    }

}