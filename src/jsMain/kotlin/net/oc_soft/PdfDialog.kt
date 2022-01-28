package net.oc_soft

import kotlinx.browser.document
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLDialogElement
import org.w3c.dom.HTMLEmbedElement
import org.w3c.dom.HTMLIFrameElement
import org.w3c.dom.get

import org.w3c.dom.url.URL

import org.w3c.dom.events.Event

/**
 * dialog for pdf
 */
class PdfDialog {

    /**
     * dialog to show pdf contents
     */
    val dialogElement: HTMLElement?
        get() {
            return document.querySelector(Site.dialogQuery)?.let {
                if (it is HTMLElement) {
                    it
                } else {
                    null
                }
            }
        }

    /**
     * pdf container element
     */
    val pdfContainerElement0: HTMLEmbedElement? 
        get() {
            return dialogElement?.let {
                it.querySelector("embed")?.let {
                    it as HTMLEmbedElement
                }
            } 
        }
    /**
     * pdf container element
     */
    val pdfContainerElement: HTMLIFrameElement? 
        get() {
            return dialogElement?.let {
                it.querySelector("iframe")?.let {
                    it as HTMLIFrameElement
                }
            } 
        }


    /**
     * pdf url in pdf container element
     */
    var pdfUrl: String?
        get() = pdfContainerElement?.let { it.src }

        set(value) {
            pdfContainerElement?.let { 
                val container = it
                value?.let { 
                    container.src = value
                }
            }
        }

    /**
     * close box element
     */
    val closeElements: Array<HTMLElement>
        get() {
            return dialogElement?.let {
                val elems = document.querySelectorAll(Site.dialogCloseQuery)
                Array<HTMLElement>(elems.length) {
                    elems[it] as HTMLElement
                }
            }?: emptyArray<HTMLElement>()
        }

    /**
     * close box element for modal dialog
     */
    val modalCloseElements: Array<HTMLElement>
        get() {
            return dialogElement?.let {
                val elems = document.querySelectorAll(
                    Site.dialogCloseModalQuery)
                Array<HTMLElement>(elems.length) {
                    elems[it] as HTMLElement
                }
            }?: emptyArray<HTMLElement>()
        }


    /**
     * close event handler
     */
    var closeHdlr: ((Event)->Unit)? = null

    /**
     * bind this object into html elements
     */
    fun bind() {
        val closeHdlr: (Event)->Unit = { 
            handleCloseEvent(it)
        }


        this.closeHdlr = closeHdlr

        dialogElement?.let {
            dialog.registerDialog(it)
        }
    }


    /**
     * unbind this object from html elements
     */
    fun unbind() {
        closeHdlr = null
    }


    /**
     * handle close event
     */
    fun handleCloseEvent(event: Event) {
        dialogElement?.let {
            close(it)
        }
    }

    /**
     * create pdf url
     */
    fun createPdfUrl(url: URL): String {

        return "${url.href}#${Site.pdfOption}"
    }

    /**
     * show dialog
     */
    fun show(url: URL) {
        dialogElement?.let {
            pdfUrl = createPdfUrl(url) 
            showModal(it)
        }
    }

    /**
     * show dialog
     */
    fun show(element: HTMLElement) {
        if (js("element.show !== void 0") as Boolean) {
            attachCloseListener(closeElements)
            js("element.show()")
        }
    }

    /**
     * close dialog
     */
    fun close(element: HTMLElement) {
        if (isOpen(element)) { 
            if (js("element.close !== void 0") as Boolean) {
                js("element.close()")
            }
        }
    }

    /**
     * attach close event listener into dialog
     */
    fun attachCloseListener(closeElements: Array<HTMLElement>) {
        closeElements.forEach {
            it.addEventListener("click", closeHdlr)
        }

        dialogElement?.let {
            it.addEventListener("close", {
                detachCloseListener(closeElements)
            }, object {
                @JsName("once")
                val once = true
            })
        }
    }

    /**
     * detach close event listener from dialog
     */
    fun detachCloseListener(closeElements: Array<HTMLElement>) {
        closeElements.forEach {
            it.removeEventListener("click", closeHdlr)
        }
    }

    /**
     * show modal
     */
    fun showModal(element: HTMLElement) {
        if (js("element.showModal !== void 0") as Boolean) {
            attachCloseListener(modalCloseElements) 
            js("element.showModal()")
        }
    }

    /**
     * you get true if element is open
     */
    fun isOpen(element: HTMLElement): Boolean {
        return if (js("element.open !== void 0") as Boolean) {
            js("element.open") as Boolean
        } else {
            false
        }
    }
}

// vi: se ts=4 sw=4 et:
