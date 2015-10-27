package challenge235.intermediate

data class Frame(val rolls: Array<Int>) {

  init {
    if (rolls.size > 3) throw IllegalArgumentException("Can't have a frame with more than 3 rolls")
    if (rolls.sum() > 10) throw IllegalArgumentException("Can't knock more than 10 pins in a frame")
  }
}

