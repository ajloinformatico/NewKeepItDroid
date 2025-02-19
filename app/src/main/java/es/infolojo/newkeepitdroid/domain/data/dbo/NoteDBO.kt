package es.infolojo.newkeepitdroid.domain.data.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteDBO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val date: Long // Room do not support Date type
)
