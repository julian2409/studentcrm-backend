package nt.jsa.testlifecyclemanager

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.PostgreSQLContainer

class DatabaseTestLifeCycleManager : QuarkusTestResourceLifecycleManager {
    private lateinit var container: PostgreSQLContainer<*>

    override fun start(): MutableMap<String, String> {
        container = PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("studentcrm")
            .withUsername("postgres")
            .withPassword("test1234")
            .withStartupAttempts(3)

        container.start()
        return mutableMapOf(
            "quarkus.datasource.jdbc.url" to container.jdbcUrl,
        )
    }

    override fun stop() {
        container.stop()
    }
}
