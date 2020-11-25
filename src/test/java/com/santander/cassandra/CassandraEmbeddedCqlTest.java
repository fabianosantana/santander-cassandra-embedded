package com.santander.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.oss.driver.api.core.CqlSession;
import com.github.nosan.embedded.cassandra.test.spring.Cql;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.nosan.embedded.cassandra.test.ClusterFactory;
import com.github.nosan.embedded.cassandra.test.CqlSessionFactory;
import com.github.nosan.embedded.cassandra.test.TestCassandra;
import com.github.nosan.embedded.cassandra.test.spring.EmbeddedCassandra;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@EmbeddedCassandra(scripts = "/schema.cql")
public class CassandraEmbeddedCqlTest {

    @Autowired
    private CqlSession session;

    @Test
    @Cql(scripts = "/data_users.cql")
    void findAllUsers() {
        assertThat(this.session.execute("SELECT * FROM test.users")).hasSize(8);
    }

    @Test
    @Cql(scripts = "/data_clients.cql")
    void findAllClients() {
        assertThat(this.session.execute("SELECT * FROM test.clients")).hasSize(2);
    }


    //com.datastax.oss:java-driver-core:4.0.1
    @Configuration
    static class CqlSessionConfiguration {

        @Bean
        public CqlSession cqlSession(TestCassandra testCassandra) {
            return new CqlSessionFactory().create(testCassandra.getSettings());
        }

    }

    //com.datastax.cassandra:cassandra-driver-core:3.7.1
    @Configuration
    static class ClusterConfiguration {

        @Bean
        public Cluster cassandraCluster(TestCassandra cassandra) {
            return new ClusterFactory().create(cassandra.getSettings());
        }

    }
}
