package com.example.demo1.my_utiles

import android.content.Context
import android.util.Log
import android.util.SparseArray
import androidx.media3.exoplayer.ExoPlayer
import com.example.demo1.entity.ResourceDataEntity

class DataCacheControl(val context: Context) {

    private val players = SparseArray<ExoPlayer>()
    private var currentIndex = 0
    private val windowSize = 10
    private val preCacheCount = 5
    private val checkCacheCount = 4

    // 假设这是你的视频URL列表
    private val videoUrls =
        listOf<ResourceDataEntity>(
            ResourceDataEntity(0,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_6f51a830_20241103_232038.mp4"),
            ResourceDataEntity(1,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_53bca88b_20241103_232111.mp4"),
            ResourceDataEntity(2,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_834525a2_20241103_232134.mp4"),
            ResourceDataEntity(3,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_6f51a830_20241103_232038.mp4"),
            ResourceDataEntity(4,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_53bca88b_20241103_232111.mp4"),
            ResourceDataEntity(5,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_834525a2_20241103_232134.mp4"),
            ResourceDataEntity(6,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_6f51a830_20241103_232038.mp4"),
            ResourceDataEntity(7,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_53bca88b_20241103_232111.mp4"),
            ResourceDataEntity(8,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_834525a2_20241103_232134.mp4"),
            ResourceDataEntity(9,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_6f51a830_20241103_232038.mp4"),
            ResourceDataEntity(10,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_53bca88b_20241103_232111.mp4"),
            ResourceDataEntity(11,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_834525a2_20241103_232134.mp4"),
            ResourceDataEntity(12,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_6f51a830_20241103_232038.mp4"),
            ResourceDataEntity(13,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_53bca88b_20241103_232111.mp4"),
            ResourceDataEntity(14,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_834525a2_20241103_232134.mp4"),
            ResourceDataEntity(15,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_6f51a830_20241103_232038.mp4"),
            ResourceDataEntity(16,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_53bca88b_20241103_232111.mp4"),
            ResourceDataEntity(17,"加个标题怎么样！！","https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/video_834525a2_20241103_232134.mp4"),
            )

    fun getDataList(): List<ResourceDataEntity> {
        return videoUrls
    }

//    预处理数据
    private fun preOptionData(){
        videoUrls.forEachIndexed{index,item->
            item.index=index
        }
    }

    // 获取当前播放的视频的索引
    fun getCurrentIndex(): Int {
        return currentIndex
    }

    // 设置当前播放的视频
    fun setCurrentIndex(index: Int,isScrollingDown: Boolean=true) {
        if (index < 0 || index >= videoUrls.size) {
            return
//            throw IllegalArgumentException("Invalid index")
        }
        Log.i("@@@@@@","$index")
        currentIndex = index
        updateCache(isScrollingDown)
        Log.i("@@@@@@","$currentIndex")
    }

    // 更新缓存
    private fun updateCache(isScrollingDown: Boolean) {
        // 清除不在窗口内的缓存
        clearOutsideWindowCache(isScrollingDown)

        // 预缓存当前视频之后的五个视频
        for (i in 0..preCacheCount) {
            val index = currentIndex + i
            if (index < videoUrls.size && players[index]==null) {
                createPlayer(index)
            }
        }

        // 检查当前视频之前的四个视频是否有缓存，如果没有则进行预缓存
        for (i in 1..checkCacheCount) {
            val index = currentIndex - i
            if (index >= 0 && players[index]==null) {
                createPlayer(index)
            }
        }
    }

    // 清除不在窗口内的缓存
//    这里的上下是窗口的上下哦
    private fun clearOutsideWindowCache(isScrollingDown: Boolean) {
        val start = maxOf(0, currentIndex - checkCacheCount)
        val end = minOf(videoUrls.size - 1, currentIndex + preCacheCount)

        if (isScrollingDown) {
            // 向下滚动时，释放窗口最前面的一个视频资源
            if (start > 0) {
                Log.i("======","${players[start-1]==null}")
                Log.i("======","release${start-1}")
                players[start - 1]?.release()
                players.remove(start - 1)
            }
        } else {
            // 向上滚动时，释放窗口最后面的一个视频资源
            if (end < videoUrls.size - 1) {
                Log.i("======","release${end+1}")
                players[end + 1]?.release()
                players.remove(end + 1)
            }
        }
    }

    // 播放当前视频
    fun playCurrentVideo() {
        val player = players[currentIndex] ?: createPlayer(currentIndex)
        player.prepare()
        player.repeatMode = ExoPlayer.REPEAT_MODE_ONE
        player.playWhenReady = true
        player.play()
    }

    fun lockPlayer(index: Int){
        players[index]?.pause()
    }

//    获取视频资源
    fun getPlayer(index: Int):ExoPlayer{
        return players[index]?:createPlayer(index)
    }

    // 创建一个新的 ExoPlayer 实例并预缓存
    private fun createPlayer(index: Int): ExoPlayer {
        Log.i("=======","${index}building!!!")
        val player = BuildExoplayer.buildExoplayer(videoUrls[index].url,context)
        players.put(index, player)
        return player
    }

    // 释放所有资源
    fun releaseAllPlayers() {
        for (i in 0 until players.size()) {
            players.valueAt(i)?.release()
        }
        players.clear()
    }

    fun initControl() {
        Log.i("^^^^^^","$currentIndex")
        currentIndex=0
        preOptionData()
        updateCache(true)
    }

    fun reInitControl() {
        Log.i("^^^^^^","$currentIndex")
        currentIndex=0
        releaseAllPlayers()
        updateCache(true)
    }
}

/*
*
* if (index==data.id){
        // 准备播放器
        ifUseController=true
        exoplayer.prepare()
//                item.seekTo(0) // 再次确保从正确的起始点开始,这个会清缓存！！！！
//        单个时评循环播放
        exoplayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
        exoplayer.playWhenReady = true
        exoplayer.play()
    }else{
        ifUseController=false
//                这里要处理一个很重要的逻辑，视屏切换与组件挂载生命周期问题
        exoplayer.pause()
    }
*
* */