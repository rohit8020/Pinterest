package com.rohitgangwar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rohitgangwar.dto.PinDTO;
import com.rohitgangwar.entity.Pin;
import com.rohitgangwar.repository.PinRepository;

@Service
public class PinService {
    @Autowired
    private PinRepository pinRepository;

    public void createPin(PinDTO pinDTO) {
        Pin pin=pinDTO.createEntity();
        pinRepository.save(pin);
    }

    public Pin getPin(Long pinId) throws Exception {
        Optional<Pin> optional=pinRepository.findById(pinId);
        Pin pin=optional.orElseThrow(()->new Exception("Pin with the id: "+pinId+" is not found!"));
        return pin;
    }

    public List<Pin> getPins(Specification<Pin> specification,Pageable pageable) {
        Page<Pin> pagePins=pinRepository.findAll(specification,pageable);
        return pagePins.getContent();
    }

    public void updatePin(PinDTO pinDTO,Long userId)throws Exception {
        Pin pin=this.getPin(pinDTO.getPinId());
        if(userId==pin.getCreatedBy()) {
            pin.setDescription(pinDTO.getDescription());
            pin.setImage(pinDTO.getImage());
            pin.setLink(pinDTO.getLink());
            pin.setPrivacy(pinDTO.getPrivacy());
            pin.setTags(pinDTO.getTags());
            pin.setTitle(pinDTO.getTitle());
        }else {
            throw new Exception("You are not authorized to do this!");
        }
    }

    public void deletePin(Long pinId,Long userId)throws Exception {
        Pin pin=this.getPin(pinId);
        if(pin.getCreatedBy()==userId) {
            pinRepository.deleteById(pinId);
        }else {
            throw new Exception("You are not authorized to do this!");
        }
    }

}

