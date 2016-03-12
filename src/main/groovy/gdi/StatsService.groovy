package gdi

import groovy.sql.Sql
import groovy.transform.TypeChecked

import javax.sql.DataSource

@TypeChecked
class StatsService {

    private Sql sql

    StatsService(DataSource dataSource) {
        sql = new Sql(dataSource)
    }

    StatsService init() {
        sql.execute('create table scores (id bigint PRIMARY KEY, username VARCHAR(20) NOT NULL, score int NOT NULL )')
        this
    }

    void input(long id, String username, int score) {
        sql.executeUpdate(
            'insert into scores (id,username,score) values (?,?,?)',
            id,
            username,
            score
        )
    }

    void report() {
        def row = sql.firstRow(
            '''
            select
                count(*) as score_count,
                avg(score) as average_score,
                min(score) as min_score,
                max(score) as max_score
            from scores
            '''
        )

        println "Count  : ${row.score_count}"
        println "Min    : ${row.min_score}"
        println "Max    : ${row.max_score}"
        println "Average: ${row.average_score}"
    }
}