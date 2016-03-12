package gdi

import com.stehno.vanilla.text.TextFileReader

/**
 * Simple data loader application to demo the DI code.
 */
class Application {

    static void main(args){
        Config config = Config.fromClasspath('/application.cfg')

        StatsService stats = config.statsService()
        TextFileReader reader = config.fileReader()

        reader.eachLine { Object[] line->
            stats.input(line[0], line[1], line[2])
        }

        stats.report()
    }
}
