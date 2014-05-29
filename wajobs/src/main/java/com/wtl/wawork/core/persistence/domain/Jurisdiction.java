package com.wtl.wawork.core.persistence.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.wtl.wawork.core.service.dto.JurisdictionDetails;

@Entity
public class Jurisdiction {

    public static Jurisdiction fromJurisdictionDetails(JurisdictionDetails details) {
        Jurisdiction jurisdiction = new Jurisdiction(details.getName());

        BeanUtils.copyProperties(details, jurisdiction);

        return jurisdiction;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jurisdiction")
    private List<Posting> postings;

    @SuppressWarnings("unused")
    private Jurisdiction() {
    }

    public Jurisdiction(String name) {
        this.name = name;
        this.postings = Lists.newArrayList();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Posting> getPostings() {
        return postings;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostings(List<Posting> postings) {
        this.postings = postings;
    }

    public JurisdictionDetails toJurisdictionDetails() {
        JurisdictionDetails details = new JurisdictionDetails(id, name);

        BeanUtils.copyProperties(this, details);

        return details;
    }

}
