package me.jcala.xmarket.server.service.inter;


import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {


    ResponseEntity<?> loginAndGetToken(String username,String password);

    ResponseEntity<?> register(String username,String password,String phone);//用户注册

    ResponseEntity<?> updateSchool(String id, String school);//设置用户所在的学校

    ResponseEntity<?> gainSchoolList();//获取学校的列表

    ResponseEntity<?> updatePassword(String username, String oldPass, String newPass);//更新用户信息

    ResponseEntity<?> updateAvatar(String username, HttpServletRequest request) throws Exception;//更新用户头像

}
