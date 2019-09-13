package com.cgi.ordermanagement.interceptor;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Spring Web filter for logging request and response.
 *
 * @see org.springframework.web.filter.AbstractRequestLoggingFilter
 * @see ContentCachingRequestWrapper
 * @see ContentCachingResponseWrapper
 */
@Slf4j
@Component
public class RequestAndResponseLoggingFilter extends OncePerRequestFilter {

    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
        }
    }

    protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws ServletException, IOException {
        try {
            beforeRequest(request, response);
            filterChain.doFilter(request, response);
        } finally {
            afterRequest(request, response);
            response.copyBodyToResponse();
        }
    }

    protected void beforeRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        if (log.isInfoEnabled()) {
            logRequestHeader(request, response, request.getRemoteAddr() + "|>");
        }
    }

    protected void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        if (log.isInfoEnabled()) {
            logRequestBody(request, request.getRemoteAddr() + "|>");
            logResponse(response, request.getRemoteAddr() + "|<");
        }
    }

    private static void logRequestHeader(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, String prefix) {

        String requestId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        response.addHeader("RequestId", requestId);
        StringBuffer stringBuffer = new StringBuffer();
        val queryString = request.getQueryString();
        if (queryString == null) {
            log.info("{} {} {}", prefix, request.getMethod(), request.getRequestURI());
        } else {
            log.info("{} {} {}?{}", prefix, request.getMethod(), request.getRequestURI(), queryString);
        }
        Collections.list(request.getHeaderNames()).forEach(headerName ->
                Collections.list(request.getHeaders(headerName)).forEach(headerValue ->
                        stringBuffer
                                .append(headerName)
                                .append(" : ").append(headerValue).append(" | ")));
        stringBuffer .append("RequestId").append(" | ").append(requestId);
        log.info(stringBuffer.toString());
    }

    private static void logRequestBody(ContentCachingRequestWrapper request, String prefix) {
        val content = request.getContentAsByteArray();
        if (content.length > 0) {
            logContent(content, request.getContentType(), request.getCharacterEncoding(), prefix);
        }
    }

    private static void logResponse(ContentCachingResponseWrapper response, String prefix) {

        val status = response.getStatus();
        StringBuffer stringBuffer = new StringBuffer();
        //log.info("{} {} {}", prefix, status, HttpStatus.valueOf(status).getReasonPhrase());

        stringBuffer .append("Status:")
                .append(status)
                .append(" | ")
                .append("Value : ")
                .append(HttpStatus.valueOf(status).getReasonPhrase())
                .append(" | ");

        response.getHeaderNames().forEach(headerName ->
                response.getHeaders(headerName).forEach(headerValue ->
                        stringBuffer
                                .append(headerName)
                                .append(" : ").append(headerValue).append(" | ")));
        val content = response.getContentAsByteArray();
        if (content.length > 0) {
            logContent(content, response.getContentType(), response.getCharacterEncoding(), prefix);
        }
    }

    private static void logContent(byte[] content, String contentType, String contentEncoding, String prefix) {
        val mediaType = MediaType.valueOf(contentType);
        val visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
        if (visible) {
            try {
                val contentString = new String(content, contentEncoding);
                Stream.of(contentString.split("\r\n|\r|\n")).forEach(line -> log.info("{} {}", prefix, line));
            } catch (UnsupportedEncodingException e) {
                log.info("{} [{} bytes content]", prefix, content.length);
            }
        } else {
            log.info("{} [{} bytes content]", prefix, content.length);
        }
    }

    private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }
}