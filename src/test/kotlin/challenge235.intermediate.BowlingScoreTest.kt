
import challenge235.intermediate.Frame
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BowlingTest {

  @Test fun createFrame() {
    assertEquals(2, Frame(arrayOf(9, 1)).rolls.size)

    assertFailsWith(IllegalArgumentException::class) {
      Frame(arrayOf(10, 2))
    }

    assertFailsWith(IllegalArgumentException::class) {
      Frame(arrayOf(1, 2, 3, 4))
    }
  }
}