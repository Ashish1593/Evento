package com.examples.android.evento.controller;

/**
 * Created by ankit on 23/1/17.
 */

import android.support.v7.widget.RecyclerView;

import com.examples.android.evento.utils.AuthWrapper;
//import com.examples.android.evento.interfacelistener.TalkfunnelAPI;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import rx.Observable;
import rx.Subscriber;

public class APIController {

    public static APIController apiController;
   // public static TalkfunnelAPI api;

    public static APIController getService() {
        if (apiController == null) {
            apiController = new APIController();

        }
        return apiController;
    }

    public String getAuthHeaderFromToken(String token) {
        return "Bearer " + token;
    }


    public Observable<AuthWrapper> getAuthVerification(final String authToken) {
        return Observable.create(new Observable.OnSubscribe<AuthWrapper>() {
            @Override
            public void call(Subscriber<? super AuthWrapper> subscriber) {
                try {

                    final Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {

                            return false;
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    }).create();

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://talkfunnel.com/api/whoami")
                            .addHeader("Authorization", getAuthHeaderFromToken(authToken))
                            .build();

                    Response response = client.newCall(request).execute();
                    AuthWrapper authWrapper = gson.fromJson(response.body().string(), AuthWrapper.class);

                    subscriber.onNext(authWrapper);
                    subscriber.onCompleted();

                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
