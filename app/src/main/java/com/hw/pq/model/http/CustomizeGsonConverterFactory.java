package com.hw.pq.model.http;

import android.widget.Toast;

import com.hw.pq.app.App;
import com.hw.pq.model.bean.BaseModel;
import com.hw.pq.util.RSAUtil;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Duran on 2019/1/9.
 */

public class CustomizeGsonConverterFactory extends Converter.Factory {

    public static CustomizeGsonConverterFactory create() {
        return create(new Gson());
    }

    public static CustomizeGsonConverterFactory create(Gson gson) {
        return new CustomizeGsonConverterFactory(gson);
    }

    private final Gson gson;

    private CustomizeGsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomizeGsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomizeGsonRequestBodyConverter<>(gson, adapter);
    }

    public class CustomizeGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = Charset.forName("UTF-8");

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        CustomizeGsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    public class CustomizeGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        CustomizeGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            //把responsebody转为string
            String response = value.string();
            response = RSAUtil.decodeStr(response);
//            if (BuildConfig.DEBUG) {
//                //打印后台数据
//                Log.e(BuildConfig.APPLICATION_ID, response);
//            }

            BaseModel baseResponse = gson.fromJson(response, BaseModel.class);
            try {
                if ("登录已过期，请重新登录".equals(baseResponse.getMsg())) {
                    App.getInstance().exitToLogin();
                    Toast.makeText(App.getInstance(), baseResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                value.close();
                return adapter.fromJson(response);
            }
        }
    }

}
