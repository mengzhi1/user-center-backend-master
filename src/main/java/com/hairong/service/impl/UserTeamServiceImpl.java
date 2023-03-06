package com.hairong.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hairong.mapper.UserTeamMapper;
import com.hairong.model.domain.UserTeam;


import com.hairong.service.UserTeamService;
import org.springframework.stereotype.Service;


/**
* @author 86139
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2023-02-27 23:48:40
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService {

}




