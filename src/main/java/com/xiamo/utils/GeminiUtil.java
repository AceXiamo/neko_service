package com.xiamo.utils;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xiamo.config.GeminiProperties;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: AceXiamo
 * @ClassName: GeminiUtil
 * @Date: 2023/12/19 23:38
 */
@Slf4j
@UtilityClass
public class GeminiUtil {

    private final String API = "https://gemini-proxy-dusky.vercel.app";

    private final GeminiProperties geminiProperties;

    static {
        geminiProperties = SpringUtil.getBean(GeminiProperties.class);
    }

    @Builder
    @Getter
    private static class RequestData {
        private List<RequestContent> contents;
    }

    @Builder
    @Getter
    private static class RequestContent {
        private String role;
        private List<RequestContentPart> parts;
    }

    @Builder
    @Getter
    private static class RequestContentPart {
        private String text;
    }

    private String prefixMessage(boolean hasAiSay) {
        StringBuilder sb = new StringBuilder();
        sb.append("# 请充当我的记账助理，我会对你说我花钱干了什么，然后你需要对我的支出进行解读，并返回一个JSON，例如:\n");
        if (hasAiSay) {
            sb.append("{\n" +
                    "  \"icon\": \"☕\uFE0F\",\n" +
                    "  \"price\": \"20\",\n" +
                    "  \"recordType\": \"0\"\n" +
                    "  \"remark\": \"一杯咖啡\",\n" +
                    "  \"aiSay\": \"xxx\",\n" +
                    "  \"typeId\": \"1\",\n" +
                    "  \"typeName\": \"餐饮\",\n" +
                    "}\n" +
                    "\n" +
                    "# 关于字段:\n" +
                    "icon: 你需要根据我所说的来选择一个合适的emoji\n" +
                    "price: 金额\n" +
                    "recordType: 0表示支出, 1表示收入\n" +
                    "remark: 消费的具体内容\n" +
                    "aiSay: 评价一下这一笔支出\n" +
                    "typeId: 分类id\n" +
                    "typeName: 分类名称\n" +
                    "\n");
        } else {
            sb.append("{\n" +
                    "  \"icon\": \"☕\uFE0F\",\n" +
                    "  \"price\": \"20\",\n" +
                    "  \"recordType\": \"0\"\n" +
                    "  \"remark\": \"一杯咖啡\",\n" +
                    "  \"typeId\": \"1\",\n" +
                    "  \"typeName\": \"餐饮\",\n" +
                    "}\n" +
                    "\n" +
                    "# 关于字段:\n" +
                    "icon: 你需要根据我所说的来选择一个合适的emoji\n" +
                    "price: 金额\n" +
                    "recordType: 0表示支出, 1表示收入\n" +
                    "remark: 消费的具体内容\n" +
                    "typeId: 分类id\n" +
                    "typeName: 分类名称\n" +
                    "\n");
        }
        // 分类
        sb.append("# 关于分类\n" +
                "支出分类:\n" +
                "[{\"id\":\"1\",\"name\":\"餐饮\"},{\"id\":\"2\",\"name\":\"交通\"},{\"id\":\"3\",\"name\":\"服饰\"},{\"id\":\"4\",\"name\":\"购物\"},{\"id\":\"5\",\"name\":\"娱乐\"},{\"id\":\"6\",\"name\":\"生活缴费\"},{\"id\":\"7\",\"name\":\"宠物\"},{\"id\":\"8\",\"name\":\"医疗\"},{\"id\":\"9\",\"name\":\"住房\"},{\"id\":\"10\",\"name\":\"通讯\"},{\"id\":\"11\",\"name\":\"学习\"},{\"id\":\"12\",\"name\":\"发红包 / 转账\"},{\"id\":\"13\",\"name\":\"其它\"},{\"id\":\"19\",\"name\":\"零食\"},{\"id\":\"20\",\"name\":\"水果\"}]\n" +
                "收入分类:\n" +
                "[{\"id\":\"14\",\"name\":\"生意\"},{\"id\":\"15\",\"name\":\"工资\"},{\"id\":\"16\",\"name\":\"奖金\"},{\"id\":\"17\",\"name\":\"红包 / 收到转账\"},{\"id\":\"18\",\"name\":\"其它\"}]\n" +
                "\n");
        sb.append("# 对于你的回答:\n" +
                "上方只是一个例子，你仅需要按照我所说的返回一个JSON即可，注意: 我不需要除JSON以外的任何字符!");
        return sb.toString();
    }

    private RequestData requestData(String msg, boolean hasAiSay) {
        return RequestData.builder()
                .contents(List.of(
                        RequestContent.builder()
                                .role("user")
                                .parts(List.of(RequestContentPart.builder()
                                        .text(prefixMessage(hasAiSay))
                                        .build()))
                                .build(),
                        RequestContent.builder()
                                .role("model")
                                .parts(List.of(RequestContentPart.builder()
                                        .text("好的")
                                        .build()))
                                .build(),
                        RequestContent.builder()
                                .role("user")
                                .parts(List.of(RequestContentPart.builder()
                                        .text(msg)
                                        .build()))
                                .build()
                ))
                .build();
    }

    @Builder
    @Getter
    public static class Result {
        private String text;
        private String body;
    }

    public Result say(String msg, boolean hasAiSay) {
        String body = HttpRequest.post(API + "?key=" + geminiProperties.getKey())
                .body(JSON.toJSONString(requestData(msg, hasAiSay)), "application/json")
                .execute()
                .body();
        JSONObject response = JSON.parseObject(body);
        JSONObject candidatesFirst = response.getJSONArray("candidates").getJSONObject(0);
        JSONObject content = candidatesFirst.getJSONObject("content");
        String text = content.getJSONArray("parts").getJSONObject(0).getString("text");
        text = text.replaceAll("```json", "").replaceAll("```", "");

        return Result.builder()
                .text(text)
                .body(body)
                .build();
    }

}
