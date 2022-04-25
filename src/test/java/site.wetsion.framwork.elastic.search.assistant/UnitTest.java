package site.wetsion.framwork.elastic.search.assistant;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import site.wetsion.framework.elastic.search.assistant.ElasticSearchAssistant;
import site.wetsion.framework.elastic.search.assistant.query.Bool;
import site.wetsion.framework.elastic.search.assistant.query.Terms;

/**
 * @author <a href="mailto:shuanghua@fordeal.com">霜华</a>
 * @date 2022-04-25 14:47
 */
public class UnitTest {

    @Test
    public void testBuilder() {
        Bool bool = new Bool();
        bool.filter(new Terms<>("age", ImmutableList.of(20, 21)));
        String query = ElasticSearchAssistant.newBuilder().query(bool).from(0).size(100).toString();
        System.out.println(query);
    }
}
