package ui.views

import javafx.application.Platform
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.input.KeyEvent
import javafx.scene.layout.StackPane
import tornadofx.*
import utils.formatJson
import utils.highlight

class LogPane : View("Log") {
    override val root: StackPane by fxml("/views/LogPane.fxml")

    private val searchField: TextField by fxid()
    val messageList: ListView<HighlightMessage> by fxid()

    init {
        messageList.setCellFactory { LogItem() }
    }

    fun handleKeyPress(keyEvent: KeyEvent) {
        val findMac = KeyCodeCombination(KeyCode.F, KeyCombination.META_DOWN)
        if (findMac.match(keyEvent)) {
            searchField.isVisible = !searchField.isVisible
            searchField.requestFocus()
        } else if (keyEvent.code == KeyCode.ESCAPE) {
            searchField.isVisible = false
        }
        search()
    }

    fun search() {
        messageList.items.forEach {
            if (searchField.isVisible) {
                it.searchTerm = searchField.text
            } else {
                it.searchTerm = ""
            }
        }
        Platform.runLater { messageList.refresh() }
    }
}

class LogItem : ListCell<HighlightMessage>() {
    override fun updateItem(item: HighlightMessage?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty || item == null) {
            text = null
            graphic = null
            return
        }

        graphic = highlight(item.message, item.searchTerm, true)
    }
}

data class HighlightMessage(val message: String, var searchTerm: String = "")