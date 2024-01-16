package com.xiamo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: AceXiamo
 * @ClassName: BillRequestVo
 * @Date: 2023/7/28 18:40
 */
@Data
public class BillRequestVo {

    @NotNull(message = "chat不能为空")
    private String chat;
    @NotNull(message = "day不能为空")
    private String day;
    private Boolean hasAiSay = false;
    private List<String> images = null;

}
