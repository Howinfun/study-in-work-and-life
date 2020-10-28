package com.hyf.testDemo.testxml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/20
 */
@XStreamAlias("RECORD")
public class RECORD {

    @XStreamAlias("Fid")
    private String Fid;
    @XStreamAlias("name")
    private String name;
    @XStreamAlias("name_en")
    private String name_en;
    @XStreamAlias("name_py")
    private String name_py;
    @XStreamAlias("Fprovince_cn")
    private String Fprovince_cn;
    @XStreamAlias("Fweathercn")
    private String Fweathercn;

    public String getFid() {
        return Fid;
    }

    public void setFid(String fid) {
        Fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_py() {
        return name_py;
    }

    public void setName_py(String name_py) {
        this.name_py = name_py;
    }

    public String getFprovince_cn() {
        return Fprovince_cn;
    }

    public void setFprovince_cn(String fprovince_cn) {
        Fprovince_cn = fprovince_cn;
    }

    public String getFweathercn() {
        return Fweathercn;
    }

    public void setFweathercn(String fweathercn) {
        Fweathercn = fweathercn;
    }
}
