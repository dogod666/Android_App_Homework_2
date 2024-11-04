package com.example.demo1.my_utiles

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.Renderer

//这里我重写了控制器
@OptIn(UnstableApi::class)
class CustomLoadControl : DefaultLoadControl() {

    private var pausePreload = false


    fun setPausePreload(preload: Boolean) {
        pausePreload = preload
    }

    @OptIn(UnstableApi::class)
    override fun shouldContinueLoading(
        parameters: LoadControl.Parameters
    ): Boolean {
        // 如果 shouldPreloadNextVideo 为 false，则停止预加载
        if (pausePreload) {
            return false
        }

        // 否则使用默认的逻辑
        return super.shouldContinueLoading(parameters)
    }
}