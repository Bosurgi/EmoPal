package com.dissertation.emopal.routes

/**
 * This enum class is used to define the routes of the application.
 * These are used to navigate between different screens.
 * @property [HOME] Route name to the Home Page
 * @property [HELP] Route name to the Help Section Page
 * @property [PLAY] Route Name to direct user to Game Selection Page
 * @property [DIARY] Route Name to direct user to Diary Sandbox Page
 * @property [NESTED_PLAY] Route Name to direct user to Nested Navigation Route for Play Page
 * @property [LEVEL1] Route Name to direct user to Level 1 Game Page
 * @property [LEVEL2] Route Name to direct user to Level 2 Game Page
 * @property [LEVEL3] Route Name to direct user to Level 3 Game Page
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

    /**
     * The nested play page route to display nested navigation route for play page.
     */
    NESTED_PLAY,

    /**
     * The level 1 game page route to display level 1 game page.
     */

    LEVEL1,

    /**
     * The level 2 game page route to display level 2 game page.
     */

    LEVEL2,

    /**
     * The level 3 game page route to display level 3 game page.
     */

    LEVEL3,

}