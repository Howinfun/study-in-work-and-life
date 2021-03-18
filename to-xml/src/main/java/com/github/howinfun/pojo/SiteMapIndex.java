package com.github.howinfun.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

import java.util.List;

/**
 *
 * @author winfun
 * @date 2021/3/18 4:20 下午
 **/
@Data
@JacksonXmlRootElement(localName = "sitemapindex")
public class SiteMapIndex {

    @JacksonXmlProperty(localName = "xmlns",isAttribute=true)
    private String xmlns;
    @JacksonXmlText
    private String desc;
    @JacksonXmlElementWrapper(useWrapping =false)
    List<SiteMap> siteMap;
}
