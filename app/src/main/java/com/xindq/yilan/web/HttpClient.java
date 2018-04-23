package com.xindq.yilan.web;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import okio.BufferedSink;

public class HttpClient {
    private static HttpClient instance;
    private OkHttpClient client;
    private Request.Builder builder;

    private HttpClient() {
        client=new OkHttpClient();
        builder=new Request.Builder();
    }

    public static HttpClient getInstance(){
        if (instance==null){
            synchronized (HttpClient.class){
                if (instance==null){
                    instance=new HttpClient();
                }
            }
        }
        return instance;
    }

    public void get(String url,OnHttpResponse onHttpResponse){
        Request request = builder.get().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (onHttpResponse!=null&&response.isSuccessful()) {
                    onHttpResponse.onHttpResponse(response.body().string());
                }
            }
        });
    }

//    public void post(String url,OnHttpResponse onHttpResponse){
//        RequestBody.
//        Request request = builder.post().url(url).build();
//    }

    public interface OnHttpResponse {
        void onHttpResponse(String reponse);
    }
}
