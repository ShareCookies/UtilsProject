package com.china.hcg.db;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MongoDB {
    @Autowired
    MongoTemplate mongoTemplate;
    /**
     * @description 查询并更新
     */
    public int getSyncMemberFindAndUpdate(String collectionName,String accountId, String fieldName, long version, Object value, boolean lastConsumeTime) {
        //query：这是一个 Query 对象，它包含了查询条件。
        // 在这个例子中，我们使用 Criteria 构建了一个查询条件，要求 _id 字段等于指定的 accountId 值，并且 fieldName + "Version" 字段不存在，或者小于指定的 version 值。
        Query query = new Query(Criteria.where("_id").is(accountId)
                .orOperator(
                        Criteria.where(fieldName + "Version").exists(false),
                        Criteria.where(fieldName + "Version").lt(version)
                )
        );
        //update：这是一个 Update 对象，它包含了要对文档执行的更新操作。
        // 在这个例子中，我们使用 Update 对象设置了两个更新操作：set(fieldName + "Version", version)：将指定的 fieldName + "Version" 字段设置为给定的 version 值。
        //set(fieldName, value)：将指定的 fieldName 字段设置为给定的 value 值。
        //如果 lastConsumeTime 为 true，还会设置 lastConsumeTime 字段为当前时间的毫秒数
        Update update = new Update()
                .set(fieldName + "Version", version)
                .set(fieldName, value);
        if (lastConsumeTime) {
            update.set("lastConsumeTime", System.currentTimeMillis());
        }

        WriteResult writeResult = mongoTemplate.updateFirst(query, update, collectionName);
        return writeResult.getN();
    }
}
