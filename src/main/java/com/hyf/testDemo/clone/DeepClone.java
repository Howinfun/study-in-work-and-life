package com.hyf.testDemo.clone;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Howinfun
 * @desc Java 实现深克隆的N种方式。
 * @date 2020/7/17
 */
public class DeepClone {

    public static void main(String[] args) throws Exception{

        /**
         * 方式一：让类实现 Cloneable 接口，并重写 Object 类中的 clone() 方法，重写时直接调用 Object 的 clone() 方法即可。
         * 因为 Object 的 clone() 方法是 native 本地方法，所以我们无法看到代码，但是它的介绍里面有一句非常重要的话：x.clone() != x，这就表明 Object#clone() 是深克隆。
         */
        User user1 = new User(1L,"Howinfun",new Address(1L,"佛山"));
        User user2 = user1.clone();
        System.out.println(user1); // com.hyf.testDemo.clone.User@7291c18f
        System.out.println(user2); // com.hyf.testDemo.clone.User@34a245ab

        /**
         * 方式二：最耿直的深克隆方式，利用构造函数创建一个新的实例对象
         */
        User user3 = new User(user1.getId(),user1.getName(),new Address(user1.getAddress().getId(),user1.getAddress().getArea()));
        System.out.println(user3); // com.hyf.testDemo.clone.User@7cc355be

        /**
         * 方式三：利用 JSON 来做深克隆。我们可以将原始对象格式化成 json格式字符串，然后用 json 将字符串反序列化成实例对象，此时这个实例对象是新的和旧的没有任何关系
         */
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(user1);
        User user4 = mapper.readValue(jsonStr, User.class);
        System.out.println(user4); // com.hyf.testDemo.clone.User@71318ec4
    }
}
