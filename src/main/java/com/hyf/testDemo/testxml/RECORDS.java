package com.hyf.testDemo.testxml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/20
 */
@XStreamAlias("RECORDS")
public class RECORDS {

    @XStreamImplicit
    List<RECORD> records;

    public List<RECORD> getRecords() {
        return records;
    }

    public void setRecords(List<RECORD> records) {
        this.records = records;
    }
}
