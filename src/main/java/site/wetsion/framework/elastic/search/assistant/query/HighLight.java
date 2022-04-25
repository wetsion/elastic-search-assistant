package site.wetsion.framework.elastic.search.assistant.query;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import site.wetsion.framework.elastic.search.assistant.QueryCondition;

import java.util.*;

/**
 * 高亮查询
 * @author wetsion
 */
public class HighLight implements QueryCondition {

    private List<String> preTags;
    private List<String> postTags;
    private List<HighLightField> fields;

    public HighLight() {
        preTags = new LinkedList<>();
        postTags = new LinkedList<>();
        fields = new LinkedList<>();
    }

    /**
     * 指定高亮匹配词的前缀标签，主要用于html显示
     * @param preTags 前缀标签
     * @return self
     */
    public HighLight preTags(String... preTags) {
        if (null != preTags && preTags.length > 0) {
            this.preTags.addAll(Arrays.asList(preTags));
        }
        return this;
    }

    /**
     * 指定高亮匹配的后缀标签，主要用于html展示
     * @param postTags 后缀标签
     * @return self
     */
    public HighLight postTags(String... postTags) {
        if (null != postTags && postTags.length > 0) {
            this.postTags.addAll(Arrays.asList(postTags));
        }
        return this;
    }

    /**
     * 指定高亮字段
     * @param field 高亮字段
     * @return self
     */
    public HighLight field(HighLightField field) {
        if (null != field) {
            this.fields.add(field);
        }
        return this;
    }

    @Override
    public String cond() {
        return "highlight";
    }

    @Override
    public Object value() {
        Map<String, Object> highlight = Maps.newHashMapWithExpectedSize(3);
        if (!preTags.isEmpty()) {
            highlight.put("pre_tags", preTags);
        }
        if (!postTags.isEmpty()) {
            highlight.put("post_tags", postTags);
        }
        if (!fields.isEmpty()) {
            List<Object> fds = new ArrayList<>(fields.size());
            for (HighLightField field : fields) {
                Map<String, Object> fd = ImmutableMap.of(field.cond(), field.value());
                fds.add(fd);
            }
            highlight.put("fields", fds);
        }
        return highlight;
    }
}
