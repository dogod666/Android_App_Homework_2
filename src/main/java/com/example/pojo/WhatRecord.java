package com.example.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * ClassName: WhatRecord
 * Package: com.example.pojo
 * Description:
 *
 * @Author 谢依雯
 * @Create 2024/11/3 22:09
 */
@Data
public class WhatRecord {
    private String ip;
    private int videoId;
    private int watchTime;
    private LocalDateTime createdTime;
    private String title;

}
