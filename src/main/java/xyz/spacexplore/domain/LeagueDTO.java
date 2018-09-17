package xyz.spacexplore.domain;

import java.io.Serializable;
import java.util.Date;

public class LeagueDTO implements Serializable {
    private Integer lid;

    private String cn;

    private String color;

    private String en;

    private String gbk;

    private String cnshort;

    private Integer cid;

    private Integer kind;

    private String gbkshort;

    private String icon;

    private Integer status=0;

    private Date setdate;

    private String remark;

    private Integer flag;

    private static final long serialVersionUID = 1L;

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn == null ? null : cn.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en == null ? null : en.trim();
    }

    public String getGbk() {
        return gbk;
    }

    public void setGbk(String gbk) {
        this.gbk = gbk == null ? null : gbk.trim();
    }

    public String getCnshort() {
        return cnshort;
    }

    public void setCnshort(String cnshort) {
        this.cnshort = cnshort == null ? null : cnshort.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getGbkshort() {
        return gbkshort;
    }

    public void setGbkshort(String gbkshort) {
        this.gbkshort = gbkshort == null ? null : gbkshort.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSetdate() {
        return setdate;
    }

    public void setSetdate(Date setdate) {
        this.setdate = setdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LeagueDTO other = (LeagueDTO) that;
        return (this.getLid() == null ? other.getLid() == null : this.getLid().equals(other.getLid()))
            && (this.getCn() == null ? other.getCn() == null : this.getCn().equals(other.getCn()))
            && (this.getColor() == null ? other.getColor() == null : this.getColor().equals(other.getColor()))
            && (this.getEn() == null ? other.getEn() == null : this.getEn().equals(other.getEn()))
            && (this.getGbk() == null ? other.getGbk() == null : this.getGbk().equals(other.getGbk()))
            && (this.getCnshort() == null ? other.getCnshort() == null : this.getCnshort().equals(other.getCnshort()))
            && (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
            && (this.getKind() == null ? other.getKind() == null : this.getKind().equals(other.getKind()))
            && (this.getGbkshort() == null ? other.getGbkshort() == null : this.getGbkshort().equals(other.getGbkshort()))
            && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSetdate() == null ? other.getSetdate() == null : this.getSetdate().equals(other.getSetdate()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getFlag() == null ? other.getFlag() == null : this.getFlag().equals(other.getFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLid() == null) ? 0 : getLid().hashCode());
        result = prime * result + ((getCn() == null) ? 0 : getCn().hashCode());
        result = prime * result + ((getColor() == null) ? 0 : getColor().hashCode());
        result = prime * result + ((getEn() == null) ? 0 : getEn().hashCode());
        result = prime * result + ((getGbk() == null) ? 0 : getGbk().hashCode());
        result = prime * result + ((getCnshort() == null) ? 0 : getCnshort().hashCode());
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getKind() == null) ? 0 : getKind().hashCode());
        result = prime * result + ((getGbkshort() == null) ? 0 : getGbkshort().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSetdate() == null) ? 0 : getSetdate().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", lid=").append(lid);
        sb.append(", cn=").append(cn);
        sb.append(", color=").append(color);
        sb.append(", en=").append(en);
        sb.append(", gbk=").append(gbk);
        sb.append(", cnshort=").append(cnshort);
        sb.append(", cid=").append(cid);
        sb.append(", kind=").append(kind);
        sb.append(", gbkshort=").append(gbkshort);
        sb.append(", icon=").append(icon);
        sb.append(", status=").append(status);
        sb.append(", setdate=").append(setdate);
        sb.append(", remark=").append(remark);
        sb.append(", flag=").append(flag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}