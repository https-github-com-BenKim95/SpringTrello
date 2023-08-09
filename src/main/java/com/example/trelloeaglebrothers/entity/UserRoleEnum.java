package com.example.trelloeaglebrothers.entity;

public enum UserRoleEnum {


    MEMBER(Authority.MEMBER),
    MANAGER(Authority.MANAGER);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String MEMBER = "ROLE_MEMBER";
        public static final String MANAGER = "ROLE_MANAGER";
    }
}

