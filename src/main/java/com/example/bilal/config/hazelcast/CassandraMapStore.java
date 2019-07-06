package com.example.bilal.config.hazelcast;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cassandra.core.keyspace.CreateTableSpecification;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.util.SerializationUtils;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapLoaderLifecycleSupport;
import com.hazelcast.core.MapStore;

@SuppressWarnings("rawtypes")
public class CassandraMapStore implements MapStore, MapLoaderLifecycleSupport {
    Logger              LOG = LoggerFactory.getLogger(CassandraMapStore.class);

    CassandraOperations cassandraOperations;

    private String      mapName;

    public CassandraMapStore(CassandraOperations cassandraOperations) {
        this.cassandraOperations = cassandraOperations;
    }

    @Override
    public Object load(Object key) {

        Object object = null;
        try {
            Select select = QueryBuilder.select().all().from(this.mapName);
            select.where(QueryBuilder.eq(CassandraColumns.ID.toString(), ByteBuffer.wrap(SerializationUtils.serialize(key))));

            ResultSet rs = cassandraOperations.query(select);
            Iterator<Row> ite = rs.iterator();
            while (ite.hasNext()) {
                Row row = ite.next();
                ByteBuffer serializedObject = row.getBytes(CassandraColumns.VALUE.toString());
                if (serializedObject != null) {
                    object = SerializationUtils.deserialize(serializedObject.array());
                } else {
                    LOG.error("[load][map={}] JSON string is null for key={} .", this.mapName, key);
                }
            }
        } catch (Exception ex) {
            LOG.error("[load][map={}] Error occured while loading object from repository for key={} .", this.mapName, key, ex);
        }
        return object;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Map loadAll(Collection keys) {
        Map<Object, Object> map = new HashMap<>();
        keys.parallelStream().forEach(key -> map.put(key, load(key)));
        return map;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Iterable loadAllKeys() {
        Set keys = new HashSet<>();
        try {
            Select select = QueryBuilder.select().column(CassandraColumns.ID.toString()).from(this.mapName);
            ResultSet resultSet = cassandraOperations.query(select);
            Iterator<Row> ite = resultSet.iterator();
            while (ite.hasNext()) {
                Row row = ite.next();
                keys.add(SerializationUtils.deserialize(row.getBytes(CassandraColumns.ID.toString()).array()));
            }
        } catch (Exception ex) {
            LOG.error("[loadAllKeys][map={}] Error occured while loading all keys from repository.", this.mapName, ex);
        }
        return keys;
    }

    @Override
    public void init(HazelcastInstance hazelcastInstance, Properties properties, String mapName) {
        this.mapName = mapName;
        CreateTableSpecification tableSpecification = CreateTableSpecification.createTable(this.mapName);
        tableSpecification.ifNotExists(true);
        tableSpecification.partitionKeyColumn(CassandraColumns.ID.toString(), DataType.blob());
        tableSpecification.column(CassandraColumns.VALUE.toString(), DataType.blob());
        cassandraOperations.execute(tableSpecification);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void store(Object key, Object value) {

        try {
            Insert insert = QueryBuilder.insertInto(this.mapName);
            insert.value(CassandraColumns.ID.toString(), ByteBuffer.wrap(SerializationUtils.serialize(key)));
            insert.value(CassandraColumns.VALUE.toString(), ByteBuffer.wrap(SerializationUtils.serialize(value)));
            cassandraOperations.execute(insert);
        } catch (Exception ex) {
            LOG.error("[store][map={}] Error occured while storing object from repository for key={} , value={}", this.mapName, key, value.toString(), ex);
        }

    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public void storeAll(Map map) {
        ((Set<Map.Entry>) map.entrySet()).parallelStream().forEach(entry -> store(entry.getKey(), entry.getValue()));
    }

    @Override
    public void delete(Object key) {
        try {
            Delete delete = QueryBuilder.delete().from(this.mapName);
            delete.where(QueryBuilder.eq(CassandraColumns.ID.toString(), ByteBuffer.wrap(SerializationUtils.serialize(key))));
            cassandraOperations.execute(delete);
        } catch (Exception ex) {
            LOG.error("[delete][map={}] Error occured while deleting object from repository for key={}", this.mapName, key, ex);
        }
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public void deleteAll(Collection keys) {
        keys.parallelStream().forEach(key -> delete(key));
    }

}
