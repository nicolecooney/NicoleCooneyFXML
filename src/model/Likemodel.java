/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nicole
 */
@Entity
@Table(name = "LIKEMODEL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Likemodel.findAll", query = "SELECT l FROM Likemodel l")
    , @NamedQuery(name = "Likemodel.findByLikepost", query = "SELECT l FROM Likemodel l WHERE l.likepost = :likepost")
    , @NamedQuery(name = "Likemodel.findByName", query = "SELECT l FROM Likemodel l WHERE l.name = :name")
    , @NamedQuery(name = "Likemodel.findByLikeid", query = "SELECT l FROM Likemodel l WHERE l.likeid = :likeid")
    , @NamedQuery(name = "Likemodel.findById", query = "SELECT l FROM Likemodel l WHERE l.id = :id")})
public class Likemodel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "LIKEPOST")
    private Boolean likepost;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LIKEID")
    private Integer likeid;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    public Likemodel() {
    }

    public Likemodel(Integer id) {
        this.id = id;
    }

    public Boolean getLikepost() {
        return likepost;
    }

    public void setLikepost(Boolean likepost) {
        this.likepost = likepost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLikeid() {
        return likeid;
    }

    public void setLikeid(Integer likeid) {
        this.likeid = likeid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Likemodel)) {
            return false;
        }
        Likemodel other = (Likemodel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Likemodel[ id=" + id + " ]";
    }
    
}
