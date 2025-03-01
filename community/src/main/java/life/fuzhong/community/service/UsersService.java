package life.fuzhong.community.service;

import jakarta.annotation.Resource;
import life.fuzhong.community.mapper.UserMapper;
import life.fuzhong.community.model.Users;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Resource
    private UserMapper userMapper;

    public void insertOrUpdate(Users users) {
        Users dbUser = userMapper.findByAccountID(users.getAccountId());

        if (dbUser == null){
            //插入
            users.setGmtCreate(System.currentTimeMillis());
            users.setGmtModified(users.getGmtCreate());
            userMapper.insert(users);
        }else{
            //更新
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(users.getAvatarUrl());
            dbUser.setName(users.getName());
            dbUser.setToken(users.getToken());
            userMapper.update(dbUser);

        }
    }
}
