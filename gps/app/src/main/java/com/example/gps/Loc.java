package com.example.gps;

public class Loc {
    private long times;
    private String locs;
    public Loc(long times, String locs){
        this.times = times;
        this.locs = locs;
    }
    public long getTimes(){
        return times;
    }
    public String getLoc(){
        return locs;
    }

}
