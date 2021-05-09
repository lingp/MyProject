package com.heima.article.service;

import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.common.dtos.ResponseResult;

public interface AppArticleService {

    /**
     * 加载数据 1:加载更多  2:加载更新
     * @param type
     * @param dto
     * @return
     */
    public ResponseResult load(Short type, ArticleHomeDto dto);

}
