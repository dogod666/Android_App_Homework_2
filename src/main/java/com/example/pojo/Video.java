package com.example.pojo;

import lombok.Data;

/**
 * ClassName: Vidio
 * Package: com.example.pojo
 * Description:
 *
 * @Author 谢依雯
 * @Create 2024/11/3 13:53
 */
@Data
public class Video {
    private int videoId;
    private String title;
    private String url;
//    我要提醒老哥，数据库字段名不能随便改，要讨论后
}
