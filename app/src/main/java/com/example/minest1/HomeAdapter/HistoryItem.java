package com.example.minest1.HomeAdapter;

import com.android.volley.toolbox.StringRequest;
import com.google.common.base.Strings;

public class HistoryItem {
    private String topImageUrl;
    private String botImageUrl;
    private String mdate;

    public HistoryItem(String topImage, String botImage, String date) {
        topImageUrl = topImage;
        botImageUrl = botImage;
        mdate = date;

    }
    public String getTopImageUrl() {
        return topImageUrl;
    }

    public void setTopImageUrl(String topImageUrl) {
        this.topImageUrl = topImageUrl;
    }

    public String getBotImageUrl() {
        return botImageUrl;
    }

    public void setBotImageUrl(String botImageUrl) {
        this.botImageUrl = botImageUrl;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }


}
