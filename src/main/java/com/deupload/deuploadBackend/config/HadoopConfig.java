package com.deupload.deuploadBackend.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path; // ✅ Correct import
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URI;

@org.springframework.context.annotation.Configuration
public class HadoopConfig {
    private static final Logger logger = LoggerFactory.getLogger(HadoopConfig.class);

    @Bean
    public FileSystem fileSystem() throws IOException {
        System.setProperty("HADOOP_USER_NAME", "hdfs"); // ✅ Explicitly set user
        System.setProperty("hadoop.home.dir", "C:\\hadoop");

        logger.info("Loading Hadoop configuration...");

        Configuration conf = new Configuration();
        conf.addResource(new Path("C:/hadoop/etc/hadoop/core-site.xml")); // ✅ Fixed Path import
        conf.addResource(new Path("C:/hadoop/etc/hadoop/hdfs-site.xml")); // ✅ Fixed Path import
        conf.set("fs.defaultFS", "hdfs://localhost:9000");

        logger.info("Setting UserGroupInformation...");
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("hdfs");
        UserGroupInformation.setLoginUser(ugi);

        logger.info("Connecting to Hadoop FileSystem...");
        FileSystem fs = FileSystem.get(URI.create("hdfs://localhost:9000"), conf);

        logger.info("Hadoop FileSystem initialized successfully!");
        return fs;
    }
}
