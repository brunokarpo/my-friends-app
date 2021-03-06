package br.ufg.pos.fswmd.myfriendsapp.repository

const val DATABASE_NAME = "friends.db"
const val DATABASE_VERSION_1 = 1
const val DATABASE_VERSION_2 = 2
const val DATABASE_VERSION = 3

const val FRIEND_TABLE_NAME = "friend"
const val KEY_ID = "id"
const val KEY_NAME = "name"
const val KEY_NICKNAME = "nickname"
const val KEY_DESCRIPTION = "description"
const val KEY_TIME_CREATED = "created_date"
const val KEY_PHOTO_URL = "photo_url"

const val QUERY_CREATE_FRIENDS_TABLE = "CREATE TABLE $FRIEND_TABLE_NAME " +
        "($KEY_ID INTEGER PRIMARY KEY, " +
        "$KEY_NAME TEXT NOT NULL, " +
        "$KEY_NICKNAME TEXT, " +
        "$KEY_DESCRIPTION TEXT, " +
        "$KEY_PHOTO_URL TEXT, " +
        "$KEY_TIME_CREATED LONG NOT NULL" +
        ")"

const val QUERY_CREATE_FRIEND_TABLE_NEW = "CREATE TABLE ${FRIEND_TABLE_NAME + "_new"} " +
        "($KEY_ID INTEGER PRIMARY KEY, " +
        "$KEY_NAME TEXT NOT NULL, " +
        "$KEY_NICKNAME TEXT, " +
        "$KEY_DESCRIPTION TEXT, " +
        "$KEY_TIME_CREATED LONG NOT NULL" +
        ")"
const val QUERY_TRANSFER_DATA_BETWEEN_TABLES_FRIEND = "INSERT INTO ${FRIEND_TABLE_NAME + "_new"}($KEY_NAME, $KEY_NICKNAME, $KEY_DESCRIPTION, $KEY_TIME_CREATED) " +
        "SELECT $KEY_NAME, $KEY_NICKNAME, $KEY_DESCRIPTION, $KEY_TIME_CREATED from $FRIEND_TABLE_NAME"

const val QUERY_DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS $FRIEND_TABLE_NAME"
const val QUERY_RENAME_TABLE_FRIEND = "ALTER TABLE ${FRIEND_TABLE_NAME + "_new"} RENAME TO $FRIEND_TABLE_NAME"

const val QUERY_ADD_PHOTO_URL_COLUMN_TABLE_FRIEND = "ALTER TABLE $FRIEND_TABLE_NAME ADD COLUMN $KEY_PHOTO_URL TEXT"