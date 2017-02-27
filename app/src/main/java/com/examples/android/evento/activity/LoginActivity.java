package com.examples.android.evento.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.examples.android.evento.controller.APIController;
import com.examples.android.evento.controller.AuthController;
import com.examples.android.evento.utils.AuthWrapper;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {
    public void l(String msg) {
        Log.d(this.getClass().getSimpleName(), msg + " ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (intent.getAction().equals(Intent.ACTION_VIEW)) {
            Uri uri = Uri.parse("talkfunnel://login?" + intent.getData().getFragment());
            final String access_token = uri.getQueryParameter("access_token");
            String token_type = uri.getQueryParameter("token_type");
            l("URI is " + uri.toString());

            APIController.getService().getAuthVerification(access_token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

                    .subscribe(new Subscriber<AuthWrapper>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            loginFailed();
                        }

                        @Override
                        public void onNext(AuthWrapper authWrapper) {
                            if (authWrapper.getCode() == 200)
                                loginSuccessful(access_token);
                            else
                                loginFailed();

                        }
                    });
        }
    }

    void loginSuccessful(String access_token) {
        AuthController.saveAuthToken(access_token);

        Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
        finish();

    }

    void loginFailed() {
        Toast.makeText(LoginActivity.this, "Oops, something went wrong", Toast.LENGTH_SHORT).show();

        finish();
    }


}
