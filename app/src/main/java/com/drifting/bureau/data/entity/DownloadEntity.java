package com.drifting.bureau.data.entity;

/**
 * create by 卫佳琪
 * on 2021/8/20
 */
public class DownloadEntity {
    private long total;
    private long bytesReaded;

    public DownloadEntity(long total, long bytesReaded) {
        this.total = total;
        this.bytesReaded = bytesReaded;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getBytesReaded() {
        return bytesReaded;
    }

    public void setBytesReaded(long bytesReaded) {
        this.bytesReaded = bytesReaded;
    }
}
