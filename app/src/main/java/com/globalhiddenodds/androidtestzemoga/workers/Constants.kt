@file:JvmName("Constants")
package com.globalhiddenodds.androidtestzemoga.workers

@JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"
@JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1
const val KEY_LIST = "KEY_LIST"
const val KEY_IS_SUCCESS = "IS_SUCCESS"
const val KEY_SIZE_LIST = "KEY_SIZE"
const val DOWNLOAD_POSTS_WORK_NAME = "download_work"
const val TAG_OUTPUT = "OUTPUT"
const val KEY_DB_EXIST = "DB_EXIST"