package com.github.howinfun.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 *
 * @author winfun
 * @date 2021/3/18 4:22 下午
 **/
@Data
@XStreamAlias("sitemap")
public class SiteMap {

    @XStreamAlias("loc")
    private String loc;
}
