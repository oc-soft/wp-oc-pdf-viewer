<?php

/**
 * Plugin Name: OC Pdf Viewer
 */
require_once implode('/', [
    plugin_dir_path( __FILE__), 'lib', 'oc-pdf-viewer.php']);
/**
 * activate plugin
 */
function oc_pdf_viewer_activate() {

    OcPdfViewer::$instance->activate();
}

/**
 * deactivate plugin
 */
function oc_pdf_viewer_deactivate() {
    OcPdfViewer::$instance->deactivate();
}


register_activation_hook(__FILE__, 'oc_pdf_viewer_activate');
register_deactivation_hook(__FILE__, 'oc_pdf_viewer_deactivate');
OcPdfViewer::$instance->run(
    implode('/', [plugin_dir_url(__FILE__), 'js']),
    implode('/', [plugin_dir_url(__FILE__), 'css']));


// vi: se ts=4 sw=4 et:
