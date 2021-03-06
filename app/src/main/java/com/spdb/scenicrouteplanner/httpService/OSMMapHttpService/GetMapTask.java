package com.spdb.scenicrouteplanner.httpService.OSMMapHttpService;

import android.os.AsyncTask;
import android.util.Log;

import com.spdb.scenicrouteplanner.httpService.HttpService;
import com.spdb.scenicrouteplanner.lib.PathsClassLib;
import com.spdb.scenicrouteplanner.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

public class GetMapTask extends AsyncTask<String, Integer, String> implements HttpService {
    // ==============================
    // Private fields
    // ==============================
    private static final Integer CONNECTION_TIMEOUT = 10000;
    private static final Integer READ_TIMEOUT = 10000;
    private static final Integer WRITE_TIMEOUT = 10000;

    private static final String BASE_URL = "http://api.openstreetmap.org/api/0.6/map";
    private static final String DIRECTORY = PathsClassLib.MAPS_DIRECTORY;
    //http://www.overpass-api.de/api/xapi_meta?*[bbox=20.9327,52.2114,21.0526,52.2642]


    // ==============================
    // Override AsyncTask
    // ==============================
    @Override
    protected String doInBackground(String... params) {
        String query = params[0];
        if (query.isEmpty()) {
            this.cancel(true);
        }

        File destFile = FileUtils.buildPath(DIRECTORY,"osm");
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url("http://www.overpass-api.de/api/xapi_meta?" + query)
                    .build();
            Response response = client.newCall(request).execute();
            BufferedSink sink = Okio.buffer(Okio.sink(destFile));
            sink.writeAll(response.body().source());
            sink.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Log.d("GET_MAP_TASK", "KONIEC");
        return null;
    }

    // ==============================
    // Override HttpService
    // ==============================


    @Override
    public HttpUrl buildURL(String query) {
        HttpUrl httpUrl = HttpUrl.parse(BASE_URL).newBuilder()
                .addEncodedQueryParameter("bbox", query)
                .build();
        return httpUrl;
    }

}
