package br.ufg.pos.fswmd.myfriendsapp.repository.creations

import android.database.sqlite.SQLiteDatabase
import br.ufg.pos.fswmd.myfriendsapp.repository.*

class Version1(
        private val db: SQLiteDatabase
    ): AbstractCreateVersionChain() {

    private val QUERY_CREATE_FRIENDS_TABLE = "CREATE TABLE $FRIEND_TABLE_NAME " +
            "($KEY_ID LONG PRIMARY KEY, " +
            "$KEY_NAME TEXT NOT NULL, " +
            "$KEY_NICKNAME TEXT, " +
            "$KEY_DESCRIPTION TEXT, " +
            "$KEY_TIME_CREATED LONG NOT NULL" +
            ")"

    override fun nextVersion(): CreateVersion? {
        return Version2(db)
    }

    override fun exec() {
        db.execSQL(QUERY_CREATE_FRIENDS_TABLE)
    }
}