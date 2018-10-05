package com.wigo.server.controllers;

import com.wigo.server.WigoEndpoints;
import com.wigo.server.dao.UserDao;
import com.wigo.server.dto.LoginDto;
import com.wigo.server.dto.User;
import com.wigo.server.dto.UserDto;
import com.wigo.server.service.JwtLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RestController
@RequestMapping(WigoEndpoints.API_URL)
@Transactional(isolation = READ_COMMITTED)
public class LoginController {

    private static final String FB_ME_URL = "https://graph.facebook.com/v2.7/me?access_token={0}&" +
            "fields=id,name,first_name,middle_name,last_name,email,link&format=json&sdk=android";
    @Autowired
    private UserDao userDao;
    @Autowired
    private RestOperations restOperations;
    @Autowired
    private  JwtLogic jwtLogic;

    @PostMapping(path = WigoEndpoints.LOGIN)
    public LoginDto login(@RequestBody LoginData loginData) {
        // TODO: add remote logout support
        FbData fbData = restOperations.getForObject(FB_ME_URL, FbData.class, loginData.getFbToken());
        User user = new User(null, fbData.name, fbData.name, fbData.email, fbData.id);
        User oldUser = userDao.getUserByFbId(fbData.id);
        if (oldUser == null)
            userDao.createUser(user);
        else {
            user.setId(oldUser.getId());
            userDao.updateUserByFbId(user);
        }
        return new LoginDto(new UserDto(user), jwtLogic.getJwtToken(user.getId()));
    }

    public static class LoginData {
        private String fbToken;

        public String getFbToken() {
            return fbToken;
        }

        public void setFbToken(String fbToken) {
            this.fbToken = fbToken;
        }
    }

    /*
    example:
    {
       "id": "1279934908707215",
       "name": "Alex Khomutenko",
       "first_name": "Alex",
       "last_name": "Khomutenko",
       "email": "alexua89\u0040mail.ru",
       "link": "https://www.facebook.com/app_scoped_user_id/1279934908707215/"
    }
     */
    public static class FbData {
        private String id;
        private String name;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
