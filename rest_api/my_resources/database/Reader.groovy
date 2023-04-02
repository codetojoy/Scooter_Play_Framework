
@Grapes(
    @Grab(group='org.postgresql', module='postgresql', version='42.6.0')
)
@GrabConfig(systemClassLoader=true)

import groovy.sql.*
import org.postgresql.*

def sql = Sql.newInstance("jdbc:postgresql://127.0.0.1:5432/scooter", "postgres","swordfish", "org.postgresql.Driver")

sql.eachRow("SELECT id, name FROM attendee") { row ->
    println "id: ${row.id} name: ${row.name}"
}

sql.close()

