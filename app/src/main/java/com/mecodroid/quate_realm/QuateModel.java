package com.mecodroid.quate_realm;

import io.realm.RealmObject;

public class QuateModel extends RealmObject {
    private int id;
    private String quateTitle, quoteContent;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuateTitle() {
        return quateTitle;
    }

    public void setQuateTitle(String quateTitle) {
        this.quateTitle = quateTitle;
    }

    public String getQuoteContent() {
        return quoteContent;
    }

    public void setQuoteContent(String quoteContent) {
        this.quoteContent = quoteContent;
    }
}
