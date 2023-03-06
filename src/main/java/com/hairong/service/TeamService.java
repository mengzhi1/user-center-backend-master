package com.hairong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hairong.model.domain.Team;
import com.hairong.model.domain.User;
import com.hairong.model.dto.TeamQuery;
import com.hairong.model.request.TeamJoinRequest;
import com.hairong.model.request.TeamQuitRequest;
import com.hairong.model.request.TeamUpdateRequest;
import com.hairong.model.vo.TeamUserVO;

import java.util.List;


/**
* @author hairong
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2023-02-27 23:46:48
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @param loginUSer
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUSer);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 删除解散队伍
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);
}
