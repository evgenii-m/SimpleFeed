/**
 * 
 */
package org.push.simplefeed.model.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.push.simplefeed.util.xml.XmlConverter;
import org.push.simplefeed.util.xml.rsstypes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Service;

/**
 * @author push
 *
 */
@Service
public class RssService {
    private XmlConverter xmlConverter;
    

    @Autowired
    public void setXmlConverter(XmlConverter xmlConverter) {
        this.xmlConverter = xmlConverter;
    }
 

    public List<RssChannelItem> getItems(String rssUrl) throws XmlMappingException, IOException {
        List<RssChannelItem> rssItemList = new ArrayList<>();
        Rss rss = (Rss) xmlConverter.xmlToObject(rssUrl);
        for (RssChannel rssChannel : rss.getChannel()) {
            rssItemList.addAll(rssChannel.getItem());
        }
        return rssItemList;
    }
}

