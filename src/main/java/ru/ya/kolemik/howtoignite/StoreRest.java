/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ya.kolemik.howtoignite;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
 * is enabled
 *
 * @author gbrey@redhat.com
 *
 */

@Path("/store")
@Singleton
public class StoreRest {
    private static final String ORG_CACHE = "Organizations";

    @Inject
    HelloService helloService;

    private Ignite ignite;

    private IgniteCache<Long, Organization> cache;

    @PostConstruct
    public void init() {
        System.out.println("TRY TO START IGNITE");
        Ignition.setClientMode(true);

        ignite = Ignition.start("persistent.xml");
            // Activate the cluster. Required to do if the persistent store is enabled because you might need
            // to wait while all the nodes, that store a subset of data on disk, join the cluster.
            ignite.cluster().active(true);

            CacheConfiguration<Long, Organization> cacheCfg = new CacheConfiguration<>(ORG_CACHE);

            cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
            cacheCfg.setBackups(1);
            cacheCfg.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
            cacheCfg.setIndexedTypes(Long.class, Organization.class);

            cache = ignite.getOrCreateCache(cacheCfg);

    }

    @PreDestroy
    public void destroy() {
        cache.close();
        cache = null;
        ignite.close();
        ignite = null;
    }

    @GET
    @Path("/text")
    @Produces({ "text/html" })
    public String text() {
        QueryCursor<List<?>> cur = cache.query(
                new SqlFieldsQuery("select id, name from Organization where name like ?")
                    .setArgs("organization-54"));
        return "SQL Result: " + cur.getAll();
    }

    @GET
    @Path("/query/{name}")
    @Produces({ "text/html" })
    public String query(@PathParam("name") String name) {
        QueryCursor<List<?>> cur = cache.query(
                new SqlFieldsQuery("select id from Organization where name like ?")
                    .setArgs(name));
        List<Organization> result = new ArrayList<>();
        cur.forEach(entry -> {
            Organization org = cache.get(Long.valueOf(String.valueOf(entry.get(0))));
            result.add(org);
        });
        return "Result: " + result;
    }

    @GET
    @Path("/prepare")
    @Produces({ "text/html" })
    public String prepare() {
        System.out.println("Populating the cache...");

        try (IgniteDataStreamer<Long, Organization> streamer = ignite.dataStreamer(ORG_CACHE)) {
            streamer.allowOverwrite(true);

            for (long i = 0; i < 100; i++) {
                streamer.addData(i, new Organization(i, "organization-" + i));
            }
        }
        return "OK";
    }

    @GET
    @Path("/json")
    @Produces({ "application/json" })
    public String getHelloWorldJSON() {
        return "{\"result\":\"" + helloService.sayHello("World") + "\"}";
    }

    @GET
    @Path("/xml")
    @Produces({ "application/xml" })
    public String getHelloWorldXML() {
        return "<xml><result>" + helloService.sayHello("World") + "</result></xml>";
    }

}
