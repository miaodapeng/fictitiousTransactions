package com.vedeng.system.service;

import com.vedeng.authorization.model.Platform;
import com.vedeng.authorization.model.RRoleDataAuthority;
import com.vedeng.common.service.BaseService;

import java.util.List;

/**
 * @Author wayne.liu
 * @Description 数据权限
 * @Date 2019/6/6 9:32
 */
public interface DataAuthorityService extends BaseService {

    void saveDataAuthority(String data,Integer roleId);

    List<RRoleDataAuthority> queryListByRoleId(Integer roleId);

}
