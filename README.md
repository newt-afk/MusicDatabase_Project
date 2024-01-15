# MusicDatabase_Project

## TODO
1. implement music using javafx media and mediaplayer
2. allow creation of playlists and albums in ui
3. implement shuffle
4. implement letting others see your playlists, albums, able to download your music
5. allow setting volume
6. implement logging and graceful failing
7. in UI, able to toggle music between linked and non-linked

## maybe?
1. make user able to set music directory, with default directory if not set

## files (not implemented)
* player setting file
* music object file, takes files from directory and attaches information
* bloc object file, links music by ID, holds more information
  * maybe split the file up?

# Contracts
* Linked music is identified by the first track in the link. 
To access a chain of music, you must first identify the first song in the link.

## Potential problems
* I want to assume that all linked music start the same