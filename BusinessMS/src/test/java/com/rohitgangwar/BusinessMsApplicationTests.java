package com.rohitgangwar;

import com.rohitgangwar.entity.Business;
import com.rohitgangwar.exception.BusinessException;
import com.rohitgangwar.repository.BusinessRepository;
import com.rohitgangwar.service.BusinessServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class BusinessMsApplicationTests {

	@Mock
	private BusinessRepository businessRepository;

	@InjectMocks
	private BusinessServiceImpl businessServiceImpl = new BusinessServiceImpl();

	@Test
	void addBusinessInvalid() throws BusinessException{
		List<Business> businessList = new ArrayList<>();
		Business business = new Business();
		business.setId((long)1);
		business.setUserId((long)9);
		business.setCommentId((long)1);
		businessList.add(business);

		Mockito.when(businessRepository.findByUserId((long)9)).thenReturn(businessList);
		BusinessException exception = Assertions.assertThrows(BusinessException.class, ()-> businessServiceImpl.addBusiness((long)9));
		Assertions.assertEquals("Business already registered for given user id", exception.getMessage());
	}

	@Test
	void addBusinessValid() throws BusinessException{
		Mockito.when(businessRepository.findByUserId((long)9)).thenReturn(new ArrayList<>());
		Assertions.assertEquals("Business add successfully", businessServiceImpl.addBusiness((long)9));
	}

}

