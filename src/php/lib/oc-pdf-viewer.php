<?php
require_once implode('/', [__DIR__, 'config.php']);

/**
 * pdf viwer
 */
class OcPdfViewer {


    /**
     * pdf viewer
     */
    static $instance = null;

    /**
     * meta box id
     */
    static $meta_id = 'oc-pdf-viewer';


    /**
     * input textbox id
     */
    static $input_query_text_id = 'oc-pdf-viewer-query-text';

    
    /**
     * classes for html element
     */
    static $dialog_classes = ['oc-pdf-viewer'];
    


    /**
     * script handle
     */
    static $script_handle = 'oc-pdf-viewer';

    /**
     * javascript name
     */
    static $js_script_name = 'oc-pdf-viewer.js';

    /**
     * css style sheet name
     */
    static $css_style_name = 'oc-pdf-viewer.css';

    /**
     * activate plugin
     */
    function activate() {
    }

    /**
     * deactivate plugin 
     */
    function deactivate() {
    }
     

    /**
     * get inline script
     */
    function get_ajax_inline_script() {
        $ajax_url = admin_url('admin-ajax.php');
        $result = <<<EOT
window.oc = window.oc || { }
window.oc.pdfViewer = window.oc.pdfViewer || {}
window.oc.pdfViewer.ajax = window.oc.pdfViewer.ajax || { }
window.oc.pdfViewer.ajax.url = '$ajax_url'
EOT;
        return $result;
    }

    /**
     * get support size query
     */
    function get_support_size_query() {
        return Config::$instance->get_media_query()['support-query'];
    }

    

    /**
     * create viewer setting inline script
     */
    function create_viewer_setting_inline_script() {
        $classes = implode('.', self::$dialog_classes);
        $dialog_query = "dialog.$classes";
        $pdf_params = implode(
            '&', [
                'toolbar=1',
                'statusbar=1'
            ]);


        $dialog_close_query = '';

        $dialog_close_modal_query = 
            implode(',', [
                "body"
            ]);

        $support_size_query = $this->get_support_size_query();
        $anchor_query = ".pdf-viewer a";
        $result = <<<EOT
window.oc.pdfViewer = window.oc.pdfViewer || {}
window.oc.pdfViewer.anchorQuery = '$anchor_query';
window.oc.pdfViewer.dialogQuery = '$dialog_query';
window.oc.pdfViewer.dialogCloseQuery = '$dialog_close_query';
window.oc.pdfViewer.dialogCloseModalQuery = '$dialog_close_modal_query';
window.oc.pdfViewer.option = '$pdf_params';
window.oc.pdfViewer.supportQuery = '$support_size_query';
EOT;
        return $result;
    }
    /**
     * setup style 
     */
    function setup_style($css_dir) {
        wp_register_style(self::$script_handle,
           implode('/', [$css_dir, self::$css_style_name])); 
        wp_enqueue_style(self::$script_handle);
    }
 
    /**
     * setup script
     */
    function setup_script($js_dir) {
        wp_register_script(self::$script_handle,
            implode('/', [$js_dir, self::$js_script_name]),
            [], false);

        wp_add_inline_script(
            self::$script_handle,
            $this->get_ajax_inline_script());

        wp_add_inline_script(
            self::$script_handle,
            $this->create_viewer_setting_inline_script());

        wp_enqueue_script(self::$script_handle);

    }

    /**
     * handle the post action
     */
    function handle_the_post($post, &$posts_info) {
        $name = self::$input_query_text_id;
        $query = get_post_meta($post->ID, $name, true);
        $posts_info[$post->ID] = $query;
    }

    /** 
     * handle loop end action
     */
    function handle_loop_end($posts_info) {
         
    }

    /**
     * add meta boxes
     */
    function add_meta_boxes() {
        $screens = ['post', 'page'];
        foreach ($screens as $screen) {
            add_meta_box(self::$meta_id,
                _('Pdf viewer queries'),
                function($post) {
                    $this->render_meta_box($post);
                },
                $screen,
                'side');
        }
    }

    /**
     * render meta box
     */
    function render_meta_box($post) {
        $desc = _('html anchor query to visible pdf viwer');
        $id = self::$input_query_text_id;
        $name = self::$input_query_text_id;
        $value = get_post_meta($post->ID, $name, true);
        $value_attr = '';
        $dbg_str = '';
        if (!empty($value)) {
            $value_attr = "value=\"$value\"";
        }
        $contents =<<<EOT
<div>
<input type="text" name="$name" id="$id" $value_attr>
</div>
<label for="$id">$desc</lable>
EOT;
        echo $contents;
    }

    /**
     * render 
     */
    function render_tag_for_viewer() {
        $classes = implode(' ', self::$dialog_classes);
        include(implode('/', [__DIR__, 'pdf-dialog.php']));
    }

    /**
     * save meta data relating post id
     */
    function save_meta_in_post($post_id) {
        $name = self::$input_query_text_id;
        if (array_key_exists($name, $_POST)) {
            update_post_meta($post_id, $name, $_POST[$name]);
        }
    }

    /**
     * add short code
     */
    function add_shortcode() {
    }


    /**
     * handle init event
     */
    function on_init(
        $js_dir,
        $css_dir) {
        add_action('wp', function() use($js_dir, $css_dir) {
            $this->setup_style($css_dir);
            $this->setup_script($js_dir);

        });
        add_action('wp_footer', function() {
            $this->render_tag_for_viewer();
        });
    }

    /**
     * start plugin
     */
    function run(
        $js_dir,
        $css_dir) {

        add_action('init', function() use($js_dir, $css_dir) {
            $this->on_init($js_dir, $css_dir);
        });
    }
}

OcPdfViewer::$instance = new OcPdfViewer;

// vi: se ts=4 sw=4 et:
