package com.example.demo1.my_utiles

import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.source.MediaSource

class BuildExoplayer {

    companion object{

        @OptIn(UnstableApi::class)
        fun buildExoplayer(dashUri:String,context:Context):ExoPlayer{

//            这里重写了里面的一个缓存加载控制函数,自定义缓存加载
//            var localControl=CustomLoadControl()
            var loadControl=DefaultLoadControl.Builder()
                .setBufferDurationsMs(5000, 10000, 2500,5000 )
                .build()

//            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
//            val mediaSource: MediaSource =
//                DashMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(dashUri))
//            var exoplayer = ExoPlayer
//                .Builder(context)
//                .setLoadControl(loadControl)
//                .build()

            val exoplayer = ExoPlayer.Builder(context).setLoadControl(loadControl).build().apply {
                setMediaItem(MediaItem.fromUri(dashUri))
                prepare()
            }

//            准备到能够播放的量就播放
//            先关掉播放
            exoplayer.playWhenReady=false
//            exoplayer.setMediaSource(mediaSource)
//            开始加载
            exoplayer.prepare()

//            我这个自定义监听器会不会有什么bug呢？？！！
            exoplayer.addListener(object : Player.Listener {

                override fun onIsLoadingChanged(isLoading: Boolean) {
                    println("loading:${exoplayer.bufferedPosition}")
//                    if (exoplayer.bufferedPosition >= 10 * 1000) { // 预加载 10 秒
////                        限制加载
//                        println("shut up!!! ${exoplayer.bufferedPosition}")
////                        localControl.setPausePreload(true)
////                        在预处理完成或者切换为当前视频播放的时候会移掉这个监听，并切换原本的参数,只是暂停了加载和播放
//                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    println("now has :${exoplayer.bufferedPosition}")
//                    localControl.setPausePreload(false)
//                    exoplayer.removeListener(this)
                }

            })
//            注意启动播放不要忘写了
            return exoplayer
        }
    }

}


/*
*
* 有默认实现类DefaultLoadControl，里面有个方法shouldContinueLoading，每次缓冲的时候都会调用这个方法判断当前状态是否需要缓冲
* */

/*
*
* 监听器这里可以有个总结了!!
*
* */