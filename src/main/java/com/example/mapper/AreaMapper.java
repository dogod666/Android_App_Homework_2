package com.example.mapper;

import com.example.pojo.Area;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: AreaMapper
 * Package: com.example.mapper
 * Description:
 *
 * @Author 谢依雯
 * @Create 2024/10/26 21:11
 */
@Mapper
public interface AreaMapper {
    @Insert("insert into time_position ( ip, col_date, gan_zhi, longitude, latitude) values(#{ip},#{colDate},#{ganZhi},#{longitude},#{latitude})")
    public void insert(Area area);
    public void inserts(List<Area> areas);
     @Select("select id, ip, col_date, gan_zhi, longitude, latitude from time_position")
     public List<Area> find();
}
