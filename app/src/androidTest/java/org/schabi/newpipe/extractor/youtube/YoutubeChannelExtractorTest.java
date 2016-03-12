package org.schabi.newpipe.extractor.youtube;

import android.test.AndroidTestCase;

import org.schabi.newpipe.Downloader;
import org.schabi.newpipe.extractor.ChannelExtractor;
import org.schabi.newpipe.extractor.ParsingException;
import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.services.youtube.YoutubeService;

/**
 * Created by rrooij on 3/12/16.
 */
public class YoutubeChannelExtractorTest extends AndroidTestCase {
    private ChannelExtractor extractor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        extractor = new YoutubeService(0).getChannelExtractorInstance(
                "https://www.youtube.com/user/Vsauce/videos",
                new Downloader());
    }

    public void testGetAccountName() throws ParsingException {
        assertTrue(!extractor.getAccountName().isEmpty());
    }

    public void testGetSubscribeCount() throws  ParsingException {
        assertTrue(extractor.getSubscribeCount() > 0);
    }

    public void testGetUploadedStreams() throws ParsingException {
        assertTrue(extractor.getUploadedStreams().size() > 0);
    }

    public void testGetUploaderThumbnailUrl() throws ParsingException {
        assertTrue(extractor.getUploaderThumbnailUrl().contains("http"));
    }
}
