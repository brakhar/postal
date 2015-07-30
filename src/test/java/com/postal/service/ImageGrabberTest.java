package com.postal.service;

import com.postal.service.stamp.ImageGrabber;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockserver.integration.ClientAndProxy;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

import static org.junit.Assert.assertArrayEquals;
import static org.mockserver.integration.ClientAndProxy.startClientAndProxy;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * Created by brakhar on 02.04.15.
 */
public class ImageGrabberTest {

    private final static String HOST_PORT = "http://localhost:8080";

    private final static String RIGHT_PATH = "/src/img/id=123";

    private final static String WRONG_PATH = "/src/ung/";

    private byte[] imageContent = new byte[]{123,23,23,12,21,34};

    private ClientAndProxy proxy;
    private ClientAndServer mockServer;

    private ImageGrabber underTest;

    @Before
    public void setUp(){
        underTest = new ImageGrabber();
        startProxy();
    }

    @After
    public void tearDown(){
        stopProxy();
    }

    @Test @Ignore
    public void shouldGrabImage(){
        mockServer.when(request().withPath(RIGHT_PATH)).respond(response()
                .withHeaders(new Header("Content-Type", "/image/jpeg"))
                .withBody(imageContent));

        byte[] grabbedImageContent = underTest.grab(HOST_PORT + RIGHT_PATH);

        assertArrayEquals(imageContent, grabbedImageContent);
    }

    @Test @Ignore
    public void shouldReturnEmptyImageWrongURL(){
        mockServer.when(request().withPath(WRONG_PATH)).respond(response()
                .withHeaders(new Header("Content-Type", "/image/jpeg"))
                .withBody(""));

        byte[] grabbedImageContent = underTest.grab(HOST_PORT + WRONG_PATH);

        assertArrayEquals(new byte[]{}, grabbedImageContent);
    }

    @Test @Ignore
    public void shouldReturnEmptyImageError404(){
        mockServer.when(request().withPath(RIGHT_PATH)).respond(response()
                .withHeaders(new Header("Content-Type", "/image/jpeg"))
                .withStatusCode(404)
                .withBody("Error 404"));

        byte[] grabbedImageContent = underTest.grab(RIGHT_PATH + WRONG_PATH);

        assertArrayEquals(new byte[]{}, grabbedImageContent);

    }

    public void startProxy() {
        mockServer = startClientAndServer(8080);
        proxy = startClientAndProxy(9090);
    }

    public void stopProxy() {
        proxy.stop();
        mockServer.stop();
    }

}
