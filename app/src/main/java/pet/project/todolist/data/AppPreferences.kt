package pet.project.todolist.data

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AppPreferences {
    val SERVER_REVISION = intPreferencesKey("actual_revision")
    val DATABASE_REVISION = intPreferencesKey("db_revision")
    val DEVICE_ID = stringPreferencesKey("device_id")
}
