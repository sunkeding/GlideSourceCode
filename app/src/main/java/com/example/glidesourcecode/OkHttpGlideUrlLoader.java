package com.example.glidesourcecode;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

public class OkHttpGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    private OkHttpClient okHttpClient;

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl glideUrl, int width, int height, @NonNull Options options) {
        return null;
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return false;
    }

//    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
//
//        private OkHttpClient client;
//
//        public Factory() {
//        }
//
//        public Factory(OkHttpClient client) {
//            this.client = client;
//        }
//
//        private synchronized OkHttpClient getOkHttpClient() {
//            if (client == null) {
//                client = new OkHttpClient();
//            }
//            return client;
//        }
//
//        @Override
//        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
//            return new OkHttpGlideUrlLoader(getOkHttpClient());
//        }
//
//        @Override
//        public void teardown() {
//        }
//    }
//
//    public OkHttpGlideUrlLoader(OkHttpClient client) {
//        this.okHttpClient = client;
//    }
//
//    @Override
//    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
//        return new OkHttpFetcher(okHttpClient, model);
//    }
}
