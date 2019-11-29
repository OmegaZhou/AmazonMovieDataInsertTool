package com.tongji.zhou.Entity;

public class Corporation {

    private String movies;
    private Integer count;
    private Person p1;
    private Person p2;
    public Corporation(Person p1,Person p2){
        if(p1.getRole()==PersonType.DIRECTOR.getRole()&&p2.getRole()==PersonType.DIRECTOR.getRole()){
            return;
        }
        if(p1.getRole()==PersonType.DIRECTOR.getRole()){
            this.p1=p2;
            this.p2=p1;
        }else if(p2.getRole()==PersonType.DIRECTOR.getRole()){
            this.p1=p1;
            this.p2=p2;
        }else{
            if(p1.getName().compareTo(p2.getName())<0){
                this.p1=p1;
                this.p2=p2;
            }else{
                this.p1=p2;
                this.p2=p1;
            }
        }
    }

    public Integer getId1() {
        return p1.getId();
    }

    public Integer getId2() {
        return p2.getId();
    }

    public String getMovies() {
        return movies;
    }

    public String getName1() {
        return p1.getName();
    }

    public String getName2() {
        return p2.getName();
    }

    public Person getP1() {
        return p1;
    }

    public Person getP2() {
        return p2;
    }

    public Integer getCount() {
        return count;
    }

    public void setId1(Integer id1) {
        p1.setId(id1);
    }

    public void setId2(Integer id2) {
        p2.setId(id2);
    }

    public void setName1(String name1) {
        p1.setName(name1);
    }

    public void setName2(String name2) {
        p2.setName(name2);
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

}
