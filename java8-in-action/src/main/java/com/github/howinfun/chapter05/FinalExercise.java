package com.github.howinfun.chapter05;

import com.github.howinfun.Trader;
import com.github.howinfun.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * 最终练习
 * @author winfun
 * @date 2021/4/6 11:44 上午
 **/
public class FinalExercise {

    public static List<Trader> traders = new ArrayList<>(10);
    public static List<Transaction> transactionList = new ArrayList<>(10);
    static {
        Trader brian = Trader.builder().name("Brian").city("Cambridge").build();
        Trader raoul = Trader.builder().name("Raoul").city("Cambridge").build();
        Trader mario = Trader.builder().name("Mario").city("Milan").build();
        Trader alan = Trader.builder().name("ALan").city("Cambridge").build();
        traders.add(brian);traders.add(raoul);traders.add(mario);traders.add(alan);
        transactionList.add(Transaction.builder().trader(brian).year(2011).value(300).build());
        transactionList.add(Transaction.builder().trader(raoul).year(2012).value(1000).build());
        transactionList.add(Transaction.builder().trader(raoul).year(2011).value(400).build());
        transactionList.add(Transaction.builder().trader(mario).year(2012).value(710).build());
        transactionList.add(Transaction.builder().trader(mario).year(2012).value(700).build());
        transactionList.add(Transaction.builder().trader(alan).year(2012).value(950).build());
    }

    public static void main(String[] args) {
        //(1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
        List<Transaction> result1 = transactionList.stream().sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
        System.out.println("找出2011年发生的所有交易，并按交易额排序（从低到高）:");
        result1.forEach(System.out::println);
        // (2) 交易员都在哪些不同的城市工作过？
        List<String> result2 = traders.stream().map(Trader::getCity).distinct().collect(Collectors.toList());
        System.out.println("交易员都在哪些不同的城市工作过:");
        result2.forEach(System.out::println);
        // (3) 查找所有来自于剑桥的交易员，并按姓名排序。
        List<Trader> result3 =
                traders.stream().filter(t -> "Cambridge".equals(t.getCity())).sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
        System.out.println("查找所有来自于剑桥的交易员，并按姓名排序:");
        result3.forEach(System.out::println);
        // (4) 返回所有交易员的姓名字符串，按字母顺序排序。
        String result4 = traders.stream().map(Trader::getName).sorted().collect(joining(","));
        System.out.println("返回所有交易员的姓名字符串，按字母顺序排序:"+result4);
        // (5) 有没有交易员是在米兰工作的？
        boolean result5 = traders.stream().anyMatch(t -> "Milan".equals(t.getCity()));
        System.out.println("有没有交易员是在米兰工作的："+result5);
        // (6) 打印生活在剑桥的交易员的所有交易额。
        Integer result6 =
                transactionList.stream().filter(t->"Cambridge".equals(t.getTrader().getCity())).map(Transaction::getValue).reduce(0,
                                                                                                             Integer::sum);
        System.out.println("打印生活在剑桥的交易员的所有交易额: "+result6);
        // (7) 所有交易中，最高的交易额是多少？
        Integer result7 = transactionList.stream().map(Transaction::getValue).reduce(Integer::max).orElse(null);
        System.out.println("所有交易中，最高的交易额是多少: "+result7);
        // (8) 找到交易额最小的交易。
        Integer result8 = transactionList.stream().map(Transaction::getValue).reduce(Integer::min).orElse(null);
        System.out.println("所有交易中，最小的交易额是多少: "+result8);
    }
}
