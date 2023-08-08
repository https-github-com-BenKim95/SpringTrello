package com.example.trelloeaglebrothers.entity;

public enum UserRoleEnum {
    MEMBER(Authority.MEMBER),  // 멤버 권한
    MANAGER(Authority.MANAGER);  // 매니저 권한

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String MEMBER = "MEMBER";
        public static final String MANAGER = "MANAGER";
    }
}