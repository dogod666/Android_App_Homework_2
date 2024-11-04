import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

/*
*author:guyueyuan
*createTime:2024/11/2
*/

@Composable
fun dash_base() {
    val context = LocalContext.current
    val dashUri = Uri.parse("https://android-pre-vedio.oss-cn-shanghai.aliyuncs.com/10%E6%9C%88%E4%BB%BD%E4%BA%A4%E4%BB%98%E7%9B%AE%E6%A0%87%EF%BC%9A%E5%86%B2%E5%88%BA2%E4%B8%87%E8%BE%86%EF%BC%81%20%E5%9C%A8%E8%BF%99%E9%87%8C%E6%84%9F%E8%B0%A2%E8%BD%A6%E4%B8%BB%E4%BB%AC%E7%9A%84%E4%BD%93%E8%B0%85%EF%BC%8C%E6%88%91%E4%BB%AC%E4%B8%80%E5%AE%9A%E5%8A%A0%E5%BF%AB%E9%80%9F%E5%BA%A6%EF%BC%8C%E4%BA%89%E5%8F%96%E8%AE%A9%E5%A4%A7%E5%AE%B6%E6%97%A9%E6%97%A5%E5%BC%80%E4%B8%8A%E6%A2%A6%E6%83%B3%E4%B9%8B%E8%BD%A6%23%E5%92%8C%E9%9B%B7%E5%86%9B%E4%B8%80%E8%B5%B7%E8%81%8A%E8%BD%A6.mp4")


//    // Create a player instance.
////    这行代码创建了一个 ExoPlayer 的实例。ExoPlayer.Builder 是一个构造器类，允许你以链式调用的方式设置播放器的各种属性
//    val player = ExoPlayer.Builder(context).build()
//    // Set the media item to be played.
////    我们设置了播放器将要播放的媒体项。MediaItem 类代表了一个单独的媒体资源，可以是一个视频、音频或任何其他类型的媒体文件。
//    player.setMediaItem(MediaItem.fromUri(dashUri))
//    // Prepare the player.
////    用 prepare() 方法会准备播放器开始播放媒体。在这个过程中，播放器会加载媒体元数据，并为实际播放做准备
//    player.prepare()

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(dashUri))
            prepare()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
//        那么 AndroidView 是必需的。这是因为 Jetpack Compose 和传统的 Android 视图系统是两个不同的 UI 框架，它们之间不能直接互操作
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Preview
@Composable
fun dash_basePre() {
    dash_base()
}