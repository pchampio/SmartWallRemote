package sdw.drakirus.xyz.smartwallremote.json

import android.widget.CheckBox
import com.google.gson.annotations.SerializedName

data class WallConfig(
	@field:SerializedName("user_profil")
	val userProfil: UserProfil,
	val wall: List<WallItem>
)

data class WallItem(
		val name: String,
		val screen: List<ScreenItem>,
		val rows: Int,
		val cols: Int
){
    fun getCheckBoxAt(col:Int, row:Int): ScreenItem? =
            screen.filter { it.col == col && it.row == row}.getOrNull(0)
}

data class UserProfil(
		val workspace: String,
		val scenario: List<Scenario>,
		val name: String
)

data class ScreenItem(
        val orientation: String,
        val ipv4: String,
        val id: String,
        val row: Int,
        val type: String,
        @field:SerializedName("cols")
        val col: Int,

        @Transient
        var checkBox: CheckBox? = null
)

