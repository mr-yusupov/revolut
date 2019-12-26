package com.revolut.money.systemTest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.CHARSET_PARAMETER;
import static org.apache.commons.codec.CharEncoding.UTF_8;
import static org.apache.http.protocol.HTTP.CONTENT_TYPE;

public class HttpUtils {

    static HttpResponse get(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        return client.execute(request);
    }

    static HttpResponse post(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        return client.execute(request);
    }

    static HttpResponse post(String url, String content) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost(url);
        request.setHeader(CONTENT_TYPE, APPLICATION_JSON);
        request.setHeader(CHARSET_PARAMETER, UTF_8);
        BasicHttpEntity requestEntity = new BasicHttpEntity();
        requestEntity.setContent(new ByteArrayInputStream(content.getBytes()));
        request.setEntity(requestEntity);

        return client.execute(request);
    }

    static String contentOf(HttpResponse response) throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            response.getEntity().writeTo(stream);
            return new String(stream.toByteArray());
        }
    }
}
