package com.json;


import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

public interface IJson {
    public <T> T parasJson(String json, Class<T> cls);

    public <T> T parasJson(InputStream is, Class<T> cls);

    public <T> T parasJson(byte[] buff, Class<T> cls);

    public <T> T parasJson(Reader reader, Class<T> cls);

    public <T> T parasJson(File file, Class<T> cls);

    public <T> T parasJson(URL url, Class<T> cls);

    public <T> T parasJson(URI uri, Class<T> cls);

    public String object2string(Object o);

    public byte[] object2bytes(Object o);

    public InputStream object2stream(Object o);

    public Reader object2reader(Object o);
}
