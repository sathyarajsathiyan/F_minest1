package com.example.minest1.util;

public class DataPart {
    private String fileName;
    private byte[] content;
    private String type;
    private String sessionid;

    public DataPart() {
    }


    public DataPart(String name, byte[] data) {
        fileName = name;
        content = data;
    }



    public DataPart(String session_id) {
        sessionid=session_id;

    }

    String getFileName() {
        return fileName;
    }

    byte[] getContent() {
        return content;
    }

    String getType() {
        return type;
    }
    public String getSessionid() {
        return sessionid;
    }



}
