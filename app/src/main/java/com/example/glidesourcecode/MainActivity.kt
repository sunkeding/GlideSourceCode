package com.example.glidesourcecode

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.glidesourcecode.process.ProgressInterceptor
import com.example.glidesourcecode.process.ProgressListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MultiDex.install(this);
        setContentView(R.layout.activity_main)
//        val webpUrl="https://img20.360buyimg.com/pop/s1180x940_jfs/t1/136288/38/9623/84080/5f59ffb7Eb7abb588/1fd4c18b3ca5491a.jpg.webp"
//        val normalUrl="https://img.leoao.com/FnwEeBtKY0-iJOGyFGxqNoAJsNdi?imageslim"
//        val normalClipUrl="https://img.leoao.com/FnwEeBtKY0-iJOGyFGxqNoAJsNdi?imageView2/2/w/165/h/165/q/75"
        val webpUrl = "https://img.leoao.com/FnwEeBtKY0-iJOGyFGxqNoAJsNdi?imageslim"
        val progressListener = object : ProgressListener {
            override fun onLoadProgress(isDone: Boolean, progress: Int) {
                Log.d("xxxxx", "isDone:" + isDone)
                Log.d("xxxxx", "progress:" + progress)
            }

            override fun onLoadFailed() {

            }

        }
        ProgressInterceptor.addListener(webpUrl, progressListener)
        val options = RequestOptions()
        options.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
//        options.transform(RoundedCorners(60))
//        options.transform(CenterCrop(), RoundedCorners(60))
//        options.transform(GlideRoundCornersTransformation(this,60,
//            GlideRoundCornersTransformation.CornerType.ALL))
//        Glide.with(this).load(webpUrl).apply(options).into(SimpleTaglet<Bitmap>(iv){
//        })
        Glide.with(this).asBitmap().apply(options)
            .load(webpUrl).listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    ProgressInterceptor.LISTENER_MAP.get(webpUrl)?.onLoadFailed()
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    ProgressInterceptor.removeListener(webpUrl)
                    return false
                }

            }).into(iv)
//        downloadImage(iv)
    }

    fun downloadImage(view: View?) {

        Thread {
            try {
                val url = "https://img.leoao.com/FnwEeBtKY0-iJOGyFGxqNoAJsNdi?imageslim"
                val context: Context = applicationContext
                val target: FutureTarget<File> = Glide.with(context)
                    .load(url)
                    .downloadOnly(100, 100)
                val imageFile: File = target.get()
                runOnUiThread {
                    Log.d("xxxx", "imageFile.getPath():" + imageFile.getPath())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}

