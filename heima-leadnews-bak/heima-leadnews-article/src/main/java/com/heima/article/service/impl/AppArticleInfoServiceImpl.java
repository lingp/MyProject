package com.heima.article.service.impl;

import com.google.common.collect.Maps;
import com.heima.article.service.AppArticleInfoService;
import com.heima.model.article.dtos.ArticleInfoDto;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.article.pojos.ApCollection;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.behavior.pojos.ApLikesBehavior;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.mappers.app.*;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserFollow;
import com.heima.utils.common.BurstUtils;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
public class AppArticleInfoServiceImpl implements AppArticleInfoService {

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private ApUnlikesBehaviorMapper apUnlikesBehaviorMapper;

    @Autowired
    private ApLikesBehaviorMapper apLikesBehaviorMapper;

    @Autowired
    private ApCollectionMapper apCollectionMapper;

    @Autowired
    private ApUserFollowMapper apUserFollowMapper;

    @Autowired
    private ApAuthorMapper apAuthorMapper;

    /**
     * 加载文章详情文章
     *
     * @param articleId
     * @return
     */
    public ResponseResult getArticleInfo(Integer articleId) {
        // 参数无效
        if (articleId == null && articleId < 1) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApArticleConfig config = apArticleConfigMapper.selectByArticleId(articleId);
        Map<String, Object> data = new HashMap<>();

        if (config == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        } else if (!config.getIsDelete()) {
            ApArticleContent content = apArticleContentMapper.selectByArticleId(articleId);
            data.put("content", content);
        }
        data.put("config", config);
        return ResponseResult.okResult(data);
    }

    /**
     * 加载文章详情的初始化配置信息，比如关注、喜欢、不喜欢、阅读位置等
     *
     * @param dto
     * @return
     */
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto) {

        ApUser user = AppThreadLocalUtils.getUser();

        // 用户和设备不能同时为空
        if (user == null && dto.getEquipmentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        Long userId = null;
        if (user != null) {
            userId = user.getId();
        }

        ApBehaviorEntry apBehaviorEntry = getApBehaviorEntryMapper().selectByUserIdOrEquipment(userId, dto.getEquipmentId());

        // 行为实体 - 一定有值（要么是设备ID/要么是用户ID）,除非参数错误
        if (apBehaviorEntry == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        boolean isUnLike = false, isLike = false, isCollection = false, isFollow = false;
        String burst = BurstUtils.groudOne(apBehaviorEntry.getId());

        // 判断是否是已经不喜欢
        ApUnlikesBehavior apUnlikesBehavior = getApUnlikesBehaviorMapper().selectLastUnLike(apBehaviorEntry.getId(), dto.getArticleId());
        if (apUnlikesBehavior != null && apUnlikesBehavior.getType() == ApUnlikesBehavior.Type.UNLIKE.getCode()) {
            isUnLike = true;
        }

        // 判断是否是已经喜欢
        ApLikesBehavior apLikesBehavior = getApLikesBehaviorMapper().selectLastLike(burst, apBehaviorEntry.getId(), dto.getArticleId(), ApCollection.Type.ARTICLE.getCode());
        if (apLikesBehavior != null && apLikesBehavior.getOperation() == ApLikesBehavior.Operation.LIKE.getCode()) {
            isLike = true;
        }

        // 判断是否收藏
        ApCollection apCollection = getApCollectionMapper().selectForEntryId(burst, apBehaviorEntry.getId(), dto.getArticleId(), ApCollection.Type.ARTICLE.getCode());
        if (apCollection != null) {
            isCollection = true;
        }
        // 判断是否关注
        ApAuthor apAuthor = getApAuthorMapper().selectById(dto.getAuthorId());
        if (user != null && apAuthor != null && apAuthor.getUserId() != null) {
            ApUserFollow apUserFollow = getApUserFollowMapper().selectByFollowId(BurstUtils.groudOne(user.getId()), user.getId(), apAuthor.getUserId());
            if (apUserFollow != null) {
                isFollow = true;
            }
        }

        Map<String, Object> data = Maps.newHashMap();
        data.put("isfollow", isFollow);
        data.put("islike", isLike);
        data.put("isunlike", isUnLike);
        data.put("iscollection", isCollection);


        return ResponseResult.okResult(data);
    }

}
