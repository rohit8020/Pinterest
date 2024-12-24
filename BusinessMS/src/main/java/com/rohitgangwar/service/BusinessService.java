package com.rohitgangwar.service;


import com.rohitgangwar.exception.BusinessException;

public interface BusinessService {
    String addBusiness(Long userId) throws BusinessException;
}
