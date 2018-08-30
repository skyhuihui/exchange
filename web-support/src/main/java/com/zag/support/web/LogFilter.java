package com.zag.support.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.Enumeration;

/**
 * 转移
 *
 * @author lei
 * @usage
 * @reviewer
 * @since 2017年8月4日
 */
public class LogFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        if (logger.isInfoEnabled()) {
            LogHttpServletResponse httpResponse = new LogHttpServletResponse((HttpServletResponse) response);
            chain.doFilter(request, httpResponse);

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            logger.info(String.format("Request [url]:%s, [queryString]:%s, [requestBody]:%s, [responseBody]:%s",
                    httpRequest.getRequestURI(), httpRequest.getQueryString(), getRequestParamBody(httpRequest),
                    replaceCRLF(new String(httpResponse.getBody()))));
        } else {
            chain.doFilter(request, response);
        }
    }

    private String getRequestParamBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramName = params.nextElement();
            sb.append(paramName).append(":").append(request.getParameter(paramName)).append(",");
        }
        return StringUtils.removeEnd(sb.toString(), ",");
    }

    private String replaceCRLF(String value) {
        return value == null ? null : value.replaceAll("(\r\n|\r|\n|\n\r)", "");
    }

    @Override
    public void destroy() {
    }

    private static class LogHttpServletResponse extends HttpServletResponseWrapper {
        private ByteArrayOutputStream responseBody = new ByteArrayOutputStream();

        /**
         * @param response
         */
        public LogHttpServletResponse(HttpServletResponse response) {
            super(response);
        }

        public byte[] getBody() {
            return responseBody.toByteArray();
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new LogServletOutputStream(super.getOutputStream(), responseBody);
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new LogPrintWriter(super.getWriter(), responseBody);
        }
    }

    private static class LogServletOutputStream extends ServletOutputStream {
        private ServletOutputStream originalStream;
        private OutputStream bodyStream;

        LogServletOutputStream(ServletOutputStream originalStream, OutputStream bodyStream) {
            this.originalStream = originalStream;
            this.bodyStream = bodyStream;
        }

        @Override
        public void write(int b) throws IOException {
            originalStream.write(b);
            bodyStream.write(b);
        }
    }

    private static class LogPrintWriter extends PrintWriter {
        private OutputStreamWriter bodyWriter;

        LogPrintWriter(PrintWriter originalWriter, OutputStream bodyStream) throws FileNotFoundException {
            super(originalWriter);
            this.bodyWriter = new OutputStreamWriter(bodyStream);
        }

        @Override
        public void write(char buf[], int off, int len) {
            super.write(buf, off, len);
            try {
                bodyWriter.write(buf, off, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
