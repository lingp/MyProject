package com.heima.model.mappers.app;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserArticleList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApUserArticleListMapper {

    /**
     * 按照用户属性阅读习惯，加载文章id
     * @param user
     * @param dto
     * @param type
     * @return
     */
    List<ApUserArticleList> loadArticleIdListByUser(@Param("user")ApUser user,
                                                    @Param("dto")ArticleHomeDto dto,
                                                    @Param("type")Short type);
}
