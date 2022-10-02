package com.swu.audit.org.controller;

import com.swu.audit.org.service.OrgSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/org/orgSet")
public class OrgSetController {

    //注入service
    @Autowired
    private OrgSetService orgSetService;
}
