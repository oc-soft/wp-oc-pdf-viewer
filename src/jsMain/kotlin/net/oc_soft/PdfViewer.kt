package net.oc_soft

import kotlinx.browser.document
import kotlinx.browser.window

import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.MediaQueryList
import org.w3c.dom.get
import org.w3c.dom.events.Event
import org.w3c.dom.url.URL

class PdfViewer(
    /**
     * dialog to show pdf file
     */
    val dialog: PdfDialog = PdfDialog()) {

    /**
     * get pdf element
     */
    val pdfElements: Array<HTMLElement>
        get() {
            val elems = document.querySelectorAll(Site.anchorQuery) 
            return Array<HTMLElement>(elems.length) {
                elems[it] as HTMLElement
            }
        }

    /**
     * you can use this instance to determine whether show dialog or not
     */
    var supportMediaQuery: MediaQueryList? = null



    /**
     * click handler
     */
    var clickHdlr: ((Event)->Unit)? = null

    /**
     * bind this object into html elements
     */
    fun bind() {
        val clickHdlr: (Event)->Unit = { handleClickEvent(it) }

        pdfElements.forEach {
            it.addEventListener("click", clickHdlr)
        }
        supportMediaQuery = window.matchMedia(Site.supportSizeQuery) 
        this.clickHdlr = clickHdlr
        dialog.bind()
    }



    /**
     * unbind this object from html elements
     */
    fun unbind() {
        dialog.unbind()
        supportMediaQuery = null
        clickHdlr?.let {
            val hdlr = it
            pdfElements.forEach {
                it.removeEventListener("click", clickHdlr)
            }
            clickHdlr = null
        }
    }


    /**
     * handle click event
     */
    fun handleClickEvent(event: Event) {
        val target = event.target
        if (target is HTMLAnchorElement) {
            handleClickEvent(event, target)
        } 
    }


    /**
     * handle click event
     */
    fun handleClickEvent(event: Event, anchor: HTMLAnchorElement) {

        supportMediaQuery?.let { 
            if (it.matches) {
                event.preventDefault()
                event.stopPropagation()
                val url = URL(anchor.href)
                visibleDialog(url)
            }
        }
    } 

    /**
     * visible dialog
     */
    fun visibleDialog(url: URL) {
        dialog.show(url) 
    }

}

// vi: se ts=4 sw=4 et:
