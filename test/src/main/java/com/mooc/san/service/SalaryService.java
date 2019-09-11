package com.mooc.san.service;

import com.mooc.san.beans.Bean;

/**
 * @author: firo
 * @date: 2019-09-11 19:22
 **/

@Bean
public class SalaryService {

    public Integer calSalary(Integer experience) {
        return experience * 500;
    }
}
