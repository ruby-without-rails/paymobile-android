package br.com.codecode.paymobile.android.rest;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.codecode.paymobile.android.R;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by felipe on 27/01/2018.
 */
public class RestApiFactory {
    private Context context;
    private Retrofit retrofit;
    private String authToken;
    private long readTimeout;
    private long writeTimeout;
    private String version;

    public RestApiFactory(){
        writeTimeout = 30;
        readTimeout = 60;
        version = null;
        retrofit = null;
    }

    public RestApiFactory(Context context, String authToken) {
        this();
        this.context = context;
        this.authToken = authToken;
    }

    public <T> T create(Class<T> service) {
        if (retrofit == null) {
            reinitialize();
        }

        return retrofit.create(service);
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void reinitialize() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder()
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClientBuilder.addInterceptor(interceptor);

        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();

                if (authToken != null) {
                    builder.header(context.getString(R.string.header_token), authToken);
                }

                if (version != null) {
                    builder.header(context.getString(R.string.header_version), version);
                }

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        });

        OkHttpClient okHttpClient = okHttpClientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.server))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
