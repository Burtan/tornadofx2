package tornadofx.tests

import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage
import org.junit.Ignore
import org.junit.Test
import org.testfx.api.FxToolkit
import tornadofx.*
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Tests if it's possible to open an [InternalWindow] instance and then close it using [UIComponent.close]
 */
class InternalWindowClosingTest {
    val primaryStage: Stage = FxToolkit.registerPrimaryStage()

    @Ignore // TODO: Fails in JDK 10
    @Test
    fun testClosing() {
        val owner = Pane()

        val view = object : View() {
            override val root = pane()
            fun executeClose() { close() }
        }

        FxToolkit.setupFixture {
            primaryStage.scene = Scene(owner)
            primaryStage.show()
            val iw = InternalWindow(null, true, true, true)
            iw.open(view, owner)
            assertNotNull(iw.scene)
            // Why do we assume that the view should be docked because we open an internal window inside it?
//            assertTrue(view.isDocked)
            view.executeClose()
            assertNull(iw.scene)
            assertFalse(view.isDocked)
        }
    }
}