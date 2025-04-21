package com.hospitalAPI.hospitalManagementSystem.controller;

import com.hospitalAPI.hospitalManagementSystem.DTO.PatientInfoDto;
import com.hospitalAPI.hospitalManagementSystem.DTO.limsDTO;
import com.hospitalAPI.hospitalManagementSystem.service.Interfaces.LimsService;
import com.hospitalAPI.hospitalManagementSystem.service.Interfaces.PatientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/patient")
public class Lims_Controller {

    @Autowired
    private PatientInfoService patientInfoService;

    @Autowired
    LimsService limsService;

    @GetMapping("/getFromLims")
    public ResponseEntity<Object> getFromLims(){
        List<limsDTO> Infos = this.limsService.getFromLims();
        return new ResponseEntity<Object>(Infos, HttpStatus.OK);
    }

    @GetMapping("/getFromBatchSetup")
    public ResponseEntity<Object> getFromBatchSetup(){
        List<limsDTO> Infos = this.limsService.getFromBatchSetup();
        return new ResponseEntity<Object>(Infos, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatientInfo(
            @PathVariable Long id,
            @RequestBody PatientInfoDto dto
    ) {
        boolean updated = patientInfoService.updatePatientInfo(id, dto);
        if (updated) {
            return ResponseEntity.ok("Patient info updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
        }
    }

}
