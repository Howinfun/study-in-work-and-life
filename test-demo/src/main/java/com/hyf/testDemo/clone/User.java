package com.hyf.testDemo.clone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/17
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Cloneable{

    private Long id;
    private String name;
    private Address address;

    @Override
    protected User clone() throws CloneNotSupportedException {

        User user = (User) super.clone();
        user.setAddress(this.address.clone());
        return user;
    }
}
