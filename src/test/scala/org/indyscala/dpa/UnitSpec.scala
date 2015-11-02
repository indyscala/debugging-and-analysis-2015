package org.indyscala.dpa

import org.scalatest.{FlatSpec, Matchers}

abstract class UnitSpec extends FlatSpec with Matchers {
  val PausePort = 9900

  /**
   * Pause until connection opened on <code>localhost:PAUSE_PORT</code>
   * or until <code>timeout</code> seconds has elapsed.
   * @param timeout seconds to wait for input before returning
   * @return true if input received and false otherwise
   */
  def waitForConnection(timeout: Int): Boolean = {
    import java.net._

    val server = new ServerSocket(PausePort)
    server.setSoTimeout(timeout * 1000)
    alert(s"Waiting $timeout seconds on port $PausePort...")
    try {
      val socket = server.accept()
      socket.close()
      true
    } catch {
      case e: SocketTimeoutException => {
        alert(s"Timeout after $timeout seconds.")
        false
      }
    } finally {
      server.close()
    }
  }
}
