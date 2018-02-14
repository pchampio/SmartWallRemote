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
		val cols: Int,
		val rows: Int,
		val grpScreen: List<GrpScreen>,
		val name: String,

		@Transient
		var bitmap: Bitmap? = null
)

data class GrpScreen(
        val listScreen: List<Screen>,
        val color: Int
){
    val NOPOS = "NOPOS"

    fun getPosition(screen: Screen): String {

        if (listScreen.find { it == screen } == null){
            return NOPOS
        }

        val hasOneLeft: Boolean = listScreen.find { it.col-1 == screen.col } != null
        val hasOneRight: Boolean = listScreen.find { it.col+1 == screen.col } != null

        val hasOneTop: Boolean = listScreen.find { it.row+1 == screen.row } != null
        val hasOneBottom: Boolean = listScreen.find { it.row-1 == screen.row } != null

        return when (listOf(hasOneLeft, hasOneRight, hasOneTop, hasOneBottom)) {
            listOf(true, false, false, false) -> "right-only"
            listOf(false, true, false, false) -> "left-only"

            listOf(false, false, true, false) -> "bottom-only"
            listOf(false, false, false, true) -> "top-only"

            listOf(true, true, false, false) -> "horizontal"
            listOf(false, false,true, true) -> "vertical"

            listOf(true, true, true, true) -> "all"
            listOf(false, true, true, true) -> "right"
            listOf(true, false, true, true) -> "left"
            listOf(true, true, false, true) -> "top"
            listOf(true, true, true, false) -> "bottom"
            listOf(true, false, true, false) -> "bottom-left"
            listOf(false, true, false, true) -> "top-right"

            listOf(true, false, false, true) -> "top-left"
            listOf(false, true, true, false) -> "bottom-right"
            else -> NOPOS
        }
    }
}

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
