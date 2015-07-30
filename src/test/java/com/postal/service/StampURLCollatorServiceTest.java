package com.postal.service;

import com.postal.model.stamp.HTMLStamp;
import com.postal.model.stamp.Stamp;
import com.postal.service.stamp.HTMLGrabber;
import com.postal.service.stamp.StampTransformer;
import com.postal.service.stamp.StampURLCollatorService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by brakhar on 30.03.15.
 */

public class StampURLCollatorServiceTest {

    private static final String SITE_URL = "http://stamp.com";

    private StampURLCollatorService underTest;

    @Mock
    private HTMLGrabber htmlGrabber;

    @Mock
    private Stamp stamp1;
    @Mock
    private Stamp stamp2;

    @Mock
    private HTMLStamp htmlStamp1;
    @Mock
    private HTMLStamp htmlStamp2;

    @Mock
    private StampTransformer stampTransformer;

    private List<Stamp> stampList;
    private List<HTMLStamp> htmlStampList;

    private List<Stamp> stampEmptyList = Collections.EMPTY_LIST;
    private List<HTMLStamp> htmlEmptyList = Collections.EMPTY_LIST;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest = new StampURLCollatorService();
        underTest.setPageUrl(SITE_URL);
        underTest.setHtmlGrabber(htmlGrabber);
        underTest.setStampTransformer(stampTransformer);

        stampList = new ArrayList<Stamp>();
        stampList.add(stamp1);
        stampList.add(stamp2);

        htmlStampList = new ArrayList<HTMLStamp>();
        htmlStampList.add(htmlStamp1);
        htmlStampList.add(htmlStamp2);
    }

    @Test @Ignore
    public void shouldGrabStamps(){
        when(htmlGrabber.grab(SITE_URL, null)).thenReturn(htmlStampList);
        when(stampTransformer.transform(htmlStampList)).thenReturn(stampList);
        List<Stamp> result = underTest.process();

        assertEquals(stampList, result);

    }

    @Test @Ignore
    public void shouldReturnEmptyListStampForEmptyPage(){
        when(htmlGrabber.grab(SITE_URL, null)).thenReturn(htmlEmptyList);
        when(stampTransformer.transform(htmlEmptyList)).thenReturn(stampEmptyList);
        List<Stamp> result = underTest.process();

        assertEquals(Collections.EMPTY_LIST, result);
    }

}
