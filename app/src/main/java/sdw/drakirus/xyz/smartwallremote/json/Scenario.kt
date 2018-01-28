package sdw.drakirus.xyz.smartwallremote.json

data class Scenario(
	val name: String,
	val video: List<VideoItem>
)

data class VideoItem(
		val volume: Int,
		val file: String,
		val screens: List<String>,
		val loop: Int,
		val distributed: Int,
		val idv: String,
		val mute: Int,
		val departure: String,
		val state: String
)

