package org.schabi.newpipe.extractor.services.youtube;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.schabi.newpipe.extractor.ChannelExtractor;
import org.schabi.newpipe.extractor.Downloader;
import org.schabi.newpipe.extractor.ParsingException;
import org.schabi.newpipe.extractor.StreamPreviewInfo;
import org.schabi.newpipe.extractor.StreamPreviewInfoCollector;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Created by robin on 3/12/16.
 */
public class YoutubeChannelExtractor extends ChannelExtractor {

    private final Document doc;
    private String exceptionString;

    public YoutubeChannelExtractor(String pageUrl, Downloader dl, int serviceId) throws IOException {
        super(pageUrl, dl, serviceId);
        String pageContent = dl.download(pageUrl);
        doc = Jsoup.parse(pageContent);
        exceptionString = "Failed to fetch %s";
    }

    @Override
    public String getAccountName() throws ParsingException {
        try {
            return doc.select(".branded-page-header-title-link").first().attr("title");
        } catch (Exception ex) {
            throw new ParsingException(String.format(exceptionString, "account name"));
        }
    }

    @Override
    public int getSubscribeCount() throws ParsingException {
        try {
            return Integer.parseInt(doc.select(".subscribed").first().attr("title").replaceAll("\\s+",""));
        } catch (Exception ex) {
            throw new ParsingException(String.format(exceptionString, "subscribe count"));
        }
    }

    @Override
    public List<StreamPreviewInfo> getUploadedStreams() throws ParsingException {
        List<StreamPreviewInfo> streamPreviews = new Vector<>();

        Elements videoUploads = doc.select(".channels-content-item");

        for (Element element: videoUploads) {
            YoutubeStreamPreviewInfoExtractor previewExtractor = new YoutubeStreamPreviewInfoExtractor(element);
            StreamPreviewInfo previewInfo = new StreamPreviewInfo();
            previewInfo.duration = previewExtractor.getDuration();
            previewInfo.service_id = getServiceId();
            previewInfo.thumbnail_url = previewExtractor.getThumbnailUrl();
            previewInfo.title = previewExtractor.getTitle();
            previewInfo.upload_date = previewExtractor.getUploadDate();
            previewInfo.uploader = getAccountName();
            previewInfo.webpage_url = previewExtractor.getWebPageUrl();
            previewInfo.view_count = previewExtractor.getViewCount();
            streamPreviews.add(previewInfo);
        }
        return streamPreviews;
    }

    @Override
    public String getUploaderThumbnailUrl() throws ParsingException {
        try {
            return doc.select(".channel-header-profile-image").first().attr("src");
        } catch (Exception ex) {
            throw new ParsingException(String.format(exceptionString, "uploader thumbnail URL"));
        }
    }
}
