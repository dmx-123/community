package com.nowcoder.community;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.ingest.Processor;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientHttpClient;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.elasticsearch.DiscussPostRepository;
import com.nowcoder.community.entity.DiscussPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.SearchTemplateQuery;
import org.springframework.data.elasticsearch.core.query.SearchTemplateQueryBuilder;
import org.springframework.data.elasticsearch.core.suggest.response.SortBy;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static co.elastic.clients.elasticsearch.ingest.Processor.Kind.Sort;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ElasticsearchTests {

    @Autowired
    private DiscussPostMapper discussMapper;

    @Autowired
    private DiscussPostRepository discussRepository;

    @Autowired
    private ElasticsearchTemplate elasticTemplate;

    @Autowired
    private ElasticsearchClient client;

    @Test
    public void testInsert() {
        discussRepository.save(discussMapper.selectDiscussPostById(241));
        discussRepository.save(discussMapper.selectDiscussPostById(242));
        discussRepository.save(discussMapper.selectDiscussPostById(243));
    }

    @Test
    public void testInsertList() {
        discussRepository.saveAll(discussMapper.selectDiscussPosts(101, 0, 100));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(102, 0, 100));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(103, 0, 100));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(111, 0, 100));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(112, 0, 100));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(131, 0, 100));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(132, 0, 100));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(133, 0, 100));
        discussRepository.saveAll(discussMapper.selectDiscussPosts(134, 0, 100));
    }

    @Test
    public void testUpdate() {
        DiscussPost post = discussMapper.selectDiscussPostById(231);
        post.setContent("我是newbee，使劲儿灌水~");
        discussRepository.save(post);
    }

    @Test
    public void testDelete() {
        // discussRepository.deleteById(231);
        discussRepository.deleteAll();
    }

    @Test
    public void testSearchClient() throws Exception {

        // 这里没明白呢。。anyway先不做了吧，接入另一个项目
        // 增加index
        CreateIndexResponse indexResponse = client.indices().create(c -> c.index("user"));

        // 查询index
        GetIndexResponse getIndexResponse = client.indices().get(i -> i.index("discusspost"));
        System.out.println(getIndexResponse.toString());

        // 判断index是否存在
//        BooleanResponse booleanResponse = client.indices().exists(e -> e.index("discusspost"));
//        System.out.println(booleanResponse.value());

        BooleanResponse booleanResponse = client.exists(e -> e.index("discusspost").id("241"));
        System.out.println(booleanResponse.value());

        // 查询Document
        String keyword = "互联网寒冬";
        SearchResponse<DiscussPost> search = client.search(s -> s.index("discusspost")
                .query(q -> q.term(t -> t.field("title").value(v -> v.stringValue(keyword))))
                .highlight(q -> q.fields("title", o -> o.matchedFields("title", "content").preTags("<p class='key' style='color:red'>").postTags("</p>")))
                .query(q -> q.term(t -> t.field("content").value(v -> v.stringValue(keyword))))
                .highlight(q -> q.fields("content", o -> o.matchedFields("content").preTags("<em>").postTags("</em>")))
                .from(0)
                .size(10)
                .sort(f -> f.field(o -> o.field("type").order(SortOrder.Desc)))
                .sort(f -> f.field(o -> o.field("score").order(SortOrder.Desc)))
                .sort(f -> f.field(o -> o.field("createTime").order(SortOrder.Desc))), DiscussPost.class);

//        discussRepository.searchSimilar();
        if (search != null) {
            for (Hit<DiscussPost> hit : search.hits().hits()) {
                System.out.println(hit);
//            for(Map.Entry<String, JsonData> map : hit.fields().entrySet()) {
//                System.out.println(map.getKey() + " " + map.getValue());
//            }
                DiscussPost post = hit.source();
                System.out.println(post.toString());
                System.out.println("highlight:");
                for (Map.Entry<String, List<String>> ss : hit.highlight().entrySet()) {
                    System.out.println(ss.getKey() + ss.getValue());
                }
                System.out.println();
            }
        } else {
            System.out.println("NULL");
        }
        System.out.println();

        List<String> searchFiels = new ArrayList<>();
        searchFiels.add("title");
        searchFiels.add("content");
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        NativeQuery query = NativeQuery.builder().withQuery(q -> q.multiMatch(t -> t.fields(searchFiels).query(keyword))
        ).build();
        SearchHits<DiscussPost> searchHits = elasticTemplate.search(query, DiscussPost.class);
        List<DiscussPost> discussPosts = new ArrayList<>();
        searchHits.forEach(hit -> {
            discussPosts.add(hit.getContent());
        });
        for (DiscussPost post : discussPosts) {
            System.out.println(post);
        }

    }




}
