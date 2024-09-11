package com.mycompany.cxacaixpkg.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cxacaix.
 */
@Entity
@Table(name = "cxacaix")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cxacaix implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cxacaix_id")
    private Long cxacaixId;

    @Size(max = 16)
    @Column(name = "xref_card_num", length = 16)
    private String xrefCardNum;

    @Max(value = 9)
    @Column(name = "xref_cust_id")
    private Integer xrefCustId;

    @Max(value = 11)
    @Column(name = "xref_acct_id")
    private Integer xrefAcctId;

    @Size(max = 14)
    @Column(name = "filler", length = 14)
    private String filler;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cxacaix id(Long id) {
        this.id = id;
        return this;
    }

    public Long getCxacaixId() {
        return this.cxacaixId;
    }

    public Cxacaix cxacaixId(Long cxacaixId) {
        this.cxacaixId = cxacaixId;
        return this;
    }

    public void setCxacaixId(Long cxacaixId) {
        this.cxacaixId = cxacaixId;
    }

    public String getXrefCardNum() {
        return this.xrefCardNum;
    }

    public Cxacaix xrefCardNum(String xrefCardNum) {
        this.xrefCardNum = xrefCardNum;
        return this;
    }

    public void setXrefCardNum(String xrefCardNum) {
        this.xrefCardNum = xrefCardNum;
    }

    public Integer getXrefCustId() {
        return this.xrefCustId;
    }

    public Cxacaix xrefCustId(Integer xrefCustId) {
        this.xrefCustId = xrefCustId;
        return this;
    }

    public void setXrefCustId(Integer xrefCustId) {
        this.xrefCustId = xrefCustId;
    }

    public Integer getXrefAcctId() {
        return this.xrefAcctId;
    }

    public Cxacaix xrefAcctId(Integer xrefAcctId) {
        this.xrefAcctId = xrefAcctId;
        return this;
    }

    public void setXrefAcctId(Integer xrefAcctId) {
        this.xrefAcctId = xrefAcctId;
    }

    public String getFiller() {
        return this.filler;
    }

    public Cxacaix filler(String filler) {
        this.filler = filler;
        return this;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cxacaix)) {
            return false;
        }
        return id != null && id.equals(((Cxacaix) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cxacaix{" +
            "id=" + getId() +
            ", cxacaixId=" + getCxacaixId() +
            ", xrefCardNum='" + getXrefCardNum() + "'" +
            ", xrefCustId=" + getXrefCustId() +
            ", xrefAcctId=" + getXrefAcctId() +
            ", filler='" + getFiller() + "'" +
            "}";
    }
}
