package com.hyf.testDemo.testxml;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/20
 */
@Mapper
public interface AreaInfoMapper {

    @Insert("insert into weather_area_info(id,name,name_en,name_py,province,weater_code) values(#{Fid},#{name},#{name_en},#{name_py},#{Fprovince_cn},#{Fweathercn})")
    void insert(RECORD record);
}
