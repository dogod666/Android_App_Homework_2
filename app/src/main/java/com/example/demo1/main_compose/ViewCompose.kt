package com.example.demo1.main_compose

import ShowCompose
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.demo1.entity.ResourceDataEntity
import com.example.demo1.my_utiles.BuildExoplayer
import com.example.demo1.my_utiles.DataCacheControl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.random.Random

@OptIn(UnstableApi::class)
@Composable
fun ViewCompose(modifier: Modifier = Modifier) {

    var context= LocalContext.current
    var coroutineScope= rememberCoroutineScope()
    var status= rememberLazyListState()

    var datas by remember {
        Log.i("######1","${Date()}")
        mutableStateOf(DataCacheControl(context))
    }

    LaunchedEffect(Unit) {
        Log.i("######2","${Date()}")
        datas.initControl()
    }
//这个也证明了layz那个item的变化不属于父组件的重组
    Log.i("######3","${datas.getCurrentIndex()}")
//    先暂时写死个列表，为了测试跳转流畅

//    var datas by remember {
//        mutableStateOf(
//            listOf(
//                BuildExoplayer.buildExoplayer(
//                    "https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/10%E6%9C%88%E4%BB%BD%E4%BA%A4%E4%BB%98%E7%9B%AE%E6%A0%87%EF%BC%9A%E5%86%B2%E5%88%BA2%E4%B8%87%E8%BE%86%EF%BC%81%20%E5%9C%A8%E8%BF%99%E9%87%8C%E6%84%9F%E8%B0%A2%E8%BD%A6%E4%B8%BB%E4%BB%AC%E7%9A%84%E4%BD%93%E8%B0%85%EF%BC%8C%E6%88%91%E4%BB%AC%E4%B8%80%E5%AE%9A%E5%8A%A0%E5%BF%AB%E9%80%9F%E5%BA%A6%EF%BC%8C%E4%BA%89%E5%8F%96%E8%AE%A9%E5%A4%A7%E5%AE%B6%E6%97%A9%E6%97%A5%E5%BC%80%E4%B8%8A%E6%A2%A6%E6%83%B3%E4%B9%8B%E8%BD%A6%23%E5%92%8C%E9%9B%B7%E5%86%9B%E4%B8%80%E8%B5%B7%E8%81%8A%E8%BD%A6.mp4",
////                    "https://m4s-segments.oss-cn-shanghai.aliyuncs.com/video_0e9f100e_20241102_171628/output.mpd" ,
//                    context
//                ),
//                BuildExoplayer.buildExoplayer(
//                    "https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/%E9%BC%BB%E5%AD%90%E4%B8%80%E6%95%8F%E6%84%9F%E8%B5%B7%E6%9D%A5%E7%A1%AE%E5%AE%9E%E5%BE%88%E9%9A%BE%E5%8F%97%EF%BC%8C%E5%A4%A7%E5%AE%B6%E8%BF%98%E6%9C%89%E4%BB%80%E4%B9%88%E5%A5%BD%E5%8A%9E%E6%B3%95%E5%90%97%EF%BC%9F%23%E9%9B%B7%E5%86%9B%E7%9A%84%E5%A5%BD%E7%89%A9%E5%88%86%E4%BA%AB%20%23%E9%BC%BB%E7%82%8E.mp4",
////                    "https://m4s-segments.oss-cn-shanghai.aliyuncs.com/video_0e9f100e_20241102_171628/output.mpd",
//                    context
//                ),
//            )
//        )
//    }

//    这种用法特别适用于需要在组件首次加载时执行一次性操作的场景。
//    其实就是开启一个协程，并且不会受到外界的影响
//    LaunchedEffect(Unit){}

    Log.i("=====","${datas.getCurrentIndex()} state change ori coming???")

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)
        .pointerInput(Unit) {
            var accumulatedDrag = 0f
            detectVerticalDragGestures(
                onVerticalDrag = { _, dragAmount ->
                    println("ttttt$dragAmount")
                    accumulatedDrag += dragAmount // 累加拖动距离
                    coroutineScope.launch {
                        // 在拖动时平滑滚动
                        status.scrollBy(-dragAmount)
                    }
                },
//                困扰很久的currentid更新异常问题，原因可能在这里，滑了就扫描并更新了items
//                后面正式开始的时候，由于设置了key，已经出现的组件不会再次进入遍历扫描，因此改变了currentid，下一个视频也不会播放，
//                因为他没有更新状态
                onDragEnd = {
//                    这个协程，和组件生命周期相关，重组不会释放它，
                    coroutineScope.launch {
//                        重加载后，这个协程也会结束？
                        val threshold = 200f // 设置滚动跳转阈值
                        val currentId = datas.getCurrentIndex()
                        when {
                            accumulatedDrag < -threshold -> { // 上滑超过阈值
                                datas.lockPlayer(currentId)
                                datas.setCurrentIndex(currentId+1,true)
                                datas.playCurrentVideo()
                                status.animateScrollToItem(currentId + 1)
                            }
                            accumulatedDrag > threshold -> { // 下滑超过阈值
                                datas.lockPlayer(currentId)
//                                if (currentIndex  > 0) {  这个bug，哇！！找了好久
                                if (currentId > 0) {
                                    datas.setCurrentIndex(currentId-1,false)
                                    datas.playCurrentVideo()
                                    status.animateScrollToItem(currentId - 1)
//                                    重加载时间？TODO
                                } else {
                                    datas.reInitControl()
                                    status.animateScrollToItem(0)
                                }
                            }

                            else -> {
                                // 未超过阈值，则回到当前 item
                                status.animateScrollToItem(currentId)
                            }
                        }
                        // 重置拖动距离
                        accumulatedDrag = 0f
                    }
                }
            )
        },
        userScrollEnabled = false, // 禁用用户直接滚动
//        verticalArrangement = Arrangement.SpaceAround,
//        contentPadding = PaddingValues(16.dp),
        state = status,
    ) {
        Log.i("=====","${datas.getCurrentIndex()} state change coming???")
        datas.playCurrentVideo()
//        之前是因为大组件重组导致状态的刷新的
//        重组机制，真的！！！！
//        逻辑与代码能力啊！！！
        itemsIndexed(items = datas.getDataList()){ index, item->
//            有个问题是组件内外部通信
//            要处理好当前播放的id，当前column的id，当前plyer的id三者关系
//            Log.i("+++++index","${index}")
//            Log.i("+++++CurrentIndex()","${datas.getCurrentIndex()}")
//            if (index==datas.getCurrentIndex()){
//                // 准备播放器
////                ifUseController=true
//                datas.playCurrentVideo()
//            }else{
////                ifUseController=false
////                这里要处理一个很重要的逻辑，视屏切换与组件挂载生命周期问题
//                datas.lockPlayer(index)
//            }
            ShowCompose(datas.getPlayer(index))
            HorizontalDivider(color = Color.Gray)
        }
    }

}

//这里每次加载这个播放的时候，各个播放是加载到这个LazyColumn中了的，我会有个逻辑
//知道当前播放的视频的id要与视频的id对比下来，因为当前播放的和其它未播放的要做不同处理
//所以在划动后，要动态并匹配上当前播放视频的id和视频的id要对应起来




//因为只有在调用 prepare() 后，ExoPlayer 才会开始实际的网络请求和资源下载。
//我们可以使用 ExoPlayer 的事件监听器来确保预加载到指定时间点后再停止预加载。




/*
*
* 在使用 ExoPlayer 播放 DASH (Dynamic Adaptive Streaming over HTTP) 格式的视频时，资源段文件（即包含实际视频数据的片段）的请求时机取决于多个因素，主要包括：

播放准备阶段：当你调用 exoplayer.prepare() 方法时，ExoPlayer 开始准备媒体资源。在这个阶段，ExoPlayer 首先会请求 DASH 清单文件（通常是一个 .mpd 文件）。这个清单文件包含了所有可用的资源段信息，如每个段的 URL、时长等。
初始缓冲：一旦 ExoPlayer 解析了 DASH 清单文件，它会根据当前网络状况选择合适的质量级别，并开始请求第一个资源段以进行初始缓冲。这通常发生在 prepare() 调用之后，当 ExoPlayer 认为有足够的数据可以开始播放时。
播放过程中：在播放过程中，ExoPlayer 会持续地请求新的资源段来补充其内部缓冲区。当播放接近当前已下载的数据末尾时，ExoPlayer 会自动请求下一个资源段。此外，如果网络条件发生变化，ExoPlayer 还可能调整请求的资源段的质量，以适应当前的网络带宽。
seek操作：当用户执行 seek 操作时，ExoPlayer 会根据新的播放位置请求相应的资源段，确保从新的位置开始播放。
*
* */















