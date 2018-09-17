package xyz.spacexplore.domain;

import java.io.Serializable;
import java.util.Date;

public class EventDTO implements Serializable {
    private Integer id;

    private String name;

    private Integer tournamentStagefk;

    private Date startdate;

    private Integer eventstatusfk;

    private String statusType;

    private Integer statusDescfk;

    private Integer enetid;

    private String enetsportid;

    private Integer n;

    private Date ut;

    private String del;

    private String locked;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTournamentStagefk() {
        return tournamentStagefk;
    }

    public void setTournamentStagefk(Integer tournamentStagefk) {
        this.tournamentStagefk = tournamentStagefk;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Integer getEventstatusfk() {
        return eventstatusfk;
    }

    public void setEventstatusfk(Integer eventstatusfk) {
        this.eventstatusfk = eventstatusfk;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType == null ? null : statusType.trim();
    }

    public Integer getStatusDescfk() {
        return statusDescfk;
    }

    public void setStatusDescfk(Integer statusDescfk) {
        this.statusDescfk = statusDescfk;
    }

    public Integer getEnetid() {
        return enetid;
    }

    public void setEnetid(Integer enetid) {
        this.enetid = enetid;
    }

    public String getEnetsportid() {
        return enetsportid;
    }

    public void setEnetsportid(String enetsportid) {
        this.enetsportid = enetsportid == null ? null : enetsportid.trim();
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Date getUt() {
        return ut;
    }

    public void setUt(Date ut) {
        this.ut = ut;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del == null ? null : del.trim();
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked == null ? null : locked.trim();
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
        EventDTO other = (EventDTO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getTournamentStagefk() == null ? other.getTournamentStagefk() == null : this.getTournamentStagefk().equals(other.getTournamentStagefk()))
            && (this.getStartdate() == null ? other.getStartdate() == null : this.getStartdate().equals(other.getStartdate()))
            && (this.getEventstatusfk() == null ? other.getEventstatusfk() == null : this.getEventstatusfk().equals(other.getEventstatusfk()))
            && (this.getStatusType() == null ? other.getStatusType() == null : this.getStatusType().equals(other.getStatusType()))
            && (this.getStatusDescfk() == null ? other.getStatusDescfk() == null : this.getStatusDescfk().equals(other.getStatusDescfk()))
            && (this.getEnetid() == null ? other.getEnetid() == null : this.getEnetid().equals(other.getEnetid()))
            && (this.getEnetsportid() == null ? other.getEnetsportid() == null : this.getEnetsportid().equals(other.getEnetsportid()))
            && (this.getN() == null ? other.getN() == null : this.getN().equals(other.getN()))
            && (this.getUt() == null ? other.getUt() == null : this.getUt().equals(other.getUt()))
            && (this.getDel() == null ? other.getDel() == null : this.getDel().equals(other.getDel()))
            && (this.getLocked() == null ? other.getLocked() == null : this.getLocked().equals(other.getLocked()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getTournamentStagefk() == null) ? 0 : getTournamentStagefk().hashCode());
        result = prime * result + ((getStartdate() == null) ? 0 : getStartdate().hashCode());
        result = prime * result + ((getEventstatusfk() == null) ? 0 : getEventstatusfk().hashCode());
        result = prime * result + ((getStatusType() == null) ? 0 : getStatusType().hashCode());
        result = prime * result + ((getStatusDescfk() == null) ? 0 : getStatusDescfk().hashCode());
        result = prime * result + ((getEnetid() == null) ? 0 : getEnetid().hashCode());
        result = prime * result + ((getEnetsportid() == null) ? 0 : getEnetsportid().hashCode());
        result = prime * result + ((getN() == null) ? 0 : getN().hashCode());
        result = prime * result + ((getUt() == null) ? 0 : getUt().hashCode());
        result = prime * result + ((getDel() == null) ? 0 : getDel().hashCode());
        result = prime * result + ((getLocked() == null) ? 0 : getLocked().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", tournamentStagefk=").append(tournamentStagefk);
        sb.append(", startdate=").append(startdate);
        sb.append(", eventstatusfk=").append(eventstatusfk);
        sb.append(", statusType=").append(statusType);
        sb.append(", statusDescfk=").append(statusDescfk);
        sb.append(", enetid=").append(enetid);
        sb.append(", enetsportid=").append(enetsportid);
        sb.append(", n=").append(n);
        sb.append(", ut=").append(ut);
        sb.append(", del=").append(del);
        sb.append(", locked=").append(locked);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}