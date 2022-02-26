package sevdotdev.dao

import io.ktor.utils.io.core.*
import sevdotdev.model.Stats

/**
 * A contract that all DAO will implement
 * @param Entity Object representing the columns of the table to access
 * @param Identifier way to uniquely identify each row in the table
 */
interface IDao<Entity, Identifier> : Closeable {
    fun create(entity: Entity, id: Identifier? = null)
    fun delete(id: Identifier)
    fun get(id: Identifier): Entity?
    fun getAll(): List<Entity>
}