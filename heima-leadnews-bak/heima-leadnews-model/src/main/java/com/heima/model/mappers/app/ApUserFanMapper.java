package com.heima.model.mappers.app;

import com.heima.model.user.pojos.ApUserFan;

public interface ApUserFanMapper {
    int insert(ApUserFan record);

    ApUserFan selectByFansId(String burst, Integer userId, Long fansId);

    int deleteByFansId(String burst, Integer userId, Long fansId);
}
