package net.oc_soft

import kotlinx.browser.window
import org.w3c.dom.url.URL
import org.w3c.dom.get

/**
 * manage site related information
 */
class Site {

    /**
     * class instance
     */
    companion object {

        /**
         * request url
         */
        val requestUrl: URL get() =
            URL(window["oc"].pdfViewer.ajax.url as String)
            
        /**
         * html target query
         */
        val anchorQuery: String get() = 
            window["oc"].pdfViewer.anchorQuery as String


        /**
         * dialog box query
         */
        val dialogQuery: String get() =
            window["oc"].pdfViewer.dialogQuery as String

        /**
         * the query to get element to close dialog box 
         */
        val dialogCloseQuery: String get() =
            window["oc"].pdfViewer.dialogCloseQuery as String
        /**
         * the query to get element to close dialog box 
         */
        val dialogCloseModalQuery: String get() =
            window["oc"].pdfViewer.dialogCloseModalQuery as String

        /**
         * option for pdf viewer
         */
        val pdfOption: String get() =
            window["oc"].pdfViewer.option as String


        /**
         * get media query to show pdf with modal dailoag
         */
        val supportSizeQuery: String get() =
            window["oc"].pdfViewer.supportQuery as String
    }
}

// vi: se ts=4 sw=4 et:
