package com.tongji.zhou.Entity;

public enum PersonType {
    ACTOR("actor","actor"),DIRECTOR("director","director"),
    STARRING("starring","actor"),SUPPORTING("supporting","actor");

    PersonType(String group,String role){
        this.group=group;
        this.role=role;
    }
    private String group;
    private String role;

    public String getRole() {
        return role;
    }

    public String getGroup() {
        return group;
    }
}
