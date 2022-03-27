package com.company.enums;


public enum UserStatusEnum {
    UNCONFIRMED(1L), CONFIRMED(2L);

    private final Long statusId;

    UserStatusEnum(Long statusId) {
        this.statusId = statusId;
    }

    public Long getStatusId() {
        return statusId;
    }
}
