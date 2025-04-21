package com.hospitalAPI.hospitalManagementSystem.service.Interfaces;

import com.hospitalAPI.hospitalManagementSystem.DTO.limsDTO;

import java.util.List;

public interface LimsService {
     List<limsDTO> getFromLims() ;

     List<limsDTO> getFromBatchSetup();
}
