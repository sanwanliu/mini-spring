package com.mooc.san.controller;

import com.mooc.san.beans.AutoWired;
import com.mooc.san.service.SalaryService;
import com.mooc.san.web.mvc.Controller;
import com.mooc.san.web.mvc.RequestMapping;
import com.mooc.san.web.mvc.RequestParam;

/**
 * @author: firo
 * @date: 2019-09-11 15:01
 **/

@Controller
public class SalaryController {

    @AutoWired
    private SalaryService salaryService;

    @RequestMapping("/get_salary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {
        return salaryService.calSalary(Integer.valueOf(experience));
    }
}
