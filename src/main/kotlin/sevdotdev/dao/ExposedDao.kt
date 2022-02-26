package sevdotdev.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


abstract class ExposedDao<E, I, Tbl: Table>(protected val database: Database) : IDao<E, I> {
    abstract val table: Tbl
    abstract fun rowToObject(row: ResultRow): E

    fun init() = transaction(database) {
        SchemaUtils.create(table)
    }

    override fun getAll(): List<E> = transaction(database) {
        table.selectAll().map {
            rowToObject(resultRow = it)
        }
    }
}