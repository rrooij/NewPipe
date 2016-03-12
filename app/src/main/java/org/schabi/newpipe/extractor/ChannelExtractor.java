package org.schabi.newpipe.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by rrooij on 3/12/16.
 */
public abstract class ChannelExtractor {
    private int serviceId;

    public ChannelExtractor(String url, Downloader dl, int serviceId) {
        this.serviceId = serviceId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public abstract String getAccountName() throws ParsingException;
    public abstract int getSubscribeCount() throws ParsingException;
    public abstract List<StreamPreviewInfo> getUploadedStreams() throws ParsingException;
    public abstract String getUploaderThumbnailUrl() throws ParsingException;
}
