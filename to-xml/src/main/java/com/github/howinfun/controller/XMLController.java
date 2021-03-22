package com.github.howinfun.controller;

import com.github.howinfun.pojo.SiteMap;
import com.github.howinfun.pojo.SiteMapIndex;
import com.github.howinfun.writer.NoneEscapeAppDriver;
import com.thoughtworks.xstream.XStream;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * xml controlelr
 * @author winfun
 * @date 2021/3/18 3:56 下午
 **/
@RequestMapping(value = "/test")
@RestController
public class XMLController {

    @GetMapping(value = "/sitemap.xml",produces = MediaType.APPLICATION_XML_VALUE)
    public SiteMapIndex getSiteMapIndex(){

        SiteMapIndex siteMapIndex = new SiteMapIndex();
        SiteMap siteMap1 = new SiteMap();
        siteMap1.setLoc("https://www.wingbell.com/sitemap_products_1.xml?from=6134894985392&to=6583218241712");
        SiteMap siteMap2 = new SiteMap();
        siteMap2.setLoc("https://www.wingbell.com/sitemap_pages_1.xml");
        SiteMap siteMap3 = new SiteMap();
        siteMap3.setLoc("https://www.wingbell.com/sitemap_collections_1.xml");
        SiteMap siteMap4 = new SiteMap();
        siteMap4.setLoc("https://www.wingbell.com/sitemap_blogs_1.xml");
        List<SiteMap> siteMapList = new ArrayList<>();
        siteMapList.add(siteMap1);
        siteMapList.add(siteMap2);
        siteMapList.add(siteMap3);
        siteMapList.add(siteMap4);
        siteMapIndex.setSiteMap(siteMapList);
        return siteMapIndex;
    }

    @GetMapping(value = "/sitemap2.xml",produces = MediaType.APPLICATION_XML_VALUE)
    public String getSiteMapIndex2(HttpServletRequest request){
        String serverName = request.getServerName();
        System.out.println(serverName);
        SiteMapIndex siteMapIndex = new SiteMapIndex();
        siteMapIndex.setXmlns("http://www.sitemaps.org/schemas/sitemap/0.9");
        SiteMap siteMap1 = new SiteMap();
        siteMap1.setLoc("https://www.wingbell.com/sitemap_products_1.xml?from=6134894985392&to=6583218241712");
        SiteMap siteMap2 = new SiteMap();
        siteMap2.setLoc("https://www.wingbell.com/sitemap_pages_1.xml");
        SiteMap siteMap3 = new SiteMap();
        siteMap3.setLoc("https://www.wingbell.com/sitemap_collections_1.xml");
        SiteMap siteMap4 = new SiteMap();
        siteMap4.setLoc("https://www.wingbell.com/sitemap_blogs_1.xml");
        List<SiteMap> siteMapList = new ArrayList<>();
        siteMapList.add(siteMap1);
        siteMapList.add(siteMap2);
        siteMapList.add(siteMap3);
        siteMapList.add(siteMap4);
        siteMapIndex.setSiteMap(siteMapList);
        XStream xstream = new XStream(new NoneEscapeAppDriver());
        xstream.processAnnotations(SiteMapIndex.class);
        String xml = xstream.toXML(siteMapIndex);
        return xml;
    }
}
