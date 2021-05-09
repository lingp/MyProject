package com.heima.model.mappers.app;

import com.heima.model.behavior.pojos.ApShowBehavior;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApShowBehaviorMapper {
    /**
     * 获取已经存在的用户数据
     * @param entryId
     * @param articleIds
     * @return
     */
    List<ApShowBehavior> selectListByEntryIdAndArticleIds(@Param("entryId") Integer entryId, @Param("articleIds") Integer[] articleIds);

    /**
     * 保存用户展现的行为数据
     * @param entryId
     * @param articleIds
     */
    void saveBehaviors(@Param("entryId") Integer entryId, @Param("articleIds") Integer[] articleIds);
}
