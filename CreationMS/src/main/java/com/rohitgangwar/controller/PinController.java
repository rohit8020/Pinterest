package com.rohitgangwar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohitgangwar.dto.PinDTO;
import com.rohitgangwar.entity.Pin;
import com.rohitgangwar.service.PinService;
import com.rohitgangwar.util.YourSpecification;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/content/pin")
public class PinController {

    @Autowired
    private PinService pinService;

    @PostMapping("/create")
    public ResponseEntity<String> createPin(@RequestBody @Valid PinDTO pinDTO) {
        try {
            pinService.createPin(pinDTO);
            return new ResponseEntity<String>("Pin created successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<PinDTO> getPin(@RequestParam Long pinId) throws Exception {
        Pin pin = pinService.getPin(pinId);
        return new ResponseEntity<PinDTO>(PinDTO.valueOf(pin), HttpStatus.OK);
    }

    @GetMapping("/getpins")
    public ResponseEntity<List<PinDTO>> getPinsByUserId(
            @RequestParam Map<String, String> params,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "title") String sortBy) {
        Pageable pageRequest = PageRequest.of(page, size,
                Sort.by(sort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));

        List<Pin> pins = pinService.getPins(YourSpecification.buildSpecification(params), pageRequest);
        List<PinDTO> pinDTOs = pins.stream().map(pin -> PinDTO.valueOf(pin)).toList();
        return new ResponseEntity<>(pinDTOs, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePin(
            @RequestBody PinDTO pinDTO,
            @RequestHeader("X_userId") Long userId) throws Exception {
        pinService.updatePin(pinDTO,userId);

        return new ResponseEntity<String>("Pin is updated!", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePin(
            @RequestParam Long pinId,
            @RequestHeader("X_userId") Long userId
    )throws Exception{
        pinService.deletePin(pinId,userId);
        return new ResponseEntity<String>("Pin is deleted!",HttpStatus.OK);
    }

}

