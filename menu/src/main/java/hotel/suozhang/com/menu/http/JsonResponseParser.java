package hotel.suozhang.com.menu.http;

import com.google.gson.Gson;

import org.xutils.common.util.ParameterizedTypeUtil;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by LIJUWEN on 2016/11/15.
 */

public class JsonResponseParser implements ResponseParser {
    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    /**
     * 转换result为resultType类型的对象
     *
     * @param resultType  返回值类型(可能带有泛型信息)
     * @param resultClass 返回值类型
     * @param result      字符串数据
     * @return
     * @throws Throwable
     */
    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        Gson gson = new Gson();
        if (resultClass == List.class) {
            return gson.fromJson(result, (Class<?>) ParameterizedTypeUtil.getParameterizedType(resultType, List.class, 0));
        } else {
            return gson.fromJson(result, resultClass);
        }
    }
}
