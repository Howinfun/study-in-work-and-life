package com.github.howinfun.writer;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;

/**
 *
 * 重写创建输出流方法
 *
 * @ClassName NoneEscapeAppDriver.java
 * @Author laizuan
 * @Date 2019年10月10日 10:22
 */
public class NoneEscapeAppDriver extends XppDriver {

    @Override
    public HierarchicalStreamWriter createWriter(Writer out) {
        return new NoneEscapePrettyPintWriter(out, getNameCoder());
    }
}
