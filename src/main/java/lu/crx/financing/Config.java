package lu.crx.financing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

@Configuration
public class Config {

    @Bean
    public Database database() {
        DatabaseConfig config = new DatabaseConfig();
        config.loadFromProperties();
        return DatabaseFactory.create(config);
    }

    @Bean
    public Flyway flyway(ResourceLoader resourceLoader, @Value("${datasource.db.url}") String url,
            @Value("${datasource.db.username}") String username, @Value("${datasource.db.password}") String password) {
        FluentConfiguration configuration = new FluentConfiguration(resourceLoader.getClassLoader());
        configuration.dataSource(url, username, password);
        configuration.baselineVersion("0");
        configuration.baselineOnMigrate(true);
        Flyway flyway = new Flyway(configuration);
        flyway.migrate();
        return flyway;
    }

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }
}
