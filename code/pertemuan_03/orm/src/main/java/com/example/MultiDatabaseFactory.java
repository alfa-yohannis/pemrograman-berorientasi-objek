package com.example;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiDatabaseFactory {

    private static final Logger logger = LoggerFactory.getLogger(MultiDatabaseFactory.class);

    
    private static final SessionFactory sqliteSessionFactory;
    private static final SessionFactory postgresSessionFactory;
    private static final SessionFactory mysqlSessionFactory;

    static {
        try {
            // Determine the configuration file locations using system properties,
            // or use the default file names if the properties are not provided.
            String sqliteConfigFile = System.getProperty("hibernate.sqlite.config", "hibernate-sqlite.cfg.xml");
            String postgresConfigFile = System.getProperty("hibernate.postgresql.config",
                    "hibernate-postgresql.cfg.xml");
            String mysqlConfigFile = System.getProperty("hibernate.mysql.config", "hibernate-mysql.cfg.xml");

            System.out.println("Using SQLite config: " + sqliteConfigFile);
            System.out.println("Using PostgreSQL config: " + postgresConfigFile);
            System.out.println("Using MySQL config: " + mysqlConfigFile);

            postgresSessionFactory = new Configuration()
                    .configure(postgresConfigFile)
                    .buildSessionFactory();

            mysqlSessionFactory = new Configuration()
                    .configure(mysqlConfigFile)
                    .buildSessionFactory();

            sqliteSessionFactory = new Configuration()
                    .configure(sqliteConfigFile)
                    .buildSessionFactory();
            
            logger.info("End of database configuration");

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSqliteSessionFactory() {
        return sqliteSessionFactory;
    }

    public static SessionFactory getPostgresSessionFactory() {
        return postgresSessionFactory;
    }

    public static SessionFactory getMysqlSessionFactory() {
        return mysqlSessionFactory;
    }
}
