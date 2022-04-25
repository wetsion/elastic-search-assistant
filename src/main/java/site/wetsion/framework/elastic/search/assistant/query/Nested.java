package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.Map;

/**
 * 嵌套查询，查询示例：
 * <p>
 * {"query":{"nested":{"path":"parent","query":{"bool":{"must":[{"term":{"parent.child_name":"blue"}}]}}}}}
 * </p>
 * <p>
 * 使用了嵌套文档的索引才需要使用这个查询，PS.不推荐使用嵌套文档索引，会导致索引体积极速膨胀
 * </p>
 * @author wetsion
 */
public class Nested implements QueryCondition {

    private String path;
    private QueryCondition query;

    /**
     * 构造器，同时指定父字段路径和查询条件
     * @param path 嵌套路径，父字段
     * @param query 查询条件
     */
    public Nested(String path, QueryCondition query) {
        this.path = path;
        this.query = query;
    }

    /**
     * 构造器，指定父字段路径
     * @param path 父字段路径
     */
    public Nested(String path) {
        this(path, null);
    }

    /**
     * 设置nested的查询body
     * @param query 查询body
     * @return self
     */
    public Nested query(QueryCondition query) {
        this.query = query;
        return this;
    }

    @Override
    public String cond() {
        return "nested";
    }

    @Override
    public Object value() {
        Map<String, Object> body = Maps.newHashMapWithExpectedSize(2);
        body.put("path", this.path);
        body.put("query", this.query.toMap());
        return body;
    }

}
