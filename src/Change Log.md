## Aurora Alpha 10c Build: `888`
### Date: `2/13/15` Authors: Sammy Guergachi


####Changes:

- Removed any form of artificial loading after first time loads = Faster Loading!


####Fixed:

- bug that prevented loading cover art from AuroraCoverDB



## Aurora Alpha 10b Build: `885`
### Date: `1/20/15` Authors: Sammy Guergachi


####Changes:

- Removed online check to potentially speed up startup


####Fixed:

- Bug that prevented loading custom cover art
- Bug that prevented from downloading updates to AuroraDB from settings



## Aurora Alpha 10 Build:`882`
### Date: `8/30/2014` Authors: Sammy Guergachi, Carlos Machado

####Additions:

* Basic Gamepad support (Windows only, tested on Xbox 360 wired controller)
* Reworked navigation with keyboard to be more reliable
* Made a lot of UI/UX improvements
* Reworked Aurora Launcher with more accurate timer and simpler UI
* Added a few more settings
* Custom games now have blank cover art overlaid to look like AuroraCoverDB cover art (need to delete games images in "Game Data" folder and re-add via Aurora for this to work on already added custom games)
* Selecting a game now shows game info in library info bar
* Pressing ENTER when searching for game in library launches the first game.
* Show "unsure" icon in auto searches that Aurora had to guess the cover art for
* Right clicking games now flips them to quickly view info about the game

####Changes:

* Library search bar is centered for large screens (yeah, it was supposed to be centered)
* Auto Game Finder now searches through all available drives for games
* Game Cover alternatives added to AuroraCoverDB .
* No more random annoying resets to first grid when adding a game etc.
* No more annoying game being unselected when trying to remove it.
* Made edit cover UI more compact and no longer make windows show desktop
* Improved loading speed to dashboard from welcome screen
* Changed how library grid animation works, hopefully its a bit smoother
* Improved speed and movement of Dashboard Carousel
* Intro animation is now properly centered
* Clicking on link in info feed now shows Aurora Mini
* On Windows, launching more than one instance of Aurora is prevented
* Added PC Gamer, Rock Paper Shotgun and Steam News to RSS feed
* Major code refactoring and clean up
* And a whole bunch of other little things. Yes, details matter.

####Fixed:

- Fixed rss feed, very weird bug that randomly stopped RSS feed parsing
- Fixed unable to delete game from library search view
- Fixed bug preventing playing game from library search view
- Fixed a few other library search related bugs
- Fixed pressing Done button in Edit Cover UI to many times causing issues
- Fixed issue with going offline and back online not being detected
- Fixed issue going in and out of Settings app re-showing info bar status
- Fixed clicking on sort button sometimes not able to dismiss popup
- Fixed a whole lot of other long standing bugs that I forgot to record