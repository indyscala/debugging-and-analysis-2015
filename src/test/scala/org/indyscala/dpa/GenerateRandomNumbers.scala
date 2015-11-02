package org.indyscala.dpa

import java.security.SecureRandom
import java.util.Random

class GenerateRandomNumbers extends UnitSpec {

  "generate random numbers" should "continue for 10 seconds" in {
    // wait for socket connection before proceeding; timeout after 20s
    waitForConnection(20)

    // val r: Random = new Random()
    val r: Random = SecureRandom.getInstance("SHA1PRNG");
    var (elapsedMs, count, countSmall) = (0L, 0L, 0L)
    val started = System.currentTimeMillis()
    while (elapsedMs < 10000) {
      val i = r.nextInt(100)
      count = count + 1
      if (i < 10) {
        countSmall = countSmall + 1
        if (elapsedMs < 3000) println("small integer")
      }
      elapsedMs = System.currentTimeMillis() - started
    }

    info(f"$countSmall%.4e of $count%.4e samples were less than 10.")
  }
}

