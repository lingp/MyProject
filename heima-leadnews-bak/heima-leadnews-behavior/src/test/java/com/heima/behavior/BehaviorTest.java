package com.heima.behavior;

import com.heima.behavior.service.AppShowBehaviorService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.behavior.dtos.ShowBehaviorDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.threadlocal.AppThreadLocalUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = BehaviorJarApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BehaviorTest {

    @Autowired
    private AppShowBehaviorService showBehaviorService;

    @Test
    public void testSaveBehavior() {
        ApUser apUser = new ApUser();
        apUser.setId(1l);
        AppThreadLocalUtils.setUser(apUser);
        ShowBehaviorDto dto = new ShowBehaviorDto();

        List<ApArticle> articles = new ArrayList<>();
        ApArticle apArticle = new ApArticle();
        apArticle.setId(19203);
        articles.add(apArticle);

        dto.setArticleIds(articles);

        showBehaviorService.saveShowBehavior(dto);

    }
}
