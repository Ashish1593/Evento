package com.examples.android.evento.controller;

/**
 * Created by ankit on 23/1/17.
 */


//import com.examples.android.evento.schedule.ScheduleHelper;
////import com.examples.android.evento.schedule.Space;
import com.examples.android.evento.utils.AuthWrapper;
import com.examples.android.evento.interfacelistener.TalkfunnelAPI;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

        import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;


public class APIController {

    public static APIController apiController;
    public static TalkfunnelAPI api;

    public static APIController getService() {
        if (apiController == null) {
            apiController = new APIController();
            apiController.api = apiController.createController();
        }
        return apiController;
    }


    public  String getAuthHeaderFromToken(String token) {
        return "Bearer "+token;
    }



    public TalkfunnelAPI createController() {
        return createController(null);
    }

    public TalkfunnelAPI createController(String spaceUrl) {

        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                // return f.getDeclaringClass() == RealmObject.class;
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();


        String baseUrl;
        if (spaceUrl != null)
            baseUrl = spaceUrl;
        else
            baseUrl = "https://talkfunnel.com/";
        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl);

        return builder.build().create(TalkfunnelAPI.class);
    }


    public Observable<AuthWrapper> getAuthVerification(final String authToken) {
        return Observable.create(new Observable.OnSubscribe<AuthWrapper>() {
            @Override
            public void call(Subscriber<? super AuthWrapper> subscriber) {
                try {

                    final Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            // return f.getDeclaringClass() == RealmObject.class;
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

//    public Observable<List<Session>> getSessionsBySpaceId(String spaceId) {
//
//        //final Space space = SpaceController.getSpaceById_Cold(Realm.getDefaultInstance(), spaceId);
//
//
//        return Observable.create(new Observable.OnSubscribe<List<Session>>() {
//            @Override
//            public void call(Subscriber<? super List<Session>> subscriber) {
//                try {
//                    final Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
//                        @Override
//                        public boolean shouldSkipField(FieldAttributes f) {
//                          //  return f.getDeclaringClass() == RealmObject.class;
//                        return false;
//                        }
//
//                        @Override
//                        public boolean shouldSkipClass(Class<?> clazz) {
//                            return false;
//                        }
//                    }).create();
//                    final OkHttpClient client = new OkHttpClient();
//                    final Request request = new Request.Builder()
//                            .url("https://50p.talkfunnel.com/2017/json")
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    List<Session> sessions = new ArrayList<>();
//                    JSONArray schedule = new JSONArray(jsonObject.optString("schedule", "[]"));
//
//                    for (int i = 0; i < schedule.length(); i++) {
//                        JSONArray slots = schedule.getJSONObject(i).getJSONArray("slots");
//                        for (int k = 0; k < slots.length(); k++) {
//                            sessions.addAll(Arrays.asList(gson.fromJson(slots.getJSONObject(k).optString("sessions", "[]"), Session[].class)));
//                        }
//                    }
////
//                    for (Session s : sessions) {
//                     //   s.setSpace(space);
//                    }
//
//                    HashMap<Integer, List<Session>> hashMap = ScheduleHelper.getDayOfYearMapFromSessions(sessions);
//                    for (Integer key : hashMap.keySet()) {
//                        ScheduleHelper.addDimensToSessions(hashMap.get(key));
//                    }
//
//                    subscriber.onNext(sessions);
//                    subscriber.onCompleted();
//
//                } catch (Exception e) {
//                    subscriber.onError(e);
//                }
//            }
//        });
//    }

}
