package com.rohitgangwar.service;
import com.rohitgangwar.entity.Business;
import com.rohitgangwar.exception.BusinessException;
import com.rohitgangwar.repository.BusinessRepository

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    BusinessRepository businessRepository;

    @Override
    public String addBusiness(Long userId) throws BusinessException{

        List<Business> businessList =  businessRepository.findByUserId(userId);
        if(businessList.size() > 0) throw new BusinessException("Business already registered for given user id");

        Business business = new Business();
        business.setUserId(userId);
        businessRepository.save(business);
        return "Business add successfully";

    }
}
