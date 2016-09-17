package com.wigo.server.controllers;

import com.wigo.server.WigoEndpionts;
import com.wigo.server.dao.UserDao;
import com.wigo.server.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(WigoEndpionts.API_URL)
public class LoginController {
    private static final String FB_ME_URL = "https://graph.facebook.com/v2.7/me?access_token={0}&" +
            "fields=id,name,first_name,middle_name,last_name,email,link&format=json&sdk=android";
    private final UserDao userDao;
    private final RestOperations restOperations;

    @Autowired
    public LoginController(UserDao userDao, RestOperations restOperations) {
        this.userDao = userDao;
        this.restOperations = restOperations;
    }

    @PostMapping(path = WigoEndpionts.LOGIN)
    public UserDto login(@RequestBody LoginData loginData) {
        // TODO: add transaction support
        // TODO: add cookie setting
        FbData fbData = restOperations.getForObject(FB_ME_URL, FbData.class, loginData.getFbToken());
        UserDto user = new UserDto(null, fbData.name);
        UserDto oldUser = userDao.getUserByEmail(fbData.email);
        if (oldUser == null)
            userDao.createUser(fbData.email, user);
        else {
            user.setId(oldUser.getId());
            userDao.updateUserByEmail(fbData.email, user);
        }
        return user;
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
    }
}
