package com.github.howinfun.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

/**
 *
 * @author winfun
 * @date 2021/3/18 4:20 下午
 **/
@Data
@XStreamAlias("sitemapindex")
public class SiteMapIndex {

    @XStreamAsAttribute
    private String xmlns;
    @XStreamImplicit
    private List<SiteMap> siteMap;
}
