package org.project.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "company", schema = "import_export_db")
public class Company {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "companyID", nullable = false)
    private int companyId;
    @Basic
    @Column(name = "companyName", nullable = false, length = 45)
    private String companyName;
    @Basic
    @Column(name = "country", nullable = false, length = 45)
    private String country;
    @Basic
    @Column(name = "checkingAccount", nullable = false, length = 45)
    private String checkingAccount;
    @Basic
    @Column(name = "companyEmail", nullable = true, length = 45)
    private String companyEmail;

    @Basic
    @Column(name = "companyType", nullable = false, length = 45)
    private String companyType;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private transient List<TradeOperation> operations;

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(String checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyId == company.companyId && Objects.equals(companyName, company.companyName) && Objects.equals(country, company.country) && Objects.equals(checkingAccount, company.checkingAccount) && Objects.equals(companyEmail, company.companyEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName, country, checkingAccount, companyEmail);
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", country='" + country + '\'' +
                ", checkingAccount='" + checkingAccount + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", operations=" + operations +
                '}';
    }
}
