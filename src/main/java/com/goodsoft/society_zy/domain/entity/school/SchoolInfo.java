package com.goodsoft.society_zy.domain.entity.school;

import java.util.Objects;

/**
 * 描述:
 * 学校信息实体
 *
 * @author 严彬荣 Created on 2017-10-25 12:03
 * @version V1.0
 */
public class SchoolInfo implements java.io.Serializable {

    private static final long serialVersionUID = 2229260940067714856L;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.BH
     */
    private String bh;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.XXMC
     */
    private String xxmc;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.XXDZ
     */
    private String xxdz;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.FRDB
     */
    private String frdb;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.FRDBSFZH
     */
    private String frdbsfzh;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.FRDBLXDH
     */
    private String frdblxdh;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.XXSX
     */
    private String xxsx;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.SSXQ
     */
    private String ssxq;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.XGR
     */
    private String xgr;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.XGSJ
     */
    private String xgsj;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column T_SHZY_JY_XX.RKSJ
     */
    private String rksj;

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.BH
     *
     * @return the value of T_SHZY_JY_XX.BH
     */
    public String getBh() {
        return bh;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.BH
     *
     * @param bh the value for T_SHZY_JY_XX.BH
     */
    public void setBh(String bh) {
        this.bh = bh == null ? null : bh.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.XXMC
     *
     * @return the value of T_SHZY_JY_XX.XXMC
     */
    public String getXxmc() {
        return xxmc;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.XXMC
     *
     * @param xxmc the value for T_SHZY_JY_XX.XXMC
     */
    public void setXxmc(String xxmc) {
        this.xxmc = xxmc == null ? null : xxmc.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.XXDZ
     *
     * @return the value of T_SHZY_JY_XX.XXDZ
     */
    public String getXxdz() {
        return xxdz;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.XXDZ
     *
     * @param xxdz the value for T_SHZY_JY_XX.XXDZ
     */
    public void setXxdz(String xxdz) {
        this.xxdz = xxdz == null ? null : xxdz.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.FRDB
     *
     * @return the value of T_SHZY_JY_XX.FRDB
     */
    public String getFrdb() {
        return frdb;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.FRDB
     *
     * @param frdb the value for T_SHZY_JY_XX.FRDB
     */
    public void setFrdb(String frdb) {
        this.frdb = frdb == null ? null : frdb.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.FRDBSFZH
     *
     * @return the value of T_SHZY_JY_XX.FRDBSFZH
     */
    public String getFrdbsfzh() {
        return frdbsfzh;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.FRDBSFZH
     *
     * @param frdbsfzh the value for T_SHZY_JY_XX.FRDBSFZH
     */
    public void setFrdbsfzh(String frdbsfzh) {
        this.frdbsfzh = frdbsfzh == null ? null : frdbsfzh.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.FRDBLXDH
     *
     * @return the value of T_SHZY_JY_XX.FRDBLXDH
     */
    public String getFrdblxdh() {
        return frdblxdh;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.FRDBLXDH
     *
     * @param frdblxdh the value for T_SHZY_JY_XX.FRDBLXDH
     */
    public void setFrdblxdh(String frdblxdh) {
        this.frdblxdh = frdblxdh == null ? null : frdblxdh.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.XXSX
     *
     * @return the value of T_SHZY_JY_XX.XXSX
     */
    public String getXxsx() {
        return xxsx;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.XXSX
     *
     * @param xxsx the value for T_SHZY_JY_XX.XXSX
     */
    public void setXxsx(String xxsx) {
        this.xxsx = xxsx == null ? null : xxsx.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.SSXQ
     *
     * @return the value of T_SHZY_JY_XX.SSXQ
     */
    public String getSsxq() {
        return ssxq;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.SSXQ
     *
     * @param ssxq the value for T_SHZY_JY_XX.SSXQ
     */
    public void setSsxq(String ssxq) {
        this.ssxq = ssxq == null ? null : ssxq.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.XGR
     *
     * @return the value of T_SHZY_JY_XX.XGR
     */
    public String getXgr() {
        return xgr;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.XGR
     *
     * @param xgr the value for T_SHZY_JY_XX.XGR
     */
    public void setXgr(String xgr) {
        this.xgr = xgr == null ? null : xgr.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.XGSJ
     *
     * @return the value of T_SHZY_JY_XX.XGSJ
     */
    public String getXgsj() {
        return xgsj;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.XGSJ
     *
     * @param xgsj the value for T_SHZY_JY_XX.XGSJ
     */
    public void setXgsj(String xgsj) {
        this.xgsj = xgsj == null ? null : xgsj.trim();
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column T_SHZY_JY_XX.RKSJ
     *
     * @return the value of T_SHZY_JY_XX.RKSJ
     */
    public String getRksj() {
        return rksj;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column T_SHZY_JY_XX.RKSJ
     *
     * @param rksj the value for T_SHZY_JY_XX.RKSJ
     */
    public void setRksj(String rksj) {
        this.rksj = rksj == null ? null : rksj.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchoolInfo)) return false;
        SchoolInfo schoolInfo = (SchoolInfo) o;
        return Objects.equals(bh, schoolInfo.bh) &&
                Objects.equals(xxmc, schoolInfo.xxmc) &&
                Objects.equals(xxdz, schoolInfo.xxdz) &&
                Objects.equals(frdb, schoolInfo.frdb) &&
                Objects.equals(frdbsfzh, schoolInfo.frdbsfzh) &&
                Objects.equals(frdblxdh, schoolInfo.frdblxdh) &&
                Objects.equals(xxsx, schoolInfo.xxsx) &&
                Objects.equals(ssxq, schoolInfo.ssxq) &&
                Objects.equals(xgr, schoolInfo.xgr) &&
                Objects.equals(xgsj, schoolInfo.xgsj) &&
                Objects.equals(rksj, schoolInfo.rksj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bh, xxmc, xxdz, frdb, frdbsfzh, frdblxdh, xxsx, ssxq, xgr, xgsj, rksj);
    }
}