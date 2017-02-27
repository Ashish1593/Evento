package com.examples.android.evento.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.examples.android.evento.controller.AppController;
import com.examples.android.evento.R;
import com.examples.android.evento.controller.AuthController;
import com.examples.android.evento.controller.SharedPreferenceController;
import com.examples.android.evento.utils.AuthUtils;
import com.examples.android.evento.utils.ContactExchangeUtils;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRcodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferenceController.init(getApplicationContext());
        // DeviceController.init(getApplicationContext());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.qrcodescanner);

        if (AuthController.isLoggedIn()) {


            Snackbar.make(findViewById(R.id.snackbar), "", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Logout", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AuthController.deleteAuthToken();
                        }

                    })

                    .show();
        } else {

            Snackbar.make(findViewById(R.id.snackbar), "Hang on, we need to know who you are", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Login", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String url = "http://auth.hasgeek.com/auth?client_id=eDnmYKApSSOCXonBXtyoDQ&scope=id+email+phone+organizations+teams+com.talkfunnel:*&response_type=token";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }

                    })

                    .show();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();


    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.resumeCameraPreview(this);
        // mScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.resumeCameraPreview(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mScannerView.stopCamera();
        // mScannerView.resumeCameraPreview(this);
    }


    @Override
    public void handleResult(Result result) {


        final String data = result.getText();


        if (ContactExchangeUtils.isValidCode(data)) {
            final String puk = ContactExchangeUtils.getPukFromCode(data);
            String key = ContactExchangeUtils.getKeyFromCode(data);


            String url = "https://50p.talkfunnel.com/2017/" + "participant?" + "puk=" + puk + "&key=" + key;
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(com.android.volley.Request.Method.GET,
                    url, null, new com.android.volley.Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONObject participants = response.getJSONObject("participant");
                        final String company = participants.getString("company");
                        final String email = participants.getString("email");
                        final String fullname = participants.getString("fullname");
                        final String job_title = participants.getString("job_title");
                        final String phone = participants.getString("phone");
                        final String twitter = participants.getString("twitter");


                        Intent intent = new Intent(Intent.ACTION_INSERT);
                        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

                        intent.putExtra(ContactsContract.Intents.Insert.NAME, fullname);
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job_title);
                        intent.putExtra(ContactsContract.Intents.Insert.IM_HANDLE, twitter);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }


                    //  mScannerView.resumeCameraPreview(QRcodeScanner.this);

                }

            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //     Log.d(TAG, "Error " + error.getMessage());

                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", AuthUtils.getAuthHeaderFromToken(AuthController.getAuthToken()));


                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(jsonObjReq);

//mScannerView.resumeCameraPreview(QRcodeScanner.this);
            mScannerView.stopCamera();


        } else {
            Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_LONG).show();
            mScannerView.resumeCameraPreview(QRcodeScanner.this);
        }


    }


}


