package com.studio.bin.kqxs;

public class KetQuaDto {

    private String no;
    private String value;
    private String date;
    private String message;
    public KetQuaDto() {
    }

    public KetQuaDto(String no, String value, String date, String message) {
        this.no = no;
        this.value = value;
        this.date = date;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
