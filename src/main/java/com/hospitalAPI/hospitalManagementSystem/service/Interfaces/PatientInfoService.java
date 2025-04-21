package com.hospitalAPI.hospitalManagementSystem.service.Interfaces;


import com.hospitalAPI.hospitalManagementSystem.DTO.PatientInfoDto;

import java.util.List;

public interface PatientInfoService {



    List<PatientInfoDto> getAllPatientInfo();
    List<PatientInfoDto> getAllPatientInfoFromSecondaryDb();



}
