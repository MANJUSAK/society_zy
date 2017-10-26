package com.goodsoft.society_zy.domain.entity.hospital;

import java.util.Objects;

/**
 * 医院信息实体类
 * Created by 严彬荣 on 2017/10/25.
 *
 * @version V1.0
 */
public class HospitalInfo implements java.io.Serializable {
    private static final long serialVersionUID = 6225062506260305451L;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.BH
     */
    private String bh;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.YYMC
     */
    private String yymc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.YYDZ
     */
    private String yydz;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.FRDB
     */
    private String frdb;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.FRDBSFZH
     */
    private String frdbsfzh;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.FRDBLXDH
     */
    private String frdblxdh;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.SSXQ
     */
    private String ssxq;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.XGR
     */
    private String xgr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.XGSH
     */
    private String xgsh;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_SHZY_YY.RKSJ
     */
    private String rksj;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.BH
     *
     * @return the value of T_SHZY_YY.BH
     */
    public String getBh() {
        return bh;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.BH
     *
     * @param bh the value for T_SHZY_YY.BH
     */
    public void setBh(String bh) {
        this.bh = bh == null ? null : bh.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.YYMC
     *
     * @return the value of T_SHZY_YY.YYMC
     */
    public String getYymc() {
        return yymc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.YYMC
     *
     * @param yymc the value for T_SHZY_YY.YYMC
     */
    public void setYymc(String yymc) {
        this.yymc = yymc == null ? null : yymc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.YYDZ
     *
     * @return the value of T_SHZY_YY.YYDZ
     */
    public String getYydz() {
        return yydz;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.YYDZ
     *
     * @param yydz the value for T_SHZY_YY.YYDZ
     */
    public void setYydz(String yydz) {
        this.yydz = yydz == null ? null : yydz.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.FRDB
     *
     * @return the value of T_SHZY_YY.FRDB
     */
    public String getFrdb() {
        return frdb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.FRDB
     *
     * @param frdb the value for T_SHZY_YY.FRDB
     */
    public void setFrdb(String frdb) {
        this.frdb = frdb == null ? null : frdb.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.FRDBSFZH
     *
     * @return the value of T_SHZY_YY.FRDBSFZH
     */
    public String getFrdbsfzh() {
        return frdbsfzh;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.FRDBSFZH
     *
     * @param frdbsfzh the value for T_SHZY_YY.FRDBSFZH
     */
    public void setFrdbsfzh(String frdbsfzh) {
        this.frdbsfzh = frdbsfzh == null ? null : frdbsfzh.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.FRDBLXDH
     *
     * @return the value of T_SHZY_YY.FRDBLXDH
     */
    public String getFrdblxdh() {
        return frdblxdh;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.FRDBLXDH
     *
     * @param frdblxdh the value for T_SHZY_YY.FRDBLXDH
     */
    public void setFrdblxdh(String frdblxdh) {
        this.frdblxdh = frdblxdh == null ? null : frdblxdh.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.SSXQ
     *
     * @return the value of T_SHZY_YY.SSXQ
     */
    public String getSsxq() {
        return ssxq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.SSXQ
     *
     * @param ssxq the value for T_SHZY_YY.SSXQ
     */
    public void setSsxq(String ssxq) {
        this.ssxq = ssxq == null ? null : ssxq.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.XGR
     *
     * @return the value of T_SHZY_YY.XGR
     */
    public String getXgr() {
        return xgr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.XGR
     *
     * @param xgr the value for T_SHZY_YY.XGR
     */
    public void setXgr(String xgr) {
        this.xgr = xgr == null ? null : xgr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.XGSH
     *
     * @return the value of T_SHZY_YY.XGSH
     */
    public String getXgsh() {
        return xgsh;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.XGSH
     *
     * @param xgsh the value for T_SHZY_YY.XGSH
     */
    public void setXgsh(String xgsh) {
        this.xgsh = xgsh == null ? null : xgsh.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_SHZY_YY.RKSH
     *
     * @return the value of T_SHZY_YY.RKSJ
     */
    public String getRksj() {
        return rksj;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_SHZY_YY.RKSJ
     *
     * @param rksj the value for T_SHZY_YY.RKSJ
     */
    public void setRksj(String rksj) {
        this.rksj = rksj == null ? null : rksj.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HospitalInfo)) return false;
        HospitalInfo that = (HospitalInfo) o;
        return Objects.equals(bh, that.bh) &&
                Objects.equals(yymc, that.yymc) &&
                Objects.equals(yydz, that.yydz) &&
                Objects.equals(frdb, that.frdb) &&
                Objects.equals(frdbsfzh, that.frdbsfzh) &&
                Objects.equals(frdblxdh, that.frdblxdh) &&
                Objects.equals(ssxq, that.ssxq) &&
                Objects.equals(xgr, that.xgr) &&
                Objects.equals(xgsh, that.xgsh) &&
                Objects.equals(rksj, that.rksj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bh, yymc, yydz, frdb, frdbsfzh, frdblxdh, ssxq, xgr, xgsh, rksj);
    }
}
