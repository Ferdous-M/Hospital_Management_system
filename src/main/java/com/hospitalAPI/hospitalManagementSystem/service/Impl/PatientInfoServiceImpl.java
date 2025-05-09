package com.hospitalAPI.hospitalManagementSystem.service.Impl;

import com.hospitalAPI.hospitalManagementSystem.DTO.PatientInfoDto;
import com.hospitalAPI.hospitalManagementSystem.service.Interfaces.PatientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientInfoServiceImpl implements PatientInfoService {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate secondaryJdbcTemplate;

    public String callProcedureFromPrimary(String param) {
        return primaryJdbcTemplate.execute((ConnectionCallback<String>) con -> {
            CallableStatement cs = con.prepareCall("{ call my_package.my_procedure(?, ?) }");
            cs.setString(1, param);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();
            return cs.getString(2);
        });
    }

    public int callProcedureFromSecondary(int id) {
        return secondaryJdbcTemplate.execute((ConnectionCallback<Integer>) con -> {
            CallableStatement cs = con.prepareCall("{ call other_package.some_proc(?) }");
            cs.setInt(1, id);
            return cs.executeUpdate();
        });
    }


    @Override
    public List<PatientInfoDto> getAllPatientInfo() {
        return primaryJdbcTemplate.execute((Connection con) -> {
            Statement stmt = con.createStatement();
           // ResultSet rs = stmt.executeQuery(
                //    "SELECT patient_name AS patientName, surgeon_name AS surgeonName " +
                     //       "FROM patient_info p ");

            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM patient_info ");

            List<PatientInfoDto> list = new ArrayList<>();
            while (rs.next()) {
                PatientInfoDto dto = new PatientInfoDto();
                dto.setId(rs.getLong("id"));
                dto.setPatientName(rs.getString("patient_name"));
                dto.setSurgeonName(rs.getString("surgeon_name"));
                dto.setOperationName(rs.getString("operation_name"));
                dto.setOtRoom(rs.getString("ot_room"));
                dto.setGender(rs.getString("gender"));
                dto.setAge(rs.getString("age"));
                dto.setRemarks(rs.getString("remarks"));
                dto.setStatus(rs.getString("status"));
                dto.setFlag(rs.getString("flag"));

                list.add(dto);
            }
            rs.close();
            stmt.close();
            return list;
        });
    }
 /*   public List<PatientInfoDto> getAllPatientInfo() {
        return primaryJdbcTemplate.execute((Connection con) -> {
            CallableStatement cs = con.prepareCall("{ call get_all_patient_info() }");
            cs.execute();

            ResultSet rs = cs.getResultSet();

            List<PatientInfoDto> list = new ArrayList<>();
            while (rs.next()) {
                PatientInfoDto dto = new PatientInfoDto();
                dto.setPatientName(rs.getString("patientName"));
                dto.setSurgeonName(rs.getString("surgeonName"));
                list.add(dto);
            }
            rs.close();
            return list;
        });
    }

  */

    @Override
    public List<PatientInfoDto> getAllPatientInfoFromSecondaryDb() {
        return secondaryJdbcTemplate.execute((Connection con) -> {
            CallableStatement cs = con.prepareCall("{ call get_all_patient_info_secondarydb() }");
            cs.execute();

            ResultSet rs = cs.getResultSet();

            List<PatientInfoDto> list = new ArrayList<>();
            while (rs.next()) {
                PatientInfoDto dto = new PatientInfoDto();
                dto.setPatientName(rs.getString("patientName"));
                dto.setSurgeonName(rs.getString("surgeonName"));
                list.add(dto);
            }
            rs.close();
            return list;
        });
    }

    @Override
    public boolean updatePatientInfo(Long id, PatientInfoDto dto) {
            String sql = "UPDATE patient_info SET patient_name = ?, surgeon_name = ?, operation_name = ?, ot_room = ?, gender = ?, age = ?, remarks = ?, status = ?, flag = ? WHERE id = ?";
            int rows = secondaryJdbcTemplate.update(sql,
                    dto.getPatientName(),
                    dto.getSurgeonName(),
                    dto.getOperationName(),
                    dto.getOtRoom(),
                    dto.getGender(),
                    dto.getAge(),
                    dto.getRemarks(),
                    dto.getStatus(),
                    dto.getFlag(),
                    id
            );
            return rows > 0;
        }
    }
/*
    @Override
    public List<PatientInfoDto> getFromLims() {
        return primaryJdbcTemplate.execute((Connection con) -> {
            Statement stmt = con.createStatement();
            // ResultSet rs = stmt.executeQuery(
            //    "SELECT patient_name AS patientName, surgeon_name AS surgeonName " +
            //       "FROM patient_info p ");

            ResultSet rs = stmt.executeQuery(
                    "select id, type_no, type_name, remarks, status from lims_material_type_infos " +
                            "where company_id = ? ");

            List<PatientInfoDto> list = new ArrayList<>();
            while (rs.next()) {
                PatientInfoDto dto = new PatientInfoDto();
                dto.setId(rs.getLong("id"));
                dto.setPatientName(rs.getString("patient_name"));
                dto.setSurgeonName(rs.getString("surgeon_name"));
                dto.setOperationName(rs.getString("operation_name"));
                dto.setOtRoom(rs.getString("ot_room"));
                dto.setGender(rs.getString("gender"));
                dto.setAge(rs.getString("age"));
                dto.setRemarks(rs.getString("remarks"));
                dto.setStatus(rs.getString("status"));
                dto.setFlag(rs.getString("flag"));

                list.add(dto);
            }
            rs.close();
            stmt.close();
            return list;
        });
    }
    */

//{
//        "patientName": "John Doe",
//        "surgeonName": "Dr. Smith",
//        "operationName": "Appendectomy",
//        "otRoom": "OT-3",
//        "gender": "Male",
//        "age": "35",
//        "remarks": "Stable",
//        "status": "Completed",
//        "flag": "N"
//        }

