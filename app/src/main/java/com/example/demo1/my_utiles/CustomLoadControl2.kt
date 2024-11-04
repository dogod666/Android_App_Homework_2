package com.example.demo1.my_utiles

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl

@OptIn(UnstableApi::class)
fun CustomLoadControl2(
    minBufferMs: Int,
    maxBufferMs: Int,
    bufferForPlaybackMs: Int,
    bufferForPlaybackAfterRebufferMs: Int
): DefaultLoadControl {
    return DefaultLoadControl.Builder()
        .setBufferDurationsMs(minBufferMs, maxBufferMs, bufferForPlaybackMs, bufferForPlaybackAfterRebufferMs)
        .build()
}