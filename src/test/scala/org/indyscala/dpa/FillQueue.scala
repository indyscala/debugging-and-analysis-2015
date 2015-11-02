package org.indyscala.dpa

import java.util.Random

import scala.collection.mutable.Queue

class FillQueue extends UnitSpec {

  "put 1M integers in a queue" should "use some memory" in {
    // wait for socket connection before proceeding; timeout after 120s
    waitForConnection(120)

    val rand = new Random()
    val queue: Queue[Int] = new Queue()
    for (i <- 0 to 1000000) {
      queue += rand.nextInt(10000)
    }

    waitForConnection(120)
    queue.clear()

    // can check retention here if desired
    waitForConnection(120)
  }
}
