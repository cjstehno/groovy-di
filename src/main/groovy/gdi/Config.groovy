package gdi

import com.stehno.vanilla.text.CommaSeparatedLineParser
import com.stehno.vanilla.text.TextFileReader
import org.h2.jdbcx.JdbcConnectionPool

import javax.sql.DataSource
import java.nio.file.Path
import java.nio.file.Paths

@SuppressWarnings('GroovyAssignabilityCheck')
class Config {

    private final ConfigObject config

    Config(final URL configLocation) {
        config = new ConfigSlurper().parse(configLocation)
    }

    static Config fromClasspath(String path) {
        new Config(Config.getResource(path))
    }

    @OneInstance DataSource dataSource() {
        JdbcConnectionPool.create(
            config.datasource.url,
            config.datasource.user,
            config.datasource.pass
        )
    }

    @OneInstance Path inputFilePath() {
        if (config.inputFile.startsWith('classpath:')) {
            return Paths.get(Config.getResource(config.inputFile - 'classpath:').toURI())
        } else {
            return new File(config.inputFile).toPath()
        }
    }

    @OneInstance TextFileReader fileReader() {
        new TextFileReader(
            filePath: inputFilePath(),
            firstLine: 2,
            lineParser: new CommaSeparatedLineParser(
                (0): { v -> v as long },
                (2): { v -> v as int }
            )
        )
    }

    @OneInstance StatsService statsService() {
        new StatsService(dataSource()).init()
    }
}
