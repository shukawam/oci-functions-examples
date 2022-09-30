package me.shukawam.apm;

public class SleepService {

    public String greeting() {
        Thread.sleep(2000);
        return "ok";
    }
    
}
