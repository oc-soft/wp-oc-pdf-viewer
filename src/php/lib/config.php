<?php
/**
 * manage configuration 
 */
class Config {

    /**
     *  a config object  
     */
    static $instance = null;


    /**
     * get media breaks configuration
     */
    function get_media_breaks() {

        if (!isset($this->media_breaks)) {
            $media_file = implode('/', 
                [dirname(__DIR__), 'config', 'media-breaks.json']); 
            $this->media_breaks = json_decode(
                file_get_contents($media_file), true);
        }
        return $this->media_breaks;
    }

    /**
     *  get support media query
     */
    function get_media_query() {
        if (!isset($this->media_query)) {
            $media_query_file = implode('/',
                [dirname(__DIR__), 'config', 'media-query.json']);
            $this->media_query = json_decode(
                file_get_contents($media_query_file), true);
        }
        return $this->media_query;
    }
}


Config::$instance = new Config;
// vi: se ts=4 sw=4 et:
