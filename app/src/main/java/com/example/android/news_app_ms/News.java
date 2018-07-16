package com.example.android.news_app_ms;

import java.util.Date;

public class News {

    private String mTitle;
    private String mSection;
    private String mUrl;
    private Date mDateTime;
    private String mAuthor;

    public News(String Title, String Section, String Url, Date DateTime, String Author) {
        mSection = Section;
        mTitle = Title;
        mUrl = Url;
        mDateTime = DateTime;
        mAuthor = Author;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmUrl() {
        return mUrl;
    }

    public Date getmDateTime() {
        return mDateTime;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}