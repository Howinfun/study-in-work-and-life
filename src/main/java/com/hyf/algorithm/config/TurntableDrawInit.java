package com.hyf.algorithm.config;

import com.hyf.algorithm.entity.TurntableDraw;
import com.hyf.algorithm.entity.TurntablePrize;
import com.hyf.algorithm.entity.TurntableRecord;
import com.hyf.algorithm.mapper.TurntableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
@Component
@Scope(SCOPE_SINGLETON)
public class TurntableDrawInit {

    @Autowired
    private TurntableMapper turntableMapper;

    private final TreeMap<Double,TreeMap<Double,TurntablePrize>> treeMap = new TreeMap<>();

    private TurntableDrawInit(){}

    @PostConstruct
    public void init(){
        List<TurntableDraw> drawList = turntableMapper.getDraw();
        if (drawList != null && drawList.size() > 0){
            for (TurntableDraw draw : drawList) {
                System.out.println();
                System.out.println("奖项："+draw);
                TreeMap<Double,TurntablePrize> drawTreeMap = new TreeMap<>();
                List<TurntablePrize> prizeList = turntableMapper.getPrizeByDraw(draw.getId());
                System.out.print("奖品：");
                for (TurntablePrize prize : prizeList) {
                    System.out.print(prize);
                    drawTreeMap.put(prize.getWeight(),prize);
                }
                treeMap.put(draw.getWeight(),drawTreeMap);
            }
        }
    }

    public TurntablePrize turntableDraw(String phone){
        TurntablePrize prize;
        // 如果还有奖项则继续抽奖
        synchronized (this.treeMap){
            if (treeMap.size() > 0){
                // 奖项随机数
                Double random = treeMap.lastKey()*Math.random();
                SortedMap<Double, TreeMap<Double,TurntablePrize>> prizeMap = treeMap.tailMap(random,false);
                // 抽中的奖项
                Double drawKey = prizeMap.firstKey();
                TreeMap<Double,TurntablePrize> draw = prizeMap.get(drawKey);
                // 奖品随机数
                Double prizeRandom = draw.lastKey()*Math.random();
                SortedMap<Double,TurntablePrize> resultMap = draw.tailMap(prizeRandom,false);
                // 抽中的奖品
                Double prizeKey = resultMap.firstKey();
                prize = resultMap.get(prizeKey);
                // 插入抽象记录
                TurntableRecord record = new TurntableRecord();
                record.setPrizeId(prize.getId());
                record.setPrizeName(prize.getPrizeName());
                record.setPhone(phone);
                turntableMapper.insertRecord(record);
                // 奖项的奖品数减一
                turntableMapper.delPrizeNumByDraw(prize.getDrawId());
                // 移除抽中的奖品
                treeMap.get(drawKey).remove(prizeKey);
                // 判断奖项是否还有奖品，如果没有则将奖项也移除
                if (treeMap.get(drawKey).size() <=0 ){
                    treeMap.remove(drawKey);
                }
            }else{
                prize = null;
            }
        }
        return prize;
    }
}
