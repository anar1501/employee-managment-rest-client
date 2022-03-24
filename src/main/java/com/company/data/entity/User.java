package com.company.data.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.company.enums.UserStatusEnum.UNCONFIRMED;

@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    private String activationCode;
    private Date expiredDate;
    @Column(length = 6)
    private String sixDigitCode;
    private Date forgetPasswordExpiredDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private UserStatus status = new UserStatus();


    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createdDate;

    @PrePersist
    public void persist() {
        getStatus().setId(UNCONFIRMED.getStatusId());
        setCreatedDate(new Date());
    }
}
