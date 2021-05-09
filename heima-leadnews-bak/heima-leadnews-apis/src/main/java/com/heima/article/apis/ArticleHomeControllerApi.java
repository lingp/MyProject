package com.heima.article.apis;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;

/**
 * 首页文章
 */
public interface ArticleHomeControllerApi {

    /**
     * 加载首页文章
     * @param dto
     * @return
     */
    ResponseResult load(ArticleHomeDto dto);

    /**
     * 加载更多
     * @param dto
     * @return
     */
    ResponseResult loadMore(ArticleHomeDto dto);

    /**
     * 加载最新的数据
     * @param dto
     * @return
     */
    ResponseResult loadNew(ArticleHomeDto dto);

}
