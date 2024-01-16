package com.xiamo.utils;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xiamo.config.OpenAiProperties;
import com.xiamo.entity.BillRecord;
import com.xiamo.vo.OpenAiMessageBody;
import com.xiamo.vo.OpenAiRequest;
import com.xiamo.vo.OpenAiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Open AI util.
 *
 * @Author: AceXiamo
 * @ClassName: OpenAiUtil
 * @Date: 2023 /7/3 17:36
 */
public class OpenAiUtil {

    /**
     * The constant API.
     */
    private static final String API = "https://api.openai.com/v1/chat/completions";

    /**
     * The constant openAiProperties.
     */
    private static OpenAiProperties openAiProperties;

    static {
        openAiProperties = SpringUtil.getBean(OpenAiProperties.class);
    }

    /**
     * Say open AI message body.
     *
     * @param request the request
     * @return the open AI message body
     */
    public static OpenAiMessageBody say(OpenAiRequest request) {
        String body = HttpRequest.post(API)
                .header("Authorization", "Bearer " + openAiProperties.getKey())
                .body(JSON.toJSONString(request), "application/json")
                .execute()
                .body();
        OpenAiResponse response = JSON.parseObject(body, OpenAiResponse.class);
        if (response != null && response.getChoices() != null && response.getChoices().size() > 0) {
            return response.getChoices().get(0).getMessage();
        }
        return null;
    }

    public static void main(String[] args) {
        openAiProperties = new OpenAiProperties();
        openAiProperties.setKey("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");

        OpenAiRequest request = new OpenAiRequest();
        List<OpenAiMessageBody> list = new ArrayList<>();
        list.add(new OpenAiMessageBody("system", "请充当我的记账助理，我会对你说我花钱干了什么，然后你需要对我的支出进行解读，并返回一个JSON，例如:\\n{\\n  \\\"icon\\\": \\\"☕\\uFE0F\\\",\\n  \\\"price\\\": \\\"20\\\",\\n  \\\"recordType\\\": \\\"0\\\",\\n  \\\"desc\\\": \\\"一杯咖啡\\\",\\n  \\\"aiSay\\\": \\\"xxx\\\"\\n }\\n 关于字段:\\nicon: 你需要根据我所说的来选择一个合适的emoji\\n price: 金额 \\n recordType: 0表示收入, 1表示支出\\n desc: 消费的具体内容\\n aiSay: 吐槽一下这一笔支出\\n \\n 注意：上方只是一个例子，并且只需要返回JSON"));
        list.add(new OpenAiMessageBody("user", "我花了20块钱买了一杯咖啡"));
        request.setMessages(list);
        OpenAiMessageBody res = say(request);
        BillRecord record = JSON.parseObject(res.getContent(), BillRecord.class);
    }

}
