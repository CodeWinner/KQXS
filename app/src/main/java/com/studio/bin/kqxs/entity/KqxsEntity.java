package com.studio.bin.kqxs.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KqxsEntity {

    @SerializedName("KQXSMB")
    @Expose
    private KqxsMbMtMnEntity kqxsmb;

    @SerializedName("KQXSMT")
    @Expose
    private KqxsMbMtMnEntity kqxsmt;

    @SerializedName("KQXSMN")
    @Expose
    private KqxsMbMtMnEntity kqxsmn;

    public KqxsMbMtMnEntity getKqxsmb() {
        return kqxsmb;
    }

    public void setKqxsmb(KqxsMbMtMnEntity kqxsmb) {
        this.kqxsmb = kqxsmb;
    }

    public KqxsMbMtMnEntity getKqxsmt() {
        return kqxsmt;
    }

    public void setKqxsmt(KqxsMbMtMnEntity kqxsmt) {
        this.kqxsmt = kqxsmt;
    }

    public KqxsMbMtMnEntity getKqxsmn() {
        return kqxsmn;
    }

    public void setKqxsmn(KqxsMbMtMnEntity kqxsmn) {
        this.kqxsmn = kqxsmn;
    }

}