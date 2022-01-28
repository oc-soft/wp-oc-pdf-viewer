package net.oc_soft


import kotlinx.browser.window
import kotlinx.browser.document


/**
 * application
 */
class App(
    /**
     * manage pdf resource
     */
    val pdfViewer: PdfViewer = PdfViewer()) {


    /**
     * bind this into html elements
     */
    fun bind() {
        
        pdfViewer.bind()
    }

    /**
     * unbind this from html elements
     */
    fun unbind() {
        pdfViewer.unbind()
    }

    /**
     * run application
     */
    fun run() {
        window.addEventListener("load",
            { this.bind() },
            object {
                @JsName("once")
                val once = true
            })
        window.addEventListener("unload",
            { this.unbind() },
            object {
                @JsName("once")
                val once = true
            })
        
    }
}

// vi: se ts=4 sw=4 et:
