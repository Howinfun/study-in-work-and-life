package com.github.howinfun.controller;

import com.github.howinfun.pojo.SiteMap;
import com.github.howinfun.pojo.SiteMapIndex;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.SneakyThrows;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
    public String getSiteMapIndex2(HttpServletRequest request) throws UnsupportedEncodingException {
        String serverName = request.getServerName();
        System.out.println(serverName);
        SiteMapIndex siteMapIndex = new SiteMapIndex();
        siteMapIndex.setXmlns("http://www.sitemaps.org/schemas/sitemap/0.9");
        SiteMap siteMap1 = new SiteMap();
        String loc = "https://www.wingbell.com/sitemap_products_1.xml?from=6134894985392&to=6583218241712\"';<>&";
        System.out.println(StringEscapeUtils.escapeXml11(loc));
        siteMap1.setLoc(loc);
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
        XStream xstream = new XStream(new StaxDriver());
        xstream.processAnnotations(SiteMapIndex.class);
        String xml = xstream.toXML(siteMapIndex);
        return xml;
    }

    @SneakyThrows
    public static void main(String[] args) {
        String fileStr = readFileByReader("/Users/winfun/Downloads/sitemap_products_1.xml.gz");
        // 压缩
        /*String gzipStr = gzip(fileStr);
        System.out.println(gzipStr);*/
        // 解压缩
        String result = gunzip(fileStr);
        writeFileByWriter(result,"/Users/winfun/Downloads/sitemap_products_1.xml");
    }

    private static String readFileByReader(String fileName){
        StringBuilder xml = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String line;
            while (StrUtil.isNotBlank(line = bufferedReader.readLine())){
                xml.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml.toString();
    }

    private static void writeFileByWriter(String content,String fileName){

        try (FileWriter writer = new FileWriter(fileName)){
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**

     * 使用gzip进行压缩
     */
    public static String gzip(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip=null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(gzip!=null){
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return new sun.misc.BASE64Encoder().encode(out.toByteArray());
    }

    /**
     *
     * <p>Description:使用gzip进行解压缩</p>
     * @param compressedStr
     * @return
     */
    public static String gunzip(String compressedStr){
        if(compressedStr==null){
            return null;
        }

        ByteArrayOutputStream out= new ByteArrayOutputStream();
        ByteArrayInputStream in=null;
        GZIPInputStream ginzip=null;
        byte[] compressed=null;
        String decompressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            in=new ByteArrayInputStream(compressed);
            ginzip=new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed=out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        return decompressed;
    }
}
