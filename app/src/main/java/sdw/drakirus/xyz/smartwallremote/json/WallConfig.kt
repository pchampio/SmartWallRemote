package sdw.drakirus.xyz.smartwallremote.json

import android.graphics.Bitmap
import android.widget.CheckBox

data class WallConfig(
	val wall: List<WallItem>
)

data class WallItem(
		val name: String,
		val screen: List<Screen>,
		val rows: Int,
		val cols: Int,
		val scenario: List<Scenario>){
	fun getCheckBoxAt(col:Int, row:Int): Screen? =
			screen.filter { it.col == col && it.row == row}.getOrNull(0)
}

data class Screen(
		val row: Int,
		val col: Int,

		@Transient
		var checkBox: CheckBox? = null
)

data class Layout(
		val width: Int,
		val height: Int,
		val grpScreen: List<GrpScreen>,
		val name: String,

		@Transient
		var bitmap: Bitmap? = null
)

data class GrpScreen(
        val listScreen: List<Screen>,
        val color: Int

)

data class Scenario(
		val name: String,
		val video: List<Video>
)

data class Video(
		val volume: Int,
		val file: String,
		val screens: List<Screen>,
		val loop: Int,
		val distributed: Int,
		val idv: String,
		val mute: Int,
		val departure: String,
		val state: String
)
