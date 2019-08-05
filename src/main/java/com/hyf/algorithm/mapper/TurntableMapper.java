package com.hyf.algorithm.mapper;

import com.hyf.algorithm.entity.TurntableDraw;
import com.hyf.algorithm.entity.TurntablePrize;
import com.hyf.algorithm.entity.TurntableRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
@Mapper
@Component
public interface TurntableMapper {

    @Select("select * from turntable_draw where prize_num >0")
    List<TurntableDraw> getDraw();

    @Update("update turntable_draw " +
            "set prize_num = prize_num-1 " +
            "where id = #{drawId}")
    void delPrizeNumByDraw(@Param("drawId") Integer drawId);

    @Select("select * from turntable_prize where draw_id = #{drawId} and id not in (select prize_id from turntable_record)")
    List<TurntablePrize> getPrizeByDraw(@Param("drawId") Integer drawId);

    @Insert("insert into turntable_record(prize_id,prize_name,phone) values(#{prizeId},#{prizeName},#{phone})")
    void insertRecord(TurntableRecord record);
}
