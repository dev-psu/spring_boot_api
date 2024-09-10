package com.boot.api.globals.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {
    private byte[] cachedInputStream;

    public RequestWrapper(HttpServletRequest request, byte[] cachedInputStream) {
        super(request);
        this.cachedInputStream = cachedInputStream;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            private InputStream cachedBodyInputStream = new ByteArrayInputStream(cachedInputStream);
            @Override
            public boolean isFinished() {
                try  {
                    return cachedBodyInputStream.available() == 0;
                } catch (IOException e) {
                    log.error("", e);
                }
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() throws IOException {
                return cachedBodyInputStream.read();
            }
        };
    }
}
