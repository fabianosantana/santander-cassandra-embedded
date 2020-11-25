package com.santander.cassandra.starter;

import com.github.nosan.embedded.cassandra.Cassandra;
import com.github.nosan.embedded.cassandra.Settings;
import com.github.nosan.embedded.cassandra.local.LocalCassandraFactory;

public class CassandraEmbeddedStarter {

    public static void main(String[] args) {
        LocalCassandraFactory cassandraFactory = new LocalCassandraFactory();
        Cassandra cassandra = cassandraFactory.create();
        cassandra.start();
        try {
            Settings settings = cassandra.getSettings();
            //
        }
        finally {
            cassandra.stop();
        }
    }
}
