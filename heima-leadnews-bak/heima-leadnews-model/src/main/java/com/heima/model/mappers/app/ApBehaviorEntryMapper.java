package com.heima.model.mappers.app;

import com.heima.model.behavior.pojos.ApBehaviorEntry;

public interface ApBehaviorEntryMapper {
    ApBehaviorEntry selectByUserIdOrEquipment(Long userId, Integer equipmentId);
}
