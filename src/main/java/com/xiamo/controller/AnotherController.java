package com.xiamo.controller;

import com.xiamo.common.AjaxResult;
import com.xiamo.service.IMaConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Another controller.
 *
 * @Author: AceXiamo
 * @ClassName: AnotherController
 * @Date: 2023 /3/30 16:52
 */
@RestController
@RequestMapping("another")
public class AnotherController {

    @Autowired
    private IMaConfigService maConfigService;

    /**
     * Config ajax result.
     *
     * @param key the key
     * @return the ajax result
     */
    @PostMapping("config")
    public AjaxResult config(@RequestParam("key") String key) {
        return AjaxResult.success(maConfigService.getById(key));
    }

}
