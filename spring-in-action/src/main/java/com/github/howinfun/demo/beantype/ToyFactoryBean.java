package com.github.howinfun.demo.beantype;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author: winfun
 * @date: 2021/6/19 5:07 下午
 **/
@Data
@Accessors(chain = true)
public class ToyFactoryBean implements FactoryBean<Toy> {

    /**
     * 可传入Child的实例，获取小孩喜欢的玩具
     */
    private Child child;

    @Override
    public Toy getObject() throws Exception {
        if (child.getWantBuy().equals("ball")){
            return new Ball("ball");
        }else {
            return new Car("car");
        }
    }

    @Override
    public Class<?> getObjectType() {
        return Toy.class;
    }
}
