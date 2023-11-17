package com.moneycore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "nationality_list")
public class NationalityList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "nationality_id", length = 3)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String nationalityId;

    @Column(name = "nationality_code", length = 60)
    private String nationalityCode;

    @Column(name = "nationality", length = 60)
    private String nationality;

    public String getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(String nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
