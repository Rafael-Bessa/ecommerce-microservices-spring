package bessa.morangon.rafael.gateway.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class HeaderMutatingRequest extends HttpServletRequestWrapper {
    private final Map<String, String> extraHeaders = new HashMap<>();

    public HeaderMutatingRequest(HttpServletRequest request) {
        super(request);
    }

    public void addHeader(String name, String value) {
        extraHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        if (extraHeaders.containsKey(name)) return extraHeaders.get(name);
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        var names = new ArrayList<String>(extraHeaders.keySet());
        super.getHeaderNames().asIterator().forEachRemaining(names::add);
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (extraHeaders.containsKey(name)) {
            return Collections.enumeration(List.of(extraHeaders.get(name)));
        }
        return super.getHeaders(name);
    }
}