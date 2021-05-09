package com.heima.zk;


import com.heima.article.ArticleJarApplication;
import com.heima.common.zookeeper.sequence.Sequences;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = ArticleJarApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class zkSequenceTest {

    @Autowired
    Sequences sequences;

    @Test
    public void testSequence() {
        System.out.println("-----" + sequences.sequenceApCollection());
    }
}
