package com.example.mapper;

import com.example.pojo.Video;
import com.example.pojo.WhatRecord;
import com.example.result.Result;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: VidioMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author 谢依雯
 * @Create 2024/11/3 13:43
 */
@Mapper
public interface VideoMapper {
    @Select("select mpd_path from learning_android_game.videos where video_id=#{id}")
    public  String byId(int id) ;
    @Select("Select mpd_path from learning_android_game.videos where video_id in ${ids}")
    public List<String> byIds(List<Integer> ids);
    @Insert("insert into watch_record ( ip, video_id, watch_time, created_time) values (#{ip},#{videoId},#{watchTime},#{created_time})")
    public WhatRecord watch(WhatRecord whatRecord);
    @Select("select w.ip,w.video_id,w.watch_time,w.created_time,v.title from watch_record as w,videos as v where w.video_id=v.video_id")
    public WhatRecord find();

    @Select("Select * from learning_android_game.videos")
    public List<Video> all();
//    TODO 这里语句还要加个数据库的名字？？！我不太了解哈，没这样玩过
}
