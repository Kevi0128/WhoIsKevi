package com.kevi.code.entity;

public class KeviWorkInfo {
    private Integer id;

    private String k;

    private String v;

    public KeviWorkInfo(Integer id, String k, String v) {
        this.id = id;
        this.k = k;
        this.v = v;
    }

    public KeviWorkInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k == null ? null : k.trim();
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v == null ? null : v.trim();
    }
}