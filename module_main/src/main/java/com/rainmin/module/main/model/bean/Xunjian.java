package com.rainmin.module.main.model.bean;

public class Xunjian {
    private long id;
    // 日期
    private String date;
    // 公司号
    private int firm;
    // 线路号
    private int line;
    // 车号
    private int train;
    // 相机号
    private int camera;
    // 上下行线
    private int updown;
    // 病害类型
    private int disease;
    // 速度
    private double speed;
    // 公里标
    private double kilometer;
    // 站名
    private String station;
    // 图像文件路径
    private String filename;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFirm() {
        return firm;
    }

    public void setFirm(int firm) {
        this.firm = firm;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getTrain() {
        return train;
    }

    public void setTrain(int train) {
        this.train = train;
    }

    public int getCamera() {
        return camera;
    }

    public void setCamera(int camera) {
        this.camera = camera;
    }

    public int getUpdown() {
        return updown;
    }

    public void setUpdown(int updown) {
        this.updown = updown;
    }

    public int getDisease() {
        return disease;
    }

    public void setDisease(int disease) {
        this.disease = disease;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getKilometer() {
        return kilometer;
    }

    public void setKilometer(double kilometer) {
        this.kilometer = kilometer;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
