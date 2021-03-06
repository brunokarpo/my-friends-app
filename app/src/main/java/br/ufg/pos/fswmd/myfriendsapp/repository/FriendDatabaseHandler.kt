package br.ufg.pos.fswmd.myfriendsapp.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.ufg.pos.fswmd.myfriendsapp.model.Friend

class FriendDatabaseHandler(context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
        FriendRepository {

    override fun onCreate(db: SQLiteDatabase?) {
        val createFriendTable = QUERY_CREATE_FRIENDS_TABLE

        db?.execSQL(createFriendTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        migrateVersion2(oldVersion, db)
        migrateVersion3(oldVersion, db)
    }

    private fun migrateVersion3(oldVersion: Int, db: SQLiteDatabase?) {
        if (oldVersion <= DATABASE_VERSION_2) {
            db?.execSQL(QUERY_ADD_PHOTO_URL_COLUMN_TABLE_FRIEND)
        }
    }

    private fun migrateVersion2(oldVersion: Int, db: SQLiteDatabase?) {
        if (oldVersion <= DATABASE_VERSION_1) {
            val createNewTableFriend = QUERY_CREATE_FRIEND_TABLE_NEW
            db?.execSQL(createNewTableFriend)

            val transferDatasBetweenTablesFriend = QUERY_TRANSFER_DATA_BETWEEN_TABLES_FRIEND
            db?.execSQL(transferDatasBetweenTablesFriend)

            val dropTable = QUERY_DROP_FRIEND_TABLE
            db?.execSQL(dropTable)

            val renameTableNew = QUERY_RENAME_TABLE_FRIEND
            db?.execSQL(renameTableNew)
        }
    }

    override fun save(friend: Friend) {
        var db: SQLiteDatabase = writableDatabase

        var values = ContentValues()
        values.put(KEY_NAME, friend.name)
        values.put(KEY_NICKNAME, friend.nickname)
        values.put(KEY_DESCRIPTION, friend.description)
        values.put(KEY_TIME_CREATED, System.currentTimeMillis())
        values.put(KEY_PHOTO_URL, friend.photoUrl)

        db.insert(FRIEND_TABLE_NAME, null, values)

        db.close()
    }

    override fun getAll(): ArrayList<Friend> {
        var db = readableDatabase
        val columns = arrayOf(KEY_ID, KEY_NAME, KEY_NICKNAME, KEY_TIME_CREATED, KEY_PHOTO_URL)
        val selection = "1=1"

        var cursor = db.query(FRIEND_TABLE_NAME, columns, selection,
                null, null, null, null)

        var friends = mutableListOf<Friend>()

        if (cursor.moveToFirst()) {

            do {
                var friend = Friend()
                friend.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                friend.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                friend.nickname = cursor.getString(cursor.getColumnIndex(KEY_NICKNAME))
                friend.timeCreated = cursor.getLong(cursor.getColumnIndex(KEY_TIME_CREATED))
                friend.photoUrl = cursor.getString(cursor.getColumnIndex(KEY_PHOTO_URL))
                friends.add(friend)

            } while( cursor.moveToNext() )

        }

        return ArrayList(friends)
    }

    override fun findById(id: Int): Friend? {
        var db = readableDatabase
        val columns = arrayOf(KEY_ID, KEY_NAME, KEY_NICKNAME, KEY_DESCRIPTION, KEY_PHOTO_URL)
        var selection = "$KEY_ID = ?"

        var cursor = db.query(FRIEND_TABLE_NAME, columns, selection, arrayOf(id.toString()),
                null, null, null)

        var friend: Friend? = null

        if (cursor.moveToFirst()) {
            friend = Friend()
            friend.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            friend.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
            friend.nickname = cursor.getString(cursor.getColumnIndex(KEY_NICKNAME))
            friend.description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
            friend.photoUrl = cursor.getString(cursor.getColumnIndex(KEY_PHOTO_URL))
        }

        return friend
    }

    override fun delete(friend: Friend) {
        var db = writableDatabase

        val whereClause = "$KEY_ID = ?"
        val idArray = arrayOf(friend.id.toString())

        db.delete(FRIEND_TABLE_NAME, whereClause, idArray)
        db.close()
    }
}