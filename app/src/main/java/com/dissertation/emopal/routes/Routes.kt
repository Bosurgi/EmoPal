package com.dissertation.emopal.routes

/**
 * This enum class is used to define the routes of the application.
 * These are used to navigate between different screens.
 * @property [HOME] Route name to the Home Page
 * @property [HELP] Route name to the Help Section Page
 * @property [PLAY] Route Name to direct user to Game Selection Page
 * @property [DIARY] Route Name to direct user to Diary Sandbox Page
 */
enum class Routes() {
    /**
     * The home page route to display main page.
     */
    HOME,

    /**
     * The help page route to display help page.
     */
    HELP,

    /**
     * The play page route to display play game selection page.
     */
    PLAY,

    /**
     * The diary page route to display diary sandbox page.
     */
    DIARY,
}