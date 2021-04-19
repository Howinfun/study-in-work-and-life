package com.github.howinfun.chapter06;

import com.github.howinfun.Trader;
import com.github.howinfun.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author winfun
 * @date 2021/4/7 4:33 下午
 **/
public class CollectorsDemo {

    public static List<Transaction> transactionList = new ArrayList<>(10);
    static {
        Trader brian = Trader.builder().name("Brian").city("Cambridge").build();
        Trader raoul = Trader.builder().name("Raoul").city("Cambridge").build();
        Trader mario = Trader.builder().name("Mario").city("Milan").build();
        Trader alan = Trader.builder().name("ALan").city("Cambridge").build();
        transactionList.add(Transaction.builder().trader(brian).year(2011).value(300).build());
        transactionList.add(Transaction.builder().trader(raoul).year(2012).value(1000).build());
        transactionList.add(Transaction.builder().trader(raoul).year(2011).value(400).build());
        transactionList.add(Transaction.builder().trader(mario).year(2012).value(710).build());
        transactionList.add(Transaction.builder().trader(mario).year(2012).value(700).build());
        transactionList.add(Transaction.builder().trader(alan).year(2012).value(950).build());
    }

    public static void main(String[] args) {
        // 交易记录，根据城市分组
        Map<String,List<Transaction>> result1 =
                transactionList.stream().collect(Collectors.groupingBy(transaction -> transaction.getTrader().getCity()));
        result1.forEach((k,v)->System.out.println("city is: "+k+",transactionList is: "+v));

        // 计算最大交易额
        Optional<Transaction> result2 =
                transactionList.stream().collect(Collectors.maxBy((t1,t2)->t1.getValue()-t2.getValue()));
        result2 = transactionList.stream().collect(Collectors.maxBy(Comparator.comparingInt(Transaction::getValue)));
        result2 = transactionList.stream().max(Comparator.comparingInt(Transaction::getValue));
        result2.ifPresent(System.out::println);

        // 计算最小交易额
        Optional<Transaction> result3 = transactionList.stream().min(Comparator.comparingInt(Transaction::getValue));
        result3.ifPresent(System.out::println);

        // 计算每个城市的总交易额
        Map<String,Integer> result4 =
                transactionList.stream().collect(Collectors.groupingBy(transaction -> transaction.getTrader().getCity(),Collectors.summingInt(Transaction::getValue)));
        result4.entrySet().forEach(entry -> System.out.println("城市："+entry.getKey()+",总交易额："+entry.getValue()));

        // 计算每个城市有多少比交易
        Map<String,Long> result5 =
                transactionList.stream().collect(Collectors.groupingBy(transaction -> transaction.getTrader().getCity(),Collectors.counting()));
        result5.forEach((key, value) -> System.out.println("城市：" + key + ",交易单数：" + value));

        // 获取每个城市的交易员列表
        Map<String, Set<Trader>> result6 =
                transactionList.stream().collect(Collectors.groupingBy(transaction -> transaction.getTrader().getCity(),
                                                                                             Collectors.mapping(Transaction::getTrader, Collectors.toSet())));
        result6.forEach((key,value) -> System.out.println("城市："+key+"交易员："+value.toString()));
    }
}
