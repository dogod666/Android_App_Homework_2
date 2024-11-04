import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.demo1.entity.ResourceDataEntity
import com.example.demo1.my_utiles.BuildExoplayer

/*
*author:guyueyuan
*createTime:2024/11/2
*/

@OptIn(UnstableApi::class)
@Composable
fun ShowCompose(exoplayer:ExoPlayer) {

//    确保重组不变嘛就是重组不会重新加载，不会存在上一个没销毁，又建立新的
//    var ifUseController by remember {
//        mutableStateOf(false)
//    }
    var context=LocalContext.current

//    Log.i("-----","children ${data.id} and cur ${index}")

    var ifController=false

    LaunchedEffect(Unit) {
        exoplayer.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_IDLE,
                    Player.STATE_BUFFERING -> {
                        // 当播放器处于空闲或缓冲状态时，隐藏控制器
                        ifController = false
                    }
                    Player.STATE_READY,
                    Player.STATE_ENDED -> {
                        // 当播放器准备好或播放结束时，显示控制器
                        ifController = true
                    }
                }
            }
        })
    }


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var player by remember {
        mutableStateOf(
            PlayerView(context).apply {
//                    这是一个作用域扩展函数
                player=exoplayer
                useController = ifController // 是否显示控制器
//                    要去充分认识重加载和组件刷新
            }
        )
    }
//  这样，充分利用了重刷新与remember的策略，实现想要的结果--播放视频的时候可以有控制器，不能播放的时候不要控制器，
//    因为使用的暂停，会让视频开始看起来是显示了控制器是黑的
//    player.useController=ifUseController

    // 使用 AndroidView 显示 PlayerView
    Box(
        modifier = Modifier
            .height(screenHeight)
            .width(screenWidth)
//            .background(color = Color.Black),
    ) {
        AndroidView(
            factory = { context ->
                player
            },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(screenHeight-10.dp)
        )

        Text("加个标题怎么样！！", color = Color.White, fontWeight = FontWeight.Bold, style = TextStyle(
            fontSize = TextUnit(20f, TextUnitType.Sp),
        ),
            modifier = Modifier
                .align(Alignment.BottomStart) // 将文本对齐到底部中央
                .fillMaxWidth() // 使文本背景填充整个宽度
                .offset(x=(20).dp,y = (-40).dp)
        )
    }

//     当组件销毁时释放 ExoPlayer 资源
//    就是说，我的缓存优化管理全权绑定了交给了这里的layoutColumn的缓存机制
    DisposableEffect(Unit) {
        onDispose {
//            Log.i("==--##","xhl${data.id}")
            exoplayer.pause()
        }
    }
}


/*
*滚动这里的问题，确实可能是要么layout被遮挡了，
* 要么就是layout滚动冲突了
* （经过室友的禁用滑动的方案检查感觉是滚动冲突了，但是看下一步具体排错吧）
* 最后选择了室友的一个很好的方案，禁用滚动，使用监听事件过度，对于滑动丝滑过度，我们使用了layout的scrollby中间强控，这个思路很妙
*
* .pointerInput(Unit){
//            记录滑动距离的
                    Log.i("====","checked？？")
                    var dragAmount=0f
                    detectDragGestures(
                        onDragStart = {
                            Log.i("====","start？？")
                            dragAmount=0f
                        },
                        onDragEnd = {
                            Log.i("====","end？？")
                            if (dragAmount<-50){
                                if(currentId!=0){
                                    currentId--
                                }
                            }else if (dragAmount>50){
                                currentId++
                            }else{
                                currentId=currentId
                            }
                            coroution.launch {
                                status.scrollToItem(currentId)
                            }
//                    我这样写，组件会重组吗？
                        },
                        onDragCancel = {
                            Log.i("====","cancel？？")
                        }
                    ) { change, dragAmount ->
                        Log.i("====","whats this？？")
                    }
                },
*
*
*
*
* .pointerInput(Unit) {
//            记录滑动距离的
                Log.i("====", "checked？？")
                var dragAmount = 0f
                detectDragGestures(
                    onDragStart = {
                        Log.i("====", "start？？")
                        dragAmount = 0f
                    },
                    onDragEnd = {
                        Log.i("====", "end？？")
                        if (dragAmount < -50) {
//                        if(currentId!=0){
//                            currentId--
//                        }
                            Log.i("====", "fro？？")
                        } else if (dragAmount > 50) {
//                        currentId++
                            Log.i("====", "next？？")
                        } else {
//                        currentId=currentId
                            Log.i("====", "equ？？")
                        }
//                    coroution.launch {
//                        status.scrollToItem(currentId)
//                    }
//                    我这样写，组件会重组吗？
                    },
                    onDragCancel = {
//                    Log.i("====","cancel？？")
                    }
                ) { change, mount ->
//                Log.i("====","whats this？？")
                    dragAmount = mount.y
                    Log.i("====","$dragAmount")
                }
            },
*
*
* */