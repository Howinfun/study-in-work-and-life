package com.hyf.algorithm.抽奖概率.config;

import com.hyf.algorithm.抽奖概率.entity.TurntableDraw;
import com.hyf.algorithm.抽奖概率.entity.TurntablePrize;
import com.hyf.algorithm.抽奖概率.entity.TurntableRecord;
import com.hyf.algorithm.抽奖概率.mapper.TurntableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

/**
 * @author Howinfun
 * @desc 抽奖工具，单例
 * @date 2019/8/5
 */
@Component
@Scope(SCOPE_SINGLETON)
public class TurntableDrawUtils {

    @Autowired
    private TurntableMapper turntableMapper;
    /** 转盘抽奖TreeMap，因为TurntableDrawInit是单例的，所以treeMap全局只有一份 */
    private final TreeMap<Double,TreeMap<Double,TurntablePrize>> treeMap = new TreeMap<>();
    /** 私有化构造函数 */
    private TurntableDrawUtils(){}

    /**
     * 初始化
     */
    //@PostConstruct
    public void init(){
        List<TurntableDraw> drawList = turntableMapper.getDraw();
        if (drawList != null && drawList.size() > 0){
            // 遍历奖项
            for (TurntableDraw draw : drawList) {
                TreeMap<Double,TurntablePrize> drawTreeMap = new TreeMap<>();
                List<TurntablePrize> prizeList = turntableMapper.getPrizeByDraw(draw.getId());
                // 遍历奖品
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
        // 加锁，防止并发问题
        synchronized (this.treeMap){
            // 如果还有奖项则进行抽奖
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
