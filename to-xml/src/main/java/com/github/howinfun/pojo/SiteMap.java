package com.github.howinfun.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 *
 * @author winfun
 * @date 2021/3/18 4:22 下午
 **/
@Data
public class SiteMap {

    @JacksonXmlProperty(localName = "loc",namespace = "")
    private String loc;
}
